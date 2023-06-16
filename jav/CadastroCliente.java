import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CadastroCliente {
    public void cadastrarCliente(Empresa empresa) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Cadastro de Cliente ===");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Idade: ");
        int idade = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Estado: ");
        String estado = scanner.nextLine();

        empresa.getClientes().add(new Cliente(nome, idade, estado));

        System.out.println("Cliente cadastrado com sucesso!");
    }

    public void exportarClientesCSV(Empresa empresa, String arquivo) {
        try {
            FileWriter writer = new FileWriter(arquivo);
            writer.write("Nome,Idade,Estado\n");
            for (Cliente cliente : empresa.getClientes()) {
                writer.write(cliente.getNome() + "," + cliente.getIdade() + "," + cliente.getEstado() + "\n");
            }
            writer.close();
            System.out.println("Arquivo CSV exportado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao exportar arquivo CSV: " + e.getMessage());
        }
    }
}