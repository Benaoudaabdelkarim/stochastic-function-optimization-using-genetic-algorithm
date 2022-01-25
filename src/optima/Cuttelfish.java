
package optima;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Lord-Karim
 */
public class Cuttelfish {
    private double[][] pop,
                       G1,
                       G2,
                       G3,
                       G4;
    
    private double[] best;
    
    private double minBound,
                   maxBound,
                   AvPointBest,
                   fitnessBest,
                   R,
                   r1=1,
                   r2=-1,
                   V,
                   v1=0.5,
                   v2=-0.5;
    
    private int width,
                height,
                nbrItiration,divheight;
    
    private Random rand=new Random();
    private ArrayList lList             =new ArrayList(),
                      bestSolutionList  =new ArrayList(),
                      minList           =new ArrayList(),
                      tempFitness       =new ArrayList();
    
    private String fNam;
    
    public Cuttelfish(String fNam, int nbrItiration, int width, int height, double minBound,double maxBound, double[][] pp){
        this.minBound=minBound;
        this.maxBound=maxBound;
        this.width=width;
        this.height=height;
        this.nbrItiration=nbrItiration;
        this.fNam=fNam;
        
        if(this.height<4){
            this.height=4;
            divheight=1;
        }
        else if(this.height % 4 == 0)divheight=this.height/4;
        else if(this.height>4 && height%4 != 0){
            while(this.height%4 != 0){
                this.height++;
            }
            divheight=this.height/4;
        }
        
        pop=new double[this.height][width];
        best=new double[width];
        
        G1=new double[divheight][width];
        G2=new double[divheight][width];
        G3=new double[divheight][width];
        G4=new double[divheight][width];
        
        R=r2+(r1-r2)*rand.nextDouble();
        V=v2+(v1-v2)*rand.nextDouble();
        //initialisation de la population
        int i;
        for(i=0; i<height;i++)System.arraycopy(pp[i], 0, pop[i], 0, width);
        
        while(i<pop.length){
            for(int j=0; j<this.width; j++)
                pop[i][j]=minBound+(maxBound - (minBound))*rand.nextDouble();
            i++;
        }
        
        /*for(int a=0; a<this.height;a++){
            for(int j=0; j<width;j++)
                System.out.print(pop[a][j]+"\t");
            System.out.println();}*/
        
        //for(int i=0; i<this.height;i++){for(int j=0; j<this.width; j++)System.out.print(pop[i][j]+"\t");System.out.println();}
    }
    
    public void algoFish(){
        //if(best!=null){System.out.print("loooooooooooooool");}
        
        lList=fitness(fNam, height, pop);
        minList.add(lList.get(0));
        fitnessBest=(double) lList.get(0);
        best=pop[(int)lList.get(1)];
        //Devision de la solution
        for(int i=0; i<divheight;i++)System.arraycopy(pop[i], 0, G1[i], 0, width);
        int a=0,b=divheight;
        while(a<divheight){
            System.arraycopy(pop[b], 0, G2[a], 0, width);
            a++;b++;
        }
        a=0;b=divheight*2;
        while(a<divheight){
            System.arraycopy(pop[b], 0, G3[a], 0, width);
            a++;b++;
        }
        a=0;b=divheight*3;
        while(a<divheight){
            System.arraycopy(pop[b], 0, G4[a], 0, width);
            a++;b++;
        }
        
        //for(int i=0; i<divheight;i++){for(int j=0; j<width; j++)System.out.print(G1[i][j]+"\t");System.out.println();}System.out.println();
        //for(int i=0; i<divheight;i++){for(int j=0; j<width; j++)System.out.print(G2[i][j]+"\t");System.out.println();}System.out.println();
        //for(int i=0; i<divheight;i++){for(int j=0; j<width; j++)System.out.print(G3[i][j]+"\t");System.out.println();}System.out.println();
        //for(int i=0; i<divheight;i++){for(int j=0; j<width; j++)System.out.print(G4[i][j]+"\t");System.out.println();}System.out.println();
        for(int itiration=0; itiration<nbrItiration;itiration++){
        //calculate averige point
            AvPointBest=averagrPoint(best);
            
            for(int i=0; i<divheight;i++)
                for(int j=0; j<width; j++)
                    G1[i][j]=R*G1[i][j]+V*(best[j]-G1[i][j]);
            tempFitness=fitness(fNam, divheight, G1);
            if((double) tempFitness.get(0)<fitnessBest){
                fitnessBest=(double)tempFitness.get(0);
                best=G1[(int)tempFitness.get(1)];
            }
            
            for(int i=0; i<divheight;i++)
                for(int j=0; j<width; j++)
                    G2[i][j]=(r2+(r1-r2)*rand.nextDouble())+V*(best[j]-G1[i][j]);
            tempFitness=fitness(fNam, divheight, G2);
            if((double) tempFitness.get(0)<fitnessBest){
                fitnessBest=(double)tempFitness.get(0);
                best=G2[(int)tempFitness.get(1)];
            }
            
            for(int i=0; i<divheight;i++)
                for(int j=0; j<width; j++)
                    G3[i][j]=R*best[j]+(v2+(v1-v2)*rand.nextDouble());
            tempFitness=fitness(fNam, divheight, G3);
            if((double) tempFitness.get(0)<fitnessBest){
                fitnessBest=(double)tempFitness.get(0);
                best=G3[(int)tempFitness.get(1)];
            }
            
            for(int i=0; i<divheight;i++)
                for(int j=0; j<width; j++)
                    G4[i][j]=minBound+(maxBound-minBound)*rand.nextDouble();
            tempFitness=fitness(fNam, divheight, G4);
            if((double) tempFitness.get(0)<fitnessBest){
                fitnessBest=(double)tempFitness.get(0);
                best=G4[(int)tempFitness.get(1)];
            }
            minList.add(fitnessBest);
        }
        for(int i=0;i<width;i++)bestSolutionList.add(best[i]);
        //for(int i=0; i<divheight;i++){for(int j=0; j<width; j++)System.out.print(G1[i][j]+"\t");System.out.println();}System.out.println();
        //for(int i=0; i<divheight;i++){for(int j=0; j<width; j++)System.out.print(G2[i][j]+"\t");System.out.println();}System.out.println();
        //for(int i=0; i<divheight;i++){for(int j=0; j<width; j++)System.out.print(G3[i][j]+"\t");System.out.println();}System.out.println();
        //for(int i=0; i<divheight;i++){for(int j=0; j<width; j++)System.out.print(G4[i][j]+"\t");System.out.println();}System.out.println();
        //System.out.println(bestSolutionList);
        //System.out.println(minList);
    }
    
