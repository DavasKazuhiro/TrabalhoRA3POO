package Interfaces;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.*;

import Modelos.Medico;
import Modelos.Paciente;
import Modelos.Consulta;


public class TelaLoginMedico extends JFrame {

    public TelaLoginMedico(List<Medico> medicos, List<Paciente> pacientes, List<Consulta> consultas) {
        setTitle("Login - Médico");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Login do Médico", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(titulo, BorderLayout.NORTH);

        JPanel painelCampos = new JPanel(new GridLayout(2, 2));
        JLabel lblID = new JLabel("ID do Médico:");
        JTextField txtID = new JTextField();
        JLabel lblSenha = new JLabel("Senha:");
        JPasswordField txtSenha = new JPasswordField();

        painelCampos.add(lblID);
        painelCampos.add(txtID);
        painelCampos.add(lblSenha);
        painelCampos.add(txtSenha);

        add(painelCampos, BorderLayout.CENTER);

        JButton btnEntrar = new JButton("Entrar");
        add(btnEntrar, BorderLayout.SOUTH);

        btnEntrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(txtID.getText());
                    String senha = new String(txtSenha.getPassword());

                    for (Medico m : medicos) {
                        if (m.getIdMedico() == id && m.getSenha().equals(senha)) {
                            JOptionPane.showMessageDialog(null, "Login bem-sucedido!");
                            new InterfaceMedico(m, consultas, medicos, pacientes);
                            dispose();
                            return;
                        }
                    }

                    JOptionPane.showMessageDialog(null, "ID ou senha incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "ID inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }
}