import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class LeitorArquivo {

    public List<Medico> lerMedicos(String caminho) {
        List<Medico> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha = br.readLine(); // pular cabeçalho
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                String nome = partes[0];
                int id = Integer.parseInt(partes[1]);
                String senha = partes[2];
                Medico m = new Medico(nome, id, senha);
                lista.add(m);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Paciente> lerPacientes(String caminho) {
        List<Paciente> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha = br.readLine(); // pular cabeçalho
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                String nome = partes[0];
                String cpf = partes[1].trim();
                lista.add(new Paciente(nome, cpf));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Consulta> lerConsultas(String caminho, List<Medico> medicos, List<Paciente> pacientes) {
        List<Consulta> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha = br.readLine(); // pular cabeçalho
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                LocalDate data = LocalDate.parse(partes[0].trim());
                LocalTime hora = LocalTime.parse(partes[1].trim());
                String cpf = partes[2].trim();
                int id_medico = Integer.parseInt(partes[3].trim());

                Paciente paciente = new Paciente("Nome", "1234578901");
                Medico medico = new Medico("Medico", 0, "senha");

                for (Paciente p : pacientes){
                    if (p.getCpf().equals(cpf)){
                        paciente = p;
                        break;
                    }
                }

                for (Medico m : medicos){
                    if (m.getIdMedico() == id_medico){
                        medico = m;
                        break;
                    }
                }

                if (paciente != null && medico != null) {
                    lista.add(new Consulta(data, hora, paciente, medico));
                } else {
                    System.out.println("Erro: médico ou paciente não encontrados para a linha: " + linha);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }
}