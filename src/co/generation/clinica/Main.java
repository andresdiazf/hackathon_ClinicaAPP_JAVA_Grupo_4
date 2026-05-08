package co.generation.clinica;

import model.*;
import service.ClinicaService;
import util.DatosCSV;

import java.time.LocalDateTime;
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
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

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
                case 0 -> System.out.println("Vuelve pronto. Datos guardados.");
                default -> System.out.println("Opción no válida.");
            }
        }

        DatosCSV.guardar(servicio);
        sc.close();
    }

    //  MENÚ
    static void mostrarMenu() {
        System.out.println("\n******************************");
        System.out.println("******* CLINICAAPP — MENÚ ******");
        System.out.println("********************************");
        System.out.println(" 1. Registrar paciente");
        System.out.println(" 2. Registrar médico");
        System.out.println(" 3. Asignar turno");
        System.out.println(" 4. Listar turnos del día");
        System.out.println(" 5. Cancelar turno");
        System.out.println(" 6. Ver turnos por médico");
        System.out.println(" 7. Ver turnos por paciente");
        System.out.println(" 8. Cambiar estado de turno");
        System.out.println(" 9. Listar pacientes");
        System.out.println("10. Listar médicos");
        System.out.println(" 0. Salir");
        System.out.println("*******************************");
    }

    // opcion 1: Registrar paciente
    static void registrarPaciente(Scanner sc, ClinicaService servicio) {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine().trim(); // manejo de espacios con sc.nextLine().trim() manejo de espacios
        System.out.print("Apellido: ");
        String apellido = sc.nextLine().trim();
        System.out.print("Cédula: ");
        String cedula = sc.nextLine().trim();
        System.out.print("Teléfono: ");
        String telefono = sc.nextLine().trim();

        Paciente p = new Paciente(nombre, apellido, cedula, telefono);
        String resultado = servicio.registrarPaciente(p);
        System.out.println(resultado);
    }

    //  opcion 2: Registrar médico
    static void registrarMedico(Scanner sc, ClinicaService servicio) {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine().trim();
        System.out.print("Apellido: ");
        String apellido = sc.nextLine().trim();
        System.out.println("Elige la especialidad (GENERAL / PEDIATRIA / CARDIOLOGIA / URGENCIAS): ");
        Especialidad esp = Especialidad.valueOf(sc.nextLine().trim().toUpperCase());

        Medico m = new Medico(nombre, apellido, esp);
        String resultado = servicio.registrarMedico(m);
        System.out.println(resultado);
    }

    // opcion 3: Asignar turno
    static void asignarTurno(Scanner sc, ClinicaService servicio) {
        // Buscar paciente por cédula
        System.out.print("Cédula del paciente: ");
        String cedula = sc.nextLine().trim();
        Paciente paciente = servicio.buscarPacientePorCedula(cedula);
        if (paciente == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }
        // Buscar médico por nombre y apellido
        System.out.print("Nombre del médico: ");
        String nombreMed = sc.nextLine().trim();
        System.out.print("Apellido del médico: ");
        String apellidoMed = sc.nextLine().trim();
        Medico medico = servicio.buscarMedicoPorNombre(nombreMed, apellidoMed);
        if (medico == null) {
            System.out.println("Médico no encontrado.");
            return;
        }

        // Construir fecha y hora
        System.out.print("Año: ");   int anio = sc.nextInt();
        System.out.print("Mes: ");   int mes  = sc.nextInt();
        System.out.print("Día: ");   int dia  = sc.nextInt();
        System.out.print("Hora: ");  int hora = sc.nextInt();
        System.out.print("Minuto: "); int min = sc.nextInt();
        sc.nextLine(); // limpiar buffer

        LocalDateTime fechaHora = LocalDateTime.of(anio, mes, dia, hora, min);
        Turno turno = new Turno(paciente, medico, fechaHora);

        String resultado = servicio.agendarTurno(turno);
        System.out.println(resultado);
    }
    

    //  opcion 4: Listar turnos del día
    static void listarTurnosDelDia(Scanner sc, ClinicaService servicio) {
        System.out.print("Año: ");  int anio = sc.nextInt();
        System.out.print("Mes: ");  int mes  = sc.nextInt();
        System.out.print("Día: ");  int dia  = sc.nextInt();
        sc.nextLine();
        servicio.listarTurnosPorFecha(anio, mes, dia);
    }

    // opcion 5: Cancelar turno
    static void cancelarTurno(Scanner sc, ClinicaService servicio) {
        System.out.print("Cédula del paciente: ");
        String cedula = sc.nextLine().trim();
        System.out.print("Nombre del médico: ");
        String nombre = sc.nextLine().trim();
        System.out.print("Apellido del médico: ");
        String apellido = sc.nextLine().trim();
        System.out.print("Año: ");  int anio = sc.nextInt();
        System.out.print("Mes: ");  int mes  = sc.nextInt();
        System.out.print("Día: ");  int dia  = sc.nextInt();
        System.out.print("Hora: "); int hora = sc.nextInt();
        System.out.print("Minuto: "); int min = sc.nextInt();
        sc.nextLine();

        LocalDateTime fechaHora = LocalDateTime.of(anio, mes, dia, hora, min);
        String resultado = servicio.cancelarTurno(cedula, nombre, apellido, fechaHora);
        System.out.println(resultado);
    }

    // opcion 6 y 7: Ver turnos por médico / paciente
    static void verTurnosPorMedico(Scanner sc, ClinicaService servicio) {
        System.out.print("Nombre del médico: ");
        String nombre = sc.nextLine().trim();
        System.out.print("Apellido: ");
        String apellido = sc.nextLine().trim();
        servicio.listarTurnosPorMedico(nombre, apellido);
    }

    static void verTurnosPorPaciente(Scanner sc, ClinicaService servicio) {
        System.out.print("Cédula del paciente: ");
        String cedula = sc.nextLine().trim();
        servicio.listarTurnosPorPaciente(cedula);
    }

    //  opcion 8: Cambiar estado de turno
    static void cambiarEstadoTurno(Scanner sc, ClinicaService servicio) {
        System.out.print("Cédula del paciente: ");
        String cedula = sc.nextLine().trim();
        System.out.print("Nombre del médico: ");
        String nombre = sc.nextLine().trim();
        System.out.print("Apellido del médico: ");
        String apellido = sc.nextLine().trim();
        System.out.print("Año: ");  int anio = sc.nextInt();
        System.out.print("Mes: ");  int mes  = sc.nextInt();
        System.out.print("Día: ");  int dia  = sc.nextInt();
        System.out.print("Hora: "); int hora = sc.nextInt();
        System.out.print("Minuto: "); int min = sc.nextInt();
        sc.nextLine();
        System.out.println("Nuevo estado (PENDIENTE / ATENDIDO / CANCELADO): ");
        EstadoTurno nuevoEstado = EstadoTurno.valueOf(sc.nextLine().trim().toUpperCase());

        LocalDateTime fechaHora = LocalDateTime.of(anio, mes, dia, hora, min);
        String resultado = servicio.cambiarEstadoTurno(cedula, nombre, apellido, fechaHora, nuevoEstado);
        System.out.println(resultado);
    }
}




