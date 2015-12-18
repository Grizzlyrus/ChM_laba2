import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Кирилл on 25.11.2015.
 */
public class CholesskyDecomposition {

    private int n;
    private Matrix L;
    private Matrix A;
    private double[] x;

    public CholesskyDecomposition(Matrix arg){
        this.n=arg.size();
        x = new double[n];
        this.A = arg;
        L = new Matrix(n);

        for(int j = 0;j< n;j++){
            double lii = 0.0;
            for (int k=0;k<=j-1;k++){
                lii+=L.get(j,k)*L.get(j,k);
            }
            lii = Math.sqrt(A.get(j,j)-lii);
            L.set(j,j,lii);

            for(int i=j+1;i<n;i++){
                    double lij=0.0;
                    for (int k = 0; k <= j - 1; k++) {
                        lij += L.get(i, k) * L.get(j, k);
                    }
                    lij = A.get(i, j) - lij;
                    lij = lij / L.get(j, j);
                    L.set(i, j, lij);
            }
        }
    }

    public double[] solve(double[] b){
        if(b.length!=n) throw new IllegalArgumentException("Wrong size of b vector");
        double X[] = Arrays.copyOf(b,n);


        for (int k = 0; k < n; k++) {
                for (int i = 0; i < k ; i++) {
                    X[k]-=X[i]*L.get(k,i);
                }
                X[k]/=L.get(k,k);
        }

        // Solve L'*X = Y;
        for (int k = n-1; k >= 0; k--) {
                for (int i = k+1; i < n ; i++) {
                    X[k]-= X[i]*L.get(i,k);
                }
            X[k]/=L.get(k,k);
        }

        this.x = X;
        return X;
    }

    public Matrix getL(){
        return L;
    }

    public void printL(){
        L.printMatrix();
    }

    public void printA(){
        A.printMatrix();
    }

    public double Error(double[] xEt){
        if(xEt.length!=n) throw new IllegalArgumentException("Vector length must be same");
        double f=0.0;
        double d=0.0;
        double[] xErr = new double[n];
        for(int i =0;i<n;i++){
            xErr[i]=x[i]-xEt[i];
        }

        for (int i = 0; i < n; i++) {
            f += Math.abs(x[i]);
            d += Math.abs(xErr[i]);
        }
        return d/f;
    }



}
