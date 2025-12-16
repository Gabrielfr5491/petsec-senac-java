package petsec_senac;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainPetShop {

    // --------------------------
    // ENUM para formas de pagamento (rótulos compatíveis com o ENUM do MySQL)
    // --------------------------
    private enum FormaPagamento {
        CREDITO("Crédito"),
        DEBITO("Débito"),
        DINHEIRO("Dinheiro"),
        PIX("PIX");

        private final String label;
        FormaPagamento(String label) { this.label = label; }
        public String getLabel() { return label; }
        @Override
        public String toString() { return label; }
    }

    // Listas principais do sistema
    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Servico> servicos = new ArrayList<>();
    private static List<Produto> racoes = new ArrayList<>();
    private static List<Funcionario> funcionarios = new ArrayList<>();
    private static List<Agendamento> agendamentos = new ArrayList<>();
    private static List<String> recibos = new ArrayList<>();

    // Ferramentas globais
    private static Scanner entrada = new Scanner(System.in);
    private static DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // Horários fixos disponíveis
    private static final String[] HORARIOS_DISPONIVEIS = {
        "09:00", "10:00", "11:00",
        "14:00", "15:00", "16:00" 	
    };

    // -----------------------------------------------------------
    // CONTROLE DE LOGIN E "CRIPTOGRAFIA" DAS SENHAS
    // -----------------------------------------------------------
    private static final String ADMIN_LOGIN = "admin";
    private static final String ADMIN_SENHA_HASH;
    private static final String FUNC_SENHA_HASH;

    // Armazena quem está logado
    private static boolean usuarioEhAdmin = false;
    private static String usuarioFuncao = null;

    // -----------------------------------------------------------
    // CONTADORES E TOTAIS DO DIA
    // -----------------------------------------------------------
    private static int clientesAtendidos = 0;
    private static double totalVendasServicos = 0.0;
    private static double totalVendasRacoes = 0.0;
    private static double totalVendasEntrega = 0.0;

    static {
        // Gera os hashes das senhas padrão
        ADMIN_SENHA_HASH = gerarHash("1234");    // senha do admin
        FUNC_SENHA_HASH  = gerarHash("petshop"); // senha dos funcionários
    }

    // Gera hash SHA-256 da senha
    private static String gerarHash(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(senha.getBytes());
            StringBuilder sb = new StringBuilder();

            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash da senha", e);
        }
    }

    // Compara senha digitada com hash armazenado
    private static boolean validarSenha(String senhaDigitada, String hashEsperado) {
        String hashDigitada = gerarHash(senhaDigitada);
        return hashDigitada.equals(hashEsperado);
    }

    // Tela inicial de login
    private static boolean realizarLoginInicial() {
        System.out.println("====================================");
        System.out.println("      SISTEMA PET SHOP - LOGIN      ");
        System.out.println("====================================");

        int tentativas = 0;
        boolean autenticado = false;

        while (tentativas < 3 && !autenticado) {
            System.out.println("\nEscolha o tipo de login:");
            System.out.println("1 - Administrador");
            System.out.println("2 - Funcionário");
            System.out.println("0 - Sair");
            int opc = lerInteiro("Opção: ");

            if (opc == 0) {
                System.out.println("Saindo do sistema...");
                return false;
            }

            switch (opc) {
                case 1 -> {
                    // Login ADMIN
                    System.out.print("Login do administrador: ");
                    String login = entrada.nextLine();
                    System.out.print("Senha: ");
                    String senha = entrada.nextLine();

                    if (login.equals(ADMIN_LOGIN) && validarSenha(senha, ADMIN_SENHA_HASH)) {
                        System.out.println("Login de ADMINISTRADOR realizado com sucesso!");
                        usuarioEhAdmin = true;
                        usuarioFuncao = "Administrador";
                        autenticado = true;
                    } else {
                        System.out.println("Login ou senha de administrador incorretos!");
                        tentativas++;
                    }
                }

                case 2 -> {
                    // Login FUNCIONÁRIO
                    System.out.print("Função para login (ex.: Atendente, Tosador, Banhista...): ");
                    String funcaoLogin = entrada.nextLine();

                    System.out.print("Senha do funcionário: ");
                    String senha = entrada.nextLine();

                    if (validarSenha(senha, FUNC_SENHA_HASH)) {
                        System.out.println("Login de FUNCIONÁRIO realizado com sucesso!");
                        usuarioEhAdmin = false;
                        usuarioFuncao = funcaoLogin;
                        autenticado = true;
                    } else {
                        System.out.println("Senha de funcionário incorreta!");
                        tentativas++;
                    }
                }

                default -> {
                    System.out.println("Opção inválida!");
                    tentativas++;
                }
            }
        }

        if (!autenticado) {
            System.out.println("\nNúmero máximo de tentativas excedido. Acesso negado.");
        }

        return autenticado;
    }

    // -----------------------------------------------------------
    // Utilitário para ler inteiro com validação
    // -----------------------------------------------------------
    public static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            if (entrada.hasNextInt()) {
                int valor = entrada.nextInt();
                entrada.nextLine(); // consome quebra de linha
                return valor;
            } else {
                System.out.println("Valor inválido! Digite um número inteiro.");
                entrada.nextLine(); // limpa lixo do buffer
            }
        }
    }

    // -----------------------------------------------------------
    // Menu principal (texto atualizado da opção 0)
    // -----------------------------------------------------------
    public static void mostrarMenu() {
        System.out.println("\n===== MENU PRINCIPAL =====");
        System.out.println("Usuário logado: " + (usuarioEhAdmin ? "ADMINISTRADOR" : "FUNCIONÁRIO (" + usuarioFuncao + ")"));
        System.out.println("1. Menu Cliente");
        System.out.println("2. Menu Serviços");
        System.out.println("3. Menu Rações");
        System.out.println("4. Menu Funcionários");
        System.out.println("5. Iniciar Atendimento");
        System.out.println("6. Ver Horários Disponíveis");
        System.out.println("7. Listar Agendamentos");
        System.out.println("8. Logout / Trocar usuário");
        System.out.println("0. Sair do sistema");
        System.out.println("==========================");
    }

    // -----------------------------------------------------------
    // SUBMENUS (com restrições para funcionário)
    // -----------------------------------------------------------
    private static void menuCliente() {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n===== MENU CLIENTE =====");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Atualizar Cliente");
            System.out.println("4. Remover Cliente");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.println("========================");

            int opc = lerInteiro("Escolha uma opção: ");
            switch (opc) {
                case 1 -> {
                    // FUNCIONÁRIO TAMBÉM PODE CADASTRAR CLIENTES
                    cadastrarCliente();
                }
                case 2 -> listarClientes();      // liberado para todos
                case 3 -> atualizarCliente();    // liberado para todos
                case 4 -> {
                    // REMOÇÃO SOMENTE ADMIN
                    if (!usuarioEhAdmin) {
                        System.out.println("Apenas o ADMINISTRADOR pode remover clientes.");
                    } else {
                        removerCliente();
                    }
                }
                case 0 -> voltar = true;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void menuServicos() {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n===== MENU SERVIÇOS =====");
            System.out.println("1. Cadastrar Serviço");
            System.out.println("2. Listar Serviços");
            System.out.println("3. Atualizar Serviço");
            System.out.println("4. Remover Serviço");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.println("=========================");

            int opc = lerInteiro("Escolha uma opção: ");
            switch (opc) {
                case 1 -> {
                    if (!usuarioEhAdmin) {
                        System.out.println("Apenas o ADMINISTRADOR pode cadastrar serviços.");
                    } else {
                        cadastrarServico();
                    }
                }
                case 2 -> listarServicos();      // liberado para todos
                case 3 -> atualizarServico();    // liberado para todos
                case 4 -> {
                    if (!usuarioEhAdmin) {
                        System.out.println("Apenas o ADMINISTRADOR pode remover serviços.");
                    } else {
                        removerServico();
                    }
                }
                case 0 -> voltar = true;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void menuRacoes() {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n===== MENU RAÇÕES =====");
            System.out.println("1. Cadastrar Ração");
            System.out.println("2. Listar Rações");
            System.out.println("3. Atualizar Ração");
            System.out.println("4. Remover Ração");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.println("=======================");

            int opc = lerInteiro("Escolha uma opção: ");
            switch (opc) {
                case 1 -> {
                    if (!usuarioEhAdmin) {
                        System.out.println("Apenas o ADMINISTRADOR pode cadastrar rações.");
                    } else {
                        cadastrarRacao();
                    }
                }
                case 2 -> listarRacoes();        // liberado para todos
                case 3 -> atualizarRacao();      // liberado para todos
                case 4 -> {
                    if (!usuarioEhAdmin) {
                        System.out.println("Apenas o ADMINISTRADOR pode remover rações.");
                    } else {
                        removerRacao();
                    }
                }
                case 0 -> voltar = true;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void menuFuncionarios() {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n===== MENU FUNCIONÁRIOS =====");
            System.out.println("1. Cadastrar Funcionário");
            System.out.println("2. Listar Funcionários");
            System.out.println("3. Atualizar Funcionário");
            System.out.println("4. Remover Funcionário");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.println("=============================");

            int opc = lerInteiro("Escolha uma opção: ");
            switch (opc) {
                case 1 -> {
                    if (!usuarioEhAdmin) {
                        System.out.println("Apenas o ADMINISTRADOR pode cadastrar funcionários.");
                    } else {
                        cadastrarFuncionario();
                    }
                }
                case 2 -> listarFuncionarios();      // na prática só admin entra aqui mesmo
                case 3 -> atualizarFuncionario();    // liberado
                case 4 -> {
                    if (!usuarioEhAdmin) {
                        System.out.println("Apenas o ADMINISTRADOR pode remover funcionários.");
                    } else {
                        removerFuncionario();
                    }
                }
                case 0 -> voltar = true;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    // -----------------------------------------------------------
    // Inicializações padrão
    // -----------------------------------------------------------
    private static void inicializarServicosPadrao() {
        if (servicos.isEmpty()) {
            servicos.add(new Servico("Banho", 40.00));
            servicos.add(new Servico("Tosa", 60.00));
            servicos.add(new Servico("Tosa Higiênica", 35.00));
            servicos.add(new Servico("Hidratação", 50.00));
        }
    }

    private static void inicializarRacoesPadrao() {
        if (racoes.isEmpty()) {
            Produto p1 = new Produto();
            p1.setNomeProduto("Ração Comum");
            p1.setDescricao("Ração padrão para cães adultos.");
            p1.setPreco(20.00);

            Produto p2 = new Produto();
            p2.setNomeProduto("Ração Premium");
            p2.setDescricao("Ração premium com mais nutrientes.");
            p2.setPreco(35.00);

            Produto p3 = new Produto();
            p3.setNomeProduto("Ração Super Premium");
            p3.setDescricao("Alto valor nutricional e digestibilidade.");
            p3.setPreco(55.00);

            Produto p4 = new Produto();
            p4.setNomeProduto("Ração Filhote");
            p4.setDescricao("Especial para cães filhotes.");
            p4.setPreco(25.00);

            Produto p5 = new Produto();
            p5.setNomeProduto("Ração Sênior");
            p5.setDescricao("Ração para cães idosos.");
            p5.setPreco(28.00);

            racoes.add(p1);
            racoes.add(p2);
            racoes.add(p3);
            racoes.add(p4);
            racoes.add(p5);
        }
    }

    private static void inicializarFuncionariosPadrao() {
        if (funcionarios.isEmpty()) {
            // 2 atendentes (um deles administrador do sistema, por exemplo)
            funcionarios.add(new Funcionario("Ana Souza", "Atendente", true));  // admin
            funcionarios.add(new Funcionario("Carlos Lima", "Atendente", false));

            // 2 tosadores
            funcionarios.add(new Funcionario("Bruno Pereira", "Tosador", false));
            funcionarios.add(new Funcionario("Mariana Alves", "Tosador", false));

            // 2 banhistas
            funcionarios.add(new Funcionario("Rita Souza", "Banhista", false));
            funcionarios.add(new Funcionario("Diego Rocha", "Banhista", false));

            // 2 entregadores
            funcionarios.add(new Funcionario("João Ferreira", "Entregador", false));
            funcionarios.add(new Funcionario("Paula Costa", "Entregador", false));
        }
    }

    // -----------------------------------------------------------
    // CRUD CLIENTE (com opção de cancelar)
    // -----------------------------------------------------------
    private static void cadastrarCliente() {
        System.out.println("\n=== Cadastrar Cliente ===");
        Cliente c = new Cliente();

        System.out.print("Nome (ou deixe em branco para cancelar): ");
        String nome = entrada.nextLine();
        if (nome.trim().isEmpty()) {
            System.out.println("Cadastro de cliente cancelado.");
            return;
        }
        c.setNomeCliente(nome);

        System.out.print("Email: ");
        c.setEmail(entrada.nextLine());

        System.out.print("Data de nascimento (DD-MM-AAAA): ");
        c.setDataNascimento(entrada.nextLine());

        System.out.print("Endereço: ");
        c.setEndereco(entrada.nextLine());

        clientes.add(c);
        System.out.println("Cliente cadastrado com sucesso!");
    }

    private static void listarClientes() {
        System.out.println("\n=== Clientes Cadastrados ===");
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        int i = 1;
        for (Cliente c : clientes) {
            System.out.println("Cliente " + i + ":");
            c.exibirDados();
            i++;
        }
    }

    private static void atualizarCliente() {
        System.out.println("\n=== Atualizar Cliente ===");
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        for (int i = 0; i < clientes.size(); i++) {
            System.out.println((i + 1) + " - " + clientes.get(i).getNomeCliente());
        }
        System.out.println("0 - Cancelar");

        int indice = lerInteiro("Escolha o número do cliente (0 para cancelar): ") - 1;
        if (indice == -1) {
            System.out.println("Atualização cancelada.");
            return;
        }
        if (indice < 0 || indice >= clientes.size()) {
            System.out.println("Cliente inválido.");
            return;
        }

        Cliente c = clientes.get(indice);

        System.out.println("Deixe em branco para manter o valor atual.\n");

        System.out.print("Nome atual (" + c.getNomeCliente() + "): ");
        String nome = entrada.nextLine();
        if (!nome.trim().isEmpty()) c.setNomeCliente(nome);

        System.out.print("Email atual (" + c.getEmail() + "): ");
        String email = entrada.nextLine();
        if (!email.trim().isEmpty()) c.setEmail(email);

        System.out.print("Data nascimento atual (" + c.getDataNascimento() + "): ");
        String data = entrada.nextLine();
        if (!data.trim().isEmpty()) c.setDataNascimento(data);

        System.out.print("Endereço atual (" + c.getEndereco() + "): ");
        String end = entrada.nextLine();
        if (!end.trim().isEmpty()) c.setEndereco(end);

        System.out.println("Cliente atualizado com sucesso!");
    }

    private static void removerCliente() {
        System.out.println("\n=== Remover Cliente ===");
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        for (int i = 0; i < clientes.size(); i++) {
            System.out.println((i + 1) + " - " + clientes.get(i).getNomeCliente());
        }
        System.out.println("0 - Cancelar");

        int indice = lerInteiro("Escolha o número do cliente (0 para cancelar): ") - 1;
        if (indice == -1) {
            System.out.println("Remoção cancelada.");
            return;
        }
        if (indice < 0 || indice >= clientes.size()) {
            System.out.println("Cliente inválido.");
            return;
        }

        Cliente alvo = clientes.get(indice);
        System.out.print("Tem certeza que deseja remover \"" + alvo.getNomeCliente() + "\"? (S/N): ");
        String conf = entrada.nextLine();
        if (!conf.equalsIgnoreCase("S")) {
            System.out.println("Remoção cancelada.");
            return;
        }

        clientes.remove(indice);
        System.out.println("Cliente removido.");
    }

    // -----------------------------------------------------------
    // CRUD SERVIÇO (com cancelar)
    // -----------------------------------------------------------
    private static void cadastrarServico() {
        System.out.println("\n=== Cadastrar Serviço ===");
        System.out.print("Nome do serviço (ou deixe em branco para cancelar): ");
        String nome = entrada.nextLine();
        if (nome.trim().isEmpty()) {
            System.out.println("Cadastro de serviço cancelado.");
            return;
        }

        System.out.print("Preço do serviço: ");
        double preco = entrada.nextDouble();
        entrada.nextLine();

        servicos.add(new Servico(nome, preco));
        System.out.println("Serviço cadastrado com sucesso!");
    }

    private static void listarServicos() {
        System.out.println("\n=== Serviços Oferecidos ===");
        if (servicos.isEmpty()) {
            System.out.println("Nenhum serviço cadastrado.");
            return;
        }

        int i = 1;
        for (Servico s : servicos) {
            System.out.print(i + " - ");
            s.exibirDados();
            i++;
        }
    }

    private static void atualizarServico() {
        System.out.println("\n=== Atualizar Serviço ===");
        if (servicos.isEmpty()) {
            System.out.println("Nenhum serviço cadastrado.");
            return;
        }

        for (int i = 0; i < servicos.size(); i++) {
            System.out.println((i + 1) + " - " + servicos.get(i).getNome());
        }
        System.out.println("0 - Cancelar");

        int indice = lerInteiro("Escolha o serviço (0 para cancelar): ") - 1;
        if (indice == -1) {
            System.out.println("Atualização cancelada.");
            return;
        }
        if (indice < 0 || indice >= servicos.size()) {
            System.out.println("Serviço inválido.");
            return;
        }

        Servico s = servicos.get(indice);

        System.out.println("Deixe em branco para manter o valor atual.\n");

        System.out.print("Nome atual (" + s.getNome() + "): ");
        String nome = entrada.nextLine();
        if (!nome.trim().isEmpty()) s.setNome(nome);

        System.out.print("Preço atual (" + s.getPreco() + "): ");
        String precoStr = entrada.nextLine();
        if (!precoStr.trim().isEmpty()) {
            try {
                double preco = Double.parseDouble(precoStr);
                s.setPreco(preco);
            } catch (NumberFormatException e) {
                System.out.println("Preço inválido, valor mantido.");
            }
        }

        System.out.println("Serviço atualizado com sucesso!");
    }

    private static void removerServico() {
        System.out.println("\n=== Remover Serviço ===");
        if (servicos.isEmpty()) {
            System.out.println("Nenhum serviço cadastrado.");
            return;
        }

        for (int i = 0; i < servicos.size(); i++) {
            System.out.println((i + 1) + " - " + servicos.get(i).getNome());
        }
        System.out.println("0 - Cancelar");

        int indice = lerInteiro("Escolha o serviço (0 para cancelar): ") - 1;
        if (indice == -1) {
            System.out.println("Remoção cancelada.");
            return;
        }
        if (indice < 0 || indice >= servicos.size()) {
            System.out.println("Serviço inválido.");
            return;
        }

        Servico alvo = servicos.get(indice);
        System.out.print("Tem certeza que deseja remover \"" + alvo.getNome() + "\"? (S/N): ");
        String conf = entrada.nextLine();
        if (!conf.equalsIgnoreCase("S")) {
            System.out.println("Remoção cancelada.");
            return;
        }

        servicos.remove(indice);
        System.out.println("Serviço removido.");
    }

    // -----------------------------------------------------------
    // CRUD RAÇÃO (Produto) com cancelar
    // -----------------------------------------------------------
    private static void cadastrarRacao() {
        System.out.println("\n=== Cadastrar Ração (por KG) ===");
        Produto p = new Produto();

        System.out.print("Nome da ração (ou deixe em branco para cancelar): ");
        String nome = entrada.nextLine();
        if (nome.trim().isEmpty()) {
            System.out.println("Cadastro de ração cancelado.");
            return;
        }
        p.setNomeProduto(nome);

        System.out.print("Descrição: ");
        p.setDescricao(entrada.nextLine());

        System.out.print("Preço por KG: ");
        double preco = entrada.nextDouble();
        entrada.nextLine();

        p.setPreco(preco);

        racoes.add(p);
        System.out.println("Ração cadastrada com sucesso!");
    }

    private static void listarRacoes() {
        System.out.println("\n=== Rações Disponíveis ===");
        if (racoes.isEmpty()) {
            System.out.println("Nenhuma ração cadastrada.");
            return;
        }

        for (int i = 0; i < racoes.size(); i++) {
            Produto p = racoes.get(i);
            System.out.println((i + 1) + " - " + p.getNomeProduto());
            System.out.println("Descrição: " + p.getDescricao());
            System.out.println("Preço por KG: R$ " + p.getPreco());
            System.out.println("---------------------------");
        }
    }

    private static void atualizarRacao() {
        System.out.println("\n=== Atualizar Ração ===");
        if (racoes.isEmpty()) {
            System.out.println("Nenhuma ração cadastrada.");
            return;
        }

        for (int i = 0; i < racoes.size(); i++) {
            System.out.println((i + 1) + " - " + racoes.get(i).getNomeProduto());
        }
        System.out.println("0 - Cancelar");

        int indice = lerInteiro("Escolha a ração (0 para cancelar): ") - 1;
        if (indice == -1) {
            System.out.println("Atualização cancelada.");
            return;
        }
        if (indice < 0 || indice >= racoes.size()) {
            System.out.println("Ração inválida.");
            return;
        }

        Produto p = racoes.get(indice);

        System.out.println("Deixe em branco para manter o valor atual.\n");

        System.out.print("Nome atual (" + p.getNomeProduto() + "): ");
        String nome = entrada.nextLine();
        if (!nome.trim().isEmpty()) p.setNomeProduto(nome);

        System.out.print("Descrição atual (" + p.getDescricao() + "): ");
        String desc = entrada.nextLine();
        if (!desc.trim().isEmpty()) p.setDescricao(desc);

        System.out.print("Preço atual (" + p.getPreco() + "): ");
        String precoStr = entrada.nextLine();
        if (!precoStr.trim().isEmpty()) {
            try {
                double preco = Double.parseDouble(precoStr);
                p.setPreco(preco);
            } catch (NumberFormatException e) {
                System.out.println("Preço inválido, valor mantido.");
            }
        }

        System.out.println("Ração atualizada com sucesso!");
    }

    private static void removerRacao() {
        System.out.println("\n=== Remover Ração ===");
        if (racoes.isEmpty()) {
            System.out.println("Nenhuma ração cadastrada.");
            return;
        }

        for (int i = 0; i < racoes.size(); i++) {
            System.out.println((i + 1) + " - " + racoes.get(i).getNomeProduto());
        }
        System.out.println("0 - Cancelar");

        int indice = lerInteiro("Escolha a ração (0 para cancelar): ") - 1;
        if (indice == -1) {
            System.out.println("Remoção cancelada.");
            return;
        }
        if (indice < 0 || indice >= racoes.size()) {
            System.out.println("Ração inválida.");
            return;
        }

        Produto alvo = racoes.get(indice);
        System.out.print("Tem certeza que deseja remover \"" + alvo.getNomeProduto() + "\"? (S/N): ");
        String conf = entrada.nextLine();
        if (!conf.equalsIgnoreCase("S")) {
            System.out.println("Remoção cancelada.");
            return;
        }

        racoes.remove(indice);
        System.out.println("Ração removida.");
    }

    // -----------------------------------------------------------
    // CRUD FUNCIONÁRIO (incluindo CADASTRAR + admin)
    // -----------------------------------------------------------
    private static void cadastrarFuncionario() {
        System.out.println("\n=== Cadastrar Funcionário ===");
        System.out.print("Nome do funcionário (ou deixe em branco para cancelar): ");
        String nome = entrada.nextLine();
        if (nome.trim().isEmpty()) {
            System.out.println("Cadastro de funcionário cancelado.");
            return;
        }

        System.out.print("Função (Atendente, Tosador, Banhista, Entregador...): ");
        String funcao = entrada.nextLine();
        if (funcao.trim().isEmpty()) {
            System.out.println("Função vazia. Cadastro cancelado.");
            return;
        }

        System.out.print("Este funcionário é administrador do sistema? (S/N): ");
        String respAdmin = entrada.nextLine();
        boolean ehAdmin = respAdmin.equalsIgnoreCase("S");

        funcionarios.add(new Funcionario(nome, funcao, ehAdmin));
        System.out.println("Funcionário cadastrado com sucesso!");
    }

    private static void listarFuncionarios() {
        System.out.println("\n=== Funcionários ===");

        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }

        System.out.println("\n-- Atendentes --");
        for (Funcionario f : funcionarios) {
            if (f.getFuncao().equalsIgnoreCase("Atendente")) {
                f.exibirDados();
                System.out.println("--------------------");
            }
        }

        System.out.println("\n-- Banhistas --");
        for (Funcionario f : funcionarios) {
            if (f.getFuncao().equalsIgnoreCase("Banhista")) {
                f.exibirDados();
                System.out.println("--------------------");
            }
        }

        System.out.println("\n-- Tosadores --");
        for (Funcionario f : funcionarios) {
            if (f.getFuncao().toLowerCase().contains("tosador")) {
                f.exibirDados();
                System.out.println("--------------------");
            }
        }

        System.out.println("\n-- Entregadores --");
        for (Funcionario f : funcionarios) {
            if (f.getFuncao().toLowerCase().contains("entregador")) {
                f.exibirDados();
                System.out.println("--------------------");
            }
        }
    }

    private static void atualizarFuncionario() {
        System.out.println("\n=== Atualizar Funcionário ===");
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }

        for (int i = 0; i < funcionarios.size(); i++) {
            Funcionario f = funcionarios.get(i);
            System.out.println((i + 1) + " - " + f.getNome() + " (" + f.getFuncao() + ") " +
                    (f.isAdministrador() ? "[ADMIN]" : ""));
        }
        System.out.println("0 - Cancelar");

        int indice = lerInteiro("Escolha o funcionário (0 para cancelar): ") - 1;
        if (indice == -1) {
            System.out.println("Atualização cancelada.");
            return;
        }
        if (indice < 0 || indice >= funcionarios.size()) {
            System.out.println("Funcionário inválido.");
            return;
        }

        Funcionario f = funcionarios.get(indice);

        System.out.println("Deixe em branco para manter o valor atual.\n");

        System.out.print("Nome atual (" + f.getNome() + "): ");
        String nome = entrada.nextLine();
        if (!nome.trim().isEmpty()) f.setNome(nome);

        System.out.print("Função atual (" + f.getFuncao() + "): ");
        String funcao = entrada.nextLine();
        if (!funcao.trim().isEmpty()) f.setFuncao(funcao);

        System.out.print("É administrador atualmente (" + (f.isAdministrador() ? "Sim" : "Não") + ")? (S/N para alterar, Enter para manter): ");
        String respAdmin = entrada.nextLine();
        if (respAdmin.equalsIgnoreCase("S")) {
            f.setAdministrador(true);
        } else if (respAdmin.equalsIgnoreCase("N")) {
            f.setAdministrador(false);
        }

        System.out.println("Funcionário atualizado com sucesso!");
    }

    private static void removerFuncionario() {
        System.out.println("\n=== Remover Funcionário ===");
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }

        for (int i = 0; i < funcionarios.size(); i++) {
            Funcionario f = funcionarios.get(i);
            System.out.println((i + 1) + " - " + f.getNome() + " (" + f.getFuncao() + ") " +
                    (f.isAdministrador() ? "[ADMIN]" : ""));
        }
        System.out.println("0 - Cancelar");

        int indice = lerInteiro("Escolha o funcionário (0 para cancelar): ") - 1;
        if (indice == -1) {
            System.out.println("Remoção cancelada.");
            return;
        }
        if (indice < 0 || indice >= funcionarios.size()) {
            System.out.println("Funcionário inválido.");
            return;
        }

        Funcionario alvo = funcionarios.get(indice);
        System.out.print("Tem certeza que deseja remover \"" + alvo.getNome() + "\"? (S/N): ");
        String conf = entrada.nextLine();
        if (!conf.equalsIgnoreCase("S")) {
            System.out.println("Remoção cancelada.");
            return;
        }

        funcionarios.remove(indice);
        System.out.println("Funcionário removido.");
    }

    // -----------------------------------------------------------
    // Helper: escolher funcionário por função
    // -----------------------------------------------------------
    private static Funcionario escolherFuncionarioPorFuncao(String funcaoDesejada) {
        List<Funcionario> filtrados = new ArrayList<>();

        for (Funcionario f : funcionarios) {
            if (f.getFuncao().equalsIgnoreCase(funcaoDesejada)
                    || f.getFuncao().toLowerCase().contains(funcaoDesejada.toLowerCase())) {
                filtrados.add(f);
            }
        }

        if (filtrados.isEmpty()) {
            System.out.println("\nNenhum funcionário encontrado para a função: " + funcaoDesejada);
            return null;
        }

        System.out.println("\nEscolha o " + funcaoDesejada + ":");
        for (int i = 0; i < filtrados.size(); i++) {
            System.out.println((i + 1) + " - " + filtrados.get(i).getNome()
                    + " (" + filtrados.get(i).getFuncao()
                    + (filtrados.get(i).isAdministrador() ? " - ADMIN" : "") + ")");
        }

        int escolha = lerInteiro("Opção: ") - 1;

        if (escolha < 0 || escolha >= filtrados.size()) {
            System.out.println("Opção inválida. Nenhum " + funcaoDesejada + " selecionado.");
            return null;
        }

        return filtrados.get(escolha);
    }

    // -----------------------------------------------------------
    // Ver horários disponíveis para hoje
    // -----------------------------------------------------------
    private static void verHorariosDisponiveis() {
        System.out.println("\n=== Horários disponíveis para hoje ===");

        String dataHoje = LocalDate.now().format(formatoData);
        boolean algumDisponivel = false;

        for (String h : HORARIOS_DISPONIVEIS) {
            boolean ocupado = false;

            for (Agendamento ag : agendamentos) {
                if (ag.getData().equals(dataHoje) && ag.getHorario().equals(h)) {
                    ocupado = true;
                    break;
                }
            }

            if (!ocupado) {
                System.out.println("- " + h);
                algumDisponivel = true;
            }
        }

        if (!algumDisponivel) {
            System.out.println("Não há horários disponíveis para hoje.");
        }
    }

    // -----------------------------------------------------------
    // Cadastrar pet vinculado ao cliente
    // -----------------------------------------------------------
    private static Pet cadastrarPetParaCliente(Cliente cliente) {
        System.out.println("\n=== Cadastrar Pet para o Cliente " + cliente.getNomeCliente() + " ===");

        Pet pet = new Pet();
        pet.setDono(cliente);

        System.out.print("Nome do pet: ");
        pet.setNome(entrada.nextLine());

        System.out.print("Tipo do pet (cachorro, gato...): ");
        pet.setTipo(entrada.nextLine());

        int idadePet = lerInteiro("Idade do pet: ");
        pet.setIdade(idadePet);

        cliente.adicionarPet(pet);

        System.out.println("Pet cadastrado e vinculado ao cliente!");
        return pet;
    }

    // -----------------------------------------------------------
    // Iniciar Atendimento
    // -----------------------------------------------------------
    private static void iniciarAtendimento() {
        System.out.println("\n=== Iniciar Atendimento ===");

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado!");
            return;
        }

        if (servicos.isEmpty()) {
            System.out.println("Nenhum serviço cadastrado!");
            return;
        }

        if (racoes.isEmpty()) {
            System.out.println("Nenhuma ração cadastrada. Vamos cadastrar uma antes do atendimento.");
            cadastrarRacao();
        }

        // Escolha do cliente
        System.out.println("\nEscolha o cliente:");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.println((i + 1) + " - " + clientes.get(i).getNomeCliente());
        }

        int indiceCliente = lerInteiro("Cliente: ") - 1;
        if (indiceCliente < 0 || indiceCliente >= clientes.size()) {
            System.out.println("Cliente inválido!");
            return;
        }

        Cliente cliente = clientes.get(indiceCliente);

        // Selecionar atendente
        Funcionario atendente = escolherFuncionarioPorFuncao("Atendente");

        // Pet vinculado
        Pet pet;
        List<Pet> petsCliente = cliente.getPets();

        if (petsCliente != null && !petsCliente.isEmpty()) {
            System.out.println("\nO cliente já possui pets cadastrados:");
            int i = 1;
            for (Pet p : petsCliente) {
                System.out.print(i + ") ");
                p.exibirDadosSimples();
                i++;
            }

            System.out.println("1 - Escolher um pet existente");
            System.out.println("2 - Cadastrar novo pet");
            int opcPet = lerInteiro("Opção: ");

            if (opcPet == 1) {
                int escolhaPet = lerInteiro("Escolha o número do pet: ") - 1;
                if (escolhaPet < 0 || escolhaPet >= petsCliente.size()) {
                    System.out.println("Pet inválido. Cadastrando novo pet.");
                    pet = cadastrarPetParaCliente(cliente);
                } else {
                    pet = petsCliente.get(escolhaPet);
                    System.out.println("Pet selecionado: ");
                    pet.exibirDadosSimples();
                }
            } else {
                pet = cadastrarPetParaCliente(cliente);
            }
        } else {
            System.out.println("\nCliente ainda não possui pets cadastrados.");
            pet = cadastrarPetParaCliente(cliente);
        }

        // Seleção de múltiplos serviços
        List<Servico> servicosSelecionados = new ArrayList<>();
        boolean adicionarMaisServicos = true;

        while (adicionarMaisServicos) {
            System.out.println("\nServiços disponíveis:");
            listarServicos();

            int indiceServico = lerInteiro("Escolha o serviço (número): ") - 1;

            if (indiceServico < 0 || indiceServico >= servicos.size()) {
                System.out.println("Serviço inválido!");
                continue;
            }

            Servico servicoEscolhido = servicos.get(indiceServico);
            servicosSelecionados.add(servicoEscolhido);

            System.out.print("Deseja adicionar outro serviço? (S/N): ");
            String respOutro = entrada.nextLine();
            if (!respOutro.equalsIgnoreCase("S")) {
                adicionarMaisServicos = false;
            }
        }

        if (servicosSelecionados.isEmpty()) {
            System.out.println("Nenhum serviço selecionado. Atendimento cancelado.");
            return;
        }

        // Verifica se precisa tosador
        boolean precisaTosador = false;
        for (Servico s : servicosSelecionados) {
            String nome = s.getNome().toLowerCase();
            if (nome.contains("tosa")) {
                precisaTosador = true;
                break;
            }
        }

        Funcionario tosador = null;
        if (precisaTosador) {
            tosador = escolherFuncionarioPorFuncao("Tosador");
        }

        // Horário
        String dataHoje = LocalDate.now().format(formatoData);

        System.out.println("\nHorários disponíveis (" + dataHoje + "):");
        verHorariosDisponiveis();

        System.out.print("Horário desejado (ex.: 09:00): ");
        String horario = entrada.nextLine();

        // Verifica conflito de horário
        for (Agendamento ag : agendamentos) {
            if (ag.getData().equals(dataHoje) && ag.getHorario().equals(horario)) {
                System.out.println("Horário já ocupado!");
                return;
            }
        }

        // Cria agendamento com TODOS os serviços selecionados
        Agendamento agendamento = new Agendamento(cliente, servicosSelecionados, pet, dataHoje, horario);
        agendamentos.add(agendamento);

        System.out.println("Atendimento agendado com sucesso!");
        agendamento.exibirDados(); // agora mostra todos os serviços

        // Venda de ração
        System.out.print("Deseja comprar ração neste atendimento? (S/N): ");
        String respRacao = entrada.nextLine();

        double totalRacoes = 0;
        StringBuilder itensRacao = new StringBuilder();

        if (respRacao.equalsIgnoreCase("S")) {
            boolean comprando = true;

            while (comprando) {
                listarRacoes();

                int indiceRacao = lerInteiro("Escolha a ração (número): ") - 1;

                if (indiceRacao < 0 || indiceRacao >= racoes.size()) {
                    System.out.println("Opção inválida!");
                    continue;
                }

                Produto r = racoes.get(indiceRacao);

                int kg = lerInteiro("Quantidade (em KG): ");
                double subtotal = r.getPreco() * kg;
                totalRacoes += subtotal;

                itensRacao.append(r.getNomeProduto())
                         .append(" - ")
                         .append(kg).append(" KG - R$ ").append(subtotal).append("\n");

                System.out.print("Adicionar outra ração? (S/N): ");
                comprando = entrada.nextLine().equalsIgnoreCase("S");
            }
        }

        // Entrega opcional
        double valorEntrega = 0.0;
        boolean usouEntrega = false;
        Funcionario entregador = null;

        System.out.print("Deseja adicionar serviço de entrega (R$ 15,00)? (S/N): ");
        String respEntrega = entrada.nextLine();

        if (respEntrega.equalsIgnoreCase("S")) {
            valorEntrega = 15.00;
            usouEntrega = true;
            entregador = escolherFuncionarioPorFuncao("Entregador");
        }

        // Forma de pagamento (usando enum)
        FormaPagamento formaPagamento = null;
        while (formaPagamento == null) {
            System.out.println("\nFormas de pagamento:");
            System.out.println("1 - Crédito");
            System.out.println("2 - Débito");
            System.out.println("3 - Dinheiro");
            System.out.println("4 - PIX");

            int opcPag = lerInteiro("Escolha a forma de pagamento: ");

            switch (opcPag) {
                case 1 -> formaPagamento = FormaPagamento.CREDITO;
                case 2 -> formaPagamento = FormaPagamento.DEBITO;
                case 3 -> formaPagamento = FormaPagamento.DINHEIRO;
                case 4 -> formaPagamento = FormaPagamento.PIX;
                default -> {
                    System.out.println("Forma de pagamento inválida. Tente novamente.");
                }
            }
        }

        // Soma serviços
        double totalServicos = 0;
        StringBuilder itensServicos = new StringBuilder();
        itensServicos.append("--- Serviços realizados ---\n");
        for (Servico s : servicosSelecionados) {
            itensServicos.append(s.getNome())
                         .append(" - R$ ")
                         .append(s.getPreco())
                         .append("\n");
            totalServicos += s.getPreco();
        }

        double totalGeral = totalServicos + totalRacoes + valorEntrega;

        // Cria objeto Pagamento — mantendo compatibilidade com Pagamento(String, double)
        Pagamento pagamento = new Pagamento(formaPagamento.getLabel(), totalGeral);

        // Hora atual
        String horaAgora = java.time.LocalTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));

        // Recibo
        StringBuilder recibo = new StringBuilder();

        recibo.append("Cliente: ").append(cliente.getNomeCliente()).append("\n");
        recibo.append("Pet: ").append(pet.getNome()).append(" (").append(pet.getTipo()).append(")\n");

        if (atendente != null) {
            recibo.append("Atendente: ").append(atendente.getNome()).append("\n");
        }
        if (precisaTosador && tosador != null) {
            recibo.append("Responsável pela tosa: ").append(tosador.getNome()).append("\n");
        }
        if (usouEntrega && entregador != null) {
            recibo.append("Entregador: ").append(entregador.getNome()).append("\n");
        }

        recibo.append("\n").append(itensServicos.toString()).append("\n");

        recibo.append("Data: ").append(dataHoje).append("\n");
        recibo.append("Horário agendado: ").append(horario).append("\n");
        recibo.append("Hora do atendimento: ").append(horaAgora).append("\n\n");

        if (totalRacoes > 0) {
            recibo.append("--- Rações compradas ---\n");
            recibo.append(itensRacao.toString());
            recibo.append("TOTAL EM Rações: R$ ").append(totalRacoes).append("\n\n");
        }

        if (usouEntrega) {
            recibo.append("Serviço de entrega: R$ ").append(valorEntrega).append("\n");
        }

        recibo.append("TOTAL DOS SERVIÇOS: R$ ").append(totalServicos).append("\n\n");
        recibo.append(pagamento.gerarResumo()).append("\n");
        recibo.append("===== TOTAL DO ATENDIMENTO: R$ ").append(totalGeral).append(" =====\n\n");
        recibo.append("Agradecemos pela preferência. Volte sempre!\n");

        System.out.println("\n--- RECIBO DO ATENDIMENTO ---");
        System.out.println(recibo.toString());

        recibos.add(recibo.toString());

        // -----------------------
        // Atualiza contadores do dia
        // -----------------------
        clientesAtendidos++;                 // 1 atendimento realizado
        totalVendasServicos += totalServicos;
        totalVendasRacoes += totalRacoes;
        totalVendasEntrega += valorEntrega;
    }

    // -----------------------------------------------------------
    // Listar agendamentos
    // -----------------------------------------------------------
    private static void listarAgendamentos() {
        System.out.println("\n=== Agendamentos Realizados ===");
        if (agendamentos.isEmpty()) {
            System.out.println("Nenhum agendamento.");
            return;
        }

        int i = 1;
        for (Agendamento ag : agendamentos) {
            System.out.println("#" + i);
            ag.exibirDados();
            i++;
        }
    }

    // -----------------------------------------------------------
    // Exibir todos os recibos ao sair + Resumo do dia
    // -----------------------------------------------------------
    private static void gerarRecibosAoSair() {
        System.out.println("\n===== RECIBOS GERADOS =====");
        if (recibos.isEmpty()) {
            System.out.println("Nenhum recibo gerado.");
        } else {
            for (String r : recibos) {
                System.out.println(r);
            }
        }

        // Resumo do dia
        System.out.println("\n===== RESUMO DO DIA =====");
        System.out.println("Total de atendimentos (clientes atendidos): " + clientesAtendidos);
        System.out.printf("Total arrecadado com serviços: R$ %.2f%n", totalVendasServicos);
        System.out.printf("Total arrecadado com vendas de rações: R$ %.2f%n", totalVendasRacoes);
        System.out.printf("Total arrecadado com entregas: R$ %.2f%n", totalVendasEntrega);
        double totalGeralDia = totalVendasServicos + totalVendasRacoes + totalVendasEntrega;
        System.out.printf("TOTAL GERAL DO DIA: R$ %.2f%n", totalGeralDia);
        System.out.println("==========================");
    }

    // -----------------------------------------------------------
    // MAIN ATUALIZADA
    // -----------------------------------------------------------
    public static void main(String[] args) {

        inicializarServicosPadrao();
        inicializarRacoesPadrao();
        inicializarFuncionariosPadrao();

        // Primeiro: LOGIN
        if (!realizarLoginInicial()) {
            entrada.close();
            return;
        }

        boolean rodando = true;

        while (rodando) {
            mostrarMenu();
            int opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> menuCliente();
                case 2 -> menuServicos();
                case 3 -> menuRacoes();
                case 4 -> {
                    if (!usuarioEhAdmin) {
                        System.out.println("Acesso negado! Apenas ADMINISTRADORES podem acessar o menu de funcionários.");
                    } else {
                        menuFuncionarios();
                    }
                }
                case 5 -> {
                    boolean continuarAtendimentos = true;

                    while (continuarAtendimentos && rodando) {
                        iniciarAtendimento();

                        System.out.println("\nO que você deseja fazer agora?");
                        System.out.println("1 - Encerrar sistema");
                        System.out.println("2 - Gerar novo atendimento");
                        System.out.println("3 - Voltar ao menu principal");
                        int opcaoPosRecibo = lerInteiro("Opção: ");

                        switch (opcaoPosRecibo) {
                            case 1 -> {
                                gerarRecibosAoSair();
                                System.out.println("Sistema encerrado.");
                                rodando = false;
                                continuarAtendimentos = false;
                            }
                            case 2 -> {
                                System.out.println("Iniciando novo atendimento...");
                            }
                            case 3 -> {
                                continuarAtendimentos = false;
                            }
                            default -> {
                                System.out.println("Opção inválida! Voltando ao menu principal.");
                                continuarAtendimentos = false;
                            }
                        }
                    }
                }
                case 6 -> verHorariosDisponiveis();
                case 7 -> listarAgendamentos();
                case 8 -> {
                    // LOGOUT / TROCAR USUÁRIO
                    System.out.println("\nRealizando logout do usuário atual...");
                    usuarioEhAdmin = false;
                    usuarioFuncao = null;

                    if (!realizarLoginInicial()) {
                        System.out.println("Não foi possível autenticar um novo usuário. Encerrando sistema.");
                        gerarRecibosAoSair();
                        rodando = false;
                    }
                }
                case 0 -> {
                    gerarRecibosAoSair();             // exibe recibos + resumo do dia
                    System.out.println("Sistema encerrado.");
                    rodando = false;
                }
                default -> System.out.println("Opção inválida!");
            }
        }

        entrada.close();
    }
}
