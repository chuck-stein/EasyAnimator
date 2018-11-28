
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Acts in case of keyboard action.
 */
public class KeyBoardListener implements ActionListener {


  /**
   * Acts depending on keyboard command.
   *
   * @param e is the keyboard command.
   */
  public void actionPerformed(ActionEvent e) {
    if (e.equals(VK_P)) {
      composite.pause();
    }
  }
}
