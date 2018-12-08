package cs3500.animator.view;

import cs3500.animator.model.hw05.IReadableShape;
import cs3500.animator.model.hw05.IState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;


/**
 * Represents a panel to display shape images on. Draws these shapes with their motions. Onto the
 * panel. Has a list of shapes to draw. And a moment in time that they are drawn at.
 */
final class ShapePanel extends JPanel {

  private List<IReadableShape> shapes;
  private int tick;
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
    tick = 1;
    this.canvasX = canvasX;
    this.canvasY = canvasY;
  }

  /**
   * Updates the moment in time of the panel. Setting the tick one forward.
   */
  void updateTick(int tick) {
    this.tick = tick;
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
        currentState = shape.getCurrentState(tick);
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

            if (shape.getName().equals("B2")) {
              AffineTransform old = g2d.getTransform();
              /*double radians = Math.toRadians(0);
              double xdif = Math.cos(radians);
              double ydif = Math.sin(radians);
              g2d.translate(xLoc + xdif,yLoc +ydif);
              g2d.rotate(radians);*/

              AffineTransform transform = new AffineTransform();
              transform.translate(old.getTranslateX(),old.getTranslateY());
              transform.rotate(Math.toRadians(180), xLoc + width/2, yLoc + height/2 -canvasY);

              System.out.println(transform.getTranslateX() + "daguq");
System.out.println(old.getTranslateX());

              g2d.setTransform(transform);

              g2d.fillRect(xLoc, yLoc,
                  width, height);
              g2d.setTransform(old);

            } else {
              g2d.fillRect(xLoc, yLoc,
                  width, height);
            }
            break;
          case ELLIPSE:
            g2d.fillOval(xLoc, yLoc,
                width, height);
            break;
          default:
            throw new IllegalStateException("Unsupported shape type used.");
        }
      }
    }


  }

  /**
   * Updates the canvas origins of this panel.
   *
   * @param canvasX the amount to transform x
   * @param canvasY the amount to transform y.
   */
  void updateCanvasOrigin(int canvasX, int canvasY) {
    this.canvasX = canvasX;
    this.canvasY = canvasY;
  }
}
