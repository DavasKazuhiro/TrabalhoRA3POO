import java.util.ArrayList;
//import java.util.List;

public class Medico {
    private String nome;
    private int id_medico;
    private String senha;
    private ArrayList<Paciente> pacientes = new ArrayList<>();

    public Medico(String nome, int id_medico, String senha) {
        this.nome = nome;
        this.id_medico = id_medico;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public int getIdMedico() {
        return id_medico;
    }

    public ArrayList<Paciente> getPacientes() {
        return new ArrayList<>(pacientes); // retorna cópia para proteger a lista original
    }

    public void adicionarPaciente(Paciente paciente) {
        pacientes.add(paciente);
    }

    public String getSenha() {
        return senha;
    }
}
