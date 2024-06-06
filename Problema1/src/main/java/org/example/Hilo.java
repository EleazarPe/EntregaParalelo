package org.example;

public class Hilo extends Thread{
    private int indice;
    private int[] arreglo;
    private int target;
    private int anteriores;

    public Hilo(int[] arreglo, int target,int anteriores){
        this.arreglo = arreglo;
        this.indice = -1;
        this.target = target;
        this.anteriores = anteriores;
    }
    public void run(){
        buscar();
    }
    public void buscar(){
        for(int i = 0; i < arreglo.length; i++){
            if(arreglo[i] == this.target){
              this.indice = i+anteriores;
            }
        }
    }
    public int getIndice(){
        return this.indice;
    }

}
