package model;


import java.util.PriorityQueue;

import util.PacienteComparator;

public class AreaAtencion {
    public String nombre;
    public PriorityQueue<Paciente> pacientesHeap;
    public int capacidadMaxima;

    public boolean estaSaturada() {
        return pacientesHeap.size() >= capacidadMaxima;
    }

    public AreaAtencion(String nombre,int capacidadMaxima) {
        this.nombre = nombre;
        pacientesHeap = new PriorityQueue<>(new PacienteComparator());
        this.capacidadMaxima = capacidadMaxima;
    }
    
    public void ingresarPaciente(Paciente paciente){
        if (!estaSaturada()) {
            pacientesHeap.offer(paciente); 
        } else {
            System.out.println("No se puede ingresar el paciente, el área está saturada.");
        }
    }
    
    public Paciente atenderPaciente() {
        if (pacientesHeap.isEmpty()) {
            System.out.println("No hay pacientes para atender.");
            return null;
        }
        Paciente p = pacientesHeap.poll();
        p.setEstado("atendido");
        p.registrarCambio("Paciente atendido");
        return p;
    }
    
    

}
