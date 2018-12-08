package cs3500.animator.model.hw05;

/**
 * Represents a shape that can be animated and modified.
 */
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
   *         motions, if the given start time is not before the given end time, if the specified
   *         shape's adjacent motions' endpoints do not match the specified start and end state, or
   *         if the given widths, heights, and ticks are not all positive
   */
  void addMotion(int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
      int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2)
      throws IllegalArgumentException;

  /**
   * Adds a motion specified by the given characteristics to this shape, including angle of
   * rotation.
   *
   * @param t1 The start time of this motion
   * @param x1 The initial x-position of the shape
   * @param y1 The initial y-position of the shape
   * @param w1 The initial width of the shape
   * @param h1 The initial height of the shape
   * @param r1 The initial red color-value of the shape
   * @param g1 The initial green color-value of the shape
   * @param b1 The initial blue color-value of the shape
   * @param a1 The initial counter-clockwise angle of rotation of the shape, in degrees
   * @param t2 The end time of this motion
   * @param x2 The final x-position of the shape
   * @param y2 The final y-position of the shape
   * @param w2 The final width of the shape
   * @param h2 The final height of the shape
   * @param r2 The final red color-value of the shape
   * @param g2 The final green color-value of the shape
   * @param b2 The final blue color-value of the shape
   * @param a2 The final counter-clockwise angle of rotation of the shape, in degrees
   * @throws IllegalArgumentException if the specified motion would overlap with the shape's current
   *         motions, if the given start time is not before the given end time, if the specified
   *         shape's adjacent motions' endpoints do not match the specified start and end state, or
   *         if the given widths, heights, and ticks are not all positive
   */
  void addRotationMotion(int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1, int a1,
                 int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2, int a2)
          throws IllegalArgumentException;

  /**
   * Removes the Nth motion in time from this shape.
   *
   * @param motionNum the place where the intended motion falls in this shape's chronological
   *        motions (e.g. first motion in time has a motionNum of 1)
   * @throws IllegalArgumentException if the given motionNum does not refer to any of this shape's
   *         motions
   */
  void removeMotion(int motionNum) throws IllegalArgumentException;

  /**
   * Removes the keyframe at the given time from this shape, by replacing the two motions adjacent
   * to it with one motion.
   *
   * @param t the time in ticks at which the keyframe which will be deleted occurs
   * @throws IllegalArgumentException if this shape does not contain a keyframe at the given time
   */
  void removeKeyFrame(int t) throws IllegalArgumentException;

  /**
   * Adds a keyframe to this shape at the given time, by replacing the motion occurring during that
   * time with two separate motions divided by the keyframe state. The keyframe is initialized to
   * whatever state the shape would already be in at this time based on the surrounding motion.
   *
   * @param t the time in ticks at which the keyframe which will be added
   * @throws IllegalArgumentException if a keyframe cannot be added at the given time
   */
  void insertKeyFrame(int t) throws IllegalArgumentException;

  /**
   * Edits this shape's keyframe at the given time to have the specified values for color, position,
   * and dimensions.
   *
   * @param t the time in ticks at which the keyframe which will be added
   * @param x the x-position of the keyframe state
   * @param y the y-position of the keyframe state
   * @param w the width of the keyframe state
   * @param h the height of the keyframe state
   * @param r the amount of red in the color of the keyframe state
   * @param g the amount of green in the color of the keyframe state
   * @param b the amount of blue in the color of the keyframe state
   * @throws IllegalArgumentException if any of the given keyframe specifications are invalid, or
   *         this shape has no keyframe at the given time
   */
  void editKeyFrame(int t, int x, int y, int w, int h, int r, int g, int b)
      throws IllegalArgumentException;

}
