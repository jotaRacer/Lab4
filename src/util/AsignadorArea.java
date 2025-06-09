package util;

import java.util.Random;

public class AsignadorArea {

    public static String asignarAreaAleatoria() {
        String[] areas = {"urgencia_adulto", "SAPU", "urgencia_infantil"};
        Random random = new Random();
        return areas[random.nextInt(areas.length)];
    }
    
}
