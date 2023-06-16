abstract class Fibra extends Servico {
    private String nome;

    public Fibra() {
        this.nome = "Fibra";
        setValor(150.0);
    }

    public String getNome() {
        return nome;
    }
}