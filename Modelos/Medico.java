package Modelos;

import java.io.Serializable;
import java.util.ArrayList;

public class Medico extends Pessoa implements Serializable {
    private int id_medico;
    private String senha;
    private ArrayList<Paciente> pacientes = new ArrayList<>();

    public Medico(String nome, int id_medico, String senha) {
        super(nome); 
        this.id_medico = id_medico;
        this.senha = senha;
    }

    public int getIdMedico() {
        return id_medico;
    }

    public String getSenha() {
        return senha;
    }

    public ArrayList<Paciente> getPacientes() {
        return new ArrayList<>(pacientes); 
    }

    public void adicionarPaciente(Paciente paciente) {
        pacientes.add(paciente);
    }

    @Override
    public String exibirInfo() {
        return "Médico: " + nome + " | ID: " + id_medico;
    }

    @Override
    public String toString() {
        return "Médico{" +
               "nome='" + nome + '\'' +
               ", id_medico=" + id_medico +
               '}';
    }
}