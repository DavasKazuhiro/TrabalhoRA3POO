import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class InterfaceMedico extends JFrame {
    private Medico medico;
    private List<Consulta> consultas;
    private JTextArea areaResultado;
    private JTextField campoCpf;

    public InterfaceMedico(Medico medico, List<Consulta> consultas) {
        this.medico = medico;
        this.consultas = consultas;

        setTitle("Área do Médico - " + medico.getNome());
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel painelTopo = new JPanel(new FlowLayout());
        JButton btnVerConsultas = new JButton("Minhas Consultas");
        JButton btnVerPacientes = new JButton("Meus Pacientes");
        JButton btnBuscarPorCpf = new JButton("Consultas por CPF");

        campoCpf = new JTextField(10);

        painelTopo.add(btnVerConsultas);
        painelTopo.add(btnVerPacientes);
        painelTopo.add(new JLabel("CPF do paciente:"));
        painelTopo.add(campoCpf);
        painelTopo.add(btnBuscarPorCpf);
        add(painelTopo, BorderLayout.NORTH);

        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        add(new JScrollPane(areaResultado), BorderLayout.CENTER);

        btnVerConsultas.addActionListener(e -> mostrarConsultas());
        btnVerPacientes.addActionListener(e -> mostrarPacientes());
        btnBuscarPorCpf.addActionListener(e -> buscarConsultasPorCpf());

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

    private void buscarConsultasPorCpf() {
        areaResultado.setText("");
        String cpf = campoCpf.getText().trim();

        List<Consulta> filtradas = consultas.stream()
            .filter(c -> c.getMedico().getIdMedico() == medico.getIdMedico() &&
                         c.getPaciente().getCpf().equals(cpf))
            .collect(Collectors.toList());

        if (filtradas.isEmpty()) {
            areaResultado.setText("Nenhuma consulta encontrada para esse CPF.");
        } else {
            for (Consulta c : filtradas) {
                areaResultado.append(c.toString() + "\n");
            }
        }
    }
}