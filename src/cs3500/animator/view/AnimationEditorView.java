package cs3500.animator.view;

import cs3500.animator.model.hw05.IReadableShape;
import cs3500.animator.model.hw05.ShapeType;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.util.List;

import cs3500.animator.controller.EditorListener;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Represents a view for editing an animation, displaying the animation being edited next to
 * playback controls and editing controls for that animation.
 */
public final class AnimationEditorView extends ASwingAnimatorView implements IEasyAnimatorView,
    ActionListener {

  private EditPanel editPanel;
  private EditorListener listener;
  private final JFileChooser fc = new JFileChooser();

  /**
   * Constructs an AnimationEditorView with the given canvas and speed settings.
   *
   * @param canvasX      how far to move the origin in the x direction.
   * @param canvasY      how far to move the origin in the y direction.
   * @param canvasWidth  how wide to make the canvas.
   * @param canvasHeight how tall to make the canvas.
   * @throws IllegalArgumentException if canvas dimensions or ticks per second are not positive.
   */
  public AnimationEditorView(int canvasX, int canvasY, int canvasWidth, int canvasHeight)
      throws IllegalArgumentException {
    super(canvasX, canvasY, canvasWidth, canvasHeight);
    editPanel = new EditPanel();

    editPanel.setPreferredSize(new Dimension(300, 490));
    editPanel.setActionListener(this);
    this.add(editPanel, BorderLayout.WEST);
    this.setTitle("Animation Editor");
    this.pack();
    this.setLocationRelativeTo(null); // center the frame
  }

  @Override
  public void animate() {
    this.setVisible(true);
    this.repaint();
  }

  @Override
  public void setShapes(List<IReadableShape> shapes) throws IllegalArgumentException {
    super.setShapes(shapes);
    editPanel.setShapes(shapes);
    this.pack();
  }

  /**
   * Updates the animation editor by redrawing it and advancing the tick by one.
   */
  public void setTime(int tick) {
    shapePanel.updateTick(tick);
  }

  @Override
  public void setTicksPerSecond(int ticksPerSecond) {
    //no effect in this view type
  }

  @Override
  public void setListener(EditorListener listener) {
    this.listener = listener;
  }

  @Override
  public boolean doneAnimating() {
    return false;
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    String cmd = event.getActionCommand();
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
              this,
              "Enter a name for the Shape:\n",
              "Create Shape",
              JOptionPane.PLAIN_MESSAGE
          );
          ShapeType[] possibilities = {ShapeType.RECTANGLE, ShapeType.ELLIPSE};
          ShapeType type = (ShapeType) JOptionPane.showInputDialog(
              this,
              "Choose a ShapeType:\n",
              "Create Shape",
              JOptionPane.PLAIN_MESSAGE, new ImageIcon("blerner.png"), possibilities,
              ShapeType.RECTANGLE
          );
          listener.addShape(shapeName, type);

          break;
        case "remove shape":
          shape = editPanel.getSelectedShape();
          listener.removeShape(shape.getName());
          break;
        case "insert keyframe":
          shape = editPanel.getSelectedShape();
          String tickTime = JOptionPane.showInputDialog(
              this,
              "Enter a time for the KeyFrame:\n",
              "Create KeyFrame",
              JOptionPane.PLAIN_MESSAGE
          );
          try {
            listener.insertKeyframe(shape.getName(), Integer.parseInt(tickTime));
          } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tick value must be an integer.", "WHOOPSY",
                JOptionPane.ERROR_MESSAGE);
          }
          break;
        case "edit keyframe":
          shape = editPanel.getSelectedShape();
          try {
            keyValues = editPanel.getKeyFrameEdits();

            listener
                .editKeyframe(shape.getName(), keyValues[0], keyValues[1], keyValues[2],
                    keyValues[3],
                    keyValues[4], keyValues[5], keyValues[6], keyValues[7]);
          } catch (NumberFormatException e1) {
            JOptionPane
                .showMessageDialog(this, "You must enter numbers for KeyFrame fields.",
                    "WHOOPSY",
                    JOptionPane.ERROR_MESSAGE);

          }
          break;
        case "remove keyframe":
          shape = editPanel.getSelectedShape();
          keyValues = editPanel.getKeyFrameEdits();

          listener.removeKeyframe(shape.getName(), keyValues[0]);
          break;
        case "save":
          String fileName = JOptionPane.showInputDialog(
              this,
              "Save as what name?\n",
              "Save File",
              JOptionPane.PLAIN_MESSAGE
          );

          String[] fileTypes = {"txt", "svg"};
          String theType = (String) JOptionPane.showInputDialog(
                  this,
                  "Choose a file type:\n",
                  "Save File",
                  JOptionPane.PLAIN_MESSAGE, new ImageIcon("blerner.png"), fileTypes,
                  "txt");

          listener.saveFile(fileName, theType);
          break;

        case "load":
          fc.showOpenDialog(this);
          File file = fc.getSelectedFile();
          listener.loadFile(file);

          break;
        default:
          break;

      }
    } catch (IllegalStateException e1) {
      JOptionPane.showMessageDialog(this, e1.getMessage(), "WHOOPSY",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public void resizeCanvas(int canvasWidth, int canvasHeight, int canvasX, int canvasY) {
    int newHeight = 490;
    if (canvasHeight > newHeight) {
      newHeight = canvasHeight;
    }
    this.setPreferredSize(new Dimension(canvasWidth + 300, newHeight));
    shapePanel.setPreferredSize(new Dimension(canvasWidth, canvasHeight));
    shapePanel.updateCanvasOrigin(-canvasX, -canvasY);
    this.pack();
  }


}
