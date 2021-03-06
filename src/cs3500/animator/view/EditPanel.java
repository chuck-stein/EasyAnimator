package cs3500.animator.view;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

import cs3500.animator.model.hw05.IReadableShape;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;

import java.util.List;
import java.util.Objects;

import javax.swing.JSlider;

import javax.swing.border.Border;
import javax.swing.event.ChangeListener;
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
  private JButton save;
  private JButton load;
  private List<IReadableShape> shapes;
  private JList shapeJList;
  private JPanel shapeListBox;
  private KeyFrameEditorPanel keyEditPanel;
  private KeyFrameListPanel keyListPanel;
  private boolean paused;
  private JScrollPane scrollBarAndShapeList;
  private IReadableShape currentSelectedShape;
  private JSlider scrubber;
  private List<List<IReadableShape>> layers;
  private JList layerJList;
  private JPanel layerListBox;
  private JScrollPane scrollBarAndLayerList;
  private JButton addLayer;
  private JButton removeLayer;
  private JButton layerForward;
  private JButton layerBackward;
  private List<IReadableShape> currentSelectedLayer;
  private int theCurrentSelectedLayer;


  /**
   * Constructs the edit panel and all its buttons and sub panels.
   */
  EditPanel() {
    super();
    this.setBackground(Color.GRAY);

    this.paused = false;

    restart = new JButton(getScaledIcon("images/restartIcon.png"));
    restart.setActionCommand("restart");
    restart.setToolTipText("restart");

    speedUp = new JButton(getScaledIcon("images/fasterIcon.png"));
    speedUp.setActionCommand("speed up");
    speedUp.setToolTipText("speed up");

    pausePlay = new JButton(getScaledIcon("images/pauseIcon.png"));
    pausePlay.setActionCommand("toggle playback");
    pausePlay.setToolTipText("play/pause");

    slowDown = new JButton(getScaledIcon("images/slowerIcon.png"));
    slowDown.setActionCommand("slow down");
    slowDown.setToolTipText("slow down");

    loopBack = new JButton(getScaledIcon("images/loopIcon.png"));
    loopBack.setActionCommand("toggle looping");
    loopBack.setToolTipText("toggle looping");

    addLayer = new JButton(" Add Layer ");
    addLayer.setActionCommand("add layer");

    removeLayer = new JButton("Remove Layer");
    removeLayer.setActionCommand("remove layer");

    layerForward = new JButton(" Layer Forward ");
    layerForward.setActionCommand("layer forward");

    layerBackward = new JButton("Layer  Backward   ");
    layerBackward.setActionCommand("layer backward");

    removeShape = new JButton("Remove Shape");
    removeShape.setActionCommand("remove shape");

    addShape = new JButton(" Add Shape ");
    addShape.setActionCommand("add shape");

    addKeyFrame = new JButton(" Add KeyFrame ");
    addKeyFrame.setActionCommand("insert keyframe");

    removeKeyFrame = new JButton("Remove KeyFrame");
    removeKeyFrame.setActionCommand("remove keyframe");

    save = new JButton("Save Animation");
    save.setActionCommand("save");

    load = new JButton("Load Animation");
    load.setActionCommand("load");

    Dimension listBoxSize = new Dimension(90, 200);

    shapeListBox = new JPanel();
    Border titledBorder = BorderFactory.createTitledBorder("Shapes");
    shapeListBox.setBorder(titledBorder);
    shapeListBox.setPreferredSize(listBoxSize);

    keyEditPanel = new KeyFrameEditorPanel();
    keyEditPanel.setPreferredSize(new Dimension(300, 200));

    keyListPanel = new KeyFrameListPanel();
    titledBorder = BorderFactory.createTitledBorder("KeyFrames");
    keyListPanel.setBorder(titledBorder);
    keyListPanel.setPreferredSize(listBoxSize);

    layerListBox = new JPanel();
    titledBorder = BorderFactory.createTitledBorder("Layers");
    layerListBox.setBorder(titledBorder);
    layerListBox.setPreferredSize(new Dimension(60, 200));

    scrubber = new JSlider(1, 100, 1);

    this.add(restart);
    this.add(speedUp);
    this.add(pausePlay);
    this.add(slowDown);
    this.add(loopBack);
    this.add(scrubber);
    this.add(save);
    this.add(load);
    this.add(layerListBox);
    this.add(shapeListBox);
    this.add(keyListPanel);
    this.add(layerForward);
    this.add(layerBackward);
    this.add(addLayer);
    this.add(removeLayer);

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
    save.addActionListener(listener);
    load.addActionListener(listener);
    addLayer.addActionListener(listener);
    removeLayer.addActionListener(listener);
    layerForward.addActionListener(listener);
    layerBackward.addActionListener(listener);

    scrubber.addChangeListener((ChangeListener) listener);
  }

  /**
   * Sets the shapes that this EditPanel will list.
   *
   * @param shapes         the readable shapes to be added to this EditPanel
   * @param buttonResponse if the call is triggered from a user pressing a button (this is to fix a
   *                       bug in the provided code that they could not fix, which we found a
   *                       workaround for that only works for the provided view but breaks our own.
   *                       Therefore this boolean allows both views to function without error)
   * @throws IllegalArgumentException if the give list of shapes is empty
   */
  void setShapes(List<IReadableShape> shapes, boolean buttonResponse)
          throws IllegalArgumentException {
    if (Objects.isNull(shapes)) {
      // throw new IllegalArgumentException("Cannot have null Shapes");
      return;
    }
    if (!buttonResponse) {
      this.shapes = shapes;
      if (!Objects.isNull(scrollBarAndShapeList)) {
        shapeListBox.remove(scrollBarAndShapeList);
      }
      shapeJList = new JList(shapes.toArray());
      shapeJList.addListSelectionListener(this);
      shapeJList.setName("ShapeList");
      scrollBarAndShapeList = new JScrollPane(shapeJList, VERTICAL_SCROLLBAR_AS_NEEDED,
              HORIZONTAL_SCROLLBAR_AS_NEEDED);
      shapeListBox.add(scrollBarAndShapeList);

      int index = shapes.indexOf(currentSelectedShape);

      if (index >= 0) {
        shapeJList.setSelectedIndex(index);

      } else {
        currentSelectedShape = null;
      }

      keyListPanel.setShape(currentSelectedShape);
    }
  }

  /**
   * Toggles the pause/play button's icon.
   */
  void togglePlayPauseIcon() {
    if (paused) {
      paused = false;
      pausePlay.setIcon(getScaledIcon("images/pauseIcon.png"));
    } else {
      paused = true;
      pausePlay.setIcon(getScaledIcon("images/playIcon.png"));
    }
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
    JList theList = (JList) e.getSource();
    String listName = theList.getName();

    if (!e.getValueIsAdjusting() && listName.equals("ShapeList")) {
      currentSelectedShape = shapes.get(shapeJList.getSelectedIndex());
      keyListPanel.setShape(currentSelectedShape);
    }

    if (!e.getValueIsAdjusting() && listName.equals("LayerList")) {
      currentSelectedLayer = layers.get(layerJList.getSelectedIndex());

      this.setShapes(currentSelectedLayer, false);
    }

  }

  /**
   * Sets this EditPanel's inner panel for editing keyframes to the given panel.
   *
   * @param p the panel to be used as a keyframe editor
   */
  private void setKeyframeEditor(KeyFrameEditorPanel p) {
    keyListPanel.setKeyFrameEditor(p);
  }

  /**
   * Gets the selected shape out of the Shape Lists.
   *
   * @return the shape from the list
   */
  IReadableShape getSelectedShape() throws IllegalStateException {
    try {
      currentSelectedShape = shapes.get(shapeJList.getSelectedIndex());
      return currentSelectedShape;
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalStateException("No Shapes Selected");
    }
  }

  /**
   * Gets an array of values that correspond to keyframe atributes. In order they are tick, xloc,
   * yloc, width, height, rColor, gColor, bColor.
   *
   * @return the keyframe details as an array.
   */
  int[] getKeyFrameEdits() {
    return keyEditPanel.getNewKeyFrame();
  }

  void sliderSetUp(int timeMax) {
    scrubber.setMaximum(timeMax);
  }

  void updateSlider(int time) {
    scrubber.setValue(time);
  }

  int getSliderPosition() {
    return this.scrubber.getValue();
  }

  void setLayers(List<List<IReadableShape>> layers) {

    if (Objects.isNull(layers)) {
      throw new IllegalArgumentException("Cannot have null layers");
    }
    this.layers = layers;
    if (!Objects.isNull(scrollBarAndLayerList)) {
      layerListBox.remove(scrollBarAndLayerList);
    }
    layerJList = new JList(layers.toArray());
    layerJList.addListSelectionListener(this);
    layerJList.setName("LayerList");
    layerJList.setCellRenderer(new NameRenderer());
    layerJList.setPreferredSize(new Dimension(20, 50));
    scrollBarAndLayerList = new JScrollPane(layerJList, VERTICAL_SCROLLBAR_AS_NEEDED,
            HORIZONTAL_SCROLLBAR_AS_NEEDED);
    layerListBox.add(scrollBarAndLayerList);

    int index = layers.indexOf(currentSelectedLayer);

    if (index >= 0) {
      layerJList.setSelectedIndex(index);

    } else {
      if (layers.size() > 0) {

        layerJList.setSelectedIndex(0);
      } else {
        currentSelectedLayer = null;
      }
    }


  }

  int getSelectedLayerNumber() {
    int index;
    try {
      index = layerJList.getSelectedIndex();
    } catch (NullPointerException e) {
      index = -1;
    }
    if (index < 0) {
      throw new IllegalStateException("No Layer Selected");
    }
    return index;
  }

  class NameRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
      super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
      List<IReadableShape> l = (List<IReadableShape>) value;
      setText(Integer.toString(index + 1));
      return this;
    }
  }

}



