// Clase Padre
package co.generation.clinica.model;
import co.generation.clinica.interfaces.Consultable;
import co.generation.clinica.interfaces.Registrable;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Turno implements Registrable, Consultable {
    private int id;
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime fechaHora;
    private EstadoTurno estado;

//constructor 1:  recibe el id (para reconstruir desde CSV)

    public Turno(int id, Paciente paciente, Medico medico, LocalDateTime fechaHora, EstadoTurno estado) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

//constructor 2: sin id (para registrar desde el menú, donde el id lo asigna ClinicaService)

    public Turno(Paciente paciente, Medico medico, LocalDateTime fechaHora, EstadoTurno estado) {
        this.paciente = paciente;
        this.medico = medico;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

// Getter y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public EstadoTurno getEstado() {
        return estado;
    }

    public void setEstado(EstadoTurno estado) {
        this.estado = estado;
    }


// equals() en Turno: dos turnos son iguales si tienen el mismo médico y la misma fechaHora. Esto permite
//detectar conflictos de agenda: un médico no puede tener dos turnos en el mismo momento.

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Turno)) return false;

        Turno otro = (Turno) obj;
        return this.medico.equals(otro.medico) &&        // usa el equals de Medico
                this.fechaHora.equals(otro.fechaHora);    // mismo momento
    }

    @Override
    public String toString() {
        return "[" + estado + "] " + paciente.getNombre() + " " + paciente.getApellido()
                + " — Dr. " + medico.getNombre() + " " + medico.getApellido()
                + " (" + medico.getEspecialidad() + ")"
                + " — " + fechaHora;
    // toString(): formato esperado ® "[PENDIENTE] María García — Dr. Carlos Pérez (CARDIOLOGIA) —
    //2026-06-10T09:30".

    }

}
