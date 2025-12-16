
import java.util.List;

// Classe que representa um agendamento de atendimento no pet shop
public class Agendamento {

    private Cliente cliente;
    private List<Servico> servicos; // agora guarda TODOS os serviços selecionados
    private Pet pet;
    private String data;    // formato dd-MM-yyyy
    private String horario; // ex.: 09:00

    public Agendamento(Cliente cliente, List<Servico> servicos, Pet pet, String data, String horario) {
        this.cliente = cliente;
        this.servicos = servicos;
        this.pet = pet;
        this.data = data;
        this.horario = horario;
    }

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
