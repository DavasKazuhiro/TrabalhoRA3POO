import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.*;

public class TelaInicial extends JFrame {

    public TelaInicial(List<Medico> medicos, List<Paciente> pacientes, List<Consulta> consultas) {
        setTitle("Sistema de Consultas Médicas");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Título
        JLabel titulo = new JLabel("Bem-vindo! Escolha o tipo de acesso:", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        // Painel central com botões
        JPanel botoes = new JPanel(new GridLayout(3, 1, 15, 15));
        botoes.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton btnMedico = new JButton("Login Médico");
        JButton btnPaciente = new JButton("Login Paciente");
        JButton btnSecretaria = new JButton("Área da Secretaria");

        // Estilo dos botões
        Font fonteBotao = new Font("Arial", Font.BOLD, 14);
        btnMedico.setFont(fonteBotao);
        btnPaciente.setFont(fonteBotao);
        btnSecretaria.setFont(fonteBotao);

        botoes.add(btnMedico);
        botoes.add(btnPaciente);
        botoes.add(btnSecretaria);

        add(botoes, BorderLayout.CENTER);

        // Ações dos botões
        btnMedico.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TelaLoginMedico(medicos, pacientes, consultas);
                dispose();
            }
        });

        btnPaciente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TelaLoginPaciente(medicos, pacientes, consultas);
                dispose();
            }
        });

        btnSecretaria.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new InterfaceSecretaria(medicos, pacientes, consultas);
                dispose();
            }
        });

        setVisible(true);
    }
}