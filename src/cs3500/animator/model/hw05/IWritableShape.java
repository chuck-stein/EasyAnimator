package cs3500.animator.model.hw05;

public interface IWritableShape extends IReadableShape {

  /**
   * Adds a motion specified by the given characteristics to this shape.
   *
   * @param t1 The start time of this motion
   * @param x1 The initial x-position of the shape
   * @param y1 The initial y-position of the shape
   * @param w1 The initial width of the shape
   * @param h1 The initial height of the shape
   * @param r1 The initial red color-value of the shape
   * @param g1 The initial green color-value of the shape
   * @param b1 The initial blue color-value of the shape
   * @param t2 The end time of this motion
   * @param x2 The final x-position of the shape
   * @param y2 The final y-position of the shape
   * @param w2 The final width of the shape
   * @param h2 The final height of the shape
   * @param r2 The final red color-value of the shape
   * @param g2 The final green color-value of the shape
   * @param b2 The final blue color-value of the shape
   * @throws IllegalArgumentException if the specified motion would overlap with the shape's current
   *                                  motions, if the given start time is not before the given end
   *                                  time, if the specified shape's adjacent motions' endpoints do
   *                                  not match the specified start and end state, or if the given
   *                                  widths, heights, and ticks are not all positive
   */
  void addMotion(int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
      int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2)
      throws IllegalArgumentException;

}
