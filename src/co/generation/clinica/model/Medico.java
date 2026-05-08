package co.generation.clinica.model;

public class Medico {
    private int id;
    private String nombre;
    private String apellido;
    private Especialidad especialidad; ///

/// /constructor 1:  recibe el id (para reconstruir desde CSV)
    public MedicoId(int id, String nombre, String apellido, Especialidad especialidad) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
    }

//constructor 2: sin id (para registrar desde el menú, donde el id lo asigna ClinicaService)
    public Medico(String nombre, String apellido, Especialidad especialidad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
    }

// Getter y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

// equals() en Medico: dos médicos son iguales si tienen el mismo nombre y apellido sin distinguir
//mayúsculas. Usa equalsIgnoreCase() en ambos campos.

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Medico)) return false;

        Medico otro = (Medico) obj;
        return this.nombre.equalsIgnoreCase(otro.nombre) &&    // ignora mayúsculas
                this.apellido.equalsIgnoreCase(otro.apellido);
    }
// toString(): formato esperado ® "Dr. Carlos Pérez - CARDIOLOGIA".
    @Override
    public String toString() {
        return "Dr. " + nombre + " " + apellido + " - " + especialidad;

    }

    
}



}
