/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication47;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.*;
import jxl.read.biff.BiffException;
import java.math.*;
import java.util.ArrayList;
import java.util.*;

/**
 *
 * @author Adam
 */
public class Node {

    public int baris = 16;
    public int kolom = 3;
    double[][] data = new double[baris][kolom];
    double[][] ortu = new double[baris][baris];
    double[][] ortu1 = new double[baris][kolom];
    double[][] ortu2 = new double[baris][kolom];
    double[][] anak1 = new double[baris][kolom];
    double[][] anak2 = new double[baris][kolom];
    double[] temp = new double[baris];
    int maxPopulasi = 99;
    int[] tempp = new int[baris];
    int ii;
    int jj = 0;
    int [][] populasi = new int [maxPopulasi][baris];
    //ArrayList<Integer> Pop = new ArrayList<Integer>(99);
    ArrayList<ArrayList<Integer>> Pop = new ArrayList<ArrayList<Integer>>();
    
    
    
    public void setPopulasi(){
        int[] temp2 = new int [baris];
        for(int i=0;i<maxPopulasi;i++){
            temp2=randompermutation();
           for(int j=0;j<baris;j++){
               
               populasi[i][j]=temp2[j];
              // Pop.add(temp2);
           }
           
        }
        
        System.out.println(" isi temp ");
        for(int i=0;i<baris;i++){
            System.out.println("temp "+temp[i]);
        }
        }
    
    public void showPopulasi(){
        for(int i=0;i<99;i++){
            for(int j=0;j<baris;j++){
                System.out.println("populasi " +i+" = "+populasi[i][j] +"");
            }
        }
    }
    
