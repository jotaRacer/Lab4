package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import model.AreaAtencion;
import model.Paciente;
import util.AsignadorArea;
import util.AsignadorCategoria;
import util.PacienteComparator;

public class Hospital {
    Map<String,Paciente> pacientesTotales;
    PriorityQueue<Paciente> colaAtencion;
    Map<String, AreaAtencion> areasAtencion;
    List<Paciente> pacientesAtendidos;

    public Hospital(Map<String, Paciente> pacientesTotales, 
                    PriorityQueue<Paciente> colaAtencion, 
                    Map<String, AreaAtencion> areasAtencion, 
                    List<Paciente> pacientesAtendidos) {
        this.pacientesTotales = pacientesTotales;
        this.colaAtencion = colaAtencion;
        this.areasAtencion = areasAtencion;
        this.pacientesAtendidos = pacientesAtendidos;
    }

    public Hospital() {
        this.pacientesTotales = new HashMap<>();
        this.colaAtencion = new PriorityQueue<>(new PacienteComparator());
        this.areasAtencion = new HashMap<>();
        this.pacientesAtendidos = new ArrayList<>();
    }

    public void reasignarCategoria(String id, int nuevaCategoria){
        Paciente p = pacientesTotales.get(id);

        if(p!=null){
            p.setCategoria(nuevaCategoria);
            p.registrarCambio("Categoría cambiada a: " + nuevaCategoria);
        }
        else{
            System.out.println("Paciente no encontrado.");
        }

    }

    public List<Paciente> obtenerPacientesPorCategoria(int categoria) {
        List<Paciente> pacientesPorCategoria = new ArrayList<>();
    
        for (Paciente p : colaAtencion) {
            if (p.getCategoria() == categoria) {
                pacientesPorCategoria.add(p);
            }
        }

        pacientesPorCategoria.sort(new PacienteComparator());
        return pacientesPorCategoria;
    }

    public AreaAtencion obteneAreaAtencion(String nombre){
        return areasAtencion.get(nombre);
    }

    public Paciente atenderSiguiente() {
    Paciente paciente = colaAtencion.poll();

    if (paciente == null) {
        System.out.println("No hay pacientes en espera.");
        return null;
    }
    String areaAsignada = AsignadorArea.asignarAreaAleatoria();

    String area = AsignadorArea.asignarAreaAleatoria();
    paciente.setArea(area);

    paciente.setEstado("atendido");

    paciente.registrarCambio("Paciente atendido en área: " + areaAsignada);

    return paciente;
    }
   public void registrarPaciente(Paciente p) {
        colaAtencion.offer(p);

        pacientesTotales.put(p.getId(), p);

        AsignadorCategoria.asignarCategoriaAleatoria(p);  

        asignarAreaDeAtencion(p); 
    }
    private void asignarAreaDeAtencion(Paciente p) {
        String areaAsignada = AsignadorArea.asignarAreaAleatoria();  
        p.setArea(areaAsignada);  
    }
    
    
    




}
