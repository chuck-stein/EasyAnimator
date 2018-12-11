package cs3500.animator.view;

import cs3500.animator.model.hw05.IState;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;


import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * Represents a panel that displays the stats of a keyframe. Also allows for the manipulation of
 * these variables and sending of new keyframe info.
 */
final class KeyFrameEditorPanel extends JPanel {

  private JTextField[] textFields;
  private JButton goChange;
  private Dimension inputSize = new Dimension(90, 50);
  private boolean keyFrameSelected;

  /**
   * Creates a panel for keyframe editing.
   */
  KeyFrameEditorPanel() {
    super();
    JPanel[] thePanels;
    this.keyFrameSelected = false;

    thePanels = new JPanel[9];

    textFields = new JTextField[9];
    for (int i = 0; i < textFields.length; i++) {
      thePanels[i] = new JPanel();
      textFields[i] = new JTextField();
      thePanels[i].add(textFields[i]);
    }

    goChange = new JButton("Change");
    goChange.setActionCommand("edit keyframe");

    this.setUpBoxes(thePanels[0], "tick");
    this.setUpBoxes(thePanels[1], "xLoc");
    this.setUpBoxes(thePanels[2], "yLoc");
    this.setUpBoxes(thePanels[3], "width");
    this.setUpBoxes(thePanels[4], "height");
    this.setUpBoxes(thePanels[6], "rColor");
    this.setUpBoxes(thePanels[7], "gColor");
    this.setUpBoxes(thePanels[8], "bColor");
    this.setUpBoxes(thePanels[5], "angle");

    for (int i = 0; i < thePanels.length; i++) {
      this.setTextPanelField(textFields[i], "  ", false);
      this.add(thePanels[i]);
    }
    this.add(goChange);

  }

  /**
   * Sets up the outline and title for the input Boxes.
   *
   * @param panel the panel that gets the outline.
   * @param name the name that the panel will display.
   */
  private void setUpBoxes(JPanel panel, String name) {
    Border border = BorderFactory.createTitledBorder(name);
    panel.setBorder(border);
    panel.setPreferredSize(inputSize);
  }


  /**
   * Sets the action listener for the buttons.
   *
   * @param listener the listener.
   */
  void setActionListener(ActionListener listener) {
    goChange.addActionListener(listener);
  }

  /**
   * Receives a keyframe to show. Displays the information in the input boxes.
   *
   * @param keyframe the keyframe to display.
   */
  void setKeyframe(IState keyframe) {
    keyFrameSelected = true;

    this.setTextPanelField(textFields[0], Integer.toString(keyframe.getTick()), false);

    this.setTextPanelField(textFields[1], Integer.toString((int) keyframe.getPositionX()), true);
    this.setTextPanelField(textFields[2], Integer.toString((int) keyframe.getPositionY()), true);
    this.setTextPanelField(textFields[3], Integer.toString((int) keyframe.getWidth()), true);
    this.setTextPanelField(textFields[4], Integer.toString((int) keyframe.getHeight()), true);
    this.setTextPanelField(textFields[6], Integer.toString(keyframe.getColorR()), true);
    this.setTextPanelField(textFields[7], Integer.toString(keyframe.getColorG()), true);
    this.setTextPanelField(textFields[8], Integer.toString(keyframe.getColorB()), true);
    this.setTextPanelField(textFields[5], Integer.toString((int)keyframe.getAngle()), true);
    if (keyframe.getAngle() != 0) {
    System.out.println(keyframe.getAngle());
    }

  }

  /**
   * Gets the input from each of the boxes which are ready to be made into a keyFrame.
   *
   * @return an 9 sized array of ints where each value corresponds to a value in a key frame. In
   * order they are tick, xloc, yloc, width, height, angle, rColor, gColor, bColor.
   * @throws IllegalStateException if no keyframe is selected it does not work.
   */
  int[] getNewKeyFrame() throws IllegalStateException {
    if (!keyFrameSelected) {
      throw new IllegalStateException("No Frame Selected");
    }

    int[] keyFrameValues = new int[9];
    for (int i = 0; i < keyFrameValues.length; i++) {
      keyFrameValues[i] = Integer.parseInt(textFields[i].getText());
      this.setTextPanelField(textFields[i], "  ", false);
    }

    keyFrameSelected = false;
    return keyFrameValues;
  }

  /**
   * Sets the text boxes up to show the inputs and their base values.
   */
  private void setTextPanelField(JTextField panel, String value, boolean editable) {
    panel.setPreferredSize(new Dimension(40, 20));
    panel.setText(value);
    panel.setEditable(editable);
  }


}
