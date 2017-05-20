package movingframe;

import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class FrameForMovement extends JFrame{
    
    public FrameForMovement() {
        JLabel lbl = new JLabel();
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        lbl.setIcon(new ImageIcon("src/snail.gif"));
        add(lbl, BorderLayout.CENTER);
        setBounds(200, 200, 100, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    
}
