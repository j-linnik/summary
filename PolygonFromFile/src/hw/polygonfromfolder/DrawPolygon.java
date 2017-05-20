package hw.polygonfromfolder;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.JComponent;

public class DrawPolygon extends JComponent {

    private static final String COORDINATES_LIST = "xy_statement.properties";

    private int index = 0;
    
    private int[] copyX = new int[10000];  //Не думаю, что у нас может быть больше десяти тысяч точек :)
    private int[] copyY = new int[10000];
    private int[] x;
    private int[] y;
    
    private int[] gx;
    private int[] gy;
    
    public void setCoordinates() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(COORDINATES_LIST));
            String in = null;

            while ((in = br.readLine()) != null) {
                if (!in.trim().isEmpty()) {
                    String[] array = in.split(",");
                    
                    if (array.length == 2) {                           //Если указаны только координаты
                        copyX[index] = Integer.parseInt(array[0]);
                        copyY[index] = Integer.parseInt(array[1]);
                        index++;
                    }
                }
            }
            br.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        x = new int[index];
        y = new int[index];
        
        //Копируем в координаты только то, что мы записали из файла
        System.arraycopy(copyX, 0, x, 0, index);  
        System.arraycopy(copyY, 0, y, 0, index);
    }

    public void setResize(){
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (int i = 0; i < x.length; i++) {
            if (x[i] > maxX) {
                maxX = x[i];
            }
            if (y[i] > maxY) {
                maxY = y[i];
            }
            if (x[i] < minX) {
                minX = x[i];
            }
            if (y[i] < minY) {
                minY = y[i];
            }
        }

        double coX = ((double) getWidth()) / ((double) (maxX - minX));
        double coY = ((double) getHeight()) / ((double) (maxY - minY));

        gx = new int[x.length];
        gy = new int[y.length];

        for (int i = 0; i < x.length; i++) {
            gx[i] = (int) ((x[i] - minX) * coX);
            gy[i] = (int) ((y[i] - minY) * coY);
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setResize();
        g.drawPolygon(gx, gy, x.length);
    }
}
