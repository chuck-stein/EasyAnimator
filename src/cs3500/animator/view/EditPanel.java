package cs3500.animator.view;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

import cs3500.animator.model.hw05.IReadableShape;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.JScrollPane;

import java.awt.event.ActionListener;

import java.util.List;
import java.util.Objects;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Represents a panel containing the controls for editing an animation, such as playback controls
 * and shape/keyframe lists.
 */
final class EditPanel extends JPanel implements ListSelectionListener {

  private JButton restart;
  private JButton slowDown;
  private JButton pausePlay;
  private JButton speedUp;
  private JButton loopBack;
  private JButton removeShape;
  private JButton addShape;
  private JButton addKeyFrame;
  private JButton removeKeyFrame;
  private List<IReadableShape> shapes;
  private JList shapeJList;
  private JPanel shapeListBox;
  private KeyFrameEditorPanel keyEditPanel;
  private KeyFrameListPanel keyListPanel;
  private boolean paused;
  private JScrollPane scrollBarAndShapeList;


  /**
   * Constructs the edit panel and all its buttons and sub panels.
   *

   */
  EditPanel() {
    super();
    this.setBackground(Color.GRAY);

    this.paused = false;


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

    removeShape = new JButton("Remove Shape");
    removeShape.setActionCommand("remove shape");

    addShape = new JButton("Add Shape");
    addShape.setActionCommand("add shape");

    addKeyFrame = new JButton("Add KeyFrame");
    addKeyFrame.setActionCommand("insert keyframe");

    removeKeyFrame = new JButton("Remove KeyFrame");
    removeKeyFrame.setActionCommand("remove keyframe");


    Dimension listBoxSize = new Dimension(100, 200);

    shapeListBox = new JPanel();
    Border titledBorder = BorderFactory.createTitledBorder("Shapes");
    shapeListBox.setBorder(titledBorder);
    shapeListBox.setPreferredSize(listBoxSize);

    keyEditPanel = new KeyFrameEditorPanel();
    keyEditPanel.setPreferredSize(new Dimension(300, 150));

    keyListPanel = new KeyFrameListPanel();
    titledBorder = BorderFactory.createTitledBorder("KeyFrames");
    keyListPanel.setBorder(titledBorder);
    keyListPanel.setPreferredSize(listBoxSize);

    this.add(restart);
    this.add(speedUp);
    this.add(pausePlay);
    this.add(slowDown);
    this.add(loopBack);
    this.add(shapeListBox);
    this.add(keyListPanel);
    this.add(addShape);
    this.add(removeShape);
    this.add(addKeyFrame);
    this.add(removeKeyFrame);
    this.add(keyEditPanel, BorderLayout.SOUTH);

    this.setKeyframeEditor(keyEditPanel);

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
    keyEditPanel.setActionListener(listener);
    removeShape.addActionListener(listener);
    addShape.addActionListener(listener);
    removeKeyFrame.addActionListener(listener);
    addKeyFrame.addActionListener(listener);

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
    if (!Objects.isNull(scrollBarAndShapeList)) {
      shapeListBox.remove(scrollBarAndShapeList);
    }
    shapeJList = new JList(shapes.toArray());
    shapeJList.addListSelectionListener(this);
    scrollBarAndShapeList = new JScrollPane(shapeJList, VERTICAL_SCROLLBAR_AS_NEEDED,
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

  @Override
  public void valueChanged(ListSelectionEvent e) {
    if (e.getValueIsAdjusting()) {
      keyListPanel.setShape(shapes.get(shapeJList.getSelectedIndex()));
    }
  }

  /**
   * Sets this EditPanel's inner panel for editing keyframes to the given panel.
   * @param p the panel to be used as a keyframe editor
   */
  private void setKeyframeEditor(KeyFrameEditorPanel p) {
    keyListPanel.setKeyFrameEditor(p);
  }

  /**
   * Gets the selected shape out of the Shape Lists.
   * @return the shape from the list
   * @throws IllegalStateException
   */
  IReadableShape getSelectedShape() throws IllegalStateException {
    try {
      return shapes.get(shapeJList.getSelectedIndex());
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalStateException("No Shapes Selected");
    }
  }

  int[] getKeyFrameEdits() {
    return keyEditPanel.getNewKeyFrame();
  }
}
