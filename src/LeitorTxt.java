import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LeitorTxt extends JFrame {
    private JTextField campoArquivo;
    private JTextField campoPalavra;
    private JButton botaoEscolherArquivo;
    private JButton botaoBuscar;

    private String caminhoArquivo = null;

    public LeitorTxt() {
        super("Contador de Palavras em Arquivo");

        setLayout(new BorderLayout());

        // Painel de seleção de arquivo
        JPanel painelArquivo = new JPanel();
        campoArquivo = new JTextField(20);
        campoArquivo.setEditable(false);
        botaoEscolherArquivo = new JButton("Escolher Arquivo");

        painelArquivo.add(new JLabel("Arquivo:"));
        painelArquivo.add(campoArquivo);
        painelArquivo.add(botaoEscolherArquivo);

        add(painelArquivo, BorderLayout.NORTH);

        // Painel de busca
        JPanel painelBusca = new JPanel();
        campoPalavra = new JTextField(20);
        botaoBuscar = new JButton("Buscar Palavra");

        painelBusca.add(new JLabel("Palavra:"));
        painelBusca.add(campoPalavra);
        painelBusca.add(botaoBuscar);

        add(painelBusca, BorderLayout.CENTER);

        // Configurações dos botões
        botaoEscolherArquivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                escolherArquivo();
            }
        });

        botaoBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPalavra();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 150);
        setLocationRelativeTo(null); // Centralizar a janela
        setVisible(true);
    }

    private void escolherArquivo() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Selecione um arquivo de texto");
        chooser.setFileFilter(new FileNameExtensionFilter("Arquivos de Texto (*.txt)", "txt"));

        int resultado = chooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            caminhoArquivo = chooser.getSelectedFile().getAbsolutePath();
            campoArquivo.setText(caminhoArquivo);
        }
    }

    private void buscarPalavra() {
        if (caminhoArquivo == null) {
            JOptionPane.showMessageDialog(this, "Por favor, escolha um arquivo primeiro!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String palavra = campoPalavra.getText().toLowerCase();

        if (palavra.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite uma palavra para buscar.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int contador = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.toLowerCase();
                int index = 0;
                while ((index = linha.indexOf(palavra, index)) != -1) {
                    contador++;
                    index += palavra.length();
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao ler o arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "A palavra \"" + palavra + "\" aparece " + contador + " vezes no arquivo.", "Resultado", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new LeitorTxt();
    }
}
