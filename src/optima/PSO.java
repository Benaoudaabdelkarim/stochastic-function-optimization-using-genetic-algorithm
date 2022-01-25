package optima;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Lord-Karim
 */
public class PSO {    
    private double 
               w         = 0.9, 
               c1        = 2.05,  c2        = 2.05, 
               r1        = 0.0,   r2        = 0.0,
               xMin, xMax,
               vMin      = 0,     vMax      = 1,
               wMin      = 0.4,   wMax      = 0.9,
               phi       = c1+c2,
               chi       = 2.0/Math.abs(2.0-phi-Math.sqrt(Math.pow(phi, 2)-4*phi)),
               nInfinite = Double.NEGATIVE_INFINITY,
               gBestValue = nInfinite;
    
    private int   Np,  // # of particles
                  Nd,   // # of dimensions
                  Nt,  // # of itirations
                  minindex;    
            
    private double[] pBestValue     ,
                 gBestPosition      ,
                 bestFitnessHistory ,
                 M                  ;
    
    private double[][] pBestPosition ,
                   pop                 ,
                   V                 ;
    
    private Random rand = new Random();
    private ArrayList gBestValueList = new ArrayList();
    private ArrayList pBestValueList = new ArrayList();
    private ArrayList bestSolutionList  = new ArrayList();
    private String fnam;
    
    public PSO(String fnam,int nbrItiration, int demantion, int particales, double xMin, double xMax, double[][] pp){
        this.Nt=nbrItiration;
        this.fnam=fnam;
        this.xMin=xMin; 
        this.xMax=xMax;
        this.Np=particales;
        this.Nd=demantion;
        
        
        this.pBestValue         = new double[Np];
        this.gBestPosition      = new double[Nd];
        this.bestFitnessHistory = new double[Nt];
        this.M                  = new double[Np];
        
        this.pBestPosition = new double[Np][Nd];
        this.pop             = new double[Np][Nd];
        this.V             = new double[Np][Nd];
        
        for(int p=0; p<Np; p++){
            pBestValue[p] = nInfinite; 
        }

        for(int i=0; i<Np;i++)System.arraycopy(pp[i], 0, pop[i], 0, Nd);
        //R=pop;
        /*for(int i=0; i<Np;i++){
            for(int j=0; j<Nd;j++)
                System.out.print(pop[i][j]+"\t");
            System.out.println();}*/
        for(int p=0; p<Np; p++){
            for(int i=0; i<Nd; i++){
                //pop[p][i] = xMin + (xMax-xMin)*rand.nextDouble();
                V[p][i] = vMin + (vMax-vMin)*rand.nextDouble();

                /*if(rand.nextDouble() < 0.5){
                    V[p][i] = -V[p][i];
                    pop[p][i] = -pop[p][i];
                }*/
            }
        }
    }
    
    public void psoAlgo(){
        /*for(int p=0; p<Np; p++){
            M[p] = fitness(fnam,pop[p]);
            //M[p] = -M[p];
        }*/
        ////////////////////////////////////////////////////////////////////////
        for(int j=0; j<Nt; j++){
            for(int p=0; p<Np;p++){     
                for(int i=0; i<Nd; i++){    
                    pop[p][i] = pop[p][i] + V[p][i];
                    
                    //corriger
                    if(pop[p][i] > xMax)          { pop[p][i] = xMax;}
                    else if(pop[p][i] < xMin)     { pop[p][i] = xMin;}
                }
            }

            for(int p=0; p<Np; p++){
                M[p] = fitness(fnam,pop[p]);
                M[p] = -M[p];
            
                if(M[p] > pBestValue[p]){
                     pBestValue[p] = M[p];
                     for(int i=0; i<Nd; i++){
                        pBestPosition[p][i] = pop[p][i];
                     }
                 }
            
                if(M[p] > gBestValue){
                    minindex=p;//min index 
                    gBestValue = M[p];          
                    for(int i=0; i<Nd; i++){
                       gBestPosition[i] =  pop[p][i];
                    }
                }
            
            }
            bestFitnessHistory[j] = gBestValue;
        
            w = wMax - ((wMax-wMin)/Nt) * j;
            for(int p=0; p<Np; p++){
                for(int i=0; i<Nd; i++){
                    
                    r1 = rand.nextDouble();
                    r2 = rand.nextDouble();
                    V[p][i] = chi * w * (V[p][i] + r1 * c1 * (pBestPosition[p][i] - pop[p][i]) + r2*c2 *(gBestPosition[i] - pop[p][i]));
                
                    // classic Velocity update formulate                
                    if      (V[p][i] > vMax) { V[p][i] = vMax; }        
                    else if (V[p][i] < vMin) { V[p][i] = vMin; }
                }
            }
            //output global best value at current timestep
            //System.out.println("iteration: " + j + " BestValue " + Math.round(gBestValue*100000.0)/100000.0);
            
            gBestValueList.add(-gBestValue);
            
        }
        for(int k=0; k<Np; k++){
                for(int l=0; l<Nd; l++)
                    if(k==minindex) {
                        bestSolutionList.add(pop[k][l]);
                    }
            }
        
        for(int p=0; p<pBestValue.length; p++)
            pBestValueList.add(pBestValue[p]);//best solution   
    }
    
    public ArrayList getgBesValueList(){
        return gBestValueList;//min in each itiration 
    }
    
    public ArrayList getBestSolution(){
        return bestSolutionList;//best solution row
    }
    
    public double fitness(String fnam,double[] x){
        double sum = 0,sum2=0;
        switch(fnam){
            case "dejong" :
                for(int i=0; i<x.length; i++)
                    sum += Math.pow(x[i], 2);            
                break;
            case "aphe" :
                for(int i=0; i<x.length; i++)
                    sum += Math.pow(x[i], 2)*(i+1);
                break;
            case "schwefel" : 
                for(int i=0; i<x.length; i++)
                    sum += (Math.pow(x[i], 2))-10*Math.cos(2*Math.PI*x[i]);
                sum += 10*x.length;
                break;
            case "rastrigin" : 
                for(int i=0; i<x.length; i++)
                    sum += -x[i]*Math.sin(Math.sqrt(Math.abs(x[i])));
                break;
            case "maphe" :
                for(int i=0; i<x.length; i++)
                    sum += Math.pow(x[i], 2)*(i+1)*5;
                break;    
            case "sodp" :
                for(int i=0; i<x.length; i++)
                    sum += Math.pow(Math.abs(x[i]), i+2);
                break;   
            case "michal" :
                for(int i=0; i<x.length; i++)
                    sum += Math.sin(x[i])*Math.pow(Math.sin((((i+1)*Math.pow(x[i], 2))/Math.PI)),20);
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
