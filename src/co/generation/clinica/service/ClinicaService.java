package co.generation.clinica.service;

import co.generation.clinica.model.EstadoTurno;
import co.generation.clinica.model.Turno;
import co.generation.clinica.model.Medico;
import co.generation.clinica.model.Paciente;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class ClinicaService {
    private List <Paciente> pacientes = new ArrayList<>();
    private List <Medico> medicos = new ArrayList<>();
    private List <Turno> turnos = new ArrayList<>();

    public void registrarMedico(Medico m){
        if (!m.esValido()){
            System.out.println("Medico no valido. Verifique los datos");
            return;
        }

        if (medicos.contains(m)){
            System.out.println("Ya existe un medico con este nombre y apellido");
            return;
        }
        int nuevoId;
        if (medicos.isEmpty()){
            nuevoId = 1;
        } else {
            int maxId = 0;
            for (Medico medico: medicos){
                if (medico.getId() > maxId){
                    maxId = medico.getId();

                }
            }
            nuevoId = maxId +1;
        }
        m.setId(nuevoId);
        medicos.add(m);
        System.out.println("Medico registrado: "+ m);
    }

    public Medico buscarPorNombreApellido(String nombre, String apellido){
        for (Medico medico : medicos){
            if (medico.getNombre().equalsIgnoreCase(nombre)&&
                    medico.getApellido().equalsIgnoreCase(apellido)) {
                return medico;
            }
        }
        return null;

    }
    public void listarMedicos (){
        List<Medico> copia =  new ArrayList<>(medicos);
        if (copia.isEmpty()){
            System.out.println("No hay medicos registrados");
        }
        copia.sort(Comparator.comparing(Medico::getEspecialidad).thenComparing(Medico::getApellido));
        for (Medico medico : copia){
            System.out.println(medico);

        }
    }

    public void asignarTurno (Turno t){
        Paciente paciente = buscarPorCedula(t.getPaciente().getCedula());
        if (paciente == null){
            System.out.println("Paciente no encontrado");
            return;
        }
        Medico medico = buscarPorNombreApellido(t.getMedico().getNombre(),t.getMedico().getApellido());
        if (medico == null){
            System.out.println("Medico no encontrado");
            return;
        }
        if (turnos.contains(t)){
            System.out.println("Ya existe un turno con ese medico en esa fecha y hora");
            return;
        }

        int nuevoId;
        if (turnos.isEmpty()){
            nuevoId = 1;
        } else {
            int maxId = 0;
            for (Turno turno: turnos){
                if (turno.getId() > maxId){
                    maxId = turno.getId();

                }
            }
            nuevoId = maxId +1;
        }
        t.setId(nuevoId);
        turnos.add(t);
        System.out.println("Turno registrado: "+ t);

    }
    public void cancelarTurno (int idTurno){
        Turno encontrado = null;
        for (Turno turno : turnos){
            if (turno.getId()== idTurno){
                encontrado=turno;
            }
        }
        if (encontrado == null ){
            System.out.println("El turno no existe");
            return;
        }
        if (encontrado.getEstado () == EstadoTurno.ATENDIDO || encontrado.getEstado() == EstadoTurno.CANCELADO) {
            System.out.println("El turno no se puede cancelar");
            return;
        }
        encontrado.setEstado(EstadoTurno.CANCELADO);
        System.out.println("Turno cancelado: " + encontrado);

    }

}
