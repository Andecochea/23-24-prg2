import java.util.Random;

public class CentroComercial {
    public static void main(String[] args) {
        final int TOTAL_CAJAS = 5;
        final int CAJA_REFUERZO_INDEX = 4;
        final int MINUTOS_JORNADA = 12 * 60;
        
        int[] clientesAtendidosPorCaja = new int[TOTAL_CAJAS];
        int[] itemsEnProcesoPorCaja = new int[TOTAL_CAJAS];
        boolean[] isCajaDisponible = { true, true, true, true, true };
        
        int personasEnCola = 0;
        int totalItemsVendidos = 0;
        int minutosSinCola = 0;
        boolean isCajaRefuerzoActiva = false;

        for (int minutoActual = 1; minutoActual < MINUTOS_JORNADA; minutoActual++) {
            
            if (Math.random() * 100 < 40) {
                personasEnCola++;
            }

            for (int i = 0; i < TOTAL_CAJAS; i++) {
                boolean puedeUsarCaja = (i == CAJA_REFUERZO_INDEX) ? isCajaRefuerzoActiva : true;

                if (personasEnCola > 0 && isCajaDisponible[i] && puedeUsarCaja) {
                    clientesAtendidosPorCaja[i]++;
                    isCajaDisponible[i] = false;
                    itemsEnProcesoPorCaja[i] = (int) (Math.random() * 11) + 5;
                    totalItemsVendidos += itemsEnProcesoPorCaja[i];
                    personasEnCola--;
                }
            }

            for (int i = 0; i < TOTAL_CAJAS; i++) {
                if (itemsEnProcesoPorCaja[i] > 0) {
                    itemsEnProcesoPorCaja[i]--;
                    if (itemsEnProcesoPorCaja[i] == 0) {
                        isCajaDisponible[i] = true;
                    }
                }
            }

            if (personasEnCola == 0) minutosSinCola++;

            isCajaRefuerzoActiva = (personasEnCola >= 15);

            imprimirEstadoMinuto(minutoActual, personasEnCola, itemsEnProcesoPorCaja, isCajaRefuerzoActiva);
        }

        imprimirResumenFinal(clientesAtendidosPorCaja, totalItemsVendidos, minutosSinCola, personasEnCola);
    }

    private static void imprimirEstadoMinuto(int min, int cola, int[] items, boolean activa) {
        System.out.printf("MINUTO %d - En cola: %d\n", min, cola);
        for (int i = 0; i < 4; i++) {
            System.out.print(" Caja " + (i+1) + ":[" + items[i] + "] |");
        }
        if (activa || items[4] > 0) {
            System.out.println(" Caja 5 (Refuerzo):[" + items[4] + "]");
        } else {
            System.out.println();
        }
    }

    private static void imprimirResumenFinal(int[] cajas, int totalI, int minVacio, int colaFinal) {
        int totalPersonas = 0;
        for (int atendidos : cajas) totalPersonas += atendidos;
        
        System.out.println("\n--- RESUMEN FINAL ---");
        System.out.println("Personas atendidas: " + totalPersonas);
        System.out.println("Productos vendidos: " + totalI);
        System.out.println("La cola estuvo vacía: " + minVacio + " minutos");
        System.out.println("Clientes sin atender: " + colaFinal);
    }
}
