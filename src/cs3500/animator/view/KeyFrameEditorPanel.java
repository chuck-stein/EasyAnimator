package cs3500.animator.view;

import cs3500.animator.model.hw05.IState;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * Represents a panel that displays the stats of a keyframe. Also allows for the manipulation of
 * these variables and sending of new keyframe info.
 */
class KeyFrameEditorPanel extends JPanel {

  private JTextField[] textFields;
  private JButton goChange;
  private Dimension inputSize = new Dimension(60, 50);

 private boolean keyFrameSelected;

  /**
   * Creates a panel for keyframe editing.
   */
  KeyFrameEditorPanel() {
    super();
    JPanel[] thePanels;
    this.keyFrameSelected = false;


    thePanels = new JPanel[8];


    textFields = new JTextField[8];
    for (int i = 0; i < textFields.length; i++) {
      thePanels[i]= new JPanel();
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
    this.setUpBoxes(thePanels[5], "rColor");
    this.setUpBoxes(thePanels[6], "gColor");
    this.setUpBoxes(thePanels[7], "bColor");

    for (int i = 0; i < thePanels.length; i++) {
      this.setTextPanelField(textFields[i],"  ", false);
      this.add(thePanels[i]);
    }
    this.add(goChange);

  }

  private void setUpBoxes(JPanel panel, String name) {
    Border border = BorderFactory.createTitledBorder(name);
    panel.setBorder(border);
    panel.setPreferredSize(inputSize);
  }



  void setActionListener(ActionListener listener) {
    goChange.addActionListener(listener);
  }

  void setKeyframe(IState keyframe) {
    keyFrameSelected = true;

      this.setTextPanelField(textFields[0],Integer.toString(keyframe.getTick()), false);

    this.setTextPanelField(textFields[1],Integer.toString((int)keyframe.getPositionY()),true);
    this.setTextPanelField( textFields[2],Integer.toString((int)keyframe.getPositionY()), true);
    this.setTextPanelField( textFields[3],Integer.toString((int)keyframe.getWidth()), true);
    this.setTextPanelField(textFields[4],Integer.toString((int)keyframe.getHeight()), true);
    this.setTextPanelField(textFields[5],Integer.toString(keyframe.getColorR()), true);
    this.setTextPanelField(textFields[6],Integer.toString(keyframe.getColorG()),true);
    this.setTextPanelField(textFields[7],Integer.toString(keyframe.getColorB()),true);

  }

  int[] getNewKeyFrame() throws IllegalStateException {
    if (!keyFrameSelected) {
      throw new IllegalStateException("No Frame Selected");
    }

    int[] keyFrameValues = new int[8];
    for (int i = 0; i < keyFrameValues.length; i++) {
      keyFrameValues[i] = Integer.parseInt(textFields[i].getText());
    }


    keyFrameSelected = false;
    return keyFrameValues;
  }

  private void setTextPanelField(JTextField panel, String value, boolean editable) {
    panel.setPreferredSize(new Dimension(40,20));
    panel.setText(value);
    panel.setEditable(editable);
  }


}
