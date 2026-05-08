package co.generation.clinica.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class ClinicaService {
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

}
