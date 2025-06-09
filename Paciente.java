import java.util.ArrayList;

public class Paciente {
    private String nome;
    private String cpf; // trocado de double para String

    private ArrayList<Consulta> consultas = new ArrayList<>();

    public Paciente(String nome, String cpf){
            this.nome = nome;
            this.cpf = cpf.trim();
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public ArrayList<Consulta> getConsultas() {
        return new ArrayList<>(consultas); // cópia defensiva
    }

    public void adicionarConsulta(Consulta consulta) {
        consultas.add(consulta);
    }


}
