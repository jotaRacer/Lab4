package util;

import model.Paciente;
import java.util.Comparator;

public class PacienteComparator implements Comparator<Paciente>{
        @Override
        public int compare(Paciente p1, Paciente p2) {
            if (p1.getCategoria() != p2.getCategoria()) {
                return Integer.compare(p1.getCategoria(), p2.getCategoria());
            }
            return Long.compare(p1.getTiempoLlegada(), p2.getTiempoLlegada());
        }
 
}
