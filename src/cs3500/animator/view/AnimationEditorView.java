package cs3500.animator.view;

import cs3500.animator.model.hw05.IReadableShape;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;
import javax.swing.*;

import cs3500.animator.controller.EditorListener;

/**
 * Represents a view for editing an animation, displaying the animation being edited next to
 * playback controls and editing controls for that animation.
 */
public class AnimationEditorView extends ASwingAnimatorView implements InteractiveAnimatorView,
    ActionListener {

  private EditPanel editPanel;
  private EditorListener listener;

  /**
   * Constructs an AnimationEditorView with the given canvas and speed settings.
   *
   * @param canvasX how far to move the origin in the x direction.
   * @param canvasY how far to move the origin in the y direction.
   * @param canvasWidth how wide to make the canvas.
   * @param canvasHeight how tall to make the canvas.
   * @param ticksPerSecond how fast to animate the image, in ticks per second.
   * @throws IllegalArgumentException if canvas dimensions or ticks per second are not positive.
   */
  public AnimationEditorView(int canvasX, int canvasY, int canvasWidth, int canvasHeight,
      int ticksPerSecond) throws IllegalArgumentException {
    super(canvasX, canvasY, canvasWidth, canvasHeight, ticksPerSecond);
    editPanel = new EditPanel(-canvasX + canvasWidth / 4 * 3, -canvasY);
    editPanel.setPreferredSize(new Dimension(canvasWidth / 4, canvasHeight));
    editPanel.setActionListener(this);
    this.add(editPanel, BorderLayout.WEST);
    this.setTitle("Animation Editor");
    this.pack();
  }

  @Override
  public void animate() {
    this.setVisible(true);
    this.repaint();
  }

  @Override
  public void setShapes(List<IReadableShape> shapes) throws IllegalArgumentException{
    super.setShapes(shapes);
    editPanel.setShapes(shapes);
  }

  /**
   * Updates the animation editor by redrawing it and advancing the tick by one.
   */
  public void setTime(int tick) {
    shapePanel.updateTick(tick);
  }

  @Override
  public void setListener(EditorListener listener) {
    this.listener = listener;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String cmd = e.getActionCommand();
    System.out.println(e.getActionCommand());
    switch (cmd) {
      case "toggle playback":
        listener.togglePlayback();
        editPanel.togglePlayPauseIcon();
        break;
      case "restart":
        listener.restart();
        break;
      case "slow down":
        listener.slowDown();
        break;
      case "speed up":
        listener.speedUp();
        break;
      case "toggle looping":
        listener.toggleLooping();
        break;
      case "add shape":
        //listener.addShape();
        break;
      case "remove shape":
        //listener.removeShape();
        break;
      case "insert keyframe":
        //listener.insertKeyframe();
        break;
      case "edit keyframe":
        //listener.editKeyframe();
        break;
      case "remove keyframe":
        //listener.removeKeyframe();
        break;
      default:
        break;

    }
  }


}
