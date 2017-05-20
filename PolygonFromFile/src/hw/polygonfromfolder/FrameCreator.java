package hw.polygonfromfolder;

import javax.swing.JFrame;

public class FrameCreator extends JFrame{
    
    public FrameCreator(){
        DrawPolygon dp = new DrawPolygon();
        add(dp);
        setBounds(200, 200, 300, 300);
        dp.setCoordinates();
    }
    
}
