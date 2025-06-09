
import service.SimuladorUrgencia;


public class Main {
    public static void main(String[] args) {
        System.out.println("=== SIMULACIÓN DE URGENCIA HOSPITALARIA ===\n");

        SimuladorUrgencia simulador = new SimuladorUrgencia();

        int pacientesPorDia = 144; // 1 paciente cada 10 minutos en 24h
        simulador.simular(pacientesPorDia);

        System.out.println("\n=== SIMULACIÓN FINALIZADA ===");
    }
}
