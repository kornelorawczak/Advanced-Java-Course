/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klasa;

/**
 *
 * @author marex
 */
public class rectangle {
    int length;
    int width;
    public void area(){
        int area=length*width;
        System.out.println("Area equals = "+area);
    }
    public void perimeter(){
        int perimeter=2*length+2*width;
        System.out.println("Perimeter equals = "+perimeter);
    }
    public void diagonal(){
        double diagonal=Math.sqrt(length*length+width*width);
        System.out.println("Diagonal equals = "+diagonal);
    }
}
