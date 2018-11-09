package cs3500.animator.model.hw05;

import java.awt.Color;
import java.util.Objects;


/**
 * Represents all the attributes relating to a shape's appearance at a certain tick, including
 * color, position, and dimensions. INVARIANTS: -color and position are never null. -height, width,
 * and tick are always positive.
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
    state.append((int) this.getPositionX());
    state.append(" ");
    state.append((int) this.getPositionY());
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

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof State)) {
      return false;
    }
    State that = (State) other;
    boolean sameColor = this.getColorR() == that.getColorR()
            && this.getColorG() == that.getColorG()
            && this.getColorB() == that.getColorB();
    boolean samePosition = this.getPositionX() == that.getPositionX()
            && this.getPositionY() == that.getPositionY();
    boolean sameDimensions = this.getWidth() == that.getWidth()
            && this.getHeight() == that.getHeight();
    boolean sameTick = this.getTick() == that.getTick();
    return sameColor && samePosition && sameDimensions && sameTick;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getColorR(), getColorG(), getColorB(), getPositionX(), getPositionY(),
            width, height, tick);
  }

}
