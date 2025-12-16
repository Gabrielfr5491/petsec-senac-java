package petsec_senac;

import java.sql.Date;
import java.util.List;

// Classe que representa um agendamento de atendimento no pet shop
public class Agendamento {

    private Integer idAgendamento;
    private Integer idCliente;
    private Integer idPet;
    private Date dataAgendamento;    // sql.Date para compatibilidade com banco
    private String horario; // ex.: 09:00
    private String descricao;
    private boolean geraPedido;
    private Integer funcionarioId;
    
    // Campos antigos (compatibilidade)
    private Cliente cliente;
    private List<Servico> servicos; // agora guarda TODOS os serviços selecionados
    private Pet pet;
    private String data;    // formato dd-MM-yyyy

    // Construtor vazio (para DAO)
    public Agendamento() {}

    // Construtor antigo (mantido para compatibilidade)
    public Agendamento(Cliente cliente, List<Servico> servicos, Pet pet, String data, String horario) {
        this.cliente = cliente;
        this.servicos = servicos;
        this.pet = pet;
        this.data = data;
        this.horario = horario;
    }

    // GETTERS e SETTERS para atributos DAO
    public Integer getIdAgendamento() {
        return idAgendamento;
    }

    public void setIdAgendamento(Integer idAgendamento) {
        this.idAgendamento = idAgendamento;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdPet() {
        return idPet;
    }

    public void setIdPet(Integer idPet) {
        this.idPet = idPet;
    }

    public Date getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(Date dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isGeraPedido() {
        return geraPedido;
    }

    public void setGeraPedido(boolean geraPedido) {
        this.geraPedido = geraPedido;
    }

    public Integer getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Integer funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    // Getters antigos (compatibilidade)
    public Cliente getCliente() {
        return cliente;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public Pet getPet() {
        return pet;
    }

    public String getData() {
        return data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    // Exibe detalhes do agendamento, agora com TODOS os serviços
    public void exibirDados() {
        System.out.println("=== Detalhes do Agendamento ===");
        System.out.println("Cliente: " + cliente.getNomeCliente());
        if (pet != null) {
            System.out.println("Pet: " + pet.getNome() + " (" + pet.getTipo() + ")");
        }

        System.out.println("Data: " + data);
        System.out.println("Horário: " + horario);

        System.out.println("Serviços agendados:");
        if (servicos == null || servicos.isEmpty()) {
            System.out.println("  (Nenhum serviço vinculado)");
        } else {
            for (Servico s : servicos) {
                System.out.println("  - " + s.getNome() + " (R$ " + s.getPreco() + ")");
            }
        }

        System.out.println("----------------------------");
    }
}
