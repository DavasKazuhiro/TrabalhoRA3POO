import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;

public class MainP2 {
    public static void main(String[] args) {
        try {
            List<Medico> medicos = new ArrayList<>();
            List<Paciente> pacientes = new ArrayList<>();
            List<Consulta> consultas = new ArrayList<>();


            //Simulação by GPT: 
            // Simular médico
            Medico medico = new Medico( "Dr. Lucas",1, "123");
            medicos.add(medico);

            // Simular paciente
            Paciente paciente = new Paciente("João da Silva", "12345678900");
            pacientes.add(paciente);

            // Simular consulta
            Consulta consulta = new Consulta(
                LocalDate.of(2024, 6, 10),
                LocalTime.of(14, 0),
                paciente,
                medico
            );
            consultas.add(consulta);

            // Relacionamentos
            consulta.ligarConsultaPaciente(pacientes);
            consulta.ligarPacienteMedico(medicos, pacientes);

            /////////

            // Abrir GUI
            new TelaLogin(medicos, pacientes, consultas);

        } catch (Exception e) {
            System.out.println("Erro ao iniciar o programa: " + e.getMessage());
            e.printStackTrace();
        }
    }
}