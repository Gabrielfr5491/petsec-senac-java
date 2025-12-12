

// Classe que representa um tipo de serviço oferecido pelo pet shop
// Ex.: Banho, Tosa, Tosa Higiênica, Hidratação.
public class Servico {

    private String nome;
    private double preco;

    // Construtor vazio
    public Servico() {
    }

    // Construtor com parâmetros
    public Servico(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    // Getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    // Exibir dados do serviço
    public void exibirDados() {
        System.out.println("Serviço: " + nome + " | Preço: R$ " + preco);
    }
}
