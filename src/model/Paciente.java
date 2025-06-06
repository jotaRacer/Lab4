package model;

import java.util.Stack;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.Duration;


public class Paciente {

    private String nombre;
    private String apellido;
    private String id;
    private int categoria;
    private long tiempoLlegada;
    private String estado;
    private String area;
    private Stack<String> historialCambios;


    public Paciente(String nombre, String apellido, String id, int categoria, long tiempoLlegada, String estado, String area) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.id = id;
        this.categoria = categoria;
        this.tiempoLlegada = tiempoLlegada;
        this.estado = estado;
        this.area = area;
        this.historialCambios = new Stack<>();
    }
    public int getCategoria() {
        return categoria;
    }
    public long getTiempoLlegada() {
        return tiempoLlegada;
    }
    public void setEstado(String estadoNuevo){
        this.estado = estadoNuevo;
    }
    public String getId(){
        return id;
    }
    public void setCategoria(int nuevaCategoria){
        this.categoria=nuevaCategoria;
    }
    public void setArea(String area){
        this.area=area;
    }

    

    public long tiempoEsperaActual() {
        ZonedDateTime llegada = Instant.ofEpochSecond(tiempoLlegada).atZone(ZoneId.of("America/Santiago"));
        
        ZonedDateTime ahora = ZonedDateTime.now(ZoneId.of("America/Santiago"));

        Duration duracion = Duration.between(llegada, ahora);

        return duracion.toMinutes();
    }

    public void registrarCambio(String descripcion){
        historialCambios.push(descripcion);
    }

    public String ultimoCambio(){
        return historialCambios.isEmpty()? "El paciente no tiene cambios" : historialCambios.pop();
    }

    
}
