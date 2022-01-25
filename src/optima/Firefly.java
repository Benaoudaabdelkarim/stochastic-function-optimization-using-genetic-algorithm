package optima;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Lord-KArim
 */
public class Firefly {
    private double fbest, h0,
                   gamma = 0.1,
                    beta = 0.5,
                   alpha = 0.4, //à ajuster
                    amax = 2, 
                    amin = 0.02;
    
    private double[][] pop, X;
    
    private double[] best,
                     intensity,
                     r,be;
    
    private double minBound,
                   maxBound,
                   min,min2,
                   sumR=0.0,
                   rando;
    
    private int nbr_luciol, 
                nbr_noeud,
                nbrItiration,
                index,
                parameter;
    
    private String funcNam;
    private Random rand=new Random();
    private ArrayList R=new ArrayList();
    private ArrayList minList=new ArrayList();
    private ArrayList minListInverted=new ArrayList();
    private ArrayList bestSolutionList=new ArrayList();

    public Firefly(String fnam, int nbrItiration, int parameter, int width, int height, double minBound,double maxBound, double pp[][]){
        this.nbr_noeud=width;
        this.nbr_luciol=height;
        this.nbrItiration=nbrItiration;
        best=new double[nbr_noeud];
        this.parameter=parameter-1;
        
        pop=new double[nbr_luciol][nbr_noeud];
        X=new double[nbr_luciol][nbr_noeud];
        r=new double[nbr_luciol];
        intensity=new double[nbr_luciol];
        
        this.minBound=minBound;
        this.maxBound=maxBound;
        funcNam=fnam;
        
        for(int i=0; i<nbr_luciol;i++)System.arraycopy(pp[i], 0, pop[i], 0, nbr_noeud);
        //for(int i=0; i<nbr_noeud;i++)for(int j=0; j<nbr_luciol;j++)pop[i][j]=minBound+(maxBound - (minBound))*rand.nextDouble();
                
            
    }
    public void FireFlyAlgo(){
        min=Double.POSITIVE_INFINITY;
        for(int nbritir=0; nbritir<nbrItiration; nbritir++){
           //for(int i=0; i<nbr_noeud;i++){for(int j=0; j<nbr_luciol;j++) System.out.print(pop[i][j]+"\t");System.out.println();}System.out.println();
            for (int i=0; i<nbr_luciol; i++) intensity[i] = objective(pop[i], funcNam);
            //for (int i=0; i<nbr_noeud; i++) System.out.print(intensity[i]+"\t");System.out.println();
            
            for(int i=0; i<nbr_luciol; i++){
                if(min>intensity[i]){
                    min=intensity[i];
                    index=i;
                }
            }
            
            //for(int i=0; i<nbr_noeud; i++)System.out.print(intensity[i]);System.out.println();
            
            minList.add(min);
            best=pop[index];
            //System.out.println(minList);
            boolean meill=false;
            for(int i=0; i<nbr_luciol;i++){
               
                for(int j=0; j<nbr_noeud;j++){
                    
                    if(i == 0) {
                        sumR += Math.pow(pop[0][j], 2)-Math.pow(pop[parameter][j], 2);
                       
                    }
                    else sumR += Math.pow(pop[i][j], 2)-Math.pow(pop[i-1][j], 2);
                }
                r[i]=Math.sqrt(Math.abs(sumR));
                sumR=0;
            }
            //for(int i=0; i<nbr_noeud;i++)System.out.print(r[i]+"\t");System.out.println();System.out.println();
           
            
            for(int i=0; i<nbr_luciol;i++){
                for(int j=0; j<nbr_noeud;j++){
                    rando=0+(1 - (0))*rand.nextDouble();
                    if(i==parameter) X[i][j] =  pop[parameter][j] + beta * Math.exp(-gamma * Math.pow(r[i], 2)) * (pop[0  ][j] - pop[parameter][j]) + alpha * (rando -  (1 / 2)) ;
                    
                    else             X[i][j] =  pop[i        ][j] + beta * Math.exp(-gamma * Math.pow(r[i], 2)) * (pop[i+1][j] - pop[i        ][j]) + alpha * (rando -  (1 / 2)) ;
                    
                    
                    
                    
                    if(X[i][j]>maxBound)X[i][j]=maxBound-(minBound +(maxBound - (minBound))*rand.nextDouble());
                    if(X[i][j]<minBound)X[i][j]=minBound+(minBound +(maxBound - (minBound))*rand.nextDouble());
                }
            }
            //for(int i=0; i<nbr_noeud;i++){for(int j=0; j<nbr_luciol;j++) System.out.print(X[i][j]+"\t");System.out.println();}System.out.println();
            //for(int i=0; i<nbr_noeud;i++){for(int j=0; j<nbr_luciol;j++) System.out.print(pp[i][j]+"\t");System.out.println();}System.out.println();
            
            //for(int i=0; i<nbr_noeud;i++)System.arraycopy(X[i], 0, pop[i], 0, nbr_luciol);
            pop=X;
        }
        for(int i=0; i<nbr_noeud;i++) bestSolutionList.add(best[i]);
        //System.out.println(bestSolutionList);
        //System.out.println(minList);
    }
    
