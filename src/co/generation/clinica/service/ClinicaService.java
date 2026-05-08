package co.generation.clinica.service;

import co.generation.clinica.model.Paciente;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;



public class ClinicaService {

    private List<Paciente> pacientes;


    public ClinicaService() {
        this.pacientes = new ArrayList<>();
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    //---------------------------------------REGISTRAR PACIENTE------------------------------------------------------
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

    //---------------------------------------BUSCAR POR CEDULA------------------------------------------------------
    public Paciente buscarPorCedula(String cedula) {

        for (Paciente p : pacientes) {
            if (p.getCedula().equals(cedula)) {
                return p;
            }
        }
        return null;
    }


    //---------------------------------------LISTAR PACIENTES------------------------------------------------------
    public void listarPacientes() {

        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
            return;
        }

        pacientes.stream()
                .sorted(Comparator
                        .comparing(Paciente::getApellido)
                        .thenComparing(Paciente::getNombre))
                .forEach(p -> System.out.println(p.toString()));
    }

}
