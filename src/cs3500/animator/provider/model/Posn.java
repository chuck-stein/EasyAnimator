package cs3500.animator.model;

import java.util.Objects;

/**
 * Represents a position in a 2-d cartesian plane.
 */
public final class Posn {


  /**
   * Represents the X and Y coordinates for {@code this} Posn.
   */
  public final int x;
  public final int y;

  /**
   * Initializes a {@code posn} at x and y.
   *
   * @param x represents the value of the x coordinate
   * @param y represents the value of the y coordinate
   */
  public Posn(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (!(o instanceof Posn)) {
      return false;
    } else {
      Posn p = (Posn) o;
      return this.x == p.x && this.y == p.y;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }

  @Override
  public String toString() {
    return x + " " + y;
  }


}
