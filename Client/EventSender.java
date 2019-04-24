import javafx.event.ActionEvent;

public interface EventSender {
    public void mouseMoved(MouseEvent event);
    public void mousePressed(MouseEvent event);
    public void mouseReleased(MouseEvent event);
    public void keyPressed(KeyEvent event);
    public void keyReleased(KeyEvent event);
}