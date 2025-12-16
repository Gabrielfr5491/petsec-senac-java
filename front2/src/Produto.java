public class Produto {

    private int idProduto;        
    private String nomeProduto;   
    private String descricao;     
    private double preco;         
    private int estoque;          
    private int categoriaId;      

    // Construtor vazio
    public Produto() {}

    // Construtor completo
    public Produto(int idProduto, String nomeProduto, String descricao,
                   double preco, int estoque, int categoriaId) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.categoriaId = categoriaId;
    }

    // Novo construtor simplificado usado no JavaFX
    public Produto(String nomeProduto, String descricao, double preco) {
        this.nomeProduto = nomeProduto;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = 0;
        this.categoriaId = 0;
    }

    // Getters e setters
    public int getIdProduto() { return idProduto; }
    public void setIdProduto(int idProduto) { this.idProduto = idProduto; }

    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public int getEstoque() { return estoque; }
    public void setEstoque(int estoque) { this.estoque = estoque; }

    public int getCategoriaId() { return categoriaId; }
    public void setCategoriaId(int categoriaId) { this.categoriaId = categoriaId; }

    public void exibirDados() {
        System.out.println("=== Dados do Produto ===");
        System.out.println("ID: " + idProduto);
        System.out.println("Nome: " + nomeProduto);
        System.out.println("Descrição: " + descricao);
        System.out.println("Preço: R$ " + preco);
        System.out.println("Estoque: " + estoque);
        System.out.println("Categoria ID: " + categoriaId);
        System.out.println("------------------------");
    }
}
