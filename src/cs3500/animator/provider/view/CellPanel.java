package cs3500.animator.provider.view;

import cs3500.animator.provider.model.IEasyAnimatorViewer;
import cs3500.animator.provider.model.Posn;
import cs3500.animator.provider.model.Shapes;
import cs3500.animator.provider.model.Size;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JPanel;

/**
 * A class that extends {@link JPanel}, where {@code paintComponent} is overridden to draw
 * rectangles and ellipses as placed from the {@code IEasyAnimatorViewer} that is inside this
 * class.
 */
class CellPanel extends JPanel {

  private IEasyAnimatorViewer model;
  private int time;
  private String curChosen;

  /**
   * Constructs a default empty cell panel.
   */
  CellPanel() {
    super();
    this.setBackground(Color.WHITE);
  }

  /**
   * Sets the model to be drawn in this panel.
   *
   * @param model the model to be set
   */
  void setModel(IEasyAnimatorViewer model) {
    this.model = model;
    Rectangle r = model.getDimensions();
    this.setSize(new Dimension(r.width, r.height));
  }

  /**
   * Sets the "tick" or whatever time that's supposed to be drawn from the {@code
   * IEasyAnimatorViewer}.
   */
  void setTime(int time) {
    this.time = time;
  }


  /**
   * Sets the currently chosen shape to the one specified by the given name.
   *
   * @param name the name of the shape to be chosen
   */
  void setCurrentlyChosen(String name) {
    curChosen = name;
  }


  @Override
  protected void paintComponent(Graphics g) {

    super.paintComponent(g);

    Graphics2D g2D = (Graphics2D) g;
    Rectangle dimensions = model.getDimensions();
    // If x/y are negative, translate the coordinates so they're drawn properly
    g2D.translate(-dimensions.x, -dimensions.y);

    int curTime = this.time;

    // g2D.clipRect(dimensions.x, dimensions.y, dimensions.width, dimensions.height);

    // Draws all of the shapes by using the tweening methods built into the IEasyAnimatorViewer
    // in this class
    for (String s : model.getAllShapeNames()) {

      if (model.isVisibleAtTime(s, curTime)) {
        Shapes shapeType = model.getShapeType(s);
        g2D.setColor(model.getCurrentColor(s, curTime));
        Posn shapePosn = model.getCurrentPosn(s, curTime);
        Size shapeSize = model.getCurrentSize(s, curTime);

        if (shapeType == Shapes.RECTANGLE) {
          g2D.fillRect(shapePosn.x, shapePosn.y, shapeSize.width, shapeSize.height);
          if (curChosen != null && curChosen.equals(s)) {
            g2D.setColor(Color.MAGENTA);
            g2D.drawRect(shapePosn.x, shapePosn.y, shapeSize.width, shapeSize.height);
          }
        } else if (shapeType == Shapes.ELLIPSE) {
          g2D.fillOval(shapePosn.x, shapePosn.y, shapeSize.width, shapeSize.height);
          if (curChosen != null && curChosen.equals(s)) {
            g2D.setColor(Color.MAGENTA);
            g2D.drawOval(shapePosn.x, shapePosn.y, shapeSize.width, shapeSize.height);
          }
        }

      }
    }
  }

}
