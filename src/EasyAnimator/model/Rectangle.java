package EasyAnimator.model;

import java.awt.Color;

/**
 * Represents a simple rectangle, with a name and a list of states of appearance.
 */
class Rectangle extends Shape {

  /**
   * Constructs a Rectangle with the given name, giving it a starting {@link State} with the given
   * characteristics.
   *
   * @param name the name of the Rectangle
   * @param startT the time in ticks of the Rectangle's first {@link State}
   * @param color the {@link Color} of the Rectangle's first {@link State}
   * @param position the {@link Position2D position} of the Rectangle's first {@link State}
   * @param w the width of the Rectangle's first {@link State}
   * @param h the height of the Rectangle's first {@link State}
   */
  public Rectangle(String name, int startT, Color color, Position2D position, double w, double h) {
    super(name, startT, color, position, w, h);
  }

  @Override
  protected String getShapeType() {
    return "rectangle";
  }

}
