package co.generation.clinica.service;

import co.generation.clinica.interfaces.Consultable;
import co.generation.clinica.model.EstadoTurno;
import co.generation.clinica.model.Medico;
import co.generation.clinica.model.Paciente;
import co.generation.clinica.model.Turno;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClinicaService implements Consultable {

    private List<Paciente> pacientes;
    private List<Medico> medicos;
    private List<Turno> turnos;

    public ClinicaService() {
        this.pacientes = new ArrayList<>();
        this.medicos = new ArrayList<>();
        this.turnos = new ArrayList<>();
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

    public void registrarPaciente(Paciente p) {
        if (!p.esValido()) {
            System.out.println("Error: datos del paciente no válidos.");
            return;
        }

        if (pacientes.contains(p)) {
            System.out.println("Error: ya existe un paciente con esa cédula.");
            return;
        }

        int nuevoId = pacientes.isEmpty()
                ? 1
                : pacientes.stream()
                .mapToInt(Paciente::getId)
                .max()
                .getAsInt() + 1;

        p.setId(nuevoId);
        pacientes.add(p);

        System.out.println("Paciente registrado exitosamente:");
        System.out.println(p.getDatosRegistro());
    }

    public Paciente buscarPorCedula(String cedula) {
        for (Paciente p : pacientes) {
            if (p.getCedula().equals(cedula)) {
                return p;
            }
        }
        return null;
    }

    public void listarPacientes() {
        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
            return;
        }

        List<Paciente> copia = new ArrayList<>(pacientes);
        copia.sort(Comparator.comparing(Paciente::getApellido)
                .thenComparing(Paciente::getNombre));

        for (Paciente p : copia) {
            System.out.println(p);
        }
    }

    public void registrarMedico(Medico m) {
        if (!m.esValido()) {
            System.out.println("Médico no válido. Verifique los datos.");
            return;
        }

        if (medicos.contains(m)) {
            System.out.println("Ya existe un médico con este nombre y apellido.");
            return;
        }

        int nuevoId = medicos.isEmpty()
                ? 1
                : medicos.stream()
                .mapToInt(Medico::getId)
                .max()
                .getAsInt() + 1;

        m.setId(nuevoId);
        medicos.add(m);

        System.out.println("Médico registrado: " + m);
    }

    public Medico buscarPorNombreApellido(String nombre, String apellido) {
        for (Medico medico : medicos) {
            if (medico.getNombre().equalsIgnoreCase(nombre)
                    && medico.getApellido().equalsIgnoreCase(apellido)) {
                return medico;
            }
        }
        return null;
    }

    public void listarMedicos() {
        if (medicos.isEmpty()) {
            System.out.println("No hay médicos registrados.");
            return;
        }

        List<Medico> copia = new ArrayList<>(medicos);
        copia.sort(Comparator.comparing(Medico::getEspecialidad)
                .thenComparing(Medico::getApellido));

        for (Medico medico : copia) {
            System.out.println(medico);
        }
    }

    public void asignarTurno(Turno t) {
        Paciente paciente = buscarPorCedula(t.getPaciente().getCedula());
        if (paciente == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }

        Medico medico = buscarPorNombreApellido(
                t.getMedico().getNombre(),
                t.getMedico().getApellido()
        );
        if (medico == null) {
            System.out.println("Médico no encontrado.");
            return;
        }

        if (turnos.contains(t)) {
            System.out.println("Ya existe un turno con ese médico en esa fecha y hora.");
            return;
        }

        int nuevoId = turnos.isEmpty()
                ? 1
                : turnos.stream()
                .mapToInt(Turno::getId)
                .max()
                .getAsInt() + 1;

        t.setId(nuevoId);
        turnos.add(t);

        System.out.println("Turno registrado: " + t);
    }

    public void cancelarTurno(int idTurno) {
        Turno encontrado = null;

        for (Turno turno : turnos) {
            if (turno.getId() == idTurno) {
                encontrado = turno;
                break;
            }
        }

        if (encontrado == null) {
            System.out.println("Turno no encontrado.");
            return;
        }

        if (encontrado.getEstado() == EstadoTurno.ATENDIDO
                || encontrado.getEstado() == EstadoTurno.CANCELADO) {
            System.out.println("El turno no se puede cancelar.");
            return;
        }

        encontrado.setEstado(EstadoTurno.CANCELADO);
        System.out.println("Turno cancelado: " + encontrado);
    }

    public void cambiarEstadoTurno(int idTurno, EstadoTurno nuevo) {
        for (Turno turno : turnos) {
            if (turno.getId() == idTurno) {
                turno.setEstado(nuevo);
                System.out.println("Estado actualizado: " + turno);
                return;
            }
        }
        System.out.println("Turno no encontrado.");
    }

    @Override
    public List<Turno> listarTurnosDelDia(LocalDate fecha) {
        List<Turno> resultado = new ArrayList<>();

        for (Turno turno : turnos) {
            if (turno.getFechaHora().toLocalDate().equals(fecha)) {
                resultado.add(turno);
            }
        }

        resultado.sort(Comparator.comparing(Turno::getFechaHora));
        return resultado;
    }

    @Override
    public List<Turno> buscarPorMedico(Medico medico) {
        List<Turno> resultado = new ArrayList<>();

        for (Turno turno : turnos) {
            if (turno.getMedico().equals(medico)) {
                resultado.add(turno);
            }
        }

        return resultado;
    }

    @Override
    public List<Turno> buscarPorPaciente(Paciente paciente) {
        List<Turno> resultado = new ArrayList<>();

        for (Turno turno : turnos) {
            if (turno.getPaciente().equals(paciente)) {
                resultado.add(turno);
            }
        }

        return resultado;
    }
}
