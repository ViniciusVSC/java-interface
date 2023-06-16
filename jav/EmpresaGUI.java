import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class EmpresaGUI extends JFrame {
    private Empresa empresa;
    private CadastroCliente cadastro;
    private Catalogo catalogo;
    private Servico[] servicos;

    public EmpresaGUI() {
        empresa = new Empresa("Rede Brasil");
        cadastro = new CadastroCliente();
        catalogo = new Catalogo();
        servicos = catalogo.getServicos();

        setTitle("Empresa GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTextArea apresentacaoTextArea = new JTextArea(30, 15);
        apresentacaoTextArea.setText(empresa.getApresentacao());
        apresentacaoTextArea.setLineWrap(true);
        apresentacaoTextArea.setWrapStyleWord(true);
        apresentacaoTextArea.setEditable(false);
        JScrollPane apresentacaoScrollPane = new JScrollPane(apresentacaoTextArea);
        add(apresentacaoScrollPane, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel(new FlowLayout());

        JButton cadastrarButton = new JButton("Cadastrar Cliente");
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executarOpcao(1);
            }
        });
        menuPanel.add(cadastrarButton);

        JButton exibirButton = new JButton("Exibir Clientes Cadastrados");
        exibirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executarOpcao(2);
            }
        });
        menuPanel.add(exibirButton);

        JButton acessarCatalogoButton = new JButton("Acessar Catálogo");
        acessarCatalogoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executarOpcao(3);
            }
        });
        menuPanel.add(acessarCatalogoButton);

        JButton sairButton = new JButton("Sair");
        sairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executarOpcao(4);
            }
        });
        menuPanel.add(sairButton);

        add(menuPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void executarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                JFrame cadastrarClienteFrame = new JFrame("Cadastrar Cliente");
                JPanel cadastrarClientePanel = new JPanel(new GridLayout(4, 2));

                JLabel nomeLabel = new JLabel("Nome:");
                JTextField nomeField = new JTextField();
                cadastrarClientePanel.add(nomeLabel);
                cadastrarClientePanel.add(nomeField);

                JLabel idadeLabel = new JLabel("Idade:");
                JTextField idadeField = new JTextField();
                cadastrarClientePanel.add(idadeLabel);
                cadastrarClientePanel.add(idadeField);

                JLabel estadoLabel = new JLabel("Estado:");
                JTextField estadoField = new JTextField();
                cadastrarClientePanel.add(estadoLabel);
                cadastrarClientePanel.add(estadoField);

                int resultado = JOptionPane.showConfirmDialog(cadastrarClienteFrame, cadastrarClientePanel, "Cadastrar Cliente",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (resultado == JOptionPane.OK_OPTION) {
                    String nome = nomeField.getText();
                    int idade = Integer.parseInt(idadeField.getText());
                    String estado = estadoField.getText();

                    Cliente cliente = new Cliente(nome, idade, estado);
                    empresa.getClientes().add(cliente);

                    JOptionPane.showMessageDialog(cadastrarClienteFrame, "Cliente cadastrado com sucesso!");
                }
                break;

            case 2:
                JFrame exibirClientesFrame = new JFrame("Clientes Cadastrados");
                JTextArea clientesTextArea = new JTextArea();
                clientesTextArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(clientesTextArea);

                StringBuilder clientesTexto = new StringBuilder();
                List<Cliente> clientes = empresa.getClientes();
                clientesTexto.append("=== Clientes Cadastrados ===\n");
                for (Cliente cliente : clientes) {
                    clientesTexto.append(cliente.getNome()).append(" - Idade: ").append(cliente.getIdade())
                            .append(" - Estado: ").append(cliente.getEstado()).append("\n");
                }
                clientesTextArea.setText(clientesTexto.toString());

                exibirClientesFrame.add(scrollPane);
                exibirClientesFrame.setSize(300, 400);
                exibirClientesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                exibirClientesFrame.setVisible(true);
                break;

            case 3:
                exibirCatalogo();
                break;

            case 4:
                System.out.println("Encerrando o programa...");
                System.exit(0);
                break;

            default:
                System.out.println("Opção inválida. Tente novamente.");
                break;
        }
    }

    public void exibirCatalogo() {
        JFrame catalogoFrame = new JFrame("Catálogo de Serviços");
        JPanel servicosPanel = new JPanel(new GridLayout(servicos.length + 1, 1));

        JLabel escolhaLabel = new JLabel("Escolha um serviço:");
        servicosPanel.add(escolhaLabel);

        for (int i = 0; i < servicos.length; i++) {
            Servico servico = servicos[i];
            JButton servicoButton = new JButton(servico.getNome());
            servicoButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    exibirDetalhesServico(servico);
                    catalogoFrame.dispose(); // Fecha a janela do catálogo
                }
            });
            servicosPanel.add(servicoButton);
        }

        catalogoFrame.add(servicosPanel, BorderLayout.CENTER);

        catalogoFrame.pack();
        catalogoFrame.setLocationRelativeTo(this);
        catalogoFrame.setVisible(true);
    }

    public void exibirDetalhesServico(Servico servico) {
        JFrame detalhesFrame = new JFrame(servico.getNome());
        detalhesFrame.setLayout(new GridLayout(6, 1));

        JLabel nomeLabel = new JLabel(servico.getNome());
        JLabel valorLabel = new JLabel("Valor: R$" + servico.getValor());
        JLabel planoLabel = new JLabel("Escolha o plano:");
        JLabel resultadoLabel = new JLabel();

        detalhesFrame.add(nomeLabel);
        detalhesFrame.add(valorLabel);
        detalhesFrame.add(planoLabel);

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton planoAnualButton = new JButton("Plano Anual");
        planoAnualButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double valorAnual = servico.getValor() * 12;
                exibirResultadoPlano("Plano Anual", valorAnual);
                criarNotaFiscal(servico, "Plano Anual", valorAnual);
            }
        });
        JButton planoMensalButton = new JButton("Plano Mensal");
        planoMensalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double valorMensal = 150.0;
                exibirResultadoPlano("Plano Mensal", valorMensal);
                criarNotaFiscal(servico, "Plano Mensal", valorMensal);
            }
        });

        botoesPanel.add(planoAnualButton);
        botoesPanel.add(planoMensalButton);
        detalhesFrame.add(botoesPanel);
        detalhesFrame.add(resultadoLabel);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detalhesFrame.dispose(); // Fecha a janela de detalhes
            }
        });
        detalhesFrame.add(okButton);

        detalhesFrame.pack();
        detalhesFrame.setLocationRelativeTo(null);
        detalhesFrame.setVisible(true);
    }

    public void exibirResultadoPlano(String plano, double valor) {
        JOptionPane.showMessageDialog(this, "Plano selecionado: " + plano + "\nValor: R$" + valor);
    }

    public void criarNotaFiscal(Servico servico, String plano, double valor) {
        String nomeArquivo = "nota_fiscal.txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo));
            writer.write("=== Nota Fiscal ===\n");
            writer.write("Serviço: " + servico.getNome() + "\n");
            writer.write("Plano: " + plano + "\n");
            writer.write("Valor: R$" + valor + "\n");
            writer.close();
            JOptionPane.showMessageDialog(this, "Nota fiscal criada com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao criar a nota fiscal.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new EmpresaGUI();
            }
        });
    }
}
