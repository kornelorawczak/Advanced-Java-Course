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
public class Person {
    Person(){
        System.out.println("Default constructor without any data given");
    }
    Person(String name, int age){
        this.name=name;
        this.age=age;
    }
    String name;
    int age;
}
