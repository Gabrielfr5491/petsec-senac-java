package petsec_senac;

/**
 * Classe Pet – representa o animal de estimação do cliente.
 */
public class Pet {

    private int idPet;
    private String nome;
    private String nomePet;  // alias para nome (DAO compatibility)
    private String tipo; // cachorro, gato, etc.
    private int idade;
    private Integer idCliente;  // para compatibilidade com DAO
    private Cliente dono; // vínculo com o cliente

    public Pet() {}

    public Pet(String nome, String tipo, int idade) {
        this.nome = nome;
        this.nomePet = nome;
        this.tipo = tipo;
        this.idade = idade;
    }

    public int getIdPet() {
        return idPet;
    }

    public void setIdPet(int idPet) {
        this.idPet = idPet;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
        this.nomePet = nome;  // keep in sync
    }

    public String getNomePet() {
        return nomePet;
    }

    public void setNomePet(String nomePet) {
        this.nomePet = nomePet;
        this.nome = nomePet;  // keep in sync
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Cliente getDono() {
        return dono;
    }

    public void setDono(Cliente dono) {
        this.dono = dono;
    }

    /**
     * Exibe dados simples do pet (uma linha).
     */
    public void exibirDadosSimples() {
        System.out.println(nome + " (" + tipo + "), " + idade + " anos");
    }

    /**
     * Exibe dados completos do pet.
     */
    public void exibirDados() {
        System.out.println("Nome do Pet: " + nome);
        System.out.println("Tipo: " + tipo);
        System.out.println("Idade: " + idade + " anos");
        if (dono != null) {
            System.out.println("Dono: " + dono.getNomeCliente());
        }
    }
}
