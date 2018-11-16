package cs3500.animator.view;

import java.awt.event.ActionListener;
import java.util.TimerTask;

import javax.swing.*;

import cs3500.animator.model.hw05.IReadableShape;

public class AnimationEditorView extends JFrame implements InteractiveAnimatorView {

  private ActionListener listener;
  private ShapePanel shapePanel;

//  @Override
//  public void animate() {
//    this.setVisible(true);
//  }

  /**
   * Updates the animation editor by redrawing it and advancing the tick by one.
   */
  public void update() {
    this.repaint();
    shapePanel.updateTick();
  }

}
