
/**
 * Classe Pagamento – representa um pagamento realizado no atendimento.
 * Guarda a forma de pagamento, o valor total e um status simples.
 */
public class Pagamento {

    private String metodo;     // Crédito, Débito, Dinheiro, PIX...
    private double valorTotal; // Valor total pago
    private String status;     // Ex.: "Concluído"

    public Pagamento(String metodo, double valorTotal) {
        this.metodo = metodo;
        this.valorTotal = valorTotal;
        this.status = "Concluído";
    }

    // Getters "originais"
    public String getMetodo() {
        return metodo;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public String getStatus() {
        return status;
    }

    /**
     * Métodos com os nomes esperados pela classe Pedido:
     * getMetodoPagamento(), getStatusPagamento(), getValorPago()
     * Eles apenas chamam os getters já existentes.
     */
    public String getMetodoPagamento() {
        return metodo;
    }

    public String getStatusPagamento() {
        return status;
    }

    public double getValorPago() {
        return valorTotal;
    }

    /**
     * Gera um pequeno resumo do pagamento para ser usado no recibo.
     */
    public String gerarResumo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Forma de pagamento: ").append(metodo).append("\n");
        sb.append("Valor pago: R$ ").append(valorTotal).append("\n");
        sb.append("Status do pagamento: ").append(status).append("\n");
        return sb.toString();
    }
}
