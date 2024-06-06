package org.example;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    private static final int MATRIX_SIZE = 1000;
    private static final int THREAD_COUNT = 4;
    private static final int[] matrix = new int[MATRIX_SIZE];
    private static final int TARGET = 256; // Número a buscar

    public static void main(String[] args) {
        generar(matrix);
        System.out.println(Arrays.toString(matrix));
        ArrayList<Hilo> misHilos = divideArreglo(matrix, THREAD_COUNT);
        ArrayList<Thread> misThreads = new ArrayList<>();
        for (Hilo h: misHilos) {
            misThreads.add(new Thread(h));
        }
        long inicio = System.nanoTime();
        for (Thread h: misThreads) {
            h.start();
        }
        for (Thread h: misThreads) {
            try {
                h.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long fin = System.nanoTime();
        int encontrado = -1;
        for (Hilo h: misHilos) {
            if(h.getIndice() != -1){
                encontrado = h.getIndice();
                break;
            }
        }

        long inicio2 = System.nanoTime();
        int resultado = buscar(matrix, TARGET);
        long fin2 = System.nanoTime();
        if (resultado != -1) {
            System.out.println("El número " + TARGET + " fue encontrado en la posición " + resultado);
            System.out.println("secuencial: "+(fin2-inicio2)+" nanosegundos");
        } else {
            System.out.println("El número " + TARGET + " no fue encontrado");
            System.out.println("Secuencial: "+(fin2-inicio2)+" nanosegundos");
        }
        if(encontrado != -1){
            System.out.println("El número " + TARGET + " fue encontrado en la posición " + encontrado);
            System.out.println("Paralelo: "+(fin-inicio)+" nanosegundos");
        }else{
            System.out.println("El número " + TARGET + " no fue encontrado");
            System.out.println("Paralelo: "+(fin-inicio)+" nanosegundos");
        }


    }
    public static void generar(int arr[]){
        for(int i = 0; i < MATRIX_SIZE; i++){
                arr[i] = (int) (Math.random() * 1000);
        }
    }
    public static int buscar(int[] arreglo, int target){
        for(int i = 0; i < arreglo.length; i++){
            if(arreglo[i] == target){
                return i;
            }
        }
        return -1;
    }
    public static ArrayList<Hilo> divideArreglo(int[] arreglo, int canthilos){
        ArrayList<Hilo> misHilos = new ArrayList<>();
        int tamano = arreglo.length;
        int tamanoHilo = tamano / canthilos;
        int inicio = 0;
        for(int i = 0; i < canthilos; i++){
            int [] arregloHilo = new int[tamanoHilo];
            for(int j = 0; j < tamanoHilo; j++){
                arregloHilo[j] = arreglo[inicio];
                inicio++;
            }
            int agregado = tamanoHilo* i;
            Hilo hilo = new Hilo(arregloHilo,TARGET,agregado);
            misHilos.add(hilo);
        }
        return misHilos;
    }
}