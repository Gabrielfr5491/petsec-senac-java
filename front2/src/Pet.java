
/**
 * Classe Pet – representa o animal de estimação do cliente.
 */
public class Pet {

    private String nome;
    private String tipo; // cachorro, gato, etc.
    private int idade;
    private Cliente dono; // vínculo com o cliente

    public Pet() {}

    // Construtor utilizado pela interface JavaFX
    public Pet(String nome, String tipo, int idade) {
        this.nome = nome;
        this.tipo = tipo;
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
