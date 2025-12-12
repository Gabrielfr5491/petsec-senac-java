

//Classe que representa um serviço realizado para um pet
//Exemplo: "Banho", "Tosa", "Consulta", etc.
public class ServicoRealizado {

 private String descricao;  // Ex.: "Banho", "Tosa completa"
 private double valor;      // Valor cobrado pelo serviço
 private Pet pet;           // Pet que recebeu o serviço

 // Construtor vazio
 public ServicoRealizado() {
 }

 // Construtor com parâmetros
 public ServicoRealizado(String descricao, double valor, Pet pet) {
     this.descricao = descricao;
     this.valor = valor;
     this.pet = pet;
 }

 // Getters e setters
 public String getDescricao() {
     return descricao;
 }

 public void setDescricao(String descricao) {
     this.descricao = descricao;
 }

 public double getValor() {
     return valor;
 }

 public void setValor(double valor) {
     this.valor = valor;
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
