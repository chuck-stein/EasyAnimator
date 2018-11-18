package cs3500.animator.view;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

class KeyFrameEditorPanel extends JPanel {
  private JPanel xLoc;
  private JPanel yLoc;
  private JPanel width;
  private JPanel height;
  private JPanel rColor;
  private JPanel gColor;
  private JPanel bColor;
  private JButton goChange;



   KeyFrameEditorPanel() {
     super();

     Dimension inputSize = new Dimension(60,75);

     xLoc = new JPanel();
    /* this.setUpInputPanel(xLoc,2,"xLoc");*/
     this.setUpTextShowPanel(xLoc,"23", "xLoc");
     xLoc.setPreferredSize(inputSize);

     yLoc = new JPanel();
     this.setUpInputPanel(yLoc,2,"yLoc");
     yLoc.setPreferredSize(inputSize);

     width = new JPanel();
     this.setUpInputPanel(width,2,"width");
     width.setPreferredSize(inputSize);

     height = new JPanel();
     this.setUpInputPanel(height,2,"height");
     height.setPreferredSize(inputSize);

     rColor = new JPanel();
     this.setUpInputPanel(rColor,2,"rColor");
     rColor.setPreferredSize(inputSize);

     gColor = new JPanel();
     this.setUpInputPanel(gColor,2,"gColor");
     gColor.setPreferredSize(inputSize);

     bColor = new JPanel();
     this.setUpInputPanel(bColor,2,"bColor");
     bColor.setPreferredSize(inputSize);

     goChange = new JButton("Change");
     goChange.setActionCommand("edit keyframe");


     this.add(xLoc);
     this.add(yLoc);
     this.add(width);
     this.add(height);
     this.add(rColor);
     this.add(gColor);
     this.add(bColor);
     this.add(goChange);
   }

  private void setUpInputPanel(JPanel panel, int colms, String name) {
    JTextField textField = new JTextField(colms);
    Border border = BorderFactory.createTitledBorder(name);
    panel.setBorder(border);
    panel.add(textField);
  }

  private void setUpTextShowPanel(JPanel panel, String display, String name) {
    JTextArea textStuff = new JTextArea(display);
    Border border = BorderFactory.createTitledBorder(name);
    panel.setBorder(border);
    panel.add(textStuff);
  }

  public void setActionListener(ActionListener listener) {
     goChange.addActionListener(listener);
  }

}
