

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Cliente – representa um cliente do Pet Shop.
 * Agora o cliente também guarda uma lista de pets vinculados a ele.
 */
public class Cliente {

    private String nomeCliente;
    private String email;
    private String dataNascimento; // armazenado como texto "dd-MM-aaaa"
    private String endereco;

    // Lista de pets do cliente
    private List<Pet> pets = new ArrayList<>();

    public Cliente() {}

    // Construtor usado pela interface JavaFX
    public Cliente(String nomeCliente, String email, String dataNascimento, String endereco) {
        this.nomeCliente = nomeCliente;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public List<Pet> getPets() {
        return pets;
    }

    /**
     * Adiciona um pet à lista de pets do cliente.
     */
    public void adicionarPet(Pet pet) {
        if (pet != null) {
            pets.add(pet);
        }
    }

    /**
     * Exibe os dados do cliente e, se houver, seus pets cadastrados.
     */
    public void exibirDados() {
        System.out.println("Nome: " + nomeCliente);
        System.out.println("Email: " + email);
        System.out.println("Data de Nascimento: " + dataNascimento);
        System.out.println("Endereço: " + endereco);

        if (pets.isEmpty()) {
            System.out.println("Pets: (nenhum cadastrado)");
        } else {
            System.out.println("Pets do cliente:");
            int i = 1;
            for (Pet p : pets) {
                System.out.print("  " + i + ") ");
                p.exibirDadosSimples();
                i++;
            }
        }
        System.out.println("-------------------------");
    }
}
