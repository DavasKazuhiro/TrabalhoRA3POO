import java.util.ArrayList;

public class Paciente extends Pessoa {
    private String cpf;
    private ArrayList<Consulta> consultas = new ArrayList<>();

    public Paciente(String nome, String cpf) {
        super(nome); 
        this.cpf = cpf.trim();
    }

    public String getCpf() {
        return cpf;
    }

    public ArrayList<Consulta> getConsultas() {
        return new ArrayList<>(consultas); 
    }

    public void adicionarConsulta(Consulta consulta) {
        consultas.add(consulta);
    }
    
    @Override
    public String exibirInfo() {
        return "Paciente: " + nome + " | CPF: " + cpf;
    }

    @Override
    public String toString() {
        return "Paciente{" +
               "nome='" + nome + '\'' +
               ", cpf='" + cpf + '\'' +
               '}';
    }
}