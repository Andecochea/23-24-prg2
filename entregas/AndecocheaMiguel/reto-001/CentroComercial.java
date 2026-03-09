public class CentroComercial {
    public static void main(String[] args) {
        final int TOTAL_CAJAS = 5;
        final int MINUTOS_JORNADA = 12 * 60;
        final int LIMITE_COLA_REFUERZO = 15;

        int[] clientesAtendidosPorCaja = new int[TOTAL_CAJAS];
        int[] itemsEnProcesoPorCaja = new int[TOTAL_CAJAS];
        boolean[] isCajaDisponible = {true, true, true, true, true};

        int personasEnCola = 0;
        int totalItemsVendidos = 0;
        int minutosSinCola = 0;
        boolean isCajaRefuerzoActiva = false;

        for (int minutoActual = 1; minutoActual < MINUTOS_JORNADA; minutoActual++) {
            
            if (Math.random() * 100 < 40) {
                personasEnCola++;
            }

            for (int i = 0; i < TOTAL_CAJAS; i++) {
                boolean puedeAtender = (i < 4) || (i == 4 && isCajaRefuerzoActiva);

                if (personasEnCola > 0 && isCajaDisponible[i] && puedeAtender) {
                    clientesAtendidosPorCaja[i]++;
                    isCajaDisponible[i] = false;
                    itemsEnProcesoPorCaja[i] = (int) (Math.random() * 11) + 5;
                    totalItemsVendidos += itemsEnProcesoPorCaja[i];
                    personasEnCola--;
                }

                if (itemsEnProcesoPorCaja[i] > 0) {
                    itemsEnProcesoPorCaja[i]--;
                    if (itemsEnProcesoPorCaja[i] == 0) {
                        isCajaDisponible[i] = true;
                    }
                }
            }

            if (personasEnCola == 0) {
                minutosSinCola++;
            }
            
            isCajaRefuerzoActiva = (personasEnCola >= LIMITE_COLA_REFUERZO);

            imprimirEstadoMinuto(minutoActual, personasEnCola, itemsEnProcesoPorCaja, isCajaRefuerzoActiva);
        }

        imprimirResumenFinal(clientesAtendidosPorCaja, totalItemsVendidos, minutosSinCola, personasEnCola);
    }

    private static void imprimirEstadoMinuto(int min, int cola, int[] items, boolean activa) {
        System.out.print("MINUTO " + min + " - Cola: " + cola + " | ");
        for (int i = 0; i < items.length; i++) {
            System.out.print("C" + (i + 1) + ":[" + items[i] + "] ");
        }
        if (activa) System.out.print(" (REFUERZO)");
        System.out.println();
    }

    private static void imprimirResumenFinal(int[] atendidos, int totalI, int minVacio, int colaFinal) {
        int totalPersonas = 0;
        System.out.println("\n--- ESTADISTICAS FINALES ---");
        for (int i = 0; i < atendidos.length; i++) {
            System.out.println("Caja " + (i + 1) + ": " + atendidos[i] + " clientes");
            totalPersonas += atendidos[i];
        }
        System.out.println("----------------------------");
        System.out.println("Total Atendidos: " + totalPersonas);
        System.out.println("Total Productos: " + totalI);
        System.out.println("Minutos sin cola: " + minVacio);
        System.out.println("Clientes restantes: " + colaFinal);
    }
}
