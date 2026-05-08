package co.generation.clinica;

import co.generation.clinica.datos.DatosCSV;
import co.generation.clinica.model.EstadoTurno;
import co.generation.clinica.model.Especialidad;
import co.generation.clinica.model.Medico;
import co.generation.clinica.model.Paciente;
import co.generation.clinica.model.Turno;
import co.generation.clinica.service.ClinicaService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ClinicaService servicio = new ClinicaService();
        DatosCSV.cargar(servicio);

        Scanner sc = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            mostrarMenu();
            System.out.print("Elige una opción: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());

                switch (opcion) {
                    case 1 -> registrarPaciente(sc, servicio);
                    case 2 -> registrarMedico(sc, servicio);
                    case 3 -> asignarTurno(sc, servicio);
                    case 4 -> listarTurnosDelDia(sc, servicio);
                    case 5 -> cancelarTurno(sc, servicio);
                    case 6 -> verTurnosPorMedico(sc, servicio);
                    case 7 -> verTurnosPorPaciente(sc, servicio);
                    case 8 -> cambiarEstadoTurno(sc, servicio);
                    case 9 -> servicio.listarPacientes();
                    case 10 -> servicio.listarMedicos();
                    case 0 -> {
                        DatosCSV.guardar(servicio);
                        System.out.println("Hasta pronto. Datos guardados.");
                    }
                    default -> System.out.println("Opción no válida.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Debes ingresar un número válido.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println();
        }

        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("========================================");
        System.out.println("         CLINICAAPP — MENÚ");
        System.out.println("========================================");
        System.out.println("1. Registrar paciente");
        System.out.println("2. Registrar médico");
        System.out.println("3. Asignar turno");
        System.out.println("4. Listar turnos del día");
        System.out.println("5. Cancelar turno");
        System.out.println("6. Ver turnos por médico");
        System.out.println("7. Ver turnos por paciente");
        System.out.println("8. Cambiar estado de turno");
        System.out.println("9. Listar pacientes");
        System.out.println("10. Listar médicos");
        System.out.println("0. Salir");
        System.out.println("========================================");
    }

    private static void registrarPaciente(Scanner sc, ClinicaService servicio) {
        System.out.print("Cédula: ");
        String cedula = sc.nextLine();

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Apellido: ");
        String apellido = sc.nextLine();

        System.out.print("Teléfono: ");
        String telefono = sc.nextLine();

        Paciente paciente = new Paciente(cedula, nombre, apellido, telefono);
        servicio.registrarPaciente(paciente);
    }

    private static void registrarMedico(Scanner sc, ClinicaService servicio) {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Apellido: ");
        String apellido = sc.nextLine();

        System.out.println("Especialidades disponibles:");
        for (Especialidad esp : Especialidad.values()) {
            System.out.println("- " + esp);
        }

        System.out.print("Especialidad: ");
        String textoEspecialidad = sc.nextLine().trim().toUpperCase();

        Especialidad especialidad = Especialidad.valueOf(textoEspecialidad);
        Medico medico = new Medico(nombre, apellido, especialidad);
        servicio.registrarMedico(medico);
    }

    private static void asignarTurno(Scanner sc, ClinicaService servicio) {
        System.out.print("Cédula del paciente: ");
        String cedula = sc.nextLine();
        Paciente paciente = servicio.buscarPorCedula(cedula);

        if (paciente == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }

        System.out.print("Nombre del médico: ");
        String nombreMedico = sc.nextLine();

        System.out.print("Apellido del médico: ");
        String apellidoMedico = sc.nextLine();

        Medico medico = servicio.buscarPorNombreApellido(nombreMedico, apellidoMedico);

        if (medico == null) {
            System.out.println("Médico no encontrado.");
            return;
        }

        System.out.print("Año: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("Mes: ");
        int mes = Integer.parseInt(sc.nextLine());

        System.out.print("Día: ");
        int dia = Integer.parseInt(sc.nextLine());

        System.out.print("Hora: ");
        int hora = Integer.parseInt(sc.nextLine());

        System.out.print("Minuto: ");
        int minuto = Integer.parseInt(sc.nextLine());

        LocalDateTime fechaHora = LocalDateTime.of(anio, mes, dia, hora, minuto);
        Turno turno = new Turno(paciente, medico, fechaHora);

        servicio.asignarTurno(turno);
    }

    private static void listarTurnosDelDia(Scanner sc, ClinicaService servicio) {
        System.out.print("Año: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("Mes: ");
        int mes = Integer.parseInt(sc.nextLine());

        System.out.print("Día: ");
        int dia = Integer.parseInt(sc.nextLine());

        LocalDate fecha = LocalDate.of(anio, mes, dia);
        List<Turno> turnos = servicio.listarTurnosDelDia(fecha);

        if (turnos.isEmpty()) {
            System.out.println("No hay turnos para ese día.");
            return;
        }

        for (Turno turno : turnos) {
            System.out.println(turno);
        }
    }

    private static void cancelarTurno(Scanner sc, ClinicaService servicio) {
        System.out.print("ID del turno a cancelar: ");
        int idTurno = Integer.parseInt(sc.nextLine());
        servicio.cancelarTurno(idTurno);
    }

    private static void verTurnosPorMedico(Scanner sc, ClinicaService servicio) {
        System.out.print("Nombre del médico: ");
        String nombre = sc.nextLine();

        System.out.print("Apellido del médico: ");
        String apellido = sc.nextLine();

        Medico medico = servicio.buscarPorNombreApellido(nombre, apellido);

        if (medico == null) {
            System.out.println("Médico no encontrado.");
            return;
        }

        List<Turno> turnos = servicio.buscarPorMedico(medico);

        if (turnos.isEmpty()) {
            System.out.println("No hay turnos para ese médico.");
            return;
        }

        for (Turno turno : turnos) {
            System.out.println(turno);
        }
    }

    private static void verTurnosPorPaciente(Scanner sc, ClinicaService servicio) {
        System.out.print("Cédula del paciente: ");
        String cedula = sc.nextLine();

        Paciente paciente = servicio.buscarPorCedula(cedula);

        if (paciente == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }

        List<Turno> turnos = servicio.buscarPorPaciente(paciente);

        if (turnos.isEmpty()) {
            System.out.println("No hay turnos para ese paciente.");
            return;
        }

        for (Turno turno : turnos) {
            System.out.println(turno);
        }
    }

    private static void cambiarEstadoTurno(Scanner sc, ClinicaService servicio) {
        System.out.print("ID del turno: ");
        int idTurno = Integer.parseInt(sc.nextLine());

        System.out.println("Estados disponibles:");
        for (EstadoTurno estado : EstadoTurno.values()) {
            System.out.println("- " + estado);
        }

        System.out.print("Nuevo estado: ");
        String textoEstado = sc.nextLine().trim().toUpperCase();

        EstadoTurno nuevoEstado = EstadoTurno.valueOf(textoEstado);
        servicio.cambiarEstadoTurno(idTurno, nuevoEstado);
    }
}
