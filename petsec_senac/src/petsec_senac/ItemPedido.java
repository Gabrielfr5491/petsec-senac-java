package petsec_senac;

import java.math.BigDecimal;

//Classe que representa um item dentro de um pedido
//Exemplo: no pedido X, 2 unidades do produto "ração"
public class ItemPedido {

 // Atributos correspondentes à tabela "item_pedido"
 private Integer idPedido;        // id_pedido (FK, parte da PK) - para DAO
 private Integer idProduto;       // id_produto (FK, parte da PK) - para DAO
 private Pedido pedido;           // id_pedido (FK, parte da PK)
 private Produto produto;         // id_produto (FK, parte da PK)
 private int quantidade;          // quantidade
 private double precoUnitario;    // preco_unitario
 private BigDecimal precoUnitarioBD; // para compatibilidade com DAO

 // Construtor vazio
 public ItemPedido() {
 }

 // Construtor com parâmetros
 // Cria um item de pedido já ligando ao pedido e ao produto
 public ItemPedido(Pedido pedido, Produto produto,
                   int quantidade, double precoUnitario) {
     this.pedido = pedido;
     this.produto = produto;
     this.quantidade = quantidade;
     this.precoUnitario = precoUnitario;
     this.precoUnitarioBD = BigDecimal.valueOf(precoUnitario);
 }

 // DAO Getters e Setters
 public Integer getIdPedido() {
     return idPedido;
 }

 public void setIdPedido(int idPedido) {
     this.idPedido = idPedido;
 }

 public Integer getIdProduto() {
     return idProduto;
 }

 public void setIdProduto(int idProduto) {
     this.idProduto = idProduto;
 }

 public BigDecimal getPrecoUnitarioBD() {
     return precoUnitarioBD;
 }

 public void setPrecoUnitario(BigDecimal precoUnitario) {
     this.precoUnitarioBD = precoUnitario;
     this.precoUnitario = precoUnitario.doubleValue();
 }

 // Retorna o pedido deste item
 public Pedido getPedido() {
     return pedido;
 }

 // Define (altera) o pedido deste item
 public void setPedido(Pedido pedido) {
     this.pedido = pedido;
 }

 // Retorna o produto deste item
 public Produto getProduto() {
     return produto;
 }

 // Define (altera) o produto deste item
 public void setProduto(Produto produto) {
     this.produto = produto;
 }

 // Retorna a quantidade comprada deste produto
 public int getQuantidade() {
     return quantidade;
 }

 // Define (altera) a quantidade comprada
 public void setQuantidade(int quantidade) {
     this.quantidade = quantidade;
 }

 // Retorna o preço unitário do produto neste pedido (double)
 public double getPrecoUnitario() {
     return precoUnitario;
 }

 // Define (altera) o preço unitário do produto neste pedido (double)
 public void setPrecoUnitarioDouble(double precoUnitario) {
     this.precoUnitario = precoUnitario;
     this.precoUnitarioBD = BigDecimal.valueOf(precoUnitario);
 }

 // Calcula o subtotal deste item
 // Subtotal = quantidade * preço unitário
 public double calcularSubtotal() {
     return quantidade * precoUnitario;
 }

 // Mostra os dados deste item de pedido na tela
 public void exibirDados() {
     System.out.println("=== Item do Pedido ===");
     if (produto != null) {
         System.out.println("Produto: " + produto.getNomeProduto());
     }
     System.out.println("Quantidade: " + quantidade);
     System.out.println("Preço unitário: R$ " + precoUnitario);
     System.out.println("Subtotal: R$ " + calcularSubtotal());
     System.out.println("----------------------");
 }
}
