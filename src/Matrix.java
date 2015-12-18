import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.OpenMapRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Кирилл on 25.11.2015.
 */
public class Matrix {

    private int n;
    private HashMap<Integer,HashMap<Integer,Double>> matrixA = new HashMap<>();

    public Matrix(double A[][]){
        if(!isSquare(A)) throw new IllegalArgumentException("Matrix A must be square");
        this.n=A.length;
        HashMap<Integer,Double> line = new HashMap<>();
        for(int i=0;i<n;i++){
            line.clear();
            for(int j=0;j<n;j++){
                if(A[i][j]!=0){
                    line.put(j,A[i][j]);
                }
            }
            matrixA.put(i,(HashMap)line.clone());
        }
    }


    public Matrix(int n){
        this.n=n;
        for(int i=0;i<n;i++){
            HashMap<Integer,Double> line = new HashMap<>();
            matrixA.put(i,line);
        }
    }

    public Matrix(int n, HashMap<Integer,HashMap<Integer,Double>> matrix){
        this.n=n;
        this.matrixA = matrix;
    }

    public double get(int i,int j){
        if(i>=n || j>=n) throw new IndexOutOfBoundsException("Wrong indexes");

        if(matrixA.get(i).get(j)==null){
            return 0.0;
        }
        return matrixA.get(i).get(j);
    }

    public void set(int i, int j, double value){
        if(value!=0.0) {
            HashMap line = matrixA.get(i);
            line.put(j, value);
            matrixA.put(i, line);
        }
    }

    public void remove(int i , int j){
        HashMap line = matrixA.get(i);
        line.remove(j);
        matrixA.put(i,line);
    }

    private boolean isSquare(double A[][]){
        for (int i = 0;i< A.length;i++){
            if(A.length!=A[i].length) return false;
        }
        return true;
    }

    public int size(){
        return matrixA.size();
    }

    public static Matrix genMatrix(int n,int c){
        if(c>n-1 || c<2) throw new IllegalArgumentException("c must be 2<=c<=n-1");
        Matrix A = new Matrix(n);
        for(int i=0;i<n;i++){
            A.set(i,i,4.0);
        }

        int i;

        for(i=0;i<n;i++){
            if(i+1<n){
                A.set(i,i+1,-1.0);
            }
            if(i+c<n){
                A.set(i,i+c,-1.0);
            }
        }
        for(i=n-1;i>=0;i--){
            if(i-1>=0){
                A.set(i,i-1,-1.0);
            }
            if(i-c>=0){
                A.set(i,i-c,-1.0);
            }
        }
        return A;
    }


    public double[] MultMatrixVector(double[] x){
        if(x.length!=n) throw new IllegalArgumentException("x size must be n");
        double[] result = new double[n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                result[i]=result[i]+this.get(i,j)*x[j];
            }
        }
        return result;
    }


    public void printMatrix() {
        for(int i = 0 ; i<n ;i++){
            for(int j = 0;j<n;j++ ) {
                System.out.print(" " + this.get(i,j) + " ");
            }
            System.out.println();
        }
    }

    public int getN(){
        return n;
    }

    public double FillValue(){
        int k=0;
        for(int i = 0;i<n;i++){
            for(int j=0;j<n;j++){
                if(this.get(i, j)!=0){
                    k++;
                }
            }
        }
        return (double)k;
    }

    public Matrix copyMatrix(){
        return new Matrix(this.n, (HashMap<Integer,HashMap<Integer,Double>>)this.matrixA.clone());
    }

    public double cond(){
        RealMatrix a= new OpenMapRealMatrix(n,n);
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                a.setEntry(i,j,this.get(i,j));
            }
        }
        RealMatrix ainv = new LUDecomposition(a).getSolver().getInverse();
        return a.getNorm()*ainv.getNorm();
    }

}
