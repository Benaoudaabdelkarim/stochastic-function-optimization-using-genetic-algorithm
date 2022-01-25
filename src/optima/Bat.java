package optima;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Lord-Karim
 */
public class Bat {
    private double[][] X; 		// Population/Solution (N x D)
	private double[][] V; 		// Velocities (N x D)
	private double[][] Q; 		// Frequency : 0 to Q_MAX (N x 1)
	private double[] F;			// Fitness (N)
	private double R; 			// Pulse Rate : 0 to 1
	private double A; 			// Louadness : A_MIN to A_MAX
	private double[][] lb;		// Lower bound (1 x D)
	private double[][] ub;		// Upper bound (1 x D)
	private double fmin; 		// Minimum fitness from F
 	private double[] B;			// Best solution array from X (D)	

	private final int N; 		// Number of bats
        private final int D;
	private final int MAX; 		// Number of iterations
	private final double Q_MIN = 0.0;
	private final double Q_MAX = 2.0;
	private final double A_MIN;
	private final double A_MAX;
	private final double R_MIN;
	private final double R_MAX; 
        private final String fnam;
	private final Random rand = new Random();
        private ArrayList minList=new ArrayList();
        private ArrayList BestSolutionList=new ArrayList();
    
    public Bat(String fnam,int MAX, int D, int N, double lowerBound, double upperBound, double[][] pop){
	this.N = N;
        this.D = D;
	this.MAX = MAX;
	this.R_MAX = 0.0;
	this.R_MIN = 0.0;
	this.A_MAX = 0.0;
	this.A_MIN = 0.0;
        this.fnam = fnam;

	this.X = new double[N][D];
	this.V = new double[N][D];
	this.Q = new double[N][1];
	this.F = new double[N];
	this.R = (0.0 + 0.0) / 2;
	this.A = (0.0 + 0.0) / 2;

	// Initialize bounds
	this.lb = new double[1][D];
	for ( int i = 0; i < D; i++ ){
		this.lb[0][i] = lowerBound;
	}
	this.ub = new double[1][D];
	for ( int i = 0; i < D; i++ ){
		this.ub[0][i] = upperBound;
	}

	// Initialize Q and V
	for ( int i = 0; i < N; i++ ){
		this.Q[i][0] = 0.0;
	}
	for ( int i = 0; i < N; i++ ){
	    for ( int j = 0; j < D; j++ ) {
		this.V[i][j] = 0.0;
	    }
	}
        
        //Set X to pop
        for(int i=0; i<N;i++)System.arraycopy(pop[i], 0, X[i], 0, D);
        
        /*for(int i=0; i<N;i++){
            for(int j=0; j<D;j++)
                System.out.print(X[i][j]+"\t");
            System.out.println();}*/

	// Initialize fitness
	for ( int i = 0; i < N; i++ ){
	    //for ( int j = 0; j < D; j++ ){this.X[i][j] =  lb[0][j] + (ub[0][j] - lb[0][j]) * rand.nextDouble();}
	   this.F[i] = objective(X[i],fnam);
	}

	// Find initial best solution
	int fmin_i = 0;
	for ( int i = 0; i < N; i++ ){
	    if ( F[i] < F[fmin_i] )
	        fmin_i = i;
	}

	// Store minimum fitness and it's index.
	// B holds the best solution array[1xD]
	this.fmin = F[fmin_i];
	this.B = X[fmin_i]; // (1xD)
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

    private double[] simpleBounds(double[] Xi){
        // Don't know if this should be implemented
	double[] Xi_temp = new double[D];
	System.arraycopy(Xi, 0, Xi_temp, 0, D);

	for ( int i = 0; i < D; i++ ){
	    if ( Xi_temp[i] < lb[0][i] )
	        Xi_temp[i] = lb[0][i];
	    else continue;
	}

	for ( int i = 0; i < D; i++ ){
	    if ( Xi_temp[i] > ub[0][i] )
	        Xi_temp[i] = lb[0][i];
	    else continue;
	}
	return Xi_temp;
    }

    public void startBat(){

	double[][] S = new double[N][D];
	int n_iter = 0;

	// Loop for all iterations/generations(MAX)
	for ( int t = 0; t < MAX; t++ ){
	    // Loop for all bats(N)
	    for ( int i = 0; i < N; i++ ){
				
		// Update frequency (Nx1)
		Q[i][0] = Q_MIN + (Q_MIN-Q_MAX) * rand.nextDouble();
		// Update velocity (NxD)
		for ( int j = 0; j < D; j++ ){
		    V[i][j] = V[i][j] + (X[i][j] - B[j]) * Q[i][0];
		}
		// Update S = X + V
		for ( int j = 0; j < D; j++ ){
		    S[i][j] = X[i][j] + V[i][j];
		}
		// Apply bounds/limits
		X[i] = simpleBounds(X[i]);
		// Pulse rate
		if ( rand.nextDouble() > R )
		    for ( int j = 0; j < D; j++ )
			X[i][j] = B[j] + 0.001 * rand.nextGaussian();
                


		// Evaluate new solutions
		double fnew = objective(X[i],fnam);

		// Update if the solution improves or not too loud
		if ( fnew <= F[i] && rand.nextDouble() < A ){
		    X[i] = S[i];
		    F[i] = fnew;
		}

		// Update the current best solution
		if ( fnew <= fmin ){
		    B = X[i];
		    fmin = fnew;
		}
	    } // end loop for N
	    n_iter = n_iter + N;
            minList.add(fmin);
	} // end loop for MAX
        
        for(int i=0;i<B.length;i++)BestSolutionList.add(B[i]);

	/*System.out.println("Number of evaluations : " + n_iter );
	System.out.println("Best = " + Arrays.toString(B) );
        System.out.println(minList );
	System.out.println("fmin = " + fmin );
        System.out.println("S = " + V );*/
    }    
    
    public ArrayList getBestSolution(){
        return BestSolutionList;
    }
    public ArrayList getminList(){
        return minList;
    }
        
}
