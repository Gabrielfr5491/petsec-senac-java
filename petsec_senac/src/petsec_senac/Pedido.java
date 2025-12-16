package petsec_senac;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

// Classe que representa um pedido/atendimento no pet shop
// Agora guarda serviços realizados e produtos comprados.
public class Pedido {

    private Integer idPedido;           // id_pedido (pode ser só um número sequencial)
    private Integer idCliente;          // para compatibilidade com DAO
    private Integer funcionarioId;      // para compatibilidade com DAO
    private Integer idPagamento;        // para compatibilidade com DAO
    private Integer idAgendamento;      // para compatibilidade com DAO
    private Integer entregadorId;       // para compatibilidade com DAO
    
    private Cliente cliente;        // cliente atendido
    private Pagamento pagamento;    // informações de pagamento
    private Timestamp dataPedido;   // data do atendimento (sql.Timestamp para DAO)
    private String statusPedido;    // status (ex.: "Concluído")
    private BigDecimal valorTotal;  // valor total final (serviços + produtos)
    private BigDecimal custoEntrega; // custo de entrega

    private Entrega entrega;        // se houver entrega de produtos (opcional)

    // Lista de itens de produto
    private List<ItemPedido> itens = new ArrayList<>();

    // Lista de serviços realizados
    private List<ServicoRealizado> servicos = new ArrayList<>();

    // Construtor padrão
    public Pedido() {}

    // Construtor com parâmetros (opcional - antigo)
    public Pedido(int idPedido, Cliente cliente, Pagamento pagamento,
                  String dataPedido, String statusPedido, double valorTotal) {
        this.idPedido = idPedido;
        this.cliente = cliente;
        this.pagamento = pagamento;
        this.statusPedido = statusPedido;
        this.valorTotal = BigDecimal.valueOf(valorTotal);
    }

    // DAO Getters e Setters
    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(int funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public Integer getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(int idPagamento) {
        this.idPagamento = idPagamento;
    }

    public Integer getIdAgendamento() {
        return idAgendamento;
    }

    public void setIdAgendamento(int idAgendamento) {
        this.idAgendamento = idAgendamento;
    }

    public Integer getEntregadorId() {
        return entregadorId;
    }

    public void setEntregadorId(int entregadorId) {
        this.entregadorId = entregadorId;
    }

    public Timestamp getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Timestamp dataPedido) {
        this.dataPedido = dataPedido;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getCustoEntrega() {
        return custoEntrega;
    }

    public void setCustoEntrega(BigDecimal custoEntrega) {
        this.custoEntrega = custoEntrega;
    }

    // Adiciona um item de produto ao pedido
    public void adicionarItem(ItemPedido item) {
        itens.add(item);
    }

    // Adiciona um serviço realizado ao pedido
    public void adicionarServico(ServicoRealizado servico) {
        servicos.add(servico);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public String getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(String statusPedido) {
        this.statusPedido = statusPedido;
    }

    public Entrega getEntrega() {
        return entrega;
    }

    public void setEntrega(Entrega entrega) {
        this.entrega = entrega;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public List<ServicoRealizado> getServicos() {
        return servicos;
    }
    public String gerarTextoRecibo() {
        StringBuilder sb = new StringBuilder();

        sb.append("=====================================\n");
        sb.append("           RECIBO DO ATENDIMENTO\n");
        sb.append("=====================================\n");

        sb.append("Pedido/Atendimento ID: ").append(idPedido).append("\n");
        sb.append("Cliente: ").append(cliente.getNomeCliente()).append("\n");
        sb.append("Data do Atendimento: ").append(dataPedido).append("\n");
        sb.append("-------------------------------------\n");

        double totalServicos = 0;
        double totalProdutos = 0;

        // Lista de serviços realizados
        sb.append("SERVIÇOS REALIZADOS:\n");
        if (servicos.isEmpty()) {
            sb.append("(Nenhum serviço cadastrado)\n");
        } else {
            for (ServicoRealizado s : servicos) {
                sb.append("- ").append(s.getDescricao());
                if (s.getPet() != null) {
                    sb.append(" para o pet ").append(s.getPet().getNome());
                }
                sb.append("\n");
                sb.append("  Valor: R$ ").append(s.getValor()).append("\n\n");
                totalServicos += s.getValor();
            }
        }

        sb.append("-------------------------------------\n");
        sb.append("PRODUTOS COMPRADOS:\n");

        if (itens.isEmpty()) {
            sb.append("(Nenhum produto cadastrado)\n");
        } else {
            for (ItemPedido item : itens) {
                double subtotal = item.calcularSubtotal();
                totalProdutos += subtotal;

                sb.append("- Produto: ").append(item.getProduto().getNomeProduto()).append("\n");
                sb.append("  Quantidade: ").append(item.getQuantidade()).append("\n");
                sb.append("  Preço Unitário: R$ ").append(item.getPrecoUnitario()).append("\n");
                sb.append("  Subtotal: R$ ").append(subtotal).append("\n\n");
            }
        }

        sb.append("-------------------------------------\n");
        sb.append("Total em Serviços: R$ ").append(totalServicos).append("\n");
        sb.append("Total em Produtos: R$ ").append(totalProdutos).append("\n");
        sb.append("Subtotal Geral: R$ ").append(totalServicos + totalProdutos).append("\n");

        // Informações do pagamento (se preenchidas)
        if (pagamento != null) {
            sb.append("\nFORMA DE PAGAMENTO:\n");
            sb.append("Método: ").append(pagamento.getMetodoPagamento()).append("\n");
            sb.append("Status: ").append(pagamento.getStatusPagamento()).append("\n");
            sb.append("Valor Pago: R$ ").append(pagamento.getValorPago()).append("\n");
        }

        // Informações da entrega (se houver)
        if (entrega != null) {
            sb.append("\nINFORMAÇÕES DE ENTREGA:\n");
            sb.append("Status da Entrega: ").append(entrega.getEntregaStatus()).append("\n");
            sb.append("Data Envio: ").append(entrega.getDataEnvio()).append("\n");
            sb.append("Data Entrega: ").append(entrega.getDataEntregaRealizada()).append("\n");
        }

        sb.append("\n-------------------------------------\n");
        sb.append("TOTAL FINAL DO ATENDIMENTO: R$ ").append(valorTotal).append("\n");
        sb.append("=====================================\n");

        return sb.toString();
    }

    // Função que imprime o recibo no console
    public void emitirRecibo() {
        String texto = gerarTextoRecibo();
        System.out.println(texto);
    }

    // Getters e Setters básicos

    // Método simples para mostrar dados resumidos do pedido
    public void exibirDados() {
        System.out.println("=== Dados do Pedido/Atendimento ===");
        System.out.println("ID: " + idPedido);
        if (cliente != null) {
            System.out.println("Cliente: " + cliente.getNomeCliente());
        }
        System.out.println("Status: " + statusPedido);
        System.out.println("Valor total: R$ " + valorTotal);
        System.out.println("-----------------------");
    }
}
