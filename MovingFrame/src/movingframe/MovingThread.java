package movingframe;

public class MovingThread extends Thread{
    private MovingFrame frame;
    
    public MovingThread(MovingFrame frame) {
        this.frame = frame;
    }
    
    @Override
    public void run() {
        while(true) {
            try{
                Thread.sleep(500);
                frame.moveFrame();
            } catch (Exception ex){
                
            }
        }
    }
}