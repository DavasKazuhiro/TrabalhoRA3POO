package Interfaces;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.event.*;

import Modelos.Medico;
import Modelos.Paciente;
import Modelos.Consulta;

public class InterfacePaciente extends JFrame {
    private Paciente paciente;
        private List<Medico> medicos;
    private List<Paciente> pacientes;
    private List<Consulta> consultas;
    private JTextArea areaResultado;
    private JTextField campoIdMedico;

    public InterfacePaciente(Paciente paciente, List<Consulta> consultas, List<Medico> medicos, List<Paciente> pacientes) {
        this.paciente = paciente;
        this.consultas = consultas;
        this.consultas = consultas;
        this.medicos = medicos;
        this.pacientes = pacientes;

        setTitle("Área do Paciente - " + paciente.getNome());
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel superior
        JPanel painelTopo = new JPanel(new FlowLayout());
        JButton btnVerTodas = new JButton("Minhas Consultas");
        JButton btnBuscarPorMedico = new JButton("Consultas com Médico");
        campoIdMedico = new JTextField(5);

        painelTopo.add(btnVerTodas);
        painelTopo.add(new JLabel("ID do médico:"));
        painelTopo.add(campoIdMedico);
        painelTopo.add(btnBuscarPorMedico);
        add(painelTopo, BorderLayout.NORTH);

        // Área de texto central
        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        add(new JScrollPane(areaResultado), BorderLayout.CENTER);

        // Painel inferior
        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnVoltar = new JButton("Voltar");
        JTextField campoIndiceConsulta = new JTextField(3);
        JButton btnGerarAutorizacaoIndividual = new JButton("Gerar Autorização da Consulta");

        painelInferior.add(new JLabel("Nº da consulta:"));
        painelInferior.add(campoIndiceConsulta);
        painelInferior.add(btnGerarAutorizacaoIndividual);
        painelInferior.add(btnVoltar);
        add(painelInferior, BorderLayout.SOUTH);

        // Ações
        btnVerTodas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarMinhasConsultas();
            }
        });

        btnBuscarPorMedico.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarPorMedico();
            }
        });

        btnVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TelaInicial(medicos, pacientes, consultas); 
                dispose();
            }
        });

        btnGerarAutorizacaoIndividual.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int indice = Integer.parseInt(campoIndiceConsulta.getText().trim()) - 1;

                    List<Consulta> minhas = consultas.stream()
                        .filter(c -> c.getPaciente().getCpf().equals(paciente.getCpf()))
                        .collect(Collectors.toList());

                    if (indice < 0 || indice >= minhas.size()) {
                        JOptionPane.showMessageDialog(null, "Índice inválido.");
                        return;
                    }

                    Consulta c = minhas.get(indice);
                    String texto =  "DECLARAÇÃO DE COMPARECIMENTO\n\n" +
                        "Declaramos para os devidos fins que o(a) paciente " + paciente.getNome() +
                        " está agendado(a) para uma consulta médica nesta instituição.\n\n" +
                        "Médico responsável: Dr(a). " + c.getMedico().getNome() + "\n" +
                        "Data da consulta: " + c.getData() + "\n" +
                        "Horário: " + c.getHora() + "\n\n" +
                        "Local: Clínica/Hospital XYZ\n" +
                        "Assinatura e carimbo: __________________________\n";

                    String nomeArquivo = "autorizacao_" + paciente.getNome().replaceAll(" ", "_") + "_consulta" + (indice + 1) + ".txt";
                    try (FileWriter writer = new FileWriter(nomeArquivo)) {
                        writer.write(texto);
                        JOptionPane.showMessageDialog(null, "Autorização salva em: " + nomeArquivo);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao salvar autorização: " + ex.getMessage());
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Digite um número válido.");
                }
            }
        });

        setVisible(true);
    }

    private void mostrarMinhasConsultas() {
        areaResultado.setText("");
        List<Consulta> minhas = consultas.stream()
            .filter(c -> c.getPaciente().getCpf().equals(paciente.getCpf()))
            .collect(Collectors.toList());

        if (minhas.isEmpty()) {
            areaResultado.setText("Você não possui consultas agendadas.");
        } else {
            for (int i = 0; i < minhas.size(); i++) {
                areaResultado.append("[" + (i + 1) + "] " + minhas.get(i).toString() + "\n");
            }
        }
    }

    private void buscarPorMedico() {
        areaResultado.setText("");
        try {
            int id = Integer.parseInt(campoIdMedico.getText().trim());

            List<Consulta> filtradas = consultas.stream()
                .filter(c -> c.getPaciente().getCpf().equals(paciente.getCpf()) &&
                             c.getMedico().getIdMedico() == id)
                .collect(Collectors.toList());

            if (filtradas.isEmpty()) {
                areaResultado.setText("Nenhuma consulta com esse médico.");
            } else {
                for (Consulta c : filtradas) {
                    areaResultado.append(c.toString() + "\n");
                }
            }
        } catch (NumberFormatException e) {
            areaResultado.setText("ID do médico inválido.");
        }
    }
}