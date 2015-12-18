/**
 * Created by Кирилл on 27.10.2015.
 */
public class GaussZeidel {
    private int n;
    private double x[];
    private Matrix A;
    private double[] b;
    private double epsilon;
    private double q;

    public GaussZeidel(Matrix A, double[] b,double epsilon){
        if(A.getN()!=b.length) throw new IllegalArgumentException("Wrong size of matrix");
        this.A=A;
        this.b=b;
        this.n = A.getN();
        this.epsilon = epsilon;
        this.q = qNorm();
    }

    public double[] solve(){
        if(!CanSolve()) throw new RuntimeException("System cannot be resolve");

        double []xold  = b;
        double [] xnew = new double[n];


            do {
                for (int i = 0; i < n; i++) {
                    if(xnew[i]!=0) {
                        xold[i] = xnew[i];
                        xnew[i] = 0;
                    }
                }

                for (int i = 0; i < n; i++) {
                    for (int k = 0; k < i ; k++) {
//                        xnew[i] -= A.get(i,k)/ A.get(i,i) * xnew[k];
                        xnew[i] -=  A.get(i,k)/ A.get(i,i) * xold[k];
                    }

                    for (int k = i+1; k < n; k++) {
                        xnew[i] -=  A.get(i,k)/ A.get(i,i) * xold[k];
                    }

                    xnew[i] += b[i] / A.get(i,i);

                }
            }while (!EndOf(xold,xnew));


        this.x = xnew;
        return xnew;
    }





    public boolean EndOf(double[] Xold, double[] Xnew){
        double f=0.0;
        for(int i=0;i<n;i++){
            f+=Math.abs(Xnew[i]-Xold[i]);
        }
        return Math.abs(f/(1-q)*q)<epsilon;
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




    public boolean CanSolve(){
        if(q<1.0)return true;
        return false;
    }

    public double qNorm(){
        Matrix DminusMultA = new Matrix(n);

        for (int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                DminusMultA.set(i,j,A.get(i,j)/A.get(i,i));
            }
        }

        for(int i=0;i<n;i++){
            if(1.0-DminusMultA.get(i,i)==0.0){
                DminusMultA.remove(i,i);
            }else {
                DminusMultA.set(i, i, 1.0 - DminusMultA.get(i, i));
            }
        }

        double f = 0;
        for (int p = 0; p < n; p++) {
            double s = 0;
            for (int t = 0; t < n; t++) {
                s += Math.abs(DminusMultA.get(t, p));
            }
            f = Math.max(f,s);
        }
        return f;
    }

}