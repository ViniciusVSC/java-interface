import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Empresa {
    private String nome;
    private String apresentacao;
    private List<Cliente> clientes;

    public Empresa(String nome) {
        this.nome = nome;
        this.apresentacao = lerArquivoApresentacao();
        this.clientes = lerClientesCSV("clientes.csv");
    }

    public String getNome() {
        return nome;
    }

    public String getApresentacao() {
        return apresentacao;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    private String lerArquivoApresentacao() {
        StringBuilder conteudo = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("empresa.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                conteudo.append(linha).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao ler o arquivo de apresentação da empresa.");
            e.printStackTrace();
        }
        return conteudo.toString();
    }

    private List<Cliente> lerClientesCSV(String arquivo) {
        List<Cliente> clientes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            br.readLine();
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 3) {
                    String nome = dados[0];
                    int idade = Integer.parseInt(dados[1]);
                    String estado = dados[2];
                    clientes.add(new Cliente(nome, idade, estado));
                } else {
                    System.out.println("Dados inválidos encontrados na linha: " + linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de clientes: " + e.getMessage());
        }
        return clientes;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Empresa empresa = new Empresa("Rede Brasil");

        System.out.println(empresa.getApresentacao());

        CadastroCliente cadastro = new CadastroCliente();
        Catalogo catalogo = new Catalogo();

        while (true) {
            System.out.println("=== Menu Principal ===");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Exibir Clientes Cadastrados");
            System.out.println("3. Acessar Catálogo");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastro.cadastrarCliente(empresa);
                    cadastro.exportarClientesCSV(empresa, "clientes.csv");
                    break;
                case 2:
                    exibirClientes(empresa, "clientes.csv");
                    break;
                case 3:
                    catalogo.exibirMenu();
                    break;
                case 4:
                    System.out.println("Encerrando o programa...");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }

            System.out.println();
        }
    }

    public static void exibirClientes(Empresa empresa, String arquivo) {
        List<Cliente> clientes = empresa.getClientes();
        System.out.println("=== Clientes Cadastrados ===");
        for (Cliente cliente : clientes) {
            System.out.println(cliente.getNome() + " - Idade: " + cliente.getIdade() + " - Estado: " + cliente.getEstado());
        }
    }
}
