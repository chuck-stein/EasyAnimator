package EasyAnimator.model;

import java.awt.Color;


/**
 * Represents the state of a shape at a given tick t. It has where this shape is, what color it is
 * and its height and width.
 */
public class State {

  private final Position2D position;
  private final Color color;
  private final double height;
  private final double width;
  private final int tick;

  /**
   * Creates a state with the specified parameters.
   * @param position where the shape is.
   * @param color what color the shape is.
   * @param height what the height of the shape is.
   * @param width what the width of the shape is.
   * @param tick the tick time the shape is at this state.
   */
  public State(Position2D position, Color color, double height, double width, int tick) {

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

  public Color getColor() {
    return new Color(this.color.getRGB());
  }

  public double getWidth() {
    return this.width;
  }

  public int getTick() {
    return this.tick;
  }

  public double getHeight() {
    return this.height;
  }


  public StringBuilder getState() {

    StringBuilder builder = new StringBuilder();
    builder.append(tick);
    builder.append(" ");
    builder.append(position.getX());
    builder.append(" ");
    builder.append(position.getY());
    builder.append(" ");
    builder.append(width);
    builder.append(" ");
    builder.append(height);
    builder.append(" ");
    builder.append(color.getRed());
    builder.append(" ");
    builder.append(color.getBlue());
    builder.append(" ");
    builder.append(color.getGreen());




    return builder;
  }


}
