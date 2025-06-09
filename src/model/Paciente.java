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
    private int minutoAtencion;
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
    public void setArea(String Area){
        this.area=Area;
    }
    public String getNombre() {
        return nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public String getEstado(){
        return estado;
    }
    public String getArea(){
        return area;
    }
    public int getMinutoAtencion() {
        return minutoAtencion;
    }
    public void setTiempoLlegada(long tiempoLlegada) {
        this.tiempoLlegada = tiempoLlegada;
    }
    public void setMinutoAtencion(int minuto) {
        this.minutoAtencion = minuto;
    }
    
    
    
    
   

    public long tiempoEsperaActual(int minutoSimulacion) {
        // Usar el minuto de simulación en lugar de la hora actual real
        long tiempoEsperado = minutoSimulacion - tiempoLlegada;  // tiempoLlegada es el minuto cuando llegó el paciente
    
        return tiempoEsperado; // Este valor es en minutos
    }

    public void registrarCambio(String descripcion){
        historialCambios.push(descripcion);
    }


    
}
