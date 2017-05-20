package drawstuff;

import javax.swing.JFrame;


public class FraimPaintor extends JFrame{
        
    public FraimPaintor() {
        PaintorOfFigure oc = new PaintorOfFigure();
        add(oc);
        
        setBounds(200, 200, 1000, 300);
        
    }
    
}
