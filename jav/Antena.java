abstract class Antena extends Servico {
    private String nome;

    public Antena() {
        this.nome = "Antena";
        setValor(150.0);
    }

    public String getNome() {
        return nome;
    }
}