import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LeitorTxt extends JFrame {
    private JTextField campoArquivo;
    private JTextField campoPalavra;
    private JButton botaoEscolherArquivo;
    private JButton botaoBuscar;

    private String caminhoArquivo = null;

    public LeitorTxt() {
        super("Contador de Palavras no Arquivo Txt");

        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245)); // cor de fundo da janela

        Font fonteCampos = new Font("Segoe UI", Font.PLAIN, 14);
        Font fonteBotoes = new Font("Segoe UI", Font.BOLD, 14);

        // Painel de seleção de arquivo
        JPanel painelArquivo = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelArquivo.setBackground(new Color(220, 230, 250));

        campoArquivo = new JTextField(20);
        campoArquivo.setEditable(false);
        campoArquivo.setFont(fonteCampos);

        botaoEscolherArquivo = new JButton("Escolher Arquivo");
        botaoEscolherArquivo.setFont(fonteBotoes);
        botaoEscolherArquivo.setBackground(new Color(100, 149, 237));
        botaoEscolherArquivo.setForeground(Color.WHITE);

        painelArquivo.add(new JLabel("Arquivo:"));
        painelArquivo.add(campoArquivo);
        painelArquivo.add(botaoEscolherArquivo);

        add(painelArquivo, BorderLayout.NORTH);

        // Painel de busca
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelBusca.setBackground(new Color(240, 255, 240));

        campoPalavra = new JTextField(20);
        campoPalavra.setFont(fonteCampos);

        botaoBuscar = new JButton("Buscar Palavra");
        botaoBuscar.setFont(fonteBotoes);
        botaoBuscar.setBackground(new Color(60, 179, 113));
        botaoBuscar.setForeground(Color.WHITE);

        painelBusca.add(new JLabel("Palavra:"));
        painelBusca.add(campoPalavra);
        painelBusca.add(botaoBuscar);

        add(painelBusca, BorderLayout.CENTER);

        // Rodapé
        JLabel rodape = new JLabel("Desenvolvido por Ísis, Gustavo e Henrique", SwingConstants.CENTER);
        rodape.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        rodape.setForeground(Color.DARK_GRAY);
        add(rodape, BorderLayout.SOUTH);

        // Ações dos botões
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
        setSize(520, 200);
        setResizable(false);
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
