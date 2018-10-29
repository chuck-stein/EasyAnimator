package cs3500.easyAnimator.model.hw05;

/**
 * Represents a position in an x-y coordinate plane.
 */
public class Position2D {

  private final double x;
  private final double y;

  /**
   * Constructs a position.
   *
   * @param x the x coordinate of the position
   * @param y the y coordinate of the position
   */
  public Position2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Creates a copy of the given position.
   *
   * @param pos a position to make a copy from.
   */
  public Position2D(Position2D pos) {
    this.x = pos.getX();
    this.y = pos.getY();
  }

  /**
   * Gets the x coordinate of the position.
   *
   * @return the x coordinate of the position.
   */
  public double getX() {
    return x;
  }

  /**
   * Gets the y coordinate of the position.
   *
   * @return the y coordinate of the position.
   */
  public double getY() {
    return y;
  }


}
