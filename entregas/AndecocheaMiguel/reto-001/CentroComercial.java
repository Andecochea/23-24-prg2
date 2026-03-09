public class CentroComercial {
    public static void main(String[] args) {
        final int TOTAL_CAJAS = 5;
        final int MINUTOS_JORNADA = 12 * 60;
        
        // CONSISTENCIA: Todas las cajas usan la misma estructura de datos
        int[] clientesPorCaja = new int[TOTAL_CAJAS];
        int[] itemsPorCaja = new int[TOTAL_CAJAS];
        boolean[] isCajaLibre = { true, true, true, true, true };
        
        int personasEnCola = 0;
        int totalItemsVendidos = 0;
        int minutosSinCola = 0;
        boolean isCajaRefuerzoHabilitada = false;

        for (int minutoActual = 1; minutoActual < MINUTOS_JORNADA; minutoActual++) {
            
            if (Math.random() * 100 < 40) {
                personasEnCola++;
            }

            // CONSISTENCIA: Un solo bucle maneja todas las cajas siguiendo el mismo convenio
            for (int i = 0; i < TOTAL_CAJAS; i++) {
                // Definimos la regla de negocio: la última caja es especial
                boolean condicionEspecial = (i == 4) ? isCajaRefuerzoHabilitada : true;

                if (personasEnCola > 0 && isCajaLibre[i] && condicionEspecial) {
                    clientesPorCaja[i]++;
                    isCajaLibre[i] = false;
                    itemsPorCaja[i] = (int) (Math.random() * 11) + 5;
                    totalItemsVendidos += itemsPorCaja[i];
                    personasEnCola--;
                }

                // CONSISTENCIA: La reducción de items es idéntica para todos los índices
                if (itemsPorCaja[i] > 0) {
                    itemsPorCaja[i]--;
                    if (itemsPorCaja[i] == 0) isCajaLibre[i] = true;
                }
            }

            if (personasEnCola == 0) minutosSinCola++;
            isCajaRefuerzoHabilitada = (personasEnCola >= 15);
            
            // Log de consola simplificado siguiendo el patrón de consistencia
            imprimirMinuto(minutoActual, personasEnCola, itemsPorCaja);
        }
    }

    private static void imprimirMinuto(int min, int cola, int[] items) {
        System.out.print("MINUTO " + min + " - En cola: " + cola + " | ");
        for (int i = 0; i < items.length; i++) {
            System.out.print("C" + (i+1) + ":[" + items[i] + "] ");
        }
        System.out.println();
    }
}
