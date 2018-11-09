package cs3500.animator.view;

import cs3500.animator.model.hw05.IReadableShape;
import cs3500.animator.model.hw05.IState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;


/**
 * Represents a panel to display shape images on. Draws these shapes with their motions. Onto the
 * panel. Has a list of shapes to draw. And a moment in time that they are drawn at.
 */
final class ShapePanel extends JPanel {

  private List<IReadableShape> shapes;

  private int theTick;
  private int canvasX;
  private int canvasY;

  /**
   * Constructs the shape panel.
   *
   * @param canvasX how far to shift the origin in the x direction.
   * @param canvasY how far to shift the origin in the y direction.
   */
  ShapePanel(int canvasX, int canvasY) {
    super();
    this.setBackground(Color.WHITE);
    shapes = new ArrayList<>();
    theTick = 1;
    this.canvasX = canvasX;
    this.canvasY = canvasY;
  }

  /**
   * Updates the moment in time of the panel. Setting the tick one forward.
   */
  void updateTick() {
    theTick++;
  }

  /**
   * Sets the list of shapes to be drawn by this panel.
   */
  public void setShapes(List<IReadableShape> shapes) {
    this.shapes = shapes;

  }

  @Override
  protected void paintComponent(Graphics g) {

    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    //moves the origin
    g2d.translate(canvasX, canvasY);

    //draws all the shapes
    for (IReadableShape shape : shapes) {
      boolean shapeIsVisible = true;
      IState currentState = null;

      try {
        currentState = shape.getCurrentState(theTick);
      } catch (IllegalArgumentException e) {
        shapeIsVisible = false;
      }

      if (shapeIsVisible) {
        g2d.setColor(
            new Color(currentState.getColorR(), currentState.getColorG(),
                currentState.getColorB()));

        int xLoc = (int) Math.round(currentState.getPositionX());
        int yLoc = (int) Math.round(currentState.getPositionY());
        int width = (int) Math.round(currentState.getWidth());
        int height = (int) Math.round(currentState.getHeight());

        switch (shape.getType()) {
          case RECTANGLE:
            g2d.fillRect(xLoc, yLoc,
                width, height);
            break;
          case ELLIPSE:
            g2d.fillOval(xLoc, yLoc,
                width, height);
            break;
            default:
              throw new IllegalStateException("Unsupported shapeType used");

        }
      }
    }


  }

}
