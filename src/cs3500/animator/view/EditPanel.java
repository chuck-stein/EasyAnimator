package cs3500.animator.view;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

import cs3500.animator.model.hw05.IReadableShape;

import java.awt.*;
import java.awt.event.ActionListener;

import java.util.List;
import java.util.Objects;

import javax.swing.*;
import javax.swing.border.Border;

/**
 * Represents a panel containing the controls for editing an animation, such as playback controls
 * and shape/keyframe lists.
 */
final class EditPanel extends JPanel {

  JButton restart;
  JButton slowDown;
  JButton pausePlay;
  JButton speedUp;
  JButton loopBack;
  private int canvasX;
  private int canvasY;
  private List<IReadableShape> shapes;
  private JList shapeJList;
  private JPanel shapeListBox;
  private KeyFrameEditorPanel keyEditPanel;
  private boolean paused;

  /**
   * Constructs the edit panel at the given canvas location.
   *
   * @param canvasX how far to shift the origin in the x direction.
   * @param canvasY how far to shift the origin in the y direction.
   */
  EditPanel(int canvasX, int canvasY) {
    super();
    this.setBackground(Color.GRAY);
    this.canvasX = canvasX;
    this.canvasY = canvasY;
    this.paused = false;

    new JOptionPane();
    restart = new JButton(getScaledIcon("restartIcon.png"));
    restart.setActionCommand("restart");
    restart.setToolTipText("restart");

    speedUp = new JButton(getScaledIcon("fasterIcon.png"));
    speedUp.setActionCommand("speed up");
    speedUp.setToolTipText("speed up");

    pausePlay = new JButton(getScaledIcon("pauseIcon.png"));
    pausePlay.setActionCommand("toggle playback");
    pausePlay.setToolTipText("play/pause");

    slowDown = new JButton(getScaledIcon("slowerIcon.png"));
    slowDown.setActionCommand("slow down");
    slowDown.setToolTipText("slow down");

    loopBack = new JButton(getScaledIcon("loopIcon.png"));
    loopBack.setActionCommand("toggle looping");
    loopBack.setToolTipText("toggle looping");

    shapeListBox = new JPanel();
    Border titledBorder = BorderFactory.createTitledBorder("Shapes");
    shapeListBox.setBorder(titledBorder);

    keyEditPanel = new KeyFrameEditorPanel();

    this.add(restart);
    this.add(speedUp);
    this.add(pausePlay);
    this.add(slowDown);
    this.add(loopBack);
    this.add(shapeListBox);
  }

  /**
   * Returns an ImageIcon of the image at the given filename (if exists), scaled to be 20x20.
   *
   * @param filename the file name or path to the image which will be displayed as the icon
   * @return the ImageIcon of the specified image after being scaled.
   */
  private ImageIcon getScaledIcon(String filename) {
    Image unscaledImage = new ImageIcon(filename).getImage();
    Image scaledImage = unscaledImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
    return new ImageIcon(scaledImage);
  }

  /**
   * Sets the listener of all components in this EditPanel to the specified  ActionListener.
   *
   * @param listener the ActionListener to be set
   */
  void setActionListener(ActionListener listener) {
    restart.addActionListener(listener);
    slowDown.addActionListener(listener);
    pausePlay.addActionListener(listener);
    speedUp.addActionListener(listener);
    loopBack.addActionListener(listener);
  }

  /**
   * Sets the shapes that this EditPanel will list.
   *
   * @param shapes the readable shapes to be added to this EditPanel
   * @throws IllegalArgumentException if the give list of shapes is empty
   */
  void setShapes(List<IReadableShape> shapes) throws IllegalArgumentException {
    if (Objects.isNull(shapes)) {
      throw new IllegalArgumentException("Cannot have null Shapes");
    }
    this.shapes = shapes;
    if (!Objects.isNull(shapeJList)) {
      shapeListBox.remove(shapeJList);
    }
    shapeJList = new JList(shapes.toArray());
    JScrollPane scrollBarAndShapeList = new JScrollPane(shapeJList, VERTICAL_SCROLLBAR_AS_NEEDED,
            HORIZONTAL_SCROLLBAR_AS_NEEDED);
    shapeListBox.add(scrollBarAndShapeList);
  }

  /**
   * Toggles the pause/play button's icon.
   */
  void togglePlayPauseIcon() {
    if (paused) {
      paused = false;
      pausePlay.setIcon(getScaledIcon("pauseIcon.png"));
    } else {
      paused = true;
      pausePlay.setIcon(getScaledIcon("playIcon.png"));
    }
  }



}
