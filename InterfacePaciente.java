import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class InterfacePaciente extends JFrame {
    private Paciente paciente;
    private List<Consulta> consultas;
    private JTextArea areaResultado;
    private JTextField campoIdMedico;

    public InterfacePaciente(Paciente paciente, List<Consulta> consultas) {
        this.paciente = paciente;
        this.consultas = consultas;

        setTitle("Área do Paciente - " + paciente.getNome());
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel painelTopo = new JPanel(new FlowLayout());
        JButton btnVerTodas = new JButton("Minhas Consultas");
        JButton btnBuscarPorMedico = new JButton("Consultas com Médico");
        campoIdMedico = new JTextField(5);

        painelTopo.add(btnVerTodas);
        painelTopo.add(new JLabel("ID do médico:"));
        painelTopo.add(campoIdMedico);
        painelTopo.add(btnBuscarPorMedico);
        add(painelTopo, BorderLayout.NORTH);

        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        add(new JScrollPane(areaResultado), BorderLayout.CENTER);

        btnVerTodas.addActionListener(e -> mostrarMinhasConsultas());
        btnBuscarPorMedico.addActionListener(e -> buscarPorMedico());

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
            for (Consulta c : minhas) {
                areaResultado.append(c.toString() + "\n");
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