package petsec_senac;

import javafx.application.Application;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PetshopFX extends Application {

    private ObservableList<Cliente> clientes = FXCollections.observableArrayList();
    private ObservableList<Servico> servicos = FXCollections.observableArrayList();
    private ObservableList<petsec_senac.Produto> racoes = FXCollections.observableArrayList();
    private ObservableList<Funcionario> funcionarios = FXCollections.observableArrayList();
    private ObservableList<Agendamento> agendamentos = FXCollections.observableArrayList();
    private ObservableList<String> recibos = FXCollections.observableArrayList();

    private Stage stage;
    private BorderPane mainRoot;
    private final DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final String[] HORARIOS_DISPONIVEIS = {"09:00", "10:00", "11:00", "14:00", "15:00", "16:00"};
    private boolean temaAzul = true;

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        inicializarPadrao();
        mostrarLogin();
    }

    private void inicializarPadrao() {
        servicos.addAll(
                new Servico("Banho", 40.00),
                new Servico("Tosa", 60.00),
                new Servico("Tosa Higiênica", 35.00),
                new Servico("Hidratação", 50.00)
        );

        Produto p1 = new Produto("Ração Comum", "Ração padrão para cães adultos.", 20.00);
        Produto p2 = new Produto("Ração Premium", "Ração premium com mais nutrientes.", 35.00);
        Produto p3 = new Produto("Ração Super Premium", "Alto valor nutricional.", 55.00);
        Produto p4 = new Produto("Ração Filhote", "Especial para cães filhotes.", 25.00);
        Produto p5 = new Produto("Ração Sênior", "Ração para cães idosos.", 28.00);
        racoes.addAll(p1, p2, p3, p4, p5);

        funcionarios.addAll(
                new Funcionario("Ana Souza", "Atendente"),
                new Funcionario("Carlos Lima", "Atendente"),
                new Funcionario("Bruno Pereira", "Tosador"),
                new Funcionario("Mariana Alves", "Tosador"),
                new Funcionario("Rita Souza", "Banhista"),
                new Funcionario("Diego Rocha", "Banhista"),
                new Funcionario("João Ferreira", "Entregador"),
                new Funcionario("Paula Costa", "Entregador")
        );

        Cliente c = new Cliente("Pedro Silva", "pedro@email.com", "10-05-1990", "Rua A, 123");
        Pet pet = new Pet("Rex", "cachorro", 3);
        c.adicionarPet(pet);
        clientes.add(c);
    }

    // login
    private Map<String, String> usuarios = new HashMap<>(); // chave: email ou CNPJ, valor: senha
    private String usuarioLogado; // guarda o login do usuário atual

    private void mostrarLogin() {
        // Adiciona usuário admin padrão
        if (usuarios.isEmpty()) usuarios.put("admin", "1234");

        Label welcomeLabel = new Label("Bem vindo!");
        welcomeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 30; -fx-text-fill: white;");

        VBox loginBox = new VBox(15);
        loginBox.setPadding(new Insets(30));
        loginBox.getStyleClass().add("login-box");

        Label loginLabel = new Label("Login");
        loginLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
        loginLabel.getStyleClass().add("login-title");

        TextField userField = new TextField();
        userField.setPromptText("CNPJ, Email");
        userField.getStyleClass().add("input-field");
        userField.setPrefWidth(300);
        userField.setPrefHeight(40);

        Label senhaLabel = new Label("Senha");
        senhaLabel.setStyle("-fx-font-size: 18;");
        senhaLabel.getStyleClass().add("senha-label");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Senha");
        passwordField.getStyleClass().add("input-field");
        passwordField.setPrefWidth(300);
        passwordField.setPrefHeight(40);

        HBox linksBox = new HBox(20);
        Label forgotLabel = new Label("Esqueceu a senha?");
        forgotLabel.getStyleClass().add("link-label");
        Label registerLabel = new Label("Cadastre-se");
        registerLabel.getStyleClass().add("link-label");
        linksBox.getChildren().addAll(forgotLabel, registerLabel);

        Button enterBtn = new Button("Entrar");
        enterBtn.getStyleClass().add("enter-button");
        enterBtn.setPrefWidth(200);
        enterBtn.setPrefHeight(35);

        // Corrigido: único handler, salva usuarioLogado e mostra dashboard
        enterBtn.setOnAction(e -> {
            String user = userField.getText().trim();
            String pass = passwordField.getText().trim();
            if (usuarios.containsKey(user) && usuarios.get(user).equals(pass)) {
                usuarioLogado = user; // salva o usuário logado
                mostrarDashboard();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Login inválido", "Usuário ou senha incorretos.");
            }
        });

        // Função "Cadastre-se"
        registerLabel.setOnMouseClicked(e -> mostrarCadastro());

        // Função "Esqueceu a senha"
        forgotLabel.setOnMouseClicked(e -> mostrarRecuperarSenha());

        loginBox.getChildren().addAll(loginLabel, userField, senhaLabel, passwordField, linksBox, enterBtn);

        VBox leftBox = new VBox(20);
        leftBox.setPadding(new Insets(40));
        leftBox.setAlignment(Pos.TOP_LEFT);
        leftBox.getChildren().addAll(welcomeLabel, loginBox);

        ImageView dogImage = null;
        java.io.InputStream imgStream = getClass().getResourceAsStream("dog.png");
        if (imgStream != null) {
            Image img = new Image(imgStream);
            dogImage = new ImageView(img);
            dogImage.setFitWidth(250);
            dogImage.setFitHeight(250);
            dogImage.setPreserveRatio(true);
            HBox.setMargin(dogImage, new Insets(0, 50, 0, 50));
        }

        HBox root = new HBox();
        root.setPrefSize(900, 450);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(50);
        root.getStyleClass().add("root-box");
        root.setStyle("-fx-background-color: #1E65A6;");

        if (dogImage != null) root.getChildren().addAll(leftBox, dogImage);
        else root.getChildren().add(leftBox);

        Scene scene = new Scene(root);
        carregarCSS(scene);
        aplicarTema(scene);

        stage.setScene(scene);
        stage.setTitle("PetSec - Login");
        stage.show();
    }

    // Tela de cadastro
    private void mostrarCadastro() {
        Stage cadastroStage = new Stage();
        cadastroStage.setTitle("Cadastro de Usuário");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField tfUser = new TextField();
        tfUser.setPromptText("Email ou CNPJ");
        PasswordField tfPass = new PasswordField();
        tfPass.setPromptText("Senha");
        PasswordField tfConf = new PasswordField();
        tfConf.setPromptText("Confirmar senha");

        Button btnCadastrar = new Button("Cadastrar");
        btnCadastrar.setOnAction(e -> {
            String user = tfUser.getText().trim();
            String pass = tfPass.getText().trim();
            String conf = tfConf.getText().trim();
            if (user.isEmpty() || pass.isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Preencha todos os campos.");
                return;
            }
            if (!pass.equals(conf)) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Senhas não conferem.");
                return;
            }
            if (usuarios.containsKey(user)) {
                mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Usuário já existe.");
                return;
            }
            usuarios.put(user, pass);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Usuário cadastrado!");
            cadastroStage.close();
        });

        grid.add(new Label("Usuário:"), 0, 0);
        grid.add(tfUser, 1, 0);
        grid.add(new Label("Senha:"), 0, 1);
        grid.add(tfPass, 1, 1);
        grid.add(new Label("Confirmar:"), 0, 2);
        grid.add(tfConf, 1, 2);
        grid.add(btnCadastrar, 1, 3);

        Scene scene = new Scene(grid, 350, 200);
        cadastroStage.setScene(scene);
        cadastroStage.show();
    }

    // Tela de recuperação de senha
    private void mostrarRecuperarSenha() {
        Stage recuperarStage = new Stage();
        recuperarStage.setTitle("Recuperar Senha");

        VBox box = new VBox(10);
        box.setPadding(new Insets(20));

        TextField tfUser = new TextField();
        tfUser.setPromptText("Email ou CNPJ");

        Button btnRecuperar = new Button("Recuperar");
        btnRecuperar.setOnAction(e -> {
            String user = tfUser.getText().trim();
            if (!usuarios.containsKey(user)) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Usuário não encontrado.");
            } else {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Senha", "Senha: " + usuarios.get(user));
                recuperarStage.close();
            }
        });

        box.getChildren().addAll(new Label("Digite seu usuário:"), tfUser, btnRecuperar);

        Scene scene = new Scene(box, 300, 150);
        recuperarStage.setScene(scene);
        recuperarStage.show();
    }

    // fim do login
    private boolean verificarAdmin() {
        return "admin".equals(usuarioLogado);
    }

    private void mostrarDashboard() {
        mainRoot = new BorderPane();

        VBox sidebar = new VBox(8);
        sidebar.setPadding(new Insets(16));
        sidebar.getStyleClass().add("sidebar");

        Button bClientes = criarBotaoSidebar("Clientes");
        Button bServicos = criarBotaoSidebar("Serviços");
        Button bRacoes = criarBotaoSidebar("Rações");
        Button bFuncionarios = criarBotaoSidebar("Funcionários");
        Button bAtendimento = criarBotaoSidebar("Iniciar Atendimento");
        Button bAgendamentos = criarBotaoSidebar("Agendamentos");
        Button bRecibos = criarBotaoSidebar("Recibos");
        Button bTema = criarBotaoSidebar("🎨 Trocar Tema");
        Button bSair = criarBotaoSidebar("Sair");

        bClientes.setOnAction(e -> mainRoot.setCenter(telaClientes()));
        bServicos.setOnAction(e -> mainRoot.setCenter(telaServicos()));
        bRacoes.setOnAction(e -> mainRoot.setCenter(telaRacoes()));
        bFuncionarios.setOnAction(e -> mainRoot.setCenter(telaFuncionarios()));
        bAtendimento.setOnAction(e -> mainRoot.setCenter(telaAtendimento()));
        bAgendamentos.setOnAction(e -> mainRoot.setCenter(telaAgendamentos()));
        bRecibos.setOnAction(e -> mainRoot.setCenter(telaRecibos()));
        bTema.setOnAction(e -> alternarTema());
        bSair.setOnAction(e -> {
            gerarRecibosAoSair();
            stage.close();
        });

        sidebar.getChildren().addAll(
                bClientes, bServicos, bRacoes, bFuncionarios,
                new Separator(), bAtendimento, bAgendamentos, bRecibos,
                new Separator(), bTema, bSair
        );

        mainRoot.setLeft(sidebar);
        mainRoot.setCenter(telaBemVindo());

        Scene scene = new Scene(mainRoot, 1200, 750);
        carregarCSS(scene);
        aplicarTema(scene);

        stage.setScene(scene);
        stage.setTitle("PetShop Petsec");
        stage.show();
    }

    private Label criarTitulo(String texto) {
        Label l = new Label(texto);
        l.getStyleClass().add("title");
        return l;
    }

    private Button criarBotaoSidebar(String texto) {
        Button b = new Button(texto);
        b.getStyleClass().add("menu-btn");
        b.setMaxWidth(Double.MAX_VALUE);
        return b;
    }

    private Pane telaBemVindo() {
        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(30));
        Label t1 = criarTitulo("Bem-vindo ao Sistema Petsec");
        Label t2 = new Label("Use o menu à esquerda para gerenciar o sistema.");
        box.getChildren().addAll(t1, t2);
        return box;
    }

    private Pane telaClientes() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(16));

        TableView<Cliente> tabela = new TableView<>(clientes);

        TableColumn<Cliente, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));

        TableColumn<Cliente, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Cliente, String> colNascimento = new TableColumn<>("Nascimento");
        colNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));

        tabela.getColumns().addAll(colNome, colEmail, colNascimento);

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(10));

        TextField tfNome = new TextField();
        TextField tfEmail = new TextField();
        TextField tfNascimento = new TextField();
        TextField tfEndereco = new TextField();

        Button btnCadastrar = new Button("Cadastrar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnRemover = new Button("Remover");

        // aplicar permissões: se não admin, desabilita remoção
        if (!verificarAdmin()) {
            btnRemover.setDisable(true);
        }

        form.add(new Label("Nome:"), 0, 0);
        form.add(tfNome, 1, 0);
        form.add(new Label("Email:"), 0, 1);
        form.add(tfEmail, 1, 1);
        form.add(new Label("Nascimento:"), 0, 2);
        form.add(tfNascimento, 1, 2);
        form.add(new Label("Endereço:"), 0, 3);
        form.add(tfEndereco, 1, 3);
        form.add(btnCadastrar, 1, 4);
        form.add(btnAtualizar, 1, 5);
        form.add(btnRemover, 1, 6);

        btnCadastrar.setOnAction(e -> {
            String nome = tfNome.getText().trim();
            if (nome.isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Nome não pode ser vazio.");
                return;
            }
            Cliente c = new Cliente(nome, tfEmail.getText().trim(), tfNascimento.getText().trim(), tfEndereco.getText().trim());
            clientes.add(c);
            tfNome.clear();
            tfEmail.clear();
            tfNascimento.clear();
            tfEndereco.clear();
        });

        btnAtualizar.setOnAction(e -> {
            Cliente sel = tabela.getSelectionModel().getSelectedItem();
            if (sel == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Selecione um cliente.");
                return;
            }
            if (!tfNome.getText().trim().isEmpty()) sel.setNomeCliente(tfNome.getText().trim());
            if (!tfEmail.getText().trim().isEmpty()) sel.setEmail(tfEmail.getText().trim());
            if (!tfNascimento.getText().trim().isEmpty()) sel.setDataNascimento(tfNascimento.getText().trim());
            if (!tfEndereco.getText().trim().isEmpty()) sel.setEndereco(tfEndereco.getText().trim());
            tabela.refresh();
        });

        btnRemover.setOnAction(e -> {
            Cliente sel = tabela.getSelectionModel().getSelectedItem();
            if (sel != null) clientes.remove(sel);
        });

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) {
                tfNome.setText(newV.getNomeCliente());
                tfEmail.setText(newV.getEmail());
                tfNascimento.setText(newV.getDataNascimento());
                tfEndereco.setText(newV.getEndereco());
            }
        });

        pane.setCenter(tabela);
        pane.setRight(form);
        return pane;
    }

    private Pane telaServicos() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(12));

        TableView<Servico> tabela = new TableView<>(servicos);

        TableColumn<Servico, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        TableColumn<Servico, Double> colPreco = new TableColumn<>("Preço");
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        tabela.getColumns().addAll(colNome, colPreco);

        TextField tfNome = new TextField();
        TextField tfPreco = new TextField();
        Button btnAdd = new Button("Adicionar");
        Button btnUpdate = new Button("Atualizar");
        Button btnRem = new Button("Remover");

        btnAdd.setOnAction(e -> {
            String n = tfNome.getText().trim();
            double p = parseDouble(tfPreco.getText(), -1);
            if (n.isEmpty() || p <= 0) { mostrarAlerta(Alert.AlertType.WARNING, "Dados inválidos", "Preencha nome e preço válido."); return; }
            servicos.add(new Servico(n, p));
            tfNome.clear();
            tfPreco.clear();
        });

        btnUpdate.setOnAction(e -> {
            Servico s = tabela.getSelectionModel().getSelectedItem();
            if (s == null) { mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Selecione serviço."); return; }
            if (!tfNome.getText().trim().isEmpty()) s.setNome(tfNome.getText().trim());
            if (!tfPreco.getText().trim().isEmpty()) s.setPreco(parseDouble(tfPreco.getText(), s.getPreco()));
            tabela.refresh();
        });

        btnRem.setOnAction(e -> {
            Servico s = tabela.getSelectionModel().getSelectedItem();
            if (s != null) servicos.remove(s);
        });

        VBox form = new VBox(8, new Label("Nome:"), tfNome, new Label("Preço:"), tfPreco, btnAdd, btnUpdate, btnRem);
        form.setPadding(new Insets(8));
        pane.setCenter(tabela);
        pane.setRight(form);
        return pane;
    }

    private Pane telaRacoes() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(12));

        TableView<Produto> tabela = new TableView<>(racoes);

        TableColumn<Produto, String> cNome = new TableColumn<>("Nome");
        cNome.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
        TableColumn<Produto, String> cDesc = new TableColumn<>("Descrição");
        cDesc.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        TableColumn<Produto, Double> cPreco = new TableColumn<>("Preço");
        cPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        tabela.getColumns().addAll(cNome, cDesc, cPreco);

        TextField tfNome = new TextField();
        TextField tfDesc = new TextField();
        TextField tfPreco = new TextField();
        Button btnAdd = new Button("Adicionar");
        Button btnUpdate = new Button("Atualizar");
        Button btnRem = new Button("Remover");

        // permissões: apenas admin pode adicionar/atualizar/remover rações
        if (!verificarAdmin()) {
            btnAdd.setDisable(true);
            btnUpdate.setDisable(true);
            btnRem.setDisable(true);
        }

        btnAdd.setOnAction(e -> {
            String n = tfNome.getText().trim();
            double p = parseDouble(tfPreco.getText(), -1);
            if (n.isEmpty() || p <= 0) { mostrarAlerta(Alert.AlertType.WARNING, "Dados inválidos", "Preencha nome e preço válido."); return; }
            racoes.add(new Produto(n, tfDesc.getText().trim(), p));
            tfNome.clear();
            tfDesc.clear();
            tfPreco.clear();
        });

        btnUpdate.setOnAction(e -> {
            Produto pr = tabela.getSelectionModel().getSelectedItem();
            if (pr == null) { mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Selecione produto."); return; }
            if (!tfNome.getText().trim().isEmpty()) pr.setNomeProduto(tfNome.getText().trim());
            if (!tfDesc.getText().trim().isEmpty()) pr.setDescricao(tfDesc.getText().trim());
            if (!tfPreco.getText().trim().isEmpty()) pr.setPreco(parseDouble(tfPreco.getText(), pr.getPreco()));
            tabela.refresh();
        });

        btnRem.setOnAction(e -> {
            Produto pr = tabela.getSelectionModel().getSelectedItem();
            if (pr != null) racoes.remove(pr);
        });

        VBox form = new VBox(8, new Label("Nome:"), tfNome, new Label("Descrição:"), tfDesc, new Label("Preço:"), tfPreco, btnAdd, btnUpdate, btnRem);
        form.setPadding(new Insets(8));
        pane.setCenter(tabela);
        pane.setRight(form);
        return pane;
    }

    private Pane telaFuncionarios() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(12));

        TableView<Funcionario> tabela = new TableView<>(funcionarios);

        TableColumn<Funcionario, String> cNome = new TableColumn<>("Nome");
        cNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        TableColumn<Funcionario, String> cFunc = new TableColumn<>("Função");
        cFunc.setCellValueFactory(new PropertyValueFactory<>("funcao"));

        tabela.getColumns().addAll(cNome, cFunc);

        TextField tfNome = new TextField();
        TextField tfFunc = new TextField();
        Button btnAdd = new Button("Adicionar");
        Button btnUpdate = new Button("Atualizar");
        Button btnRem = new Button("Remover");

        // permissões: apenas admin pode mexer em funcionários
        if (!verificarAdmin()) {
            btnAdd.setDisable(true);
            btnUpdate.setDisable(true);
            btnRem.setDisable(true);
        }

        btnAdd.setOnAction(e -> {
            String n = tfNome.getText().trim();
            String f = tfFunc.getText().trim();
            if (n.isEmpty() || f.isEmpty()) { mostrarAlerta(Alert.AlertType.WARNING, "Dados inválidos", "Preencha nome e função."); return; }
            funcionarios.add(new Funcionario(n, f));
            tfNome.clear();
            tfFunc.clear();
        });

        btnUpdate.setOnAction(e -> {
            Funcionario ff = tabela.getSelectionModel().getSelectedItem();
            if (ff == null) { mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Selecione funcionário."); return; }
            if (!tfNome.getText().trim().isEmpty()) ff.setNome(tfNome.getText().trim());
            if (!tfFunc.getText().trim().isEmpty()) ff.setFuncao(tfFunc.getText().trim());
            tabela.refresh();
        });

        btnRem.setOnAction(e -> {
            Funcionario f = tabela.getSelectionModel().getSelectedItem();
            if (f != null) funcionarios.remove(f);
        });

        VBox form = new VBox(8, new Label("Nome:"), tfNome, new Label("Função:"), tfFunc, btnAdd, btnUpdate, btnRem);
        form.setPadding(new Insets(8));
        pane.setCenter(tabela);
        pane.setRight(form);
        return pane;
    }

    private Pane telaAtendimento() {
        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);
        grid.setPadding(new Insets(16));

        ComboBox<Cliente> cbCliente = new ComboBox<>(clientes);
        ComboBox<Pet> cbPet = new ComboBox<>();
        cbCliente.setOnAction(e -> {
            Cliente sel = cbCliente.getValue();
            if (sel != null) cbPet.setItems(FXCollections.observableArrayList(sel.getPets()));
            else cbPet.setItems(FXCollections.emptyObservableList());
        });

        ListView<Servico> lvServicos = new ListView<>(servicos);
        lvServicos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvServicos.setPrefHeight(120);

        ComboBox<String> cbHorario = new ComboBox<>();
        atualizarHorarios(cbHorario);

        ComboBox<Produto> cbRacao = new ComboBox<>(racoes);
        TextField tfKg = new TextField();
        tfKg.setPromptText("Kg");

        CheckBox chEntrega = new CheckBox("Entrega (R$ 15,00)");

        ComboBox<String> cbPagamento = new ComboBox<>(FXCollections.observableArrayList("Crédito","Débito","Dinheiro","PIX"));
        cbPagamento.getSelectionModel().selectFirst();

        Button btnAgendar = new Button("Agendar");
        Button btnGerar = new Button("Gerar Recibo");

        TextArea taResumo = new TextArea();
        taResumo.setEditable(false);
        taResumo.setPrefHeight(200);

        btnAgendar.setOnAction(e -> {
            Cliente c = cbCliente.getValue();
            if (c == null) { mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Selecione cliente."); return; }
            Pet pet = cbPet.getValue();
            if (pet == null) { mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Selecione pet."); return; }
            List<Servico> selecionados = new ArrayList<>(lvServicos.getSelectionModel().getSelectedItems());
            if (selecionados.isEmpty()) { mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Selecione serviço."); return; }
            String horario = cbHorario.getValue();
            if (horario == null) { mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Selecione horário."); return; }
            String dataHoje = LocalDate.now().format(formatoData);
            boolean ocupado = agendamentos.stream().anyMatch(ag -> ag.getData().equals(dataHoje) && ag.getHorario().equals(horario));
            if (ocupado) { mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Horário ocupado."); atualizarHorarios(cbHorario); return; }
            Agendamento ag = new Agendamento(c, selecionados, pet, dataHoje, horario);
            agendamentos.add(ag);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Agendado!");
            atualizarHorarios(cbHorario);
        });

        btnGerar.setOnAction(e -> {
            Cliente c = cbCliente.getValue();
            Pet pet = cbPet.getValue();
            List<Servico> selecionados = new ArrayList<>(lvServicos.getSelectionModel().getSelectedItems());
            if (c == null || pet == null || selecionados.isEmpty()) { mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Preencha campos obrigatórios."); return; }
            String horario = cbHorario.getValue();
            if (horario == null) { mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Selecione horário."); return; }
            String dataHoje = LocalDate.now().format(formatoData);
            boolean ocupado = agendamentos.stream().anyMatch(ag -> ag.getData().equals(dataHoje) && ag.getHorario().equals(horario));
            if (ocupado) { mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Horário ocupado."); atualizarHorarios(cbHorario); return; }

            double totalServicos = selecionados.stream().mapToDouble(Servico::getPreco).sum();
            double totalRacoes = 0;
            Produto pr = cbRacao.getValue();
            int kg = parseInt(tfKg.getText(), 0);
            if (pr != null && kg > 0) totalRacoes = pr.getPreco() * kg;
            double valorEntrega = chEntrega.isSelected() ? 15.00 : 0.0;
            double totalGeral = totalServicos + totalRacoes + valorEntrega;

            StringBuilder recibo = new StringBuilder();
            recibo.append("Cliente: ").append(c.getNomeCliente()).append("\n");
            recibo.append("Pet: ").append(pet.getNome()).append("\n");
            for (Servico s : selecionados) recibo.append(s.getNome()).append(" - R$ ").append(String.format("%.2f", s.getPreco())).append("\n");
            recibo.append("TOTAL: R$ ").append(String.format("%.2f", totalGeral)).append("\n");

            taResumo.setText(recibo.toString());
            recibos.add(recibo.toString());
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Recibo gerado!");
        });

        grid.add(new Label("Cliente:"), 0, 0);
        grid.add(cbCliente, 1, 0);
        grid.add(new Label("Pet:"), 0, 1);
        grid.add(cbPet, 1, 1);
        grid.add(new Label("Serviços:"), 0, 2);
        grid.add(lvServicos, 1, 2);
        grid.add(new Label("Horário:"), 0, 3);
        grid.add(cbHorario, 1, 3);
        grid.add(new Label("Ração:"), 0, 4);
        HBox hRacao = new HBox(8, cbRacao, tfKg);
        grid.add(hRacao, 1, 4);
        grid.add(chEntrega, 1, 5);
        grid.add(new Label("Pagamento:"), 0, 6);
        grid.add(cbPagamento, 1, 6);
        grid.add(btnAgendar, 0, 7);
        grid.add(btnGerar, 1, 7);
        grid.add(taResumo, 0, 8, 2, 1);

        return grid;
    }

    private void atualizarHorarios(ComboBox<String> cbHorario) {
        String dataHoje = LocalDate.now().format(formatoData);
        List<String> disponiveis = new ArrayList<>();
        for (String h : HORARIOS_DISPONIVEIS) {
            boolean ocupado = agendamentos.stream().anyMatch(ag -> ag.getData().equals(dataHoje) && ag.getHorario().equals(h));
            if (!ocupado) disponiveis.add(h);
        }
        cbHorario.setItems(FXCollections.observableArrayList(disponiveis));
        if (!disponiveis.isEmpty()) cbHorario.getSelectionModel().selectFirst();
    }

    private Pane telaAgendamentos() {
        VBox box = new VBox(8);
        box.setPadding(new Insets(12));

        TableView<Agendamento> tabela = new TableView<>(agendamentos);

        TableColumn<Agendamento, String> c1 = new TableColumn<>("Cliente");
        c1.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().getCliente().getNomeCliente()));
        TableColumn<Agendamento, String> c2 = new TableColumn<>("Pet");
        c2.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().getPet().getNome()));
        TableColumn<Agendamento, String> c3 = new TableColumn<>("Data");
        c3.setCellValueFactory(new PropertyValueFactory<>("data"));
        TableColumn<Agendamento, String> c4 = new TableColumn<>("Horário");
        c4.setCellValueFactory(new PropertyValueFactory<>("horario"));

        tabela.getColumns().addAll(c1,c2,c3,c4);

        box.getChildren().addAll(criarTitulo("Agendamentos"), tabela);
        return box;
    }

    private Pane telaRecibos() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(12));
        ListView<String> lv = new ListView<>(recibos);
        lv.setPrefHeight(400);

        Button btnSalvar = new Button("Exportar");
        btnSalvar.setOnAction(e -> {
            String selecionado = lv.getSelectionModel().getSelectedItem();
            if (selecionado == null) { mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Selecione recibo."); return; }
            FileChooser fc = new FileChooser();
            fc.setInitialFileName("recibo.txt");
            File f = fc.showSaveDialog(stage);
            if (f != null) {
                try (FileWriter fw = new FileWriter(f)) {
                    fw.write(selecionado);
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Salvo!");
                } catch (Exception ex) {
                    mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Falha ao salvar.");
                }
            }
        });

        box.getChildren().addAll(criarTitulo("Recibos"), lv, btnSalvar);
        return box;
    }

    private void mostrarAlerta(Alert.AlertType type, String titulo, String msg) {
        Alert a = new Alert(type);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    private void gerarRecibosAoSair() {
        System.out.println("\n===== RECIBOS =====");
        if (recibos.isEmpty()) System.out.println("Nenhum recibo.");
        else for (String r : recibos) System.out.println(r);
    }

    private void alternarTema() {
        temaAzul = !temaAzul;
        Scene s = stage.getScene();
        if (s != null) aplicarTema(s);
    }

    private void aplicarTema(Scene scene) {
        if (scene == null) return;
        Parent root = scene.getRoot();
        root.getStyleClass().removeAll("tema-azul", "tema-laranja");
        if (temaAzul) root.getStyleClass().add("tema-azul");
        else root.getStyleClass().add("tema-laranja");
        root.applyCss();
        root.layout();
    }

    private void carregarCSS(Scene scene) {
        try {
            String css = getClass().getResource("petshop.css").toExternalForm();
            System.out.println("✓ CSS carregado: " + css);
            scene.getStylesheets().add(css);
        } catch (Exception e1) {
            try {
                File cssFile = new File("bin/petshop.css");
                if (cssFile.exists()) {
                    String css = cssFile.toURI().toURL().toExternalForm();
                    System.out.println("✓ CSS carregado do arquivo: " + css);
                    scene.getStylesheets().add(css);
                }
            } catch (Exception e2) {
                System.err.println("✗ CSS não encontrado: " + e2.getMessage());
            }
        }
    }

    private static int parseInt(String s, int def) {
        try { return Integer.parseInt(s.trim()); } catch (Exception ex) { return def; }
    }

    private static double parseDouble(String s, double def) {
        try { return Double.parseDouble(s.trim().replace(",", ".")); } catch (Exception ex) { return def; }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
