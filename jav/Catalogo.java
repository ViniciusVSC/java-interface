import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Catalogo {
    private Servico[] servicos;

    public Catalogo() {
        servicos = new Servico[2];
        servicos[0] = new Fibra() {
            @Override
            public String getTipo() {
                return null;
            }
        };
        servicos[1] = new Antena() {
            @Override
            public String getTipo() {
                return null;
            }
        };
    }

    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Catálogo de Serviços ===");
        System.out.println("Escolha um serviço:");
        for (int i = 0; i < servicos.length; i++) {
            System.out.println((i + 1) + ". " + servicos[i].getNome());
        }
        System.out.println((servicos.length + 1) + ". Sair");

        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        if (opcao >= 1 && opcao <= servicos.length) {
            Servico selecionado = servicos[opcao - 1];
            System.out.println("Você escolheu o serviço: " + selecionado.getNome());
            System.out.println("Valor do serviço: R$" + selecionado.getValor());

            System.out.println("Escolha o plano:");
            System.out.println("1. Anual");
            System.out.println("2. Mensal");
            System.out.print("Opção: ");
            int plano = scanner.nextInt();
            scanner.nextLine();

        } else if (opcao == servicos.length + 1) {
            System.out.println("Saindo do catálogo...");
        } else {
            System.out.println("Opção inválida. Tente novamente.");
        }
    }

    private void gerarNotaFiscal(String servico, String plano, double valor) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("nota_fiscal.txt"))) {
            writer.write("=== Nota Fiscal ===");
            writer.newLine();
            writer.write("Serviço: " + servico);
            writer.newLine();
            writer.write("Plano: " + plano);
            writer.newLine();
            writer.write("Valor: R$" + valor);
            writer.newLine();

            System.out.println("Nota fiscal gerada com sucesso. Verifique o arquivo nota_fiscal.txt");
        } catch (IOException e) {
            System.out.println("Erro ao gerar a nota fiscal: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Catalogo catalogo = new Catalogo();
        catalogo.exibirMenu();
    }

    public Servico[] getServicos() {
        return servicos;
    }

}