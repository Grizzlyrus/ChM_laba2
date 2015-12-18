import java.io.IOException;

/**
 * Created by Кирилл on 25.11.2015.
 */
public class Laba2 {
    public static void main(String argsp[]){
        int n = 100;
        int c = 3;
        double[] b= new double[n];
        double[] x = new double[n];
        Matrix A = Matrix.genMatrix(n,c);
        for(int i=0;i<n;i++){
            x[i]=i+1;
        }
        b = A.MultMatrixVector(x);

        CholesskyDecomposition chd = new CholesskyDecomposition(A);
        double x1[] = new double[n];
        x1 = chd.solve(b);

        try {
            Portrait portA = new Portrait(A);
        }catch (IOException e){}

        System.out.println("Разложение Холецкого");
        System.out.println("Коэффициент заполнения "+A.FillValue());
        System.out.println("Относительная могрешность "+chd.Error(x));
        System.out.println("Число обусловленности "+ A.cond());


        GaussZeidel gz = new GaussZeidel(A,b,10e-5);
        double[] x2 = gz.solve();
        System.out.println("Якоби");
        System.out.println("Относительная погрешность "+gz.Error(x));


    }
}
