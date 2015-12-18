import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Кирилл on 01.12.2015.
 */
public class Portrait {

    private static BufferedImage img;
    private int n;

    public  Portrait(Matrix A) throws IOException {

        this.n = A.getN();

        img = new BufferedImage(n * 10, n * 10, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, n * 10, n * 10);
        g.setColor(Color.BLACK);
//        MatrixNode subNode = matrixA.element();
//        int i = 0, j = 0;
//        for (Iterator<MatrixNode> iter = matrixA.iterator(); iter.hasNext(); subNode = iter.next()) {
//            g.fillOval(10 * subNode.i + 5, 10 * subNode.j + 5, 5, 5);
//        }
        for(int i = 0;i<n;i++) {
            for(int j = 0;j<n;j++){
                if(A.get(i,j)!=0){
                    g.fillOval(10 * i + 5, 10 * j + 5, 5, 5);
                }
            }
        }

        ImageIO.write(img, "jpg", new File("portrait1.jpg"));
        JFrame window = new JFrame("Matrix's portrait");
        window.getContentPane().add(new JScrollPane(new JLabel(new ImageIcon(img))));
        window.setSize(1024, 768);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }
}
