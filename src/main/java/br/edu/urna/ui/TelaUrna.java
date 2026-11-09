package br.edu.urna.ui;

import br.edu.urna.modelo.Candidato;
import br.edu.urna.modelo.Cargo;
import br.edu.urna.modelo.Voto;
import br.edu.urna.servico.ServicoVotacao;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Map;

public class TelaUrna extends JFrame {
    private final ServicoVotacao servico;
    private final JLabel cargoLabel = new JLabel("", SwingConstants.CENTER);
    private final JLabel numeroLabel = new JLabel("Digite o número do candidato", SwingConstants.CENTER);
    private final JLabel candidatoLabel = new JLabel(" ", SwingConstants.CENTER);
    private final JTextField numeroCampo = new JTextField();
    private final JTextArea listaCandidatos = new JTextArea();
    private final JButton confirmar = new JButton("CONFIRMAR");
    private final JButton corrigir = new JButton("CORRIGIR");
    private final JButton branco = new JButton("VOTO EM BRANCO");
    private final JButton nulo = new JButton("VOTO NULO");

    public TelaUrna(ServicoVotacao servico) {
        this.servico = servico;
        setTitle("Urna Cidadã — Simulador Educativo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(760, 500);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(650, 430));
        montarInterface();
        atualizarTela();
    }

    private void montarInterface() {
        JPanel raiz = new JPanel(new BorderLayout(12, 12));
        raiz.setBorder(new EmptyBorder(16, 16, 16, 16));
        setContentPane(raiz);

        JLabel titulo = new JLabel("URNA CIDADÃ", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        raiz.add(titulo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridLayout(1, 2, 14, 0));
        JPanel painelVoto = new JPanel(new GridLayout(0, 1, 8, 8));
        painelVoto.setBorder(BorderFactory.createTitledBorder("Área de votação"));
        cargoLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        numeroLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        numeroCampo.setFont(new Font("Monospaced", Font.BOLD, 24));
        candidatoLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        painelVoto.add(cargoLabel);
        painelVoto.add(numeroLabel);
        painelVoto.add(numeroCampo);
        painelVoto.add(candidatoLabel);

        JPanel botoes = new JPanel(new GridLayout(2, 2, 8, 8));
        botoes.add(confirmar);
        botoes.add(corrigir);
        botoes.add(branco);
        botoes.add(nulo);
        painelVoto.add(botoes);

        listaCandidatos.setEditable(false);
        listaCandidatos.setFont(new Font("Monospaced", Font.PLAIN, 14));
        listaCandidatos.setBorder(BorderFactory.createTitledBorder("Candidatos fictícios"));
        centro.add(painelVoto);
        centro.add(new JScrollPane(listaCandidatos));
        raiz.add(centro, BorderLayout.CENTER);

        JLabel aviso = new JLabel("SIMULAÇÃO EDUCATIVA — não é uma urna oficial", SwingConstants.CENTER);
        aviso.setForeground(new Color(150, 50, 30));
        raiz.add(aviso, BorderLayout.SOUTH);

        confirmar.addActionListener(e -> confirmarVoto(false, false));
        corrigir.addActionListener(e -> {
            numeroCampo.setText("");
            candidatoLabel.setText("Digite novamente o número.");
            numeroCampo.requestFocusInWindow();
        });
        branco.addActionListener(e -> confirmarVoto(true, false));
        nulo.addActionListener(e -> confirmarVoto(false, true));
        numeroCampo.addActionListener(e -> mostrarCandidato());
        numeroCampo.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { mostrarCandidato(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { mostrarCandidato(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { mostrarCandidato(); }
        });
    }

    private void atualizarTela() {
        if (servico.finalizada()) {
            mostrarResumo();
            return;
        }
        Cargo cargo = servico.getCargoAtual();
        cargoLabel.setText(cargo.getNome() + " (" + (servico.getIndiceCargo() + 1)
                + "/" + servico.getTotalCargos() + ")");
        StringBuilder texto = new StringBuilder();
        for (Candidato c : cargo.getCandidatos()) {
            texto.append(c.getNumero()).append("  -  ").append(c.getNome())
                    .append("\n      ").append(c.getPartido()).append("\n\n");
        }
        listaCandidatos.setText(texto.toString());
        numeroCampo.setText("");
        candidatoLabel.setText(" ");
        numeroCampo.setEnabled(true);
        confirmar.setEnabled(true);
        corrigir.setEnabled(true);
        branco.setEnabled(true);
        nulo.setEnabled(true);
        numeroCampo.requestFocusInWindow();
    }

    private void mostrarCandidato() {
        if (servico.finalizada()) return;
        String numero = numeroCampo.getText().trim();
        if (numero.isEmpty()) {
            candidatoLabel.setText(" ");
            return;
        }
        Candidato candidato = servico.buscarCandidato(numero);
        if (candidato == null) {
            candidatoLabel.setText("Número não encontrado. Confira a lista.");
        } else {
            candidatoLabel.setText("<html><b>" + candidato.getNome() + "</b><br>"
                    + candidato.getPartido() + "</html>");
        }
    }

    private void confirmarVoto(boolean emBranco, boolean votoNulo) {
        if (servico.finalizada()) return;

        String numero;
        String nome;
        if (emBranco) {
            numero = "BRANCO";
            nome = "Voto em branco";
        } else if (votoNulo) {
            numero = "NULO";
            nome = "Voto nulo";
        } else {
            numero = numeroCampo.getText().trim();
            Candidato candidato = servico.buscarCandidato(numero);
            if (candidato == null) {
                JOptionPane.showMessageDialog(this, "Digite um número válido da lista.",
                        "Número inválido", JOptionPane.WARNING_MESSAGE);
                return;
            }
            nome = candidato.getNome();
        }

        int resposta = JOptionPane.showConfirmDialog(this,
                "Confirmar " + nome + " para " + servico.getCargoAtual().getNome() + "?",
                "Confirmação do voto", JOptionPane.YES_NO_OPTION);
        if (resposta != JOptionPane.YES_OPTION) return;

        try {
            Voto voto = servico.criarVoto(numero, nome);
            servico.registrarVoto(voto);
            if (!servico.finalizada()) {
                JOptionPane.showMessageDialog(this, "Voto registrado. Próximo cargo.");
                atualizarTela();
            } else {
                mostrarResumo();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Não foi possível salvar o voto. Nenhuma confirmação de gravação foi feita.\n"
                            + ex.getMessage(),
                    "Falha ao salvar", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarResumo() {
        StringBuilder resumo = new StringBuilder("Votação simulada finalizada!\n\n");
        for (Map.Entry<String, Map<String, Integer>> cargo : servico.resumoDaSessao().entrySet()) {
            resumo.append(cargo.getKey()).append(":\n");
            for (Map.Entry<String, Integer> opcao : cargo.getValue().entrySet()) {
                resumo.append("  ").append(opcao.getKey()).append(": ")
                        .append(opcao.getValue()).append("\n");
            }
            resumo.append("\n");
        }
        resumo.append("Este resumo considera somente esta execução do programa.");
        JOptionPane.showMessageDialog(this, resumo.toString(),
                "Resultado da simulação", JOptionPane.INFORMATION_MESSAGE);
        confirmar.setEnabled(false);
        corrigir.setEnabled(false);
        branco.setEnabled(false);
        nulo.setEnabled(false);
        numeroCampo.setEnabled(false);
        cargoLabel.setText("Votação encerrada");
        numeroLabel.setText("Obrigado por participar");
        candidatoLabel.setText(" ");
        listaCandidatos.setText("Simulação concluída.");
    }
}
