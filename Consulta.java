import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Consulta {
    private LocalDate data;
    private LocalTime hora;
    private Paciente paciente;
    private Medico medico;

    public Consulta(LocalDate data, LocalTime hora, Paciente paciente, Medico medico){
        this.data = data;
        this.hora = hora;
        this.medico = medico;
        this.paciente = paciente;
    }

    public void ligarConsultaPaciente(List<Paciente> pacientes){
        for(Paciente p : pacientes){
            if(p.getCpf().equals(this.paciente.getCpf())){
                p.adicionarConsulta(this);
            }
        }
    }

    public void ligarPacienteMedico(List<Medico> medicos, List<Paciente> pacientes){
        for(Medico m : medicos){
            if(this.medico.getIdMedico() == m.getIdMedico()){
                for(Paciente p : pacientes){
                    if(this.paciente.getCpf().equals(p.getCpf()) && !m.getPacientes().contains(p)){
                        m.adicionarPaciente(p);
                    }
                }
            }
        }
    }

    public Medico getMedico() {
        return medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getHora() {
        return hora;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
        return String.format(
            "Consulta com Dr. %s para %s (CPF: %s) em %s às %s",
            medico.getNome(),
            paciente.getNome(),
            paciente.getCpf(),
            data.format(formatoData),
            hora.format(formatoHora)
        );
    }
}