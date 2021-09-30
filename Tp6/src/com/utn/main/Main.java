package com.utn.main;

import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) {

        Compiler compiler = new Compiler();

        //CONDICIONES INICIALES :
        int IA = 0; //intervalo entre arribo de pedidos
        int TA = 0; //Tiempo de atencion del empleado en minutos
        int CT = 0; //cantidad de tickets en el sistema
        int TPLL = 0; //Tiempo de proxima llegada de ticket al sistema
        Integer ticketsQueEntraron = 0; //cantidad de tickets atendidos al final de la simulacion
        int sumatoriaTiempoEsperaDeTickets = 0; //Sumatoria del tiempo desde que el ticket ingresa hasta que es atendido

        int STLL = 0; //Sumatoria de tiempos de llegada
        int STS = 0; //Sumatoria de tiempos de salida de tickets

        //CANTIDAD EMPLEADOS
        int cantEmpleados = 3;
        int cantidadHoras = 8; //2924
        int HV = 1000000;
        //Calculo de Tiempo Ocioso:
        List<Integer> STO = new ArrayList<>(); //Para el STO de cada empleado
        List<Integer> ITO = new ArrayList<>(); //Para el ITO de cada empleado
        List<Integer> TPS = new ArrayList<>(); //Para el TPS de cada cola, hacemos una lista

        int tiempo = 0;

        //................... COMIENZO DE SIMULACION ...............

        Integer tiempoSimulacion = compiler.calcularTiempoDeSimulacion();
        compiler.inicializacionDeSimulacion(cantEmpleados, STO, ITO, TPS);
        int calc = tiempoSimulacion - 0 * 6 * 60 * cantidadHoras;

        while (tiempo < tiempoSimulacion) {

            if(tiempo >= 20 * 6 * 60 * cantidadHoras){ //siguen entrando tickets, los empleados se VAN
               compiler.seVanLosEmpleados(cantEmpleados,TPS);
            }

            if (TPLL <= TPS.stream().min(Integer::compare).get()) { //Cortamos cuando llegamos a las 6 horas
                tiempo = TPLL;
                if(tiempo <= 20 * 6 * 60 * cantidadHoras) {
                    STLL = STLL + tiempo;
                }
                IA = compiler.calcularIntervaloDeArribo();
                TPLL = tiempo + IA;
                CT = CT + 1;
                ticketsQueEntraron = ticketsQueEntraron + 1;


                if (CT <= cantEmpleados && tiempo <= 20 * 6 * 60 * cantidadHoras) {
                    //busco el empleado libre
                    int emplLibre = compiler.obtenerEmpleadoLibre(cantEmpleados, TPS);

                    TA = compiler.calcularTiempoDeAtencion();
                    // TPS.add(emplLibre, tiempo + TA);
                    compiler.actualizarValor(TPS, emplLibre, tiempo + TA);
                    // STO.add(emplLibre,STO.get(emplLibre) + (tiempo - ITO.get(emplLibre)));
                    int nuevoSTO = STO.get(emplLibre) + (tiempo - ITO.get(emplLibre));
                    compiler.actualizarValor(STO, emplLibre, nuevoSTO);
                    continue;

                } else {
                    continue;
                }

            } else {
                // TPLL >= Algun TPS ----> SALE UN TICKET
                tiempo = TPS.stream().min(Integer::compare).get();
                STS = STS + tiempo;
                CT = CT - 1;
                int emplAux = compiler.empleadoConMenorTPS(cantEmpleados, TPS);

                if (CT >= cantEmpleados) {
                    TA = compiler.calcularTiempoDeAtencion();
                    //TPS.add(emplAux, tiempo + TA);
                    compiler.actualizarValor(TPS, emplAux, tiempo + TA);
                    continue;
                } else {
                    //ITO.add(emplAux,tiempo);
                    //TPS.add(emplAux,HV);
                    compiler.actualizarValor(ITO, emplAux, tiempo);
                    compiler.actualizarValor(TPS, emplAux, HV);

                    continue;
                }
            }

        }

        //HAY TODAVIA TICKETS POR ATENDER:
        //TPLL = HV;
       /* while (CT != 0) {
            // TPLL >= Algun TPS ----> SALE UN TICKET
            tiempo = TPS.stream().min(Integer::compare).get();
            STS = STS + tiempo;
            CT = CT - 1;
            int emplAux = compiler.empleadoConMenorTPS(cantEmpleados, TPS);

            if (CT >= cantEmpleados) {
                TA = compiler.calcularTiempoDeAtencion();
                //TPS.add(emplAux, tiempo + TA);
                compiler.actualizarValor(TPS, emplAux, tiempo + TA);
                continue;
            } else {
                //ITO.add(emplAux,tiempo);
                //TPS.add(emplAux,HV);
                compiler.actualizarValor(ITO, emplAux, tiempo);
                compiler.actualizarValor(TPS, emplAux, HV);

                continue;
            }
        }*/


        //TERMINA LA SIMULACION
        System.out.println("_________________INICIO SIMULACION_____________________");
        System.out.println("CANTIDAD DE EMPLEADOS: " + cantEmpleados + " TRABAJANDO " + cantidadHoras + " HORAS");
        System.out.println("TIEMPO TOTAL DE SIMULACION: " + tiempoSimulacion);

        for (int i = 0; i < cantEmpleados; i++) {
            int numeroEmpleado = i + 1;
            Float stoi = (STO.get(i).floatValue() / tiempoSimulacion) * 100;
            System.out.println("Porcentaje de tiempo ocioso de empleado " + numeroEmpleado + ": " + stoi.shortValue() + "%");
        }
        System.out.println("Cantidad de tickets recibidos en total: " + ticketsQueEntraron);
        System.out.println("Cantidad de tickets atendidos: " + (ticketsQueEntraron - CT));
        Float porcentajeAtendidos = (ticketsQueEntraron.floatValue() - CT) / ticketsQueEntraron.floatValue() * 100;
        System.out.println("Porcentaje de tickets atendidos: " + porcentajeAtendidos.shortValue() + "%");


        Float cantTicketsAtendidosPorDia = tiempoSimulacion.floatValue() / ticketsQueEntraron.floatValue() / 60 * cantidadHoras;
        System.out.println("Cantidad de tickets atendidos por hora: " + cantTicketsAtendidosPorDia);
        Integer aux =  (STS - STLL) / (ticketsQueEntraron.intValue()-CT);
        System.out.println("Promedio de permanencia en el sistema de ticket " + aux.toString() );
        System.out.println("_________________________FIN___________________________\n");


    }
}






