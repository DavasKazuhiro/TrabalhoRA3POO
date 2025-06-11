package Interfaces;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.*;

import Modelos.Medico;
import Modelos.Paciente;
import Modelos.Consulta;

public class TelaLoginPaciente extends JFrame {

    public TelaLoginPaciente(List<Medico> medicos, List<Paciente> pacientes, List<Consulta> consultas) {
        setTitle("Login - Paciente");
        setSize(350, 160);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Login do Paciente", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(titulo, BorderLayout.NORTH);

        JPanel painelCampos = new JPanel(new GridLayout(1, 2));
        JLabel lblCPF = new JLabel("CPF:");
        JTextField txtCPF = new JTextField();

        painelCampos.add(lblCPF);
        painelCampos.add(txtCPF);

        add(painelCampos, BorderLayout.CENTER);

        JButton btnEntrar = new JButton("Entrar");
        add(btnEntrar, BorderLayout.SOUTH);

        btnEntrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cpf = txtCPF.getText();

                for (Paciente p : pacientes) {
                    if (p.getCpf().equals(cpf)) {
                        JOptionPane.showMessageDialog(null, "Login bem-sucedido!");
                        new InterfacePaciente(p, consultas, medicos, pacientes);
                        dispose();
                        return;
                    }
                }

                JOptionPane.showMessageDialog(null, "CPF não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });



        setVisible(true);
    }
}