import Dados.LeitorArquivo;
import Modelos.Paciente;
import Modelos.Medico;
import Modelos.Consulta;

import java.io.File;
import java.util.List;

public class MainP1 {
    public static void main(String[] args) {
        try {
            File dirResultados = new File("Resultados");
            if (!dirResultados.exists()) {
                dirResultados.mkdir();
            }

            LeitorArquivo leitor = new LeitorArquivo();

            String basePath = System.getProperty("user.dir") + "/";
            
            System.out.println("Lendo arquivos CSV...");
            List<Medico> medicos = leitor.lerMedicos(basePath + "Dados/medicos.csv");
            List<Paciente> pacientes = leitor.lerPacientes(basePath + "Dados/pacientes.csv");
            List<Consulta> consultas = leitor.lerConsultas(basePath + "Dados/consultas.csv", medicos, pacientes);

            // Salvar em arquivos binários
            System.out.println("Salvando arquivos binários...");
            leitor.salvarMedicos(medicos, basePath + "Resultados/medicos.dat");
            leitor.salvarPacientes(pacientes, basePath + "Resultados/pacientes.dat");
            leitor.salvarConsultas(consultas, basePath + "Resultados/consultas.dat");

            // Exibir estatísticas
            System.out.println("\nEstatísticas:");
            System.out.println("Médicos processados: " + medicos.size());
            System.out.println("Pacientes processados: " + pacientes.size());
            System.out.println("Consultas processadas: " + consultas.size());
            System.out.println("\nDados salvos com sucesso em arquivos binários!");

        } catch (Exception e) {
            System.err.println("Erro durante a execução: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 