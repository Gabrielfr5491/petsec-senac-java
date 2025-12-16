package petsec_senac;

import java.math.BigDecimal;

// Classe que representa um tipo de serviço oferecido pelo pet shop
// Ex.: Banho, Tosa, Tosa Higiênica, Hidratação.
public class Servico {

    private Integer idServico;
    private String nome;
    private String nomeServico;     // alias para compatibilidade com DAO
    private double preco;
    private BigDecimal precoServico; // para compatibilidade com DAO

    // Construtor vazio
    public Servico() {
    }

    // Construtor com parâmetros
    public Servico(String nome, double preco) {
        this.nome = nome;
        this.nomeServico = nome;
        this.preco = preco;
        this.precoServico = BigDecimal.valueOf(preco);
    }

    // DAO Getters e Setters
    public Integer getIdServico() {
        return idServico;
    }

    public void setIdServico(int idServico) {
        this.idServico = idServico;
    }

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
        this.nome = nomeServico;
    }

    public BigDecimal getPrecoServico() {
        return precoServico;
    }

    public void setPrecoServico(BigDecimal precoServico) {
        this.precoServico = precoServico;
        this.preco = precoServico.doubleValue();
    }

    // Getters e setters originais
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
        this.nomeServico = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
        this.precoServico = BigDecimal.valueOf(preco);
    }

    // Exibir dados do serviço
    public void exibirDados() {
        System.out.println("Serviço: " + nome + " | Preço: R$ " + preco);
    }
}
