/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optima;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Lord-Karim
 */
public class Global {
    private ArrayList minList=new ArrayList();
    private ArrayList bestSolutionList=new ArrayList();
    private double rowssum=0;
    private int minindex;
    private double min=Double.POSITIVE_INFINITY;
    
    private int widthsize,
                heightsize,
                nbrItiration,
                minRowIndex;
    
    private String funcNam;
    
    private double[][] finalmatrix,
                       pop;
    
    private double[] fitness,
                     best;
    
    private double minBound,
                   maxBound,
                   tempMin;
    
    private boolean initialisation=false;
    
   private Random rand=new Random();
   private ArrayList tempList=new ArrayList();
    
    
    public Global(String fnam, int nbrItiration, int width, int height, double minBound,double maxBound, double[][] pp){
        this.widthsize=width;
        this.heightsize=height;
        this.funcNam=fnam;
        this.nbrItiration=nbrItiration;
        
        pop=new double[heightsize][widthsize];
        
        fitness=new double[heightsize];
        best=new double[widthsize];
        this.minBound=minBound;
        this.maxBound=maxBound;
        
        /*for(int i=0; i<heightsize;i++)
            for(int j=0; j<widthsize;j++)
                pp[i][j]=minBound+(maxBound - (minBound))*rand.nextDouble();*/
        
        //pop=pp;
        for(int i=0; i<heightsize;i++)System.arraycopy(pp[i], 0, pop[i], 0, widthsize);
                
        /*for(int i=0; i<heightsize;i++){
            for(int j=0; j<widthsize;j++)
                System.out.print(pp[i][j]+"\t");
            System.out.println();}
       */
    }
    
    public void local(){
        tempList=fitness(funcNam,pop);
        
        //if(initialisation==false){
            minList.add(tempList.get(0));
            tempMin=(double) tempList.get(0);
            best=pop[(int)tempList.get(1)];
            
        /*}else if(initialisation==true){
            minList.add(objective(funcNam,best));
            System.out.print("init from 2nd");
        }*/
        
        for(int i=0;i<widthsize;i++)
            bestSolutionList.add(best[i]);
        
        for(int k=0;k<nbrItiration;k++){
            tempList.clear();
            for(int i=0; i<heightsize;i++)
                for(int j=0; j<widthsize;j++)
                    pop[i][j]=minBound+(maxBound - (minBound))*rand.nextDouble();
            
            tempList=fitness(funcNam,pop);
            best=pop[(int)tempList.get(1)];
            if((double) tempList.get(0)<=tempMin){
                bestSolutionList.clear();
                for(int i=0;i<widthsize;i++)
                    bestSolutionList.add(best[i]);
            }
            
            if((double)tempList.get(0)<tempMin){
                minList.add(tempList.get(0));
                tempMin=(double) tempList.get(0);
            }else minList.add(tempMin);
        }
    }
     public ArrayList getMinList(){
        return minList;
    }
    
    public ArrayList getBestSolution(){
        return bestSolutionList;
    }
    public ArrayList fitness(String fnam,double[][] x){
        double somme=0.0,somme2=0.0, min, raised;
        double[] a=new double[heightsize];
        int minindex=0;
        ArrayList l=new ArrayList();
        switch (fnam) {
            case "dejong":
                for(int i=0;i<heightsize;i++){
                    for(int j=0;j<widthsize;j++){
                        //raised=Math.pow(;
                        somme+=Math.pow(x[i][j], 2);
                    }
                    a[i]=somme;
                    somme=0;
                }
            break;
            case "aphe" :
                for(int i=0; i<heightsize; i++){
                    for(int j=0;j<widthsize;j++){
                        somme += Math.pow(x[i][j], 2)*(j+1);
                    }
                    a[i]=somme;
                    somme=0;
                }
                break;
            case "rastrigin" : 
                for(int i=0; i<heightsize; i++){
                    for(int j=0;j<widthsize;j++){
                        somme += (Math.pow(x[i][j], 2))-(10*Math.cos(2*Math.PI*x[i][j]));
                    }
                    somme += 10*widthsize;
                    a[i]=somme;
                    somme=0;
                }
                
                break;
            case "schwefel" : 
                for(int i=0; i<heightsize; i++){
                    for(int j=0;j<widthsize;j++){
                        somme += -x[i][j]*Math.sin(Math.sqrt(Math.abs(x[i][j])));
                    }
                    a[i]=somme;
                    somme=0;
                }
                break;
            case "maphe" :
                for(int i=0; i<heightsize; i++){
                    for(int j=0;j<widthsize;j++){
                        somme += Math.pow(x[i][j], 2)*(j+1)*5;
                    }
                    a[i]=somme;
                    somme=0;
                }
                break;    
            case "sodp" :
                for(int i=0; i<heightsize; i++){
                    for(int j=0;j<widthsize;j++){
                        somme += Math.pow(Math.abs(x[i][j]), j+2);
                    }
                    a[i]=somme;
                    somme=0;
                }
                break;   
            case "michal" :
                for(int i=0; i<heightsize; i++){
                    for(int j=0;j<widthsize;j++){
                        somme += Math.sin(x[i][j])*Math.pow(Math.sin((((i+1)*Math.pow(x[i][j], 2))/Math.PI)),20);
                    }
                    somme=-somme;
                    a[i]=somme;
                    somme=0;
                }
                break;
            case "ackley" :
                for(int i=0; i<heightsize; i++){
                    for(int j=0;j<widthsize;j++){
                        somme  += Math.pow(x[i][j], 2);
                        somme2 += Math.cos(2*Math.PI*x[i][j]);
                    }
                    somme=-20*Math.exp(-0.2*Math.sqrt(somme/widthsize))-Math.exp(somme2/widthsize)+20+Math.exp(1);
                    a[i]=somme;
                    somme=0;
                }
                break;
            case "griewangk" :
                somme2=1;
                for(int i=0; i<heightsize; i++){
                    for(int j=0;j<widthsize;j++){
                        somme  += Math.pow(x[i][j], 2)/4000;
                        somme2 *= Math.cos(x[i][j]/Math.sqrt(j+1));
                    }
                    somme=somme-somme2+1;
                    a[i]=somme;
                    somme=0;
                }
                break;
            case "rbv" : 
                for(int i=0; i<heightsize; i++){
                    for(int j=0;j<widthsize-1;j++){
                        somme +=100*Math.pow(x[i][j+1]-Math.pow(x[i][j],2),2)+Math.pow(1-x[i][j], 2);
                    }
                    a[i]=somme;
                    somme=0;
                }
                break;
            case "rhe" : 
                for(int i=0; i<heightsize; i++){
                    for(int j=0;j<widthsize-1;j++){
                        for(int k=0;k<j;k++){
                            somme2 +=x[i][k];
                        }
                        somme += Math.pow(somme2, 2);
                    }
                    a[i]=somme;
                    somme=0;
                }
                break;
        }
        min=a[0];
        for(int i=0;i<heightsize;i++)
            if(min>=a[i]){
                min=a[i];
                minindex=i;
            }
        l.add(min);
        l.add(minindex);
        return l;
    }
    public void clear(){
         minList.clear();
         bestSolutionList.clear();
    }
}
