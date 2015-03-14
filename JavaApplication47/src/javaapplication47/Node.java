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

    public int baris = 22;
    public int kolom = 3;
    double[][] original = new double[baris][kolom];
    double[][] data = new double[baris][kolom];
    double[][] ortu = new double[baris][baris];
    double[][] ortu1 = new double[baris][kolom];
    double[][] ortu2 = new double[baris][kolom];
    double[][] anak1 = new double[baris][kolom];
    double[][] anak2 = new double[baris][kolom];
    double[] temp = new double[baris];
    int maxPopulasi = 100;
    int[] tempp = new int[baris];
    int ii;
    int jj = 0;
    int[][] populasi = new int[maxPopulasi][baris];
    int[][] temppopulasi = new int[maxPopulasi][baris];
    double[][] tempfitness = new double[maxPopulasi][1];
    double maxFitness;
    Random rankom = new Random();
    int xx=0;
    int yy=0;
    //ArrayList<Integer> Pop = new ArrayList<Integer>(99);
    HashMap<Integer[], Double> BFK = new HashMap<Integer[], Double>();
    //ArrayList<ArrayList<Double>> temppopulasi = new ArrayList<ArrayList<Double>>();

    public void setPopulasi() {
        //membuat populasi dari kumpulan kromosom
        int[] temp2 = new int[baris];
        for (int i = 0; i < maxPopulasi; i++) {
            temp2 = randompermutation();
            for (int j = 0; j < baris; j++) {

                populasi[i][j] = temp2[j];
                //RP();
                // Pop.add(temp2);
            }

        }

    }

    public void showPopulasi() {
        for (int i = 0; i < maxPopulasi; i++) {
            for (int j = 0; j < baris; j++) {
                System.out.println("populasi " + i + " = " + populasi[i][j] + "");
            }
        }
        System.out.println("");
    }
    
    public void showTempPopulasi() {
        for (int i = 0; i < maxPopulasi; i++) {
            for (int j = 0; j < baris; j++) {
                System.out.println("temp populasi " + i + " = " + temppopulasi[i][j] + "");
            }
        }
        System.out.println("");
        System.out.println("end");
    }

    public void setData(String input) {
        //input dari data excel
        try {
            Workbook w = Workbook.getWorkbook(new File(input));

            Sheet s = w.getSheet(1);
            for (int i = 0; i < baris; i++) {
                for (int j = 0; j < kolom; j++) {
                    Cell c = s.getCell(j, i);
                    String isi = c.getContents();
                    original[i][j] = Double.parseDouble(isi);

                }
            }
            data=original;
        } catch (IOException ex) {
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showData(double[][] data) {
        //show data kromosom
        for (int i = 0; i < baris; i++) {
            System.out.println(data[i][0] + "; " + data[i][1] + "; " + data[i][2]);

        }

    }

    public void EucDistance() {
        //udah gk dipake gk guna
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

    public int[] randompermutation() {
        //membuat angka random dari kota 
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
//        System.out.println("fitness " + fitness);
//        System.out.println("total tour " + hasil);

        return fitness;
    }

    public void ShowFitnessKromosom() {
        for (int i = 0; i < maxPopulasi; i++) {
            //System.out.println(" fitness kromosom ke " + i + " = " + tempfitness[i][0]);
        }
    }

    public void resetMaxFitness(){
        for(int i=0;i<maxPopulasi;i++){
            tempfitness[i][0]=0;
        }
        
    }
    
    public void maxFitnessPopulasi() {
        int MF;
        
        maxFitness = tempfitness[0][0];
        int idx = 0;
        //mencari nilai maksimal fitness populasi
        for (int i = 0; i < maxPopulasi; i++) {
            
            if (tempfitness[i][0] > maxFitness) {
                maxFitness = tempfitness[i][0];
                idx = i;

            }
        }
        //System.out.println(" max fitness adalah " + max + " ada di kromosom ke " + idx);
        //System.out.println(" tour "+1/max);
       // System.out.println(" isi kromosom ");
        
        //output kromosom dengan fitness maksimmal
        for(int i=0;i<baris;i++){
            //System.out.print(populasi[idx][i]+" "+" kromosom nomor "+idx +" tour "+1/tempfitness[idx][0]+"fitness ="+ tempfitness[idx][0] +'\n');
        }
        System.out.println("");
        
        //memindahkan isi maxfitness populasi ke temppopulasi
        for(int i=0;i<baris;i++){
            temppopulasi[0][i]=populasi[idx][i];
            
//            MF=populasi[idx][i];
//            temppopulasi.add(Arrays.asList(MF));
        }
        
    }
    
    public double[][] RP2() {
        //untuk mengambil nilai urut dari populasi
        double[][] eud = new double[baris][kolom];
        double[][] eud2 = new double[baris][kolom];
        double deltaX;
        double deltaY;
        double hasil = 0;
        double fitness = 0;
        double fit = 0;
        
        //System.out.println(" orang tua RP 2 ");

        //int ran = rankom.nextInt(99 - 2) + 1;
        //System.out.println(" random populasi ke " + ran);
        for (int j = 0; j < maxPopulasi; j++) {
            for (int i = 0; i < baris; i++) {
                //int xx = tempp[i];
                int xx = populasi[j][i];
                xx = xx - 1;
                //System.out.println(" hasil populasi "+ populasi[j][i]);
                eud[i][0] = original[xx][0];
                eud[i][1] = original[xx][1];
                eud[i][2] = original[xx][2];
                //System.out.println(data[xx][0] + " ; x = " + data[xx][1] + " ; y = " + data[xx][2]);

                // menghitung euclidian distance 
            }
            data = eud;
            fitness = Fitness(data);
            tempfitness[j][0] = fitness;
        }
        //fit = Fitness(data);
        //System.out.println(" fitness kromosom" +fit);
//        if(tempfitness[ran][0] == 0){
//            tempfitness[ran][1] = fit;
//        }
        return data;
    }

    public double[][] RP() {
        //hasil random permutasi di masukan ke dalam kromosom aka data[][]
        double[][] eud = new double[baris][kolom];
        double deltaX;
        double deltaY;
        double hasil = 0;
        double fitness = 0;
        double fit = 0;

        //System.out.println(" orang tua ");

        int ran = rankom.nextInt(99 - 2) + 1;
        //System.out.println(" random populasi ke " + ran);
        for (int i = 0; i < baris; i++) {
            //int xx = tempp[i];
            int xx = populasi[ran][i];
            xx = xx - 1;

            eud[i][0] = original[xx][0];
            eud[i][1] = original[xx][1];
            eud[i][2] = original[xx][2];
            //System.out.println(data[xx][0] + " ; x = " + data[xx][1] + " ; y = " + data[xx][2]);

            // menghitung euclidian distance 
        }
        data = eud;
        fit=Fitness(data);
        return data;

    }

    public void ambilortu() {
        // randompermutation();
        ortu1 = RP();
        // randompermutation();
        ortu2 = RP();

        //xover(ortu1, ortu2);
    }

    public void xover(double[][] data1, double[][] data2, double pxover) {
        double p_xover = 0.8;
        Random r = new Random();
        int temp = 0;
        int idx = 0;
        double[][] temp1 = new double[baris][kolom];
        double[][] temp2 = new double[baris][kolom];
        if(pxover < p_xover){
            ortu1 = data1;
        ortu2 = data2;
        int ran = r.nextInt(baris - 1) + 1;
        //int ran = 8;
        //int ran2 = r.nextInt(baris - 1) + 1;       
        //System.out.println(" hasil random 1 = " + ran);
       // System.out.println(" kromosom 1 " + ortu1[ran][0] + " x1 = " + ortu1[ran][1] + " y2 = " + ortu1[ran][2]);

        System.out.println("");
        for (int j = ran; j < baris; j++) {

            anak1[j][0] = ortu2[j][0];
            anak1[j][1] = ortu2[j][1];
            anak1[j][2] = ortu2[j][2];
            //System.out.println(" ortu 2 , anak 1 " + j + " " + anak1[j][0]);
        }
        for (int i = 0; i < ran; i++) {

            anak2[i][0] = ortu1[i][0];
            anak2[i][1] = ortu1[i][1];
            anak2[i][2] = ortu1[i][2];
            //System.out.println(" ortu 1 , anak 2 " + i + " " + anak2[i][0]);
        }
        //System.out.println(" ");

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

//        for (int i = 0; i < baris; i++) {
//            System.out.println("anak 1 " + i + " " + anak1[i][0]);
//
//        }
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
            //System.out.println("anak 2 " + i + " " + anak2[i][0]);

        }
        double fit;
        fit = Fitness(anak2);
        System.out.println("");
        }
        else{
            anak1=ortu1;
            anak2=ortu2;
        }

        

    }

    public void mutasi(double[][] anak1, double[][] anak2) {
        double p_mutasi = 0.3;
        Random rand = new Random();
        int ran1 = rand.nextInt(baris - 1) + 1;
        int ran2 = rand.nextInt(baris - 1) + 1;
        int ran3 = rand.nextInt(baris - 1) + 1;
        int ran4 = rand.nextInt(baris - 1) + 1;
        double[][] temp = new double[baris][kolom];
        //anak1 mutasi

        {
//            System.out.println("random 1 " + ran1);
//            System.out.println("random 2 " + ran2);
            temp[ran1][0] = anak1[ran1][0];
            temp[ran1][1] = anak1[ran1][1];
            temp[ran1][2] = anak1[ran1][2];
            anak1[ran1][0] = anak1[ran2][0];
            anak1[ran1][1] = anak1[ran2][1];
            anak1[ran1][2] = anak1[ran2][2];
            anak1[ran2][0] = temp[ran1][0];
            anak1[ran2][1] = temp[ran1][1];
            anak1[ran2][2] = temp[ran1][2];
        }

        //anak 2 mutasi
        {
            temp[ran3][0] = anak2[ran3][0];
            temp[ran3][1] = anak2[ran3][1];
            temp[ran3][2] = anak2[ran3][2];
            anak2[ran3][0] = anak2[ran4][0];
            anak2[ran3][1] = anak2[ran4][1];
            anak2[ran3][2] = anak2[ran4][2];
            anak2[ran4][0] = temp[ran3][0];
            anak2[ran4][1] = temp[ran3][1];
            anak2[ran4][2] = temp[ran3][2];
        }

        //System.out.println(" mutasi anak ");
        //showData(anak1);
        Fitness(anak1);
        //showData(anak2);
        Fitness(anak2);
    }

    public void pindah(int i) {

        //anak1
        if(i<=maxPopulasi-2){

            for (xx = 0; xx < baris; xx++) {
                temppopulasi[i][xx] = (int) anak1[xx][0];
            }
        //anak2

            yy = xx + 1;
            for (yy = 0; yy < baris; yy++) {
                temppopulasi[i + 1][yy] = (int) anak2[yy][0];
            }
            xx = i + yy;
        }
    }
    
    public void tukar(){
        populasi=temppopulasi;
    }
    
    public void start() {
        //generasi
        Random r = new Random();
        double rr;
        int generasi = 300;
        setPopulasi();
        for (int j = 0; j < generasi; j++) {
            System.out.println(" populasi generasi ke "+j);
            resetMaxFitness();
            //showPopulasi();
            RP2();
            maxFitnessPopulasi();
            for (int i = 1; i < maxPopulasi; i++) {
                
                //maxFitnessPopulasi();
                RP();
                //Fitness(data);
                ambilortu();
                rr = r.nextFloat();
                xover(ortu1, ortu2,rr);
                mutasi(anak1, anak2);
                pindah(i);
            }
            
            System.out.println(" kromosom generasi terbaik ke " +j+" "+maxFitness +" tour ="+1/maxFitness);
            showTempPopulasi();
            tukar();
            
        }

    }
}
