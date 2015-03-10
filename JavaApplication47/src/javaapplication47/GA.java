/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication47;

/**
 *
 * @author Adam
 */
public class GA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Populasi p = new Populasi();
        String input = "D:/don't open/semester 6/AI/Tugas Besar/16Ulysses.xls";
        Node n = new Node();
        
        n.setData(input);
        n.start();
       // p.isiPopulasi();
        
        //n.xover();
       //n.showData();
       
        
        
    }
    
}
