package util;

import model.Paciente;

public class AsignadorCategoria {
    
    public static int asignarCategoriaAleatoria() {
        double probabilidad = Math.random() * 100; // Genera un número entre 0 y 100

        // Asigna la categoría de acuerdo con las probabilidades
        if (probabilidad < 10) {
            return 1; // C1: Emergencia Vital (10%)
        } else if (probabilidad < 25) {
            return 2;
        } else if (probabilidad < 43) {
            return 3;
        } else if (probabilidad < 70) {
            return 4;
        } else {
            return 5;
        }
    }

    
}
