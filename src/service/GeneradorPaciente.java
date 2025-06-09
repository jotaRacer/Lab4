package service;

import model.Paciente;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import util.AsignadorArea;
import util.AsignadorCategoria;

public class GeneradorPaciente {

    private static final String[] NOMBRES = {"Juan", "Ana", "Carlos", "María", "Luis", "Isabel","Carolina","Pablo","Matias","Maurico","Tomas","Igncia","Valentina","Paulina"};
    private static final String[] APELLIDOS = {"Pérez", "González", "Rojas", "Fernández", "Soto", "Ramírez","Ugaz","Sandoval","Balboa","Sanchez","Vidal","Diaz","Ortiz",};
    private static final Random random = new Random();

    public static List<Paciente> generarPacientes(int cantidad) {
        List<Paciente> lista = new ArrayList<>();
        long tiempoBase = 0;

        for (int i = 0; i < cantidad; i++) {
            String nombre = NOMBRES[random.nextInt(NOMBRES.length)];
            String apellido = APELLIDOS[random.nextInt(APELLIDOS.length)];
            String id = "P" + (i + 1);
            int categoria = AsignadorCategoria.asignarCategoriaAleatoria(); // ← AQUÍ está el uso correcto
            long llegada = tiempoBase + (i * 10); // en minutos
            String estado = "en_espera";
            String area = AsignadorArea.asignarAreaAleatoria();

            Paciente paciente = new Paciente(nombre, apellido, id, categoria, llegada, estado, area);
            lista.add(paciente);
        }

        guardarEnArchivo(lista, "Pacientes_24h.txt");
        return lista;
    }

    private static void guardarEnArchivo(List<Paciente> pacientes, String archivo) {
        try (FileWriter writer = new FileWriter(archivo)) {
            for (Paciente p : pacientes) {
                writer.write(p.getId() + "," + p.getNombre() + "," + p.getApellido() + "," +
                        p.getCategoria() + "," + p.getTiempoLlegada() + "," +
                        p.getEstado() + "," + p.getArea() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
