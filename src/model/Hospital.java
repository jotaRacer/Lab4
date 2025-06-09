package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import util.AsignadorArea;
import util.AsignadorCategoria;
import util.PacienteComparator;

public class Hospital {
    Map<String,Paciente> pacientesTotales;
    public PriorityQueue<Paciente> colaAtencion;
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

    public AreaAtencion obtenerAreaAtencion(String nombre){
        return areasAtencion.get(nombre);
    }

    public Paciente atenderSiguiente() {
    Paciente paciente = colaAtencion.poll();

    if (paciente == null) {
        System.out.println("No hay pacientes en espera.");
        return null;
    }

    String area = AsignadorArea.asignarAreaAleatoria();
    paciente.setArea(area);

    paciente.setEstado("atendido");

    paciente.registrarCambio("Paciente atendido en área: " + area);
    System.out.println("paciente atendido");
    return paciente;
    
    }
   public void registrarPaciente(Paciente p) {
        colaAtencion.offer(p);

        pacientesTotales.put(p.getId(), p);

        p.setCategoria(AsignadorCategoria.asignarCategoriaAleatoria());  

        asignarAreaDeAtencion(p); 
    }
    private void asignarAreaDeAtencion(Paciente p) {
        String areaAsignada = AsignadorArea.asignarAreaAleatoria();  
        p.setArea(areaAsignada);  
    }

    public void registrarPacienteAtendido(Paciente p, int minutoAtencion) {
        pacientesAtendidos.add(p);
        p.setEstado("atendido");
        p.registrarCambio("Atendido a los " + (minutoAtencion - p.getTiempoLlegada()) + " minutos");
    }
    //metodo extra para calcular el tiempo maximo de espera.
    public int tiempoMaximoEspera(int categoria) {
        switch (categoria) {
            case 1: return 10;
            case 2: return 20;
            case 3: return 35;
            case 4: return 40;
            case 5: return 120;
            default: return 999;
        }
    }

    //metodo extra para imprimir las estadisticas de la simulacion.
    public void generarInformeFinal(List<Paciente> pacientesExcedidos) {
        System.out.println("\n----- INFORME FINAL DE LA SIMULACIÓN -----\n");
    
        Map<Integer, List<Integer>> tiemposPorCategoria = new HashMap<>();
    
        for (Paciente p : pacientesAtendidos) {
            int categoria = p.getCategoria();
            int tiempoEspera = p.getMinutoAtencion() - (int) p.getTiempoLlegada();

    
            tiemposPorCategoria
                .computeIfAbsent(categoria, k -> new ArrayList<>())
                .add(tiempoEspera);
        }
    
        for (int categoria = 1; categoria <= 5; categoria++) {
            List<Integer> tiempos = tiemposPorCategoria.getOrDefault(categoria, new ArrayList<>());
            int total = tiempos.size();
            double promedio = tiempos.stream().mapToInt(Integer::intValue).average().orElse(0);
    
            System.out.println("Categoría C" + categoria + ":");
            System.out.println("  Pacientes atendidos: " + total);
            System.out.println("  Promedio de espera: " + Math.round(promedio) + " minutos\n");
        }
    
        System.out.println("Pacientes que excedieron el tiempo máximo de espera: " + pacientesExcedidos.size());
        for (Paciente p : pacientesExcedidos) {
            System.out.println("  - " + p.getId() + " | " + p.getNombre() + " " + p.getApellido() +
                    " | Categoría C" + p.getCategoria());
        }
    }
    

    
    
    }

    
    
    





