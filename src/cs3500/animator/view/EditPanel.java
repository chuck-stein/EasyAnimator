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

final class EditPanel extends JPanel {

  private int canvasX;
  private int canvasY;
  private List<IReadableShape> shapes;
  private JList shapeJList;
  private JPanel shapeListBox;
  private KeyFrameEditorPanel keyEditPanel;

  JButton restart;
  JButton slowDown;
  JButton pausePlay;
  JButton speedUp;
  JButton loopBack;

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

    restart = new JButton(UIManager.getIcon("OptionPane.informationIcon"));
    restart.setActionCommand("restart");

    slowDown = new JButton(UIManager.getIcon("OptionPane.questionIcon"));
    slowDown.setActionCommand("slow down");

    pausePlay = new JButton(UIManager.getIcon("OptionPane.errorIcon"));
    pausePlay.setActionCommand("toggle playback");

    speedUp = new JButton(UIManager.getIcon("OptionPane.warningIcon"));
    speedUp.setActionCommand("speed up");

    loopBack = new JButton("looping");
    loopBack.setActionCommand("toggle looping");

    shapeListBox = new JPanel();
    Border titledBorder = BorderFactory.createTitledBorder("Shapes");
shapeListBox.setBorder(titledBorder);

keyEditPanel = new KeyFrameEditorPanel();

    this.add(restart);
    this.add(slowDown);
    this.add(pausePlay);
    this.add(speedUp);
    this.add(loopBack);
    this.add(shapeListBox);

  }

  void setActionListener(ActionListener listener) {
    restart.addActionListener(listener);
    slowDown.addActionListener(listener);
    pausePlay.addActionListener(listener);
    speedUp.addActionListener(listener);
    loopBack.addActionListener(listener);
  }

  void setShapes(List<IReadableShape> shapes) throws IllegalArgumentException {
    if (Objects.isNull(shapes)) {
      throw new IllegalArgumentException("Cannont have null Shapes");
    }

    this.shapes = shapes;

    if (!Objects.isNull(shapeJList)) {
      shapeListBox.remove(shapeJList);
    }


    shapeJList = new JList(shapes.toArray());


    JScrollPane scrollBarAndShapeList = new JScrollPane(shapeJList,VERTICAL_SCROLLBAR_AS_NEEDED,
        HORIZONTAL_SCROLLBAR_AS_NEEDED);
    shapeListBox.add(scrollBarAndShapeList);

  }


}
