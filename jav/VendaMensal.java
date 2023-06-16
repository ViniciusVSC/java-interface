public abstract class VendaMensal implements Venda {

    private String nome;

    public String getNome() {
        return nome;
    }

    public int metodoPagamento() {
        return 150;
    }
}