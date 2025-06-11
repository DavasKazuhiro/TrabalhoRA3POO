import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.awt.event.*;


public class InterfaceSecretaria extends JFrame {

    private List<Medico> medicos;
    private List<Paciente> pacientes;
    private List<Consulta> consultas;

    private JComboBox<String> cbMedicos;
    private JComboBox<String> cbPacientes;
    private JTextField tfData;
    private JTextField tfHora;
    private JTextArea areaConsultas;

    public InterfaceSecretaria(List<Medico> medicos, List<Paciente> pacientes, List<Consulta> consultas) {
        this.medicos = medicos;
        this.pacientes = pacientes;
        this.consultas = consultas;

        setTitle("Área da Secretaria");
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Painel formulário
        JPanel painelCadastro = new JPanel(new GridLayout(5, 2, 10, 10));
        painelCadastro.setBorder(BorderFactory.createTitledBorder("Agendar Nova Consulta"));

        cbMedicos = new JComboBox<>();
        for (Medico m : medicos) cbMedicos.addItem(m.getIdMedico() + " - " + m.getNome());

        cbPacientes = new JComboBox<>();
        for (Paciente p : pacientes) cbPacientes.addItem(p.getCpf() + " - " + p.getNome());

        tfData = new JTextField("2025-06-10");
        tfHora = new JTextField("14:00");

        painelCadastro.add(new JLabel("Médico:"));
        painelCadastro.add(cbMedicos);
        painelCadastro.add(new JLabel("Paciente:"));
        painelCadastro.add(cbPacientes);
        painelCadastro.add(new JLabel("Data (YYYY-MM-DD):"));
        painelCadastro.add(tfData);
        painelCadastro.add(new JLabel("Hora (HH:MM):"));
        painelCadastro.add(tfHora);

        JButton btnAgendar = new JButton("Agendar");
        JButton btnExportarTxt = new JButton("Exportar (.txt)");
        JButton btnExportarCsv = new JButton("Exportar (.csv)");
        JButton btnVoltar = new JButton("Voltar");

        JPanel painelBotoes = new JPanel(new GridLayout(1, 4, 10, 10));
        painelBotoes.add(btnAgendar);
        painelBotoes.add(btnExportarTxt);
        painelBotoes.add(btnExportarCsv);
        painelBotoes.add(btnVoltar);

        JPanel painelTopo = new JPanel();
        painelTopo.setLayout(new BoxLayout(painelTopo, BoxLayout.Y_AXIS));
        painelTopo.add(painelCadastro);
        painelTopo.add(Box.createVerticalStrut(10));
        painelTopo.add(painelBotoes);
        add(painelTopo, BorderLayout.NORTH);

        // Área de exibição das consultas
        areaConsultas = new JTextArea();
        areaConsultas.setEditable(false);
        atualizarListaConsultas();
        JScrollPane scroll = new JScrollPane(areaConsultas);
        scroll.setBorder(BorderFactory.createTitledBorder("Consultas Agendadas"));
        add(scroll, BorderLayout.CENTER);

        // Ações
        btnAgendar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agendarConsulta();
            }
        });

        btnExportarTxt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exportarConsultasTxt();
            }
        });

        btnExportarCsv.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exportarConsultasCsv();
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

    private void atualizarListaConsultas() {
        areaConsultas.setText("");
        for (Consulta c : consultas) {
            areaConsultas.append(c.toString() + "\n");
        }
    }

    private void agendarConsulta() {
        try {
            int medicoIndex = cbMedicos.getSelectedIndex();
            int pacienteIndex = cbPacientes.getSelectedIndex();
            Medico medico = medicos.get(medicoIndex);
            Paciente paciente = pacientes.get(pacienteIndex);

            LocalDate data = LocalDate.parse(tfData.getText().trim());
            LocalTime hora = LocalTime.parse(tfHora.getText().trim());

            Consulta nova = new Consulta(data, hora, paciente, medico);
            consultas.add(nova);
            nova.ligarConsultaPaciente(pacientes);
            nova.ligarPacienteMedico(medicos, pacientes);

            JOptionPane.showMessageDialog(this, "Consulta agendada com sucesso!");
            atualizarListaConsultas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao agendar consulta: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportarConsultasTxt() {
        try (FileWriter writer = new FileWriter("consultas_exportadas.txt")) {
            for (Consulta c : consultas) {
                writer.write(c.toString() + "\n");
            }
            JOptionPane.showMessageDialog(this, "Consultas exportadas para consultas_exportadas.txt");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao exportar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportarConsultasCsv() {
        try (FileWriter writer = new FileWriter("consultas_exportadas.csv")) {
            writer.write("Data,Hora,Paciente,CPF,Médico,ID\n");
            for (Consulta c : consultas) {
                writer.write(String.format("%s,%s,%s,%s,%s,%d\n",
                    c.getData(), c.getHora(),
                    c.getPaciente().getNome(),
                    c.getPaciente().getCpf(),
                    c.getMedico().getNome(),
                    c.getMedico().getIdMedico()));
            }
            JOptionPane.showMessageDialog(this, "Consultas exportadas para consultas_exportadas.csv");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao exportar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}