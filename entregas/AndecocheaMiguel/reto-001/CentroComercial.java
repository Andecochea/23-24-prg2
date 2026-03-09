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
        }

        imprimirResumenFinal(clientesAtendidosPorCaja, totalItemsVendidos, minutosSinCola, personasEnCola);
    }

    private static void imprimirResumenFinal(int[] atendidos, int totalI, int minVacio, int colaFinal) {
        int totalPersonas = 0;
        System.out.println("--- ESTADISTICAS DE LA JORNADA ---");
        for (int i = 0; i < atendidos.length; i++) {
            System.out.println("Caja " + (i + 1) + ": " + atendidos[i] + " clientes atendidos");
            totalPersonas += atendidos[i];
        }
        System.out.println("----------------------------------");
        System.out.println("Total de clientes atendidos: " + totalPersonas);
        System.out.println("Total de productos vendidos: " + totalI);
        System.out.println("Minutos con la cola vacía: " + minVacio);
        System.out.println("Clientes que no pudieron ser atendidos: " + colaFinal);
    }
}
