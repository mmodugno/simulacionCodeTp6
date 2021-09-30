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
    public int calcularTiempoDeSimulacion() {
        return 20 * 6 * 60 * 9;
    }


    public Integer calcularIntervaloDeArribo() {
        /*Random r = new Random();
        int low = 10;
        int high = 16;
        return r.nextInt(high - low) + low;
        */
        int b = 12;
        int c = 2;
        double a = 1 / (c * Math.sqrt(2 * Math.PI) );
        double yi = HV;
        double func = 0;
        Double xi = 0.0;
        while (yi > func){
            Random r = new Random();

            double aux = Math.random();
            double aux2 = Math.random();
            xi = 8 + 8 * aux;
            yi = a * aux2;
            func = a * Math.pow(Math.E, - Math.sqrt(xi-b)/(2*Math.sqrt(c)));

        }
        System.out.println(xi.intValue());
        return xi.intValue();
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
    public boolean isEmpleadoLibre(int cantEmpleados, List<Integer> TPS){
        for (int i = 0; i < cantEmpleados; i++) {
            if (TPS.get(i) == HV) {
                return true;
            }
        }
        return false;
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


    public void seVanLosEmpleados(int cantEmpleados,List<Integer> TPS) {
        for (int i = 0; i < cantEmpleados; i++) {
            TPS.set(i, HV);
        }
    }


}

