

/**
 * Classe Funcionário – representa um funcionário do Pet Shop.
 * Pensada para alunos iniciantes, com atributos simples e fáceis de entender.
 */
public class Funcionario {

    private String nome;
    private String funcao; // Ex.: "Atendente", "Tosador", "Entregador"

    public Funcionario() {}

    public Funcionario(String nome, String funcao) {
        this.nome = nome;
        this.funcao = funcao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    /**
     * Exibe informações do funcionário.
     */
    public void exibirDados() {
        System.out.println("Nome: " + nome);
        System.out.println("Função: " + funcao);
    }
}
