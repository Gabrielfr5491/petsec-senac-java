package petsec_senac;

public class Funcionario {

    private int idFuncionario;
    private String nome;
    private String nomeFuncionario;  // alias para compatibilidade com DAO
    private String funcao;
    private boolean administrador; // novo campo: se é admin ou não

    // Construtor vazio (para DAO)
    public Funcionario() {}

    // Construtor antigo (continua existindo, mas assume NÃO administrador)
    public Funcionario(String nome, String funcao) {
        this(nome, funcao, false);
    }

    // Construtor completo
    public Funcionario(String nome, String funcao, boolean administrador) {
        this.nome = nome;
        this.nomeFuncionario = nome;
        this.funcao = funcao;
        this.administrador = administrador;
    }

    // GETTERS e SETTERS
    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
        this.nomeFuncionario = nome;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
        this.nome = nomeFuncionario;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public void exibirDados() {
        System.out.println("Nome: " + nome);
        System.out.println("Função: " + funcao);
        System.out.println("Administrador: " + (administrador ? "Sim" : "Não"));
    }
}
