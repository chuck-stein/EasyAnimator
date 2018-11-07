package cs3500.animator.view;

import cs3500.animator.model.hw05.IState;
import cs3500.animator.model.hw05.Position2D;
import cs3500.animator.model.hw05.ReadableShape;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javax.swing.JPanel;

public class ShapePanel extends JPanel {

  private List<ReadableShape> shapes;
  private Position2D minD;
  private Position2D maxD;
  private int theTick;

  public ShapePanel() {
    super();
    this.setBackground(Color.WHITE);
    minD = new Position2D(0, 0);
    maxD = new Position2D(0, 0);
    shapes = new ArrayList<>();
    theTick = 1;
  }

  public void updateTick() {
    theTick++;
  }

  public void setShapes(List<ReadableShape> shapes) {
    this.shapes = shapes;

  }

  @Override
  protected void paintComponent(Graphics g) {

    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;
    g2d.translate(0, this.getPreferredSize().getHeight());
    g2d.scale(1, -1);


    for (ReadableShape shape : shapes) {
      boolean shapeIsVisible = true;
      IState currentState = null;

      try {
        currentState = shape.getCurrentState(theTick);
      } catch (IllegalArgumentException e) {
        shapeIsVisible = false;
      }

      if(shapeIsVisible) {
        g2d.setColor(
            new Color(currentState.getColorR(), currentState.getColorG(),
                currentState.getColorB()));
        switch (shape.getType()) {
          case RECTANGLE:
            g2d.fillRect((int) currentState.getPositionX(), (int) currentState.getPositionY(),
                (int) currentState.getWidth(), (int) currentState.getHeight());
            break;
          case ELLIPSE:
            g2d.fillOval((int) currentState.getPositionX(), (int) currentState.getPositionY(),
                (int) currentState.getWidth(), (int) currentState.getHeight());
            break;

        }
      }
    }


  }

}
