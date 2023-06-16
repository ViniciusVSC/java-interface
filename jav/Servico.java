abstract class Servico {
    private double valor;

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public abstract String getNome();

    public abstract String getTipo();
}
