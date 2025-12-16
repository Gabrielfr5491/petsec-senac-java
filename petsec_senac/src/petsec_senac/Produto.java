package petsec_senac;

import java.math.BigDecimal;

/**
 * Classe que representa um produto vendido no pet shop.
 * Compatível com o JavaFX (TableView, ComboBox e PropertyValueFactory).
 */
public class Produto {

    // Atributos
    private int idProduto;
    private String nomeProduto;
    private String descricao;
    private double preco;
    private BigDecimal precoProduto;  // para compatibilidade com DAO
    private int estoque;
    private Integer categoriaId;      // Integer para poder ser null

    // ===============================
    // CONSTRUTORES
    // ===============================

    // Construtor vazio (obrigatório para JavaFX)
    public Produto() {
    }

    // 🔹 CONSTRUTOR USADO NO PetshopFX (ESSENCIAL)
    public Produto(String nomeProduto, String descricao, double preco) {
        this.nomeProduto = nomeProduto;
        this.descricao = descricao;
        this.preco = preco;
        this.precoProduto = BigDecimal.valueOf(preco);
        this.estoque = 0;
        this.categoriaId = 0;
    }

    // Construtor completo (uso com banco de dados)
    public Produto(int idProduto, String nomeProduto, String descricao,
                   double preco, int estoque, int categoriaId) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.descricao = descricao;
        this.preco = preco;
        this.precoProduto = BigDecimal.valueOf(preco);
        this.estoque = estoque;
        this.categoriaId = categoriaId;
    }

    // ===============================
    // GETTERS (OBRIGATÓRIOS PARA TABLEVIEW)
    // ===============================

    public int getIdProduto() {
        return idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }

    public BigDecimal getPrecoProduto() {
        return precoProduto;
    }

    public int getEstoque() {
        return estoque;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    // ===============================
    // SETTERS
    // ===============================

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPreco(double preco) {
        this.preco = preco;
        this.precoProduto = BigDecimal.valueOf(preco);
    }

    public void setPrecoProduto(BigDecimal precoProduto) {
        this.precoProduto = precoProduto;
        this.preco = precoProduto.doubleValue();
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    // ===============================
    // MÉTODO AUXILIAR
    // ===============================

    public void exibirDados() {
        System.out.println("=== Produto ===");
        System.out.println("ID: " + idProduto);
        System.out.println("Nome: " + nomeProduto);
        System.out.println("Descrição: " + descricao);
        System.out.println("Preço: R$ " + preco);
        System.out.println("Estoque: " + estoque);
        System.out.println("Categoria ID: " + categoriaId);
        System.out.println("----------------------");
    }

    // ===============================
    // IMPORTANTE PARA ComboBox (JavaFX)
    // ===============================

    @Override
    public String toString() {
        return nomeProduto + " (R$ " + String.format("%.2f", preco) + ")";
    }
}
