package drawstuff;
import java.awt.Graphics;
import javax.swing.JComponent;

public class PaintorOfFigure extends JComponent{
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //квадраты - коробки для фигур
        g.drawRect(10, 10, getWidth() / 4 - 10, getHeight() - 20);
        g.drawRect(getWidth() / 4 + 10, 10, getWidth()/4 - 10, getHeight() - 20); 
        g.drawRect(getWidth()/2 + 10, 10, getWidth() /4 - 10, getHeight() - 20);
        g.drawRect(getWidth()/2 + getWidth()/4 + 10, 10, getWidth() /4 - 20,getHeight() - 20);
    
        //фигуры
        
        //треугольник
        g.drawLine(getWidth() / 8, 20, 20, getHeight() - 20);
        g.drawLine( 20, getHeight() - 20, getWidth() / 4 - 20, getHeight() - 20);
        g.drawLine(getWidth() / 4 - 20, getHeight() - 20,  getWidth() / 8, 20);
        
        //квадрат
        g.drawRect( getWidth() / 4 + 30, 30, getWidth() / 4 - 50, getHeight() - 60);
        
        //ромб
        g.drawLine(getWidth()/2 + getWidth()/ 8, 20, getWidth()/2 + 40, getHeight() / 2);
        g.drawLine(getWidth()/2 + 40, getHeight() / 2, getWidth()/ 2 + getWidth() / 8,
                getHeight() - 20);
        g.drawLine(getWidth()/ 2 + getWidth() / 8, getHeight() - 20, getWidth() / 2 +
                getWidth() / 4 - 40, getHeight() / 2);
        g.drawLine( getWidth() / 2 + getWidth() / 4 - 40, getHeight() / 2,
                getWidth()/2 + getWidth()/ 8, 20);
        
        //квадрат с линиями---
        g.drawRect(getWidth() - getWidth()/ 4 + 30, 30, getWidth()/4 - 60, getHeight() - 60);
        
        //верхний левый угол квадрата
        int upLeftX = getWidth() - getWidth()/ 4 + 30;
        int upLeftY = 30;
        
        //верхний правый угол квадрата
        int upRightX = getWidth() - getWidth()/ 4 + 30 + getWidth()/4 - 60;
        
        //нижний левый угол квадрата
        int downLeftY = 30 +  getHeight() - 60;
        
        int numOfLines = 5;
        int x1, y1;
        int x2, y2;
        
        //вертикальные
        x1 = upLeftX - 7;
        for(int i = 0; i < 10; i++){
            x1 += (upRightX - upLeftX) / numOfLines;
            y1 = upLeftY;
            x2 = x1;
            y2 = downLeftY;
            
            g.drawLine(x1, y1, x2, y2);
        }
        
        //горизонтальные
        y1 = upLeftY - 7;
        for(int i = 0; i < 10; i++){
            x1 = upLeftX;
            y1 += (downLeftY - upLeftY) / numOfLines;
            x2 = upRightX;
            y2 = y1;
            
            g.drawLine(x1, y1, x2, y2);
        }
        
        
// НИЖЕ ШЕДЕВР НА КОТОРЫЙ МОЖНО ЗАЛИПАТЬ ВЕЧНО! На самом деле - ошибка.
        
       /* for(int i = upLeftX; i < upRightX; i++){
            if(i % 40 == 0) {
                x1 = i;
                y1 = upLeftY;
                x2 = i;
                y2 = downLeftY;
                g.drawLine(x1, y1, x2, y2);
            }
        }
        
        for(int i = upLeftY; i < downLeftY; i++){
            if(i % 40 == 0) {
                x1 = upLeftX;
                y1 = i;
                x2 = upRightX;
                y2 = i;
                g.drawLine(x1, y1, x2, y2);
            }
        } */
    }
}
