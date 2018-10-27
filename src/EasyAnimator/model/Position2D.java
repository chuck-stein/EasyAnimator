package EasyAnimator.model;

/**
 * Represents a position in a coordiante plane.
 */
public class Position2D {

  final double x;
  final double y;

  /**
   * Constructs a position
   * @param x
   * @param y
   */
  public Position2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public Position2D(Position2D pos) {
    this.x = pos.getX();
    this.y = pos.getY();
  }

    public double getX() {
    return x;
  }


  public double getY() {
    return y;
  }


}
