package co.generation.clinica.model;

public class Paciente {
    private int id;
    private String cedula;
    private String nombre;
    private String apellido;
    private String telefono;

//constructor 1:  recibe el id (para reconstruir desde CSV)
    public PacienteID(int id, String cedula, String nombre, String apellido, String telefono) {
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }

//constructor 2: sin id (para registrar desde el menú, donde el id lo asigna ClinicaService)
    public Paciente(String cedula, String nombre, String apellido, String telefono) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }

// Getter y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


// uso de equals() en Paciente: dos pacientes son iguales si y solo si tienen la misma cédula.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;              // mismo objeto en memoria
        if (obj == null) return false;             // el otro es nulo
        if (!(obj instanceof Paciente)) return false; // no es Paciente

        Paciente otro = (Paciente) obj;            // cast seguro
        return this.cedula.equals(otro.cedula);    // compara solo cédula
    }
// toString(): formato esperado ® "María García - 1020304050 - 3001234567" (nombre + apellido +
//cédula + teléfono separados por guión).
    @Override
    public String toString() {
        return nombre + " " + apellido + " - " + cedula + " - " + telefono;
           }
}



