/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app1.hello;

import java.util.Scanner;

/**
 *
 * @author marex
 */
public class App1Hello {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        /*
        int wiek;
        System.out.print("Podaj wiek: ");
        wiek = Integer.parseInt(new Scanner(System.in).nextLine());
        if (wiek<=12&&wiek>=0){
            System.out.println("dziecko");
        }
        if (wiek<=17&&wiek>=13){
            System.out.println("młodzież");
        }
        if (wiek>=18){
            System.out.println("dorosły");      
        }
        if(wiek<0){
            System.out.println("żartowniś!");
        } */
        int[] tablica = new int [5];
        for (int i=0; i<5;i++){
            tablica[i]= i+1;
        }
        for (int x : tablica){
            System.out.println(x);
        }
    }
    
}
