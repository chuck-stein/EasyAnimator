package cs3500.animator.view;

import cs3500.animator.model.hw05.IReadableShape;
import cs3500.animator.model.hw05.IWritableShape;
import cs3500.animator.model.hw05.ShapeType;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;
import java.util.Objects;
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
    editPanel.setPreferredSize(new Dimension(300, 450));
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
    int[] keyValues;
    IReadableShape shape;
    try {
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
          String shapeName = JOptionPane.showInputDialog(
              new JPanel(),
              "Enter a name for the Shape:\n",
              "Create Shape",
              JOptionPane.PLAIN_MESSAGE
              );

          ShapeType[] possibilities = {ShapeType.RECTANGLE,ShapeType.ELLIPSE};
           ShapeType type = (ShapeType)JOptionPane.showInputDialog(
              new JPanel(),
              "Choose a ShapeType:\n"
                 ,
              "Create Shape",
              JOptionPane.PLAIN_MESSAGE, UIManager.getIcon("Tree.expandedIcon"), possibilities,ShapeType.RECTANGLE
              );
           listener.addShape(shapeName,type);

          break;
        case "remove shape":
          shape = editPanel.getSelectedShape();
          listener.removeShape(shape.getName());
          break;
        case "insert keyframe":
          shape = editPanel.getSelectedShape();
          String tickTime = JOptionPane.showInputDialog(
              new JPanel(),
              "Enter a time for the KeyFrame:\n",
              "Create KeyFrame",
              JOptionPane.PLAIN_MESSAGE
          );
          listener.insertKeyframe(shape.getName(),Integer.parseInt(tickTime));

          break;
        case "edit keyframe":
          shape = editPanel.getSelectedShape();
          keyValues = editPanel.getKeyFrameEdits();

          listener
              .editKeyframe(shape.getName(), keyValues[0], keyValues[1], keyValues[2], keyValues[3],
                  keyValues[4], keyValues[5], keyValues[6], keyValues[7]);
          break;
        case "remove keyframe":
          shape = editPanel.getSelectedShape();
          keyValues = editPanel.getKeyFrameEdits();

          listener.removeKeyframe(shape.getName(),keyValues[0]);
          break;
        default:
          break;

      }
    } catch (IllegalStateException e1) {
      JOptionPane.showMessageDialog(new JPanel(), e1.getMessage(), "WHOOPSY",
          JOptionPane.ERROR_MESSAGE);
    }
    }


}
