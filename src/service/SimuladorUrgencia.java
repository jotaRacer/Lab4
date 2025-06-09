package service;

import model.Paciente;
import model.Hospital;

import util.PacienteComparator;

import java.io.FileWriter;
import java.util.*;

public class SimuladorUrgencia {

    private Hospital hospital;
    private List<Paciente> pacientes;
    private PriorityQueue<Paciente> colaEspera;
    private int pacientesIngresados = 0;
    private List<Paciente> pacientesExcedidos = new ArrayList<>();

    public SimuladorUrgencia() {
        this.hospital = new Hospital();
        this.colaEspera = new PriorityQueue<>(new PacienteComparator());
    }

    public void simular(int pacientesPorDia) {
        pacientes = GeneradorPaciente.generarPacientes(pacientesPorDia);

        for (int minuto = 0; minuto < 24 * 60; minuto++) {

            // Cada 10 minutos entra un nuevo paciente
            if (minuto % 10 == 0 && pacientesIngresados < pacientes.size()) {
                Paciente p = pacientes.get(pacientesIngresados++);
                colaEspera.add(p);
                hospital.registrarPaciente(p);

            }

            // Cada 15 minutos se atiende un paciente
            if (minuto % 15 == 0 && !colaEspera.isEmpty()) {
                atenderPaciente(colaEspera.poll(), minuto);
            }

            // Si se acumulan 3, se atienden 2 de inmediato
            if (colaEspera.size() >= 3) {
                atenderPaciente(colaEspera.poll(), minuto);
                if (!colaEspera.isEmpty())
                    atenderPaciente(colaEspera.poll(), minuto);
            }

            // Forzar atención por exceso de espera
            List<Paciente> pendientes = new ArrayList<>(colaEspera);
            for (Paciente p : pendientes) {
                int maxEspera = hospital.tiempoMaximoEspera(p.getCategoria());
                long espera = minuto - p.getTiempoLlegada();
                if (espera > maxEspera) {
                    colaEspera.remove(p);
                    pacientesExcedidos.add(p);
                    atenderPaciente(p, minuto);
                }
            }
        }

        hospital.generarInformeFinal(pacientesExcedidos);
        guardarPacientesEnArchivo("Pacientes_24h.txt");
    }

    private void guardarPacientesEnArchivo(String nombreArchivo) {
    try (FileWriter writer = new FileWriter(nombreArchivo)) {
        for (Paciente p : pacientes) {
            writer.write(p.getId() + "," +
                         p.getNombre() + "," +
                         p.getApellido() + "," +
                         p.getCategoria() + "," +
                         p.getTiempoLlegada() + "," +
                         p.getEstado() + "," +
                         p.getArea() + "\n");
        }
    } catch (Exception e) {
        System.out.println("Error al guardar pacientes en archivo: " + e.getMessage());
    }
}

    private void atenderPaciente(Paciente p, int minutoActual) {
        p.setMinutoAtencion(minutoActual); // ← nuevo
        p.setEstado("atendido");
        p.registrarCambio("Paciente atendido a los " + (minutoActual - p.getTiempoLlegada()) + " minutos");
        hospital.registrarPacienteAtendido(p, minutoActual);
    }
}
