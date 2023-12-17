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
public class Movie {
    String title="Default";
    int year;
    String rating;
    public void info(){
        System.out.println("Title: "+title+", year: "+year+". My rating: "+rating);
    }
}
