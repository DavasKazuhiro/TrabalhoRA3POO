package Interfaces;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.event.*;

import Modelos.Medico;
import Modelos.Paciente;
import Modelos.Consulta;

public class InterfaceMedico extends JFrame {
    private Medico medico;
    private List<Medico> medicos;
    private List<Paciente> pacientes;
    private List<Consulta> consultas;
    private JTextArea areaResultado;
    private JTextField campoNome;
    

    public InterfaceMedico(Medico medico, List<Consulta> consultas, List<Medico> medicos, List<Paciente> pacientes) {
        this.medico = medico;
        this.consultas = consultas;
        this.medicos = medicos;
        this.pacientes = pacientes;


        setTitle("Área do Médico - " + medico.getNome());
        setSize(950, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // === PAINEL SUPERIOR ===
        JPanel painelTopo = new JPanel(new FlowLayout());
        JButton btnVerConsultas = new JButton("Minhas Consultas");
        JButton btnHoje = new JButton("Consultas Hoje");
        JButton btnVerPacientes = new JButton("Meus Pacientes");
        JButton btnBuscarPorNome = new JButton("Buscar por Nome");
        campoNome = new JTextField(12);



        painelTopo.add(btnVerConsultas);
        painelTopo.add(btnHoje);
        painelTopo.add(btnVerPacientes);
        painelTopo.add(new JLabel("Nome do paciente:"));
        painelTopo.add(campoNome);
        painelTopo.add(btnBuscarPorNome);
        add(painelTopo, BorderLayout.NORTH);

        // === PAINEL CENTRAL ===
        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaResultado);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultado"));
        add(scroll, BorderLayout.CENTER);

        // === PAINEL INFERIOR ===
        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnExportar = new JButton("Exportar Consultas");
        JButton btnVoltar = new JButton("Voltar");

        painelInferior.add(btnExportar);
        painelInferior.add(btnVoltar);
        add(painelInferior, BorderLayout.SOUTH);

        // === AÇÕES ===
        btnVerConsultas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarConsultas();
            }
        });

        btnHoje.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarConsultasHoje();
            }
        });

        btnVerPacientes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarPacientes();
            }
        });

        btnBuscarPorNome.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarConsultasPorNome();
            }
        });

        btnExportar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exportarConsultas();
            }
        });

        btnVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TelaInicial(medicos, pacientes, consultas); 
                dispose();
            }
        });

        setVisible(true);
    }

    private void mostrarConsultas() {
        areaResultado.setText("");
        List<Consulta> minhas = consultas.stream()
            .filter(c -> c.getMedico().getIdMedico() == medico.getIdMedico())
            .collect(Collectors.toList());

        if (minhas.isEmpty()) {
            areaResultado.setText("Nenhuma consulta encontrada.");
        } else {
            for (Consulta c : minhas) {
                areaResultado.append(c.toString() + "\n");
            }
        }
    }

    private void mostrarConsultasHoje() {
        areaResultado.setText("");
        LocalDate hoje = LocalDate.now();
        List<Consulta> hojeConsultas = consultas.stream()
            .filter(c -> c.getMedico().getIdMedico() == medico.getIdMedico()
                      && c.getData().equals(hoje))
            .collect(Collectors.toList());

        if (hojeConsultas.isEmpty()) {
            areaResultado.setText("Nenhuma consulta para hoje.");
        } else {
            for (Consulta c : hojeConsultas) {
                areaResultado.append(c.toString() + "\n");
            }
        }
    }

    private void mostrarPacientes() {
        areaResultado.setText("");
        List<Paciente> pacientes = medico.getPacientes();
        if (pacientes.isEmpty()) {
            areaResultado.setText("Nenhum paciente encontrado.");
        } else {
            for (Paciente p : pacientes) {
                areaResultado.append(p.getNome() + " (CPF: " + p.getCpf() + ")\n");
            }
        }
    }

    private void buscarConsultasPorNome() {
        areaResultado.setText("");
        String nomeBuscado = campoNome.getText().trim().toLowerCase();

        List<Consulta> filtradas = consultas.stream()
            .filter(c -> c.getMedico().getIdMedico() == medico.getIdMedico()
                    && c.getPaciente().getNome().toLowerCase().contains(nomeBuscado))
            .collect(Collectors.toList());

        if (filtradas.isEmpty()) {
            areaResultado.setText("Nenhuma consulta encontrada para esse nome.");
        } else {
            for (Consulta c : filtradas) {
                areaResultado.append(c.toString() + "\n");
            }
        }
    }

    private void exportarConsultas() {
        try (FileWriter writer = new FileWriter(System.getProperty("user.dir") + "/src/Exportacoes/" + "consultas_medico_" + medico.getIdMedico() + ".txt")) {
            for (Consulta c : consultas) {
                if (c.getMedico().getIdMedico() == medico.getIdMedico()) {
                    writer.write(c.toString() + "\n");
                }
            }
            JOptionPane.showMessageDialog(this, "Consultas exportadas com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao exportar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}