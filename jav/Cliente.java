public class Cliente {
    private String nome;
    private int idade;
    private String estado;

    public Cliente(String nome, int idade, String estado) {
        this.nome = nome;
        this.idade = idade;
        this.estado = estado;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public String getEstado() {
        return estado;
    }

    public Empresa getServicoEscolhido() {
        return null;
    }

    public String getPlanoEscolhido() {
        return null;
    }

    public String calcularValorPlano() {
        return null;
    }
}