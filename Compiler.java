package com.utn.main;

import java.util.List;
import java.util.Random;

public class Compiler {
    int HV = 1000000;

    public void inicializacionDeSimulacion(int cantEmpleados, List<Integer> STO, List<Integer> ITO, List<Integer> TPS) {

        for (int i = 0; i < cantEmpleados; i++) {

            TPS.add(i, HV);
            STO.add(i, 0);
            ITO.add(i, 0);
        }
    }

    //Se calculan 20 días laborables por seis meses, por los minutos de cantidadHoras que trabaja el empleado
    public int calcularTiempoDeSimulacion(int cantidadHoras) {
        return 20 * 6 * 60 * cantidadHoras;
    }


    public int calcularIntervaloDeArribo() {
        Random r = new Random();
        int low = 10;
        int high = 16;
        return r.nextInt(high - low) + low;
    }

    public int calcularTiempoDeAtencion() {
        Random r = new Random();
        int low = 25;
        int high = 35;
        return r.nextInt(high - low) + low;
    }


    public int obtenerEmpleadoLibre(int cantEmpleados, List<Integer> TPS) {

        for (int i = 0; i < cantEmpleados; i++) {
            if (TPS.get(i) == HV) {
                return i;
            }

        }
        return 1000000;
    }

    public int empleadoConMenorTPS(int cantEmpleados, List<Integer> TPS) {
        int minimo = 0;
        for(int i = 1 ; i< cantEmpleados ; i++)
        {
            if(TPS.get(i) < TPS.get(minimo)){
                minimo = i;
            }
        }
        return minimo;
    }


    public void actualizarValor(List<Integer> lista, int indice, int nuevoValor){
        lista.set(indice,nuevoValor);
        //lista.remove(lista.size()-1);
    }


}

