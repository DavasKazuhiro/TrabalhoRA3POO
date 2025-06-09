import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaLogin extends JFrame {
    private List<Medico> medicos;
    private List<Paciente> pacientes;
    private List<Consulta> consultas;

    private JRadioButton rbMedico;
    private JRadioButton rbPaciente;
    private JTextField tfIdOuCpf;
    private JPasswordField pfSenha;
    private JButton btnEntrar;

    public TelaLogin(List<Medico> medicos, List<Paciente> pacientes, List<Consulta> consultas) {
        this.medicos = medicos;
        this.pacientes = pacientes;
        this.consultas = consultas;

        setTitle("Login");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        rbMedico = new JRadioButton("Médico");
        rbPaciente = new JRadioButton("Paciente");
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbMedico);
        grupo.add(rbPaciente);

        JPanel painelRadio = new JPanel();
        painelRadio.add(rbMedico);
        painelRadio.add(rbPaciente);
        add(painelRadio);

        tfIdOuCpf = new JTextField(15);
        add(new JLabel("ID do Médico ou CPF do Paciente:"));
        add(tfIdOuCpf);

        pfSenha = new JPasswordField(15);
        add(new JLabel("Senha (somente para médico):"));
        add(pfSenha);

        btnEntrar = new JButton("Entrar");
        add(btnEntrar);

        btnEntrar.addActionListener(e -> autenticar());

        setVisible(true);
    }

    private void autenticar() {
        if (rbMedico.isSelected()) {
            try {
                int id = Integer.parseInt(tfIdOuCpf.getText().trim());
                String senha = new String(pfSenha.getPassword());

                for (Medico m : medicos) {
                    if (m.getIdMedico() == id && m.getSenha().equals(senha)) {
                        JOptionPane.showMessageDialog(this, "Bem-vindo Dr. " + m.getNome());
                        new InterfaceMedico(m, consultas); // <- chama GUI do médico
                        dispose();
                        return;
                    }
                }
                JOptionPane.showMessageDialog(this, "ID ou senha inválidos.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido.");
            }
        } else if (rbPaciente.isSelected()) {
            String cpf = tfIdOuCpf.getText().trim();

            for (Paciente p : pacientes) {
                if (p.getCpf().equals(cpf)) {
                    JOptionPane.showMessageDialog(this, "Bem-vindo " + p.getNome());
                    new InterfacePaciente(p, consultas); // <- chama GUI do paciente
                    dispose();
                    return;
                    }
            }
            JOptionPane.showMessageDialog(this, "CPF não encontrado.");
        } else {
            JOptionPane.showMessageDialog(this, "Selecione Médico ou Paciente.");
        }
    }
}