package cs3500.animator.provider.model;

import java.util.Objects;

/**
 * Represents the shape's width and height.
 */
public final class Size {

  /**
   * Represents the width and height for {@code this} Size.
   */
  public final int width;
  public final int height;

  /**
   * Creates a size with the given width and height.
   *
   * @param width  the width of the size wanted
   * @param height the height of the size wanted
   */
  public Size(int width, int height) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("One of the size fields is not positive");
    }
    this.width = width;
    this.height = height;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (!(o instanceof Size)) {
      return false;
    } else {
      Size s = (Size) o;
      return this.width == s.width && this.height == s.height;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.width, this.height);
  }

  @Override
  public String toString() {
    return width + " " + height;
  }

}
