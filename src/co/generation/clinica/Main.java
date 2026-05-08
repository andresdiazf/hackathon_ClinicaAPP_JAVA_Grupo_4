//// Secuencia en main():
//ClinicaService servicio = new ClinicaService(); ok
//        DatosCSV.cargar(servicio);
//// bucle while con Scanner para el menú...ok
//// Al salir (opción 0):
//        DatosCSV.guardar(servicio);ok
//        System.out.println("Hasta pronto. Datos guardados."); ok

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
                case 0 -> System.out.println("Hasta pronto. Datos guardados.");
                default -> System.out.println("Opción no válida.");
            }
        }

        DatosCSV.guardar(servicio);
        sc.close();
    }

}