    public ArrayList getMinList(){
        return minList;
    }
    
    public ArrayList getBestSolution(){
        return bestSolutionList;
    }
    static double min(double[] x){
        double m=x[0];
        for(int i=0; i<x.length; i++)
            if(m>=x[i]) m=x[i];
        return m;
    }
    private double objective(double[] x, String fnam){
	double sum = 0.0,sum2=0.0;
        switch(fnam){
            case "dejong" :
                for(int i=0; i<x.length; i++){
                    sum += Math.pow(x[i], 2);            
                }
                break;
            case "aphe" :
                for(int i=0; i<x.length; i++){
                    sum += Math.pow(x[i], 2)*(i+1);
                }
                break;
            case "schwefel" : 
                for(int i=0; i<x.length; i++){
                    sum += (Math.pow(x[i], 2))-10*Math.cos(2*Math.PI*x[i]);
                }
                sum += 10*x.length;
                break;
            case "rastrigin" : 
                for(int i=0; i<x.length; i++){
                    sum += -x[i]*Math.sin(Math.sqrt(Math.abs(x[i])));
                }
                break;
            case "maphe" :
                for(int i=0; i<x.length; i++){
                    sum += Math.pow(x[i], 2)*(i+1)*5;
                }
                break;    
            case "sodp" :
                for(int i=0; i<x.length; i++){
                    sum += Math.pow(Math.abs(x[i]), i+2);
                }
                break;   
            case "michal" :
                for(int i=0; i<x.length; i++){
                    sum += Math.sin(x[i])*Math.pow(Math.sin((((i+1)*Math.pow(x[i], 2))/Math.PI)),20);
                }
                sum=-sum;
                break;
            case "ackley" :
                for(int i=0; i<x.length; i++){
                        sum  += Math.pow(x[i], 2);
                        sum2 += Math.cos(2*Math.PI*x[i]);
                    }
                    sum=-20*Math.exp(-0.2*Math.sqrt(sum/x.length))-Math.exp(sum2/x.length)+20+Math.exp(1);
                break; 
            case "griewangk" :
                sum2=1;
                for(int i=0; i<x.length; i++){
                    sum  += Math.pow(x[i], 2)/4000;
                    sum2 *= Math.cos(x[i]/Math.sqrt(i+1));
                }
                sum=sum-sum2+1;
                break;
            case "rbv" : 
                for(int i=0; i<x.length-1; i++)
                        sum +=100*Math.pow(x[i+1]-Math.pow(x[i],2),2)+Math.pow(1-x[i], 2);
                break;
            case "rhe" : 
                for(int i=0; i<x.length; i++){
                    for(int j=0;j<i;j++)
                        sum2 +=x[j];
                    sum += Math.pow(sum2, 2);
                }
                break;
        }
	return sum;
    }
}
