package cs3500.animator.view;

import cs3500.animator.model.hw05.IReadableShape;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;
import javax.swing.*;

import cs3500.animator.controller.EditorListener;

public class AnimationEditorView extends ASwingAnimatorView implements InteractiveAnimatorView,
    ActionListener {

  private EditPanel editPanel;
  private EditorListener listener;

  public AnimationEditorView(int canvasX, int canvasY, int canvasWidth, int canvasHeight,
      int ticksPerSecond)
      throws IllegalArgumentException {
    super();
    if (canvasWidth <= 0 || canvasHeight <= 0) {
      throw new IllegalArgumentException("Canvas dimensions must be positive.");
    }
    if (ticksPerSecond <= 0) {
      throw new IllegalArgumentException("Ticks per second must be be positive.");
    }
    this.ticksPerSecond = ticksPerSecond;
    this.setTitle("Animation Editor");
    this.setSize(canvasWidth, canvasHeight);
    this.setLayout(new BorderLayout());

    shapePanel = new ShapePanel(-canvasX, -canvasY);
    shapePanel.setPreferredSize(new Dimension(canvasWidth, canvasHeight));
    JScrollPane scrollBarAndPane = new JScrollPane(shapePanel,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    editPanel = new EditPanel(-canvasX + canvasWidth / 4 * 3, -canvasY);
    editPanel.setPreferredSize(new Dimension(300, canvasHeight));

    editPanel.setActionListener(this);

    this.add(scrollBarAndPane, BorderLayout.CENTER);
    this.add(editPanel, BorderLayout.WEST);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