    public void setData(String input) {
        try {
            Workbook w = Workbook.getWorkbook(new File(input));

            Sheet s = w.getSheet(0);
            for (int i = 0; i < baris; i++) {
                for (int j = 0; j < kolom; j++) {
                    Cell c = s.getCell(j, i);
                    String isi = c.getContents();
                    data[i][j] = Double.parseDouble(isi);

                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    
    public void showData(double[][] data) {
        for (int i = 0; i < baris; i++) {
             System.out.println(data[i][0] +"; "+data[i][1]+"; "+data[i][2]);
            
        }

    }

    public void EucDistance() {
        double deltaX = (data[0][1] - data[1][1]);
        double Xsqr = Math.pow(deltaX, 2);
        double deltaY = (data[0][2] - data[1][2]);
        double Ysqr = Math.pow(deltaY, 2);
        //double hasil  = (data[0][1]-data[1][1])+ (data[0][2]-data[1][2]);
        double hasil = Math.sqrt(Xsqr + Ysqr);
        System.out.println("delta x " + deltaX);
        System.out.println("delta y " + deltaY);
        System.out.println(" X  kuadrat " + Xsqr);
        System.out.println(" Y  kuadrat " + Ysqr);
        System.out.println("hasil : " + hasil);
    }

//    public void cobaEuc() {
//        double deltaX, deltaY, hasil = 0;
//        for (int i = 0; i < baris; i++) {
//            for (int j = i + 1; j < baris; j++) {
//                System.out.println(" x ke : " + i + " : " + data[i][1]);
//                System.out.println(" x ke : " + j + " : " + data[j][1]);
//                System.out.println(" y ke : " + i + " : " + data[i][2]);
//                System.out.println(" y ke : " + j + " : " + data[j][2]);
//                deltaX = Math.pow(data[i][1] - data[j][1], 2);
//                System.out.println("delta X kuadrat : " + deltaX);
//                deltaY = Math.pow(data[i][2] - data[j][2], 2);
//                System.out.println("delta Y kuadrat : " + deltaY);
//                hasil = Math.sqrt(deltaX + deltaY);
//                System.out.println(" hasil euclidian : " + hasil);
//
//            }
//        }
//    }

    
    public  int[] randompermutation() {

        
        //id kota dipindah ke temp
        for (int i = 0; i < baris; i++) {

            temp[i] = data[i][0];

        }
        //proses random 
        for (int i = temp.length - 1; i > 0; i--) {
            int w = (int) Math.floor(Math.random() * (i + 1));
            double temp2 = temp[w];
            temp[w] = temp[i];
            temp[i] = temp2;
        }
        
        //konversi temp double ke tempp integer
        for (ii = 0; ii < temp.length; ii++) {

            tempp[ii] = (int) temp[ii];

        }
        return tempp;
       

    }

    public double Fitness(double[][] data) {
        double deltaX;
        double deltaY;
        double hasil = 0;
        double fitness = 0;
        for (int i = 0; i < baris; i++) {
            if (i > 0) {
                deltaX = Math.pow(data[i][1] - data[i - 1][1], 2);
                deltaY = Math.pow(data[i][2] - data[i - 1][2], 2);

                hasil = hasil + Math.sqrt(deltaX + deltaY);

                fitness = 1 / (hasil + fitness);
            }
        }
        System.out.println("fitness " + fitness);
        System.out.println("total tour " + hasil);
        return fitness;
    }

    public double[][] RP() {
        double[][] eud = new double[baris][kolom];
        double deltaX;
        double deltaY;
        double hasil = 0;
        double fitness = 0;
        
        
        System.out.println(" orang tua ");
        Random r = new Random();
        int ran = r.nextInt(99-1)+2;
        System.out.println(" random populasi ke "+ran);
        for (int i = 0; i < baris; i++) {
            //int xx = tempp[i];
            int xx=populasi[ran][i];
            xx = xx - 1;

            eud[i][0] = data[xx][0];
            eud[i][1] = data[xx][1];
            eud[i][2] = data[xx][2];
            System.out.println(data[xx][0] + " ; x = " + data[xx][1] + " ; y = " + data[xx][2]);

            // menghitung euclidian distance 
        }
        data = eud;

//        for (int i = 0; i < baris; i++) {
//            if (i > 0) {
//                deltaX = Math.pow(data[i][1] - data[i - 1][1], 2);
//                deltaY = Math.pow(data[i][2] - data[i - 1][2], 2);
//
//                hasil = hasil + Math.sqrt(deltaX + deltaY);
//
//                fitness = 1 / (hasil + fitness);
//            }
//        }
        
        Fitness(data);
        System.out.println("");
        return data;

    }

    public void ambilortu() {
       // randompermutation();
        ortu1 = RP();
       // randompermutation();
        ortu2 = RP();

//           for(int i=0;i<baris;i++){
//               System.out.println("ortu 1 "+ortu1[i][0]);
//           }
//           System.out.println("");
//           for(int i=0;i<baris;i++){
//               System.out.println("ortu 2 "+ortu2[i][0]);
//           }
        //xover(ortu1, ortu2);
    }

    public void xover(double[][] data1, double[][] data2) {
        Random r = new Random();
        int temp = 0;
        int idx = 0;
        double[][] temp1 = new double[baris][kolom];
        double[][] temp2 = new double[baris][kolom];

        ortu1 = data1;
        ortu2 = data2;
        int ran = r.nextInt(baris) + 1;
        //int ran = 8;
        //int ran2 = r.nextInt(baris - 1) + 1;       
        System.out.println(" hasil random 1 = " + ran);
        System.out.println(" kromosom 1 " + ortu1[ran][0] + " x1 = " + ortu1[ran][1] + " y2 = " + ortu1[ran][2]);

        System.out.println("");
        for (int j = ran; j < baris; j++) {

            anak1[j][0] = ortu2[j][0];
            anak1[j][1] = ortu2[j][1];
            anak1[j][2] = ortu2[j][2];
            System.out.println(" ortu 2 , anak 1 " + j + " " + anak1[j][0]);
        }
        for (int i = 0; i < ran; i++) {

            anak2[i][0] = ortu1[i][0];
            anak2[i][1] = ortu1[i][1];
            anak2[i][2] = ortu1[i][2];
            System.out.println(" ortu 1 , anak 2 " + i + " " + anak2[i][0]);
        }
        System.out.println(" ");
        //pengisian sisa kromosom anak1

        double[][] tempanak = new double[baris][kolom];
        double[][] tempanak2 = new double[baris][kolom];
        for (int o = 0; o < baris; o++) {

            for (int a = ran; a < baris; a++) {
                if (anak1[a][0] == ortu1[o][0]) {
                    temp = 1;
                    //System.out.println(" hasil sama " +ortu1[o][0] +" temp " +temp);
                    break;
                }

                if (anak1[a][0] != ortu1[o][0]) {
                    temp = 0;
                    //System.out.println(" hasil beda "+ortu1[o][0]+" temp " +temp);

                }
            }

            if (temp == 0) {
                anak1[idx][0] = ortu1[o][0];
                anak1[idx][1] = ortu1[o][1];
                anak1[idx][2] = ortu1[o][2];
                idx++;
            }

            //System.out.println(" idx "+idx);
        }

        for (int i = 0; i < baris; i++) {
            System.out.println("anak 1 " + i + " " + anak1[i][0]);

        }
        Fitness(anak1);
        System.out.println("");

        //pengisian sisa kromosom anak2
        for (int o = 0; o < baris; o++) {

            for (int a = 0; a < ran; a++) {
                if (anak2[a][0] == ortu2[o][0]) {
                    temp = 1;
                    //System.out.println(" hasil sama " +ortu1[o][0] +" temp " +temp);
                    break;
                }

                if (anak2[a][0] != ortu2[o][0]) {
                    temp = 0;
                    //System.out.println(" hasil beda "+ortu1[o][0]+" temp " +temp);

                }
            }

            if (temp == 0) {
                anak2[idx][0] = ortu2[o][0];
                anak2[idx][1] = ortu2[o][1];
                anak2[idx][2] = ortu2[o][2];
                idx++;
            }

            //System.out.println(" idx "+idx);
        }

        for (int i = 0; i < baris; i++) {
            System.out.println("anak 2 " + i + " " + anak2[i][0]);

        }
        double fit;
        fit=Fitness(anak2);
        System.out.println("");
        

    }

    public void mutasi(double[][] anak1 , double [][] anak2){
        Random rand = new Random();
        int ran1 = rand.nextInt(baris - 1) + 1;  
        int ran2 = rand.nextInt(baris - 1) + 1; 
        int ran3 = rand.nextInt(baris - 1) + 1;
        int ran4 = rand.nextInt(baris - 1) + 1;
        double [][] temp = new double[baris][kolom];
        //anak1 mutasi
        
        {
            System.out.println("random 1 "+ran1);
            System.out.println("random 2 "+ran2);
            temp[ran1][0] = anak1[ran1][0];
            temp[ran1][1] = anak1[ran1][1];
            temp[ran1][2] = anak1[ran1][2];
            anak1[ran1][0]=anak1[ran2][0];
            anak1[ran1][1]=anak1[ran2][1];
            anak1[ran1][2]=anak1[ran2][2];
            anak1[ran2][0]=temp[ran1][0];
            anak1[ran2][1]=temp[ran1][1];
            anak1[ran2][2]=temp[ran1][2];
        }
        
        //anak 2 mutasi
        {
            temp[ran3][0] = anak2[ran3][0];
            temp[ran3][1] = anak2[ran3][1];
            temp[ran3][2] = anak2[ran3][2];
            anak2[ran3][0]=anak2[ran4][0];
            anak2[ran3][1]=anak2[ran4][1];
            anak2[ran3][2]=anak2[ran4][2];
            anak2[ran4][0]=temp[ran3][0];
            anak2[ran4][1]=temp[ran3][1];
            anak2[ran4][2]=temp[ran3][2];
        }
        
        System.out.println(" mutasi anak ");
        showData(anak1);
        Fitness(anak1);
        showData(anak2);
        Fitness(anak2);
    }
    
    public void start() {
        int populasi = kolom;

        //for (int i = 0; i < 100; i++) {
        setPopulasi();
        //showPopulasi();
        //randompermutation();
        RP();
        ambilortu();
        xover(ortu1, ortu2);
        mutasi(anak1, anak2);

         //}
    }
}
