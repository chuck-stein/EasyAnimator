package cs3500.animator.view;

import cs3500.animator.controller.EditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MockChangeListener implements ChangeListener {
  EditorListener listener;

  @Override
  public void stateChanged(ChangeEvent e) {
    listener.setTime(0);
  }

  public void setListener(EditorListener listener) {
    this.listener = listener;
  }
}
