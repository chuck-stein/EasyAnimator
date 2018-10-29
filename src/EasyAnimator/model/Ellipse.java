package EasyAnimator.model;

import java.awt.Color;

/**
 * Represents a simple ellipse, with a name and a list of states of appearance.
 */
class Ellipse extends Shape {

  /**
   * Constructs an Ellipse with the given name, giving it a starting {@link State} with the given
   * characteristics.
   *
   * @param name the name of the Ellipse
   * @param startT the time in ticks of the Ellipse's first {@link State}
   * @param color the {@link Color} of the Ellipse's first {@link State}
   * @param position the {@link Position2D position} of the Ellipse's first {@link State}
   * @param w the width of the Ellipse's first {@link State}
   * @param h the height of the Ellipse's first {@link State}
   */
  public Ellipse(String name, int startT, Color color, Position2D position, double w, double h) {
    super(name, startT, color, position, w, h);
  }

  @Override
  protected String getShapeType() {
    return "ellipse";
  }

}
