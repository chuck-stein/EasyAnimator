package EasyAnimator.model;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * Represents the state of a shape at a given tick t. It has where this shape is, what color it is
 * and its height and width.
 */
public class State {

  private final Position2D position;
  private final Color color;
  private final int height;
  private final int width;
  private final int tick;

  /**
   * Creates a state with the specified parameters.
   * @param position where the shape is.
   * @param color what color the shape is.
   * @param height what the height of the shape is.
   * @param width what the width of the shape is.
   * @param tick the tick time the shape is at this state.
   */
  public State(Position2D position, Color color, int height, int width, int tick) {

    this.position = position;
    this.color = color;
    this.height = height;
    this.width = width;
    this.tick = tick;
  }

  /**
   * Returns the position, in an immutable way.
   * @return the position of the state.
   */
  public Position2D getPosition() {
    return new Position2D(this.position);
      }
}
