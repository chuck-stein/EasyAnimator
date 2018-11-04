package cs3500.animator.model.hw05;

import java.awt.*;
import java.util.Objects;


/**
 * Represents the state of a shape at a given tick t. It has where this shape is, what color it is
 * and its height and width. INVARIANTS: -color and position are never null. -height, width, and
 * tick are always positive.
 */
 final class State implements IState {

  private final Color color;
  private final Position2D position;
  private final double height;
  private final double width;
  private final int tick;


  /**
   * Creates a state with the specified parameters.
   *
   * @param position where the shape is.
   * @param color    what color the shape is.
   * @param height   what the height of the shape is.
   * @param width    what the width of the shape is.
   * @param tick     the tick time the shape is at this state.
   * @throws IllegalArgumentException if position or color is null, or width, height or tick are
   *                                  less than 1.
   */
  State(Color color, Position2D position, double width, double height, int tick)
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

  @Override
  public double getPositionX() {
    return position.getX();
  }

  @Override
  public double getPositionY() {
    return position.getY();
  }

  @Override
  public int getColorR() {
    return color.getRed();
  }

  @Override
  public int getColorG() {
    return color.getGreen();
  }

  @Override
  public int getColorB() {
    return color.getBlue();
  }

  @Override
  public double getWidth() {
    return this.width;
  }

  @Override
  public double getHeight() {
    return this.height;
  }

  @Override
  public int getTick() {
    return this.tick;
  }

  @Override
  public String getState() {
    StringBuilder state = new StringBuilder();

    state.append(tick);
    state.append(" ");
    state.append(this.getPositionX());
    state.append(" ");
    state.append(this.getPositionY());
    state.append(" ");
    state.append((int) width);
    state.append(" ");
    state.append((int) height);
    state.append(" ");
    state.append(this.getColorR());
    state.append(" ");
    state.append(this.getColorG());
    state.append(" ");
    state.append(this.getColorB());

    return state.toString();
  }


}