    public ArrayList getBestSolution(){
        return bestSolutionList;
    }
    
    public ArrayList getMinList(){
        return minList;
    }
    
    public double averagrPoint(double[] x){
        double sum=0;
        for(int i=0; i<x.length; i++) sum+=x[i];
        sum=sum/x.length;
        return sum;
    }
    
    public ArrayList fitness(String fnam, int popheight, double[][] x){
        double somme=0.0,somme2=0.0, min, raised;
        double[] a=new double[popheight];
        int minindex=0;
        ArrayList l=new ArrayList();
        switch (fnam) {
            case "dejong":
                for(int i=0;i<popheight;i++){
                    for(int j=0;j<width;j++){
                        //raised=Math.pow(;
                        somme+=Math.pow(x[i][j], 2);
                    }
                    a[i]=somme;
                    somme=0;
                }
            break;
            case "aphe" :
                for(int i=0; i<popheight; i++){
                    for(int j=0;j<width;j++){
                        somme += Math.pow(x[i][j], 2)*(j+1);
                    }
                    a[i]=somme;
                    somme=0;
                }
                break;
            case "rastrigin" : 
                for(int i=0; i<popheight; i++){
                    for(int j=0;j<width;j++){
                        somme += (Math.pow(x[i][j], 2))-(10*Math.cos(2*Math.PI*x[i][j]));
                    }
                    somme += 10*width;
                    a[i]=somme;
                    somme=0;
                }
                
                break;
            case "schwefel" : 
                for(int i=0; i<popheight; i++){
                    for(int j=0;j<width;j++){
                        somme += -x[i][j]*Math.sin(Math.sqrt(Math.abs(x[i][j])));
                    }
                    a[i]=somme;
                    somme=0;
                }
                break;
            case "maphe" :
                for(int i=0; i<popheight; i++){
                    for(int j=0;j<width;j++){
                        somme += Math.pow(x[i][j], 2)*(j+1)*5;
                    }
                    a[i]=somme;
                    somme=0;
                }
                break;    
            case "sodp" :
                for(int i=0; i<popheight; i++){
                    for(int j=0;j<width;j++){
                        somme += Math.pow(Math.abs(x[i][j]), j+2);
                    }
                    a[i]=somme;
                    somme=0;
                }
                break;   
            case "michal" :
                for(int i=0; i<popheight; i++){
                    for(int j=0;j<width;j++){
                        somme += Math.sin(x[i][j])*Math.pow(Math.sin((((i+1)*Math.pow(x[i][j], 2))/Math.PI)),20);
                    }
                    somme=-somme;
                    a[i]=somme;
                    somme=0;
                }
                break;
            case "ackley" :
                for(int i=0; i<popheight; i++){
                    for(int j=0;j<width;j++){
                        somme  += Math.pow(x[i][j], 2);
                        somme2 += Math.cos(2*Math.PI*x[i][j]);
                    }
                    somme=-20*Math.exp(-0.2*Math.sqrt(somme/width))-Math.exp(somme2/width)+20+Math.exp(1);
                    a[i]=somme;
                    somme=0;
                }
                break;
            case "griewangk" :
                somme2=1;
                for(int i=0; i<popheight; i++){
                    for(int j=0;j<width;j++){
                        somme  += Math.pow(x[i][j], 2)/4000;
                        somme2 *= Math.cos(x[i][j]/Math.sqrt(j+1));
                    }
                    somme=somme-somme2+1;
                    a[i]=somme;
                    somme=0;
                }
                break;
            case "rbv" : 
                for(int i=0; i<popheight; i++){
                    for(int j=0;j<width-1;j++){
                        somme +=100*Math.pow(x[i][j+1]-Math.pow(x[i][j],2),2)+Math.pow(1-x[i][j], 2);
                    }
                    a[i]=somme;
                    somme=0;
                }
                break;
            case "rhe" : 
                for(int i=0; i<popheight; i++){
                    for(int j=0;j<width-1;j++){
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
        for(int i=0;i<popheight;i++)
            if(min>=a[i]){
                min=a[i];
                minindex=i;
            }
        l.add(min);
        l.add(minindex);
        return l;
    }
}
