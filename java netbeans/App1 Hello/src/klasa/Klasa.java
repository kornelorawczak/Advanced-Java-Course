
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klasa;
import java.util.Scanner;
/**
 *
 * @author marex
 */
public class Klasa {
    public static void main(String[]args){
//        Movie gentlemen = new Movie();
//        gentlemen.title="The Gentlemen";
//        gentlemen.year=2019;
//        gentlemen.rating="9/10";
//        gentlemen.info();


//        Animal animal = new Animal();
//        Cat cat = new Cat();
//        cat.getVoice();
//        cat.eat();

//        int length, width;
//        System.out.println("Insert length: ");
//        length = Integer.parseInt(new Scanner(System.in).nextLine());
//        System.out.println("Insert width: ");
//        width = Integer.parseInt(new Scanner(System.in).nextLine());
//        rectangle rectangle = new rectangle();
//        rectangle.length=length;
//        rectangle.width=width;
//        rectangle.area();
//        rectangle.perimeter();
//        rectangle.diagonal();
        
//        Person p1= new Person("Kornel",17);
//        //System.out.println(p1.name + ", "+ p1.age);
//        Person p2 = new Person();
        try{
            int res = 5/0;
        }
        catch(ArithmeticException ex){
            System.out.println("Previous one failed...");
            System.out.println(ex.getMessage());
        }
        
                
    }
}
