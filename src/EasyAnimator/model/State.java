package EasyAnimator.model;

import java.awt.Color;
import java.util.Objects;


/**
 * Represents the state of a shape at a given tick t. It has where this shape is, what color it is
 * and its height and width.
 */
 class State {

  private final Color color;
  private final Position2D position;
  private final double height;
  private final double width;
  private final int tick;

  /**
   * Creates a state with the specified parameters.
   *
   * @param position where the shape is.
   * @param color what color the shape is.
   * @param height what the height of the shape is.
   * @param width what the width of the shape is.
   * @param tick the tick time the shape is at this state.
   * @throws IllegalArgumentException if position or color is null, or width, height or tick are
   * less than 1.
   */
  public State(Color color, Position2D position, double width, double height, int tick)
      throws IllegalArgumentException {

    if (Objects.isNull(position) || Objects.isNull(color)) {
      throw new IllegalArgumentException("Cannot have a null position or color.");
    }
    if (height <= 0 || width <= 0 || tick <= 0) {
      throw new IllegalArgumentException("Height, width, or tick cannot be less than 1.");
    }
    this.color = color;
    this.position = position;
    this.height = height;
    this.width = width;
    this.tick = tick;
  }

  /**
   * Returns the position, in an immutable way.
   *
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


  public String getState() {

    StringBuilder state = new StringBuilder();
    state.append(tick);
    state.append(" ");
    state.append((int)position.getX());
    state.append(" ");
    state.append((int)position.getY());
    state.append(" ");
    state.append((int)width);
    state.append(" ");
    state.append((int)height);
    state.append(" ");
    state.append(color.getRed());
    state.append(" ");
    state.append(color.getGreen());
    state.append(" ");
    state.append(color.getBlue());

    return state.toString();
  }


}
