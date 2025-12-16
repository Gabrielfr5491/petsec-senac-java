package petsec_senac;

import java.math.BigDecimal;
import java.sql.Timestamp;

//Classe que representa a entrega de um pedido
//Parecida com a tabela "entrega" do banco.
public class Entrega {

 // Atributos correspondentes às colunas da tabela "entrega"
 private int idEntrega;               // id_entrega
 private Integer idPedido;            // id_pedido (FK, UNIQUE) - para DAO
 private Integer idEntregador;        // id_entregador (FK) - para DAO
 private Pedido pedido;               // id_pedido (FK, UNIQUE)
 private Entregador entregador;       // id_entregador (FK)
 private double custoEntrega;         // custo_entrega
 private BigDecimal custoEntregaBD;   // para compatibilidade com DAO
 private String dataEnvio;            // data_envio
 private Timestamp dataEnvioTS;       // para compatibilidade com DAO
 private String dataEntregaRealizada; // data_entrega_realizada
 private Timestamp dataEntregaRealizadaTS; // para compatibilidade com DAO
 private String entregaStatus;        // entrega_status

 // Construtor vazio
 public Entrega() {
 }

 // Construtor com parâmetros
 // Cria uma entrega já com todos os dados
 public Entrega(int idEntrega, Pedido pedido, Entregador entregador,
                double custoEntrega, String dataEnvio,
                String dataEntregaRealizada, String entregaStatus) {
     this.idEntrega = idEntrega;
     this.pedido = pedido;
     this.entregador = entregador;
     this.custoEntrega = custoEntrega;
     this.custoEntregaBD = BigDecimal.valueOf(custoEntrega);
     this.dataEnvio = dataEnvio;
     this.dataEntregaRealizada = dataEntregaRealizada;
     this.entregaStatus = entregaStatus;
 }

 // DAO Getters e Setters
 public Integer getIdPedido() {
     return idPedido;
 }

 public void setIdPedido(int idPedido) {
     this.idPedido = idPedido;
 }

 public Integer getIdEntregador() {
     return idEntregador;
 }

 public void setIdEntregador(int idEntregador) {
     this.idEntregador = idEntregador;
 }

 public BigDecimal getCustoEntregaBD() {
     return custoEntregaBD;
 }

 public void setCustoEntrega(BigDecimal custoEntrega) {
     this.custoEntregaBD = custoEntrega;
     this.custoEntrega = custoEntrega.doubleValue();
 }

 public Timestamp getDataEnvioTS() {
     return dataEnvioTS;
 }

 public void setDataEnvio(Timestamp dataEnvio) {
     this.dataEnvioTS = dataEnvio;
 }

 public Timestamp getDataEntregaRealizadaTS() {
     return dataEntregaRealizadaTS;
 }

 public void setDataEntregaRealizada(Timestamp dataEntrega) {
     this.dataEntregaRealizadaTS = dataEntrega;
 }

 // Retorna o ID da entrega
 public int getIdEntrega() {
     return idEntrega;
 }

 // Define (altera) o ID da entrega
 public void setIdEntrega(int idEntrega) {
     this.idEntrega = idEntrega;
 }

 // Retorna o pedido associado a esta entrega
 public Pedido getPedido() {
     return pedido;
 }

 // Define (altera) o pedido desta entrega
 public void setPedido(Pedido pedido) {
     this.pedido = pedido;
 }

 // Retorna o entregador que faz esta entrega
 public Entregador getEntregador() {
     return entregador;
 }

 // Define (altera) o entregador desta entrega
 public void setEntregador(Entregador entregador) {
     this.entregador = entregador;
 }

 // Retorna o custo da entrega (double)
 public double getCustoEntrega() {
     return custoEntrega;
 }

 // Define (altera) o custo da entrega (double)
 public void setCustoEntregaDouble(double custoEntrega) {
     this.custoEntrega = custoEntrega;
     this.custoEntregaBD = BigDecimal.valueOf(custoEntrega);
 }

 // Retorna a data de envio do pedido (String)
 public String getDataEnvio() {
     return dataEnvio;
 }

 // Define (altera) a data de envio do pedido (String)
 public void setDataEnvioString(String dataEnvio) {
     this.dataEnvio = dataEnvio;
 }

 // Retorna a data em que a entrega foi realizada (String)
 public String getDataEntregaRealizada() {
     return dataEntregaRealizada;
 }

 // Define (altera) a data em que a entrega foi realizada (String)
 public void setDataEntregaRealizadaString(String dataEntregaRealizada) {
     this.dataEntregaRealizada = dataEntregaRealizada;
 }

 // Retorna o status da entrega (ex: entregue, em rota)
 public String getEntregaStatus() {
     return entregaStatus;
 }

 // Define (altera) o status da entrega
 public void setEntregaStatus(String entregaStatus) {
     this.entregaStatus = entregaStatus;
 }

 // Mostra os dados da entrega na tela
 public void exibirDados() {
     System.out.println("=== Dados da Entrega ===");
     System.out.println("ID entrega: " + idEntrega);

     if (pedido != null) {
         System.out.println("Pedido ID: " + pedido.getIdPedido());
     }

     if (entregador != null) {
         System.out.println("Entregador: " + entregador.getNomeEntregador());
     }

     System.out.println("Custo da entrega: R$ " + custoEntrega);
     System.out.println("Data de envio: " + dataEnvio);
     System.out.println("Data de entrega realizada: " + dataEntregaRealizada);
     System.out.println("Status da entrega: " + entregaStatus);
     System.out.println("------------------------");
 }
}
