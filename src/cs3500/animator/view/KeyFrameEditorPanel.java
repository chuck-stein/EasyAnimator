package cs3500.animator.view;

import cs3500.animator.model.hw05.IState;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import javax.swing.JTextField;
import javax.swing.border.Border;

class KeyFrameEditorPanel extends JPanel {

  private JPanel tickPanel;
  private JPanel xLocPanel;
  private JPanel yLocPanel;
  private JPanel widthPanel;
  private JPanel heightPanel;
  private JPanel rColorPanel;
  private JPanel gColorPanel;
  private JPanel bColorPanel;

  private JTextField tickPanelField;
  private JTextField xLocPanelField;
  private JTextField yLocPanelField;
  private JTextField widthPanelField;
  private JTextField heightPanelField;
  private JTextField rColorPanelField;
  private JTextField gColorPanelField;
  private JTextField bColorPanelField;
  private JButton goChange;




  private Dimension inputSize = new Dimension(60, 50);
  boolean keyFrameSelected;


  KeyFrameEditorPanel() {
    super();

    this.keyFrameSelected = false;

    tickPanel = new JPanel();
    xLocPanel = new JPanel();
    yLocPanel = new JPanel();
    widthPanel = new JPanel();
    heightPanel = new JPanel();
    rColorPanel = new JPanel();
    gColorPanel = new JPanel();
    bColorPanel = new JPanel();



    this.setUpBoxes(tickPanel, "tick");
    this.setUpBoxes(xLocPanel, "xLoc");
    this.setUpBoxes(yLocPanel, "yLoc");
    this.setUpBoxes(widthPanel, "width");
    this.setUpBoxes(heightPanel, "height");
    this.setUpBoxes(rColorPanel, "rColor");
    this.setUpBoxes(gColorPanel, "gColor");
    this.setUpBoxes(bColorPanel, "bColor");

    this.setTickPanelField("  ");
    this.setXLocPanelField("  ", false);
    this.setYLocPanelField("  ", false);
    this.setWidthPanelField("  ", false);
    this.setHeightPanelField("  ", false);
    this.setRColorPanelField("  ", false);
    this.setGColorPanelField("  ", false);
    this.setBColorPanelField("  ", false);


    goChange = new JButton("Change");
    goChange.setActionCommand("edit keyframe");

    this.add(tickPanel);
    this.add(xLocPanel);
    this.add(yLocPanel);
    this.add(widthPanel);
    this.add(heightPanel);
    this.add(rColorPanel);
    this.add(gColorPanel);
    this.add(bColorPanel);
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

    this.setTickPanelField(Integer.toString(keyframe.getTick()));
    this.setXLocPanelField(Integer.toString((int)keyframe.getPositionY()),true);
    this.setYLocPanelField( Integer.toString((int)keyframe.getPositionY()), true);
    this.setWidthPanelField( Integer.toString((int)keyframe.getWidth()), true);
    this.setHeightPanelField(Integer.toString((int)keyframe.getHeight()), true);
    this.setRColorPanelField(Integer.toString(keyframe.getColorR()), true);
    this.setGColorPanelField(Integer.toString(keyframe.getColorG()),true);
    this.setBColorPanelField(Integer.toString(keyframe.getColorB()),true);

  }

  int[] getNewKeyFrame() throws IllegalStateException {
    if (!keyFrameSelected) {
      throw new IllegalStateException("No Frame Selected");
    }

    int[] keyFrameValues = new int[8];
    keyFrameValues[0] = Integer.parseInt(tickPanelField.getText());
    keyFrameValues[1] = Integer.parseInt(xLocPanelField.getText());
    keyFrameValues[2] = Integer.parseInt(yLocPanelField.getText());
    keyFrameValues[3] = Integer.parseInt(widthPanelField.getText());
    keyFrameValues[4] = Integer.parseInt(heightPanelField.getText());
    keyFrameValues[5] = Integer.parseInt(rColorPanelField.getText());
    keyFrameValues[6] = Integer.parseInt(gColorPanelField.getText());
    keyFrameValues[7] = Integer.parseInt(bColorPanelField.getText());


    keyFrameSelected = false;
    return keyFrameValues;
  }

  private void setTickPanelField(String value) {
    if(!Objects.isNull(tickPanelField)){
      tickPanel.remove(tickPanelField);
    }
    tickPanelField = new JTextField(value);
    tickPanelField.setEditable(false);
    tickPanel.add(tickPanelField);
  }

  private void setXLocPanelField(String value, boolean editable) {
    if(!Objects.isNull(xLocPanelField)){
      xLocPanel.remove(xLocPanelField);
    }
    xLocPanelField = new JTextField(value);
    xLocPanelField.setEditable(editable);
    xLocPanelField.setPreferredSize(new Dimension(20,20));
    xLocPanel.add(xLocPanelField);

  }

  private void setYLocPanelField(String value, boolean editable) {
    if(!Objects.isNull(yLocPanelField)){
      yLocPanel.remove(yLocPanelField);
    }
    yLocPanelField = new JTextField(value);
    yLocPanelField.setEditable(editable);
    yLocPanelField.setPreferredSize(new Dimension(20,20));
    yLocPanel.add(yLocPanelField);
  }

  private void setWidthPanelField(String value, boolean editable) {
    if(!Objects.isNull(widthPanelField)){
      widthPanel.remove(widthPanelField);
    }
    widthPanelField = new JTextField(value);
    widthPanelField.setEditable(editable);
    widthPanelField.setPreferredSize(new Dimension(20,20));
    widthPanel.add(widthPanelField);

  }

  private void setHeightPanelField(String value, boolean editable) {
    if(!Objects.isNull(heightPanelField)){
      heightPanel.remove(heightPanelField);
    }
    heightPanelField = new JTextField(value);
    heightPanelField.setEditable(editable);
    heightPanelField.setPreferredSize(new Dimension(20,20));
    heightPanel.add(heightPanelField);
  }

  private void setRColorPanelField(String value, boolean editable) {
    if(!Objects.isNull(rColorPanelField)){
      rColorPanel.remove(rColorPanelField);
    }
    rColorPanelField = new JTextField(value);
    rColorPanelField.setEditable(editable);
    rColorPanelField.setPreferredSize(new Dimension(20,20));
    rColorPanel.add(rColorPanelField);
  }

  private void setGColorPanelField(String value, boolean editable) {
    if(!Objects.isNull(gColorPanelField)){
      gColorPanel.remove(gColorPanelField);
    }
    gColorPanelField = new JTextField(value);
    gColorPanelField.setEditable(editable);
    gColorPanelField.setPreferredSize(new Dimension(20,20));
    gColorPanel.add(gColorPanelField);
  }

  private void setBColorPanelField(String value, boolean editable) {
    if(!Objects.isNull(bColorPanelField)){
      bColorPanel.remove(bColorPanelField);
    }
    bColorPanelField = new JTextField(value);
    bColorPanelField.setEditable(editable);
    bColorPanelField.setPreferredSize(new Dimension(20,20));
    bColorPanel.add(bColorPanelField);
  }

}
