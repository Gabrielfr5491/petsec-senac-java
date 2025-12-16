package petsec_senac;

import java.math.BigDecimal;

/**
 * Classe Pagamento – representa um pagamento realizado no atendimento.
 * Guarda a forma de pagamento, o valor total e um status simples.
 */
public class Pagamento {

    private Integer idPagamento;    // para compatibilidade com DAO
    private String metodo;     // Crédito, Débito, Dinheiro, PIX...
    private double valorTotal; // Valor total pago
    private BigDecimal valorTotalBD; // para compatibilidade com DAO (BigDecimal)
    private String status;     // Ex.: "Concluído"

    // Construtor vazio (para DAO)
    public Pagamento() {
        this.status = "Concluído";
    }

    // Construtor com parâmetros
    public Pagamento(String metodo, double valorTotal) {
        this.metodo = metodo;
        this.valorTotal = valorTotal;
        this.valorTotalBD = BigDecimal.valueOf(valorTotal);
        this.status = "Concluído";
    }

    // DAO Getters e Setters
    public Integer getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(int idPagamento) {
        this.idPagamento = idPagamento;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotalBD = valorTotal;
        this.valorTotal = valorTotal.doubleValue();
    }

    // Getters "originais"
    public String getMetodo() {
        return metodo;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public BigDecimal getValorTotalBD() {
        return valorTotalBD;
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
