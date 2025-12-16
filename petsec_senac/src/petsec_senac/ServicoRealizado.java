package petsec_senac;

import java.math.BigDecimal;

//Classe que representa um serviço realizado para um pet
//Exemplo: "Banho", "Tosa", "Consulta", etc.
public class ServicoRealizado {

 private Integer idServicoRealizado; // para compatibilidade com DAO
 private Integer idPedido;           // para compatibilidade com DAO
 private Integer idServico;          // para compatibilidade com DAO
 private Integer idPet;              // para compatibilidade com DAO
 private Integer funcionarioId;      // para compatibilidade com DAO

 private String descricao;  // Ex.: "Banho", "Tosa completa"
 private double valor;      // Valor cobrado pelo serviço
 private BigDecimal valorBD; // para compatibilidade com DAO
 private Pet pet;           // Pet que recebeu o serviço

 // Construtor vazio
 public ServicoRealizado() {
 }

 // Construtor com parâmetros
 public ServicoRealizado(String descricao, double valor, Pet pet) {
     this.descricao = descricao;
     this.valor = valor;
     this.valorBD = BigDecimal.valueOf(valor);
     this.pet = pet;
 }

 // DAO Getters e Setters
 public Integer getIdServicoRealizado() {
     return idServicoRealizado;
 }

 public void setIdServicoRealizado(int idServicoRealizado) {
     this.idServicoRealizado = idServicoRealizado;
 }

 public Integer getIdPedido() {
     return idPedido;
 }

 public void setIdPedido(int idPedido) {
     this.idPedido = idPedido;
 }

 public Integer getIdServico() {
     return idServico;
 }

 public void setIdServico(int idServico) {
     this.idServico = idServico;
 }

 public Integer getIdPet() {
     return idPet;
 }

 public void setIdPet(int idPet) {
     this.idPet = idPet;
 }

 public Integer getFuncionarioId() {
     return funcionarioId;
 }

 public void setFuncionarioId(int funcionarioId) {
     this.funcionarioId = funcionarioId;
 }

 public BigDecimal getValorBD() {
     return valorBD;
 }

 public void setValor(BigDecimal valor) {
     this.valorBD = valor;
     this.valor = valor.doubleValue();
 }

 // Getters e setters originais
 public String getDescricao() {
     return descricao;
 }

 public void setDescricao(String descricao) {
     this.descricao = descricao;
 }

 public double getValor() {
     return valor;
 }

 public void setValorDouble(double valor) {
     this.valor = valor;
     this.valorBD = BigDecimal.valueOf(valor);
 }

 public Pet getPet() {
     return pet;
 }

 public void setPet(Pet pet) {
     this.pet = pet;
 }

 // Mostra os dados do serviço na tela
 public void exibirDados() {
     System.out.println("=== Serviço Realizado ===");
     System.out.println("Descrição: " + descricao);
     System.out.println("Valor: R$ " + valor);
     if (pet != null) {
         System.out.println("Pet: " + pet.getNome() + " (" + pet.getTipo() + ")");
     }
     System.out.println("-------------------------");
 }
}
