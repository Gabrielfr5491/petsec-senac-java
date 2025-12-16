package petsec_senac;

//Classe que representa um entregador (quem leva os pedidos até o cliente)
//Parecida com a tabela "entregador" do banco.
public class Entregador {

 // Atributos correspondentes às colunas da tabela "entregador"
 private int idEntregador;            // id_entregador
 private String nomeEntregador;       // nome_entregador
 private int tempoMedioEntregaDias;   // tempo_medio_entrega_dias
 private double valorEntrega;         // valor_entrega

 // Construtor vazio
 public Entregador() {
 }

 // Construtor com parâmetros
 // Serve para criar um Entregador já com todos os dados.
 public Entregador(int idEntregador, String nomeEntregador,
                   int tempoMedioEntregaDias, double valorEntrega) {
     this.idEntregador = idEntregador;
     this.nomeEntregador = nomeEntregador;
     this.tempoMedioEntregaDias = tempoMedioEntregaDias;
     this.valorEntrega = valorEntrega;
 }

 // Retorna o ID do entregador
 public int getIdEntregador() {
     return idEntregador;
 }

 // Define (altera) o ID do entregador
 public void setIdEntregador(int idEntregador) {
     this.idEntregador = idEntregador;
 }

 // Retorna o nome do entregador
 public String getNomeEntregador() {
     return nomeEntregador;
 }

 // Define (altera) o nome do entregador
 public void setNomeEntregador(String nomeEntregador) {
     this.nomeEntregador = nomeEntregador;
 }

 // Retorna o tempo médio de entrega em dias
 public int getTempoMedioEntregaDias() {
     return tempoMedioEntregaDias;
 }

 // Define (altera) o tempo médio de entrega em dias
 public void setTempoMedioEntregaDias(int tempoMedioEntregaDias) {
     this.tempoMedioEntregaDias = tempoMedioEntregaDias;
 }

 // Retorna o valor cobrado pela entrega
 public double getValorEntrega() {
     return valorEntrega;
 }

 // Define (altera) o valor cobrado pela entrega
 public void setValorEntrega(double valorEntrega) {
     this.valorEntrega = valorEntrega;
 }

 // Mostra os dados do entregador na tela
 public void exibirDados() {
     System.out.println("=== Dados do Entregador ===");
     System.out.println("ID: " + idEntregador);
     System.out.println("Nome: " + nomeEntregador);
     System.out.println("Tempo médio de entrega (dias): " + tempoMedioEntregaDias);
     System.out.println("Valor da entrega: R$ " + valorEntrega);
     System.out.println("---------------------------");
 }
}
