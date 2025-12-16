package petsec_senac;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Cliente – representa um cliente do Pet Shop.
 * Agora o cliente também guarda uma lista de pets vinculados a ele.
 */
public class Cliente {

    private Integer idCliente;          // para compatibilidade com DAO
    private String nomeCliente;
    private String email;
    private String dataNascimentoString; // armazenado como texto "dd-MM-aaaa"
    private Date dataNascimento;        // sql.Date para compatibilidade com DAO
    private String endereco;

    // Lista de pets do cliente
    private List<Pet> pets = new ArrayList<>();

    public Cliente() {}

    public Cliente(String nomeCliente, String email, String dataNascimento, String endereco) {
        this.nomeCliente = nomeCliente;
        this.email = email;
        this.dataNascimentoString = dataNascimento;
        this.endereco = endereco;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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
        return dataNascimentoString;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimentoString = dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Date getDataNascimentoDate() {
        return dataNascimento;
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
