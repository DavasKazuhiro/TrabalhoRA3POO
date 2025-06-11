import java.util.List;

import Modelos.Medico;
import Modelos.Paciente;
import Modelos.Consulta;

import Interfaces.TelaInicial;

import Dados.LeitorArquivo;

public class MainP2 {
    public static void main(String[] args) {
        // Declarar o leitor de arquivos
        LeitorArquivo leitor = new LeitorArquivo();

        // Declarar o caminho dos arquivos
        String basePath = System.getProperty("user.dir") + "/src/";
        // Se nao tiver usando Intellij e nao tem uma pasta src que o prorjeto esteja descomentar essa e comente a decima
        // String basePath = System.getProperty("user.dir") + "/";

        String caminho_medicos = basePath + "Dados/medicos.csv";
        String caminho_pacientes = basePath + "Dados/pacientes.csv";
        String caminho_consultas = basePath + "Dados/consultas.csv";

        // Ler dados dos arquivos
        List<Medico> medicos = leitor.lerMedicos(caminho_medicos);
        List<Paciente> pacientes = leitor.lerPacientes(caminho_pacientes);
        List<Consulta> consultas = leitor.lerConsultas(caminho_consultas, medicos, pacientes);

        // Conectar as consultas aos pacientes e médicos
        for (Consulta c : consultas) {
            c.ligarConsultaPaciente(pacientes);
            c.ligarPacienteMedico(medicos, pacientes);
        }

        // ✅ Iniciar a interface gráfica principal (tela inicial)
        new TelaInicial(medicos, pacientes, consultas);
    }
}