package cs3500.animator.model.hw05;

import java.util.List;

/**
 * Represents for the model for an Easy Animator, which can create shapes and their states, and
 * output motions of shapes.
 */
public interface IEasyAnimatorModel {


  /**
   * Sets the canvas of the animation model.
   *
   * @param x the x location of the canvas
   * @param y the y location of the canvas
   * @param w the width of the canvas
   * @param h the height of the canvas
   * @throws IllegalArgumentException if width or height are less than 1.
   */
  void setCanvas(int x, int y, int w, int h) throws IllegalArgumentException;

  /**
   * Gets the width of the canvas.
   *
   * @return returns the width of the canvas.
   */
  int getCanvasWidth();

  /**
   * Gets the height of the canvas.
   *
   * @return the height of the canvas.
   */
  int getCanvasHeight();

  /**
   * Gets the x location of the origin.
   *
   * @return the x location of the origin.
   */
  int getCanvasX();


  /**
   * Gets the y location of the origin.
   *
   * @return the y location of the origin.
   */
  int getCanvasY();

  /**
   * Adds a shape to the model of the given type with the given name, and a default layer of 0.
   *
   * @param type  the type of shape being added
   * @param name  the name of the shape being added
   * @throws IllegalArgumentException if the given type, name, or list of motions are null
   */
  void addShape(ShapeType type, String name) throws IllegalArgumentException;

  /**
   * Adds a shape to the model of the given type with the given name, at the given layer number.
   *
   * @param type  the type of shape being added
   * @param name  the name of the shape being added
   * @param layer the layer number at which this shape should be drawn, with 0 being the background
   *              and higher meaning closer to the foreground
   * @throws IllegalArgumentException if the given type, name, or list of motions are null, or the
   *                                  given layer is negative
   */
  void addShape(ShapeType type, String name, int layer) throws IllegalArgumentException;

  /**
   * Adds a motion specified by the given characteristics to the shape with the given name in the
   * model, with a default angle of rotation as 0 (not rotated).
   *
   * @param shapeName The name of the shape this motion is added to
   * @param t1        The start time of this transformation
   * @param x1        The initial x-position of the shape
   * @param y1        The initial y-position of the shape
   * @param w1        The initial width of the shape
   * @param h1        The initial height of the shape
   * @param r1        The initial red color-value of the shape
   * @param g1        The initial green color-value of the shape
   * @param b1        The initial blue color-value of the shape
   * @param t2        The end time of this transformation
   * @param x2        The final x-position of the shape
   * @param y2        The final y-position of the shape
   * @param w2        The final width of the shape
   * @param h2        The final height of the shape
   * @param r2        The final red color-value of the shape
   * @param g2        The final green color-value of the shape
   * @param b2        The final blue color-value of the shape
   * @throws IllegalArgumentException if there is no shape with the given name in the model, if the
   *                                  specified motion would overlap with the shape's current
   *                                  motions, if the given start time is not before the given end
   *                                  time, if the specified shape's adjacent motions' endpoints do
   *                                  not match the specified start and end state, if the given
   *                                  widths, heights, and ticks are not all positive, or if the
   *                                  given RGB values are not all within 0-255.
   */
  void addMotion(String shapeName,
                 int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
                 int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2)
          throws IllegalArgumentException;

  /**
   * Adds a motion specified by the given characteristics to the shape with the given name in the
   * model, including angle of rotation.
   *
   * @param shapeName The name of the shape this motion is added to
   * @param t1        The start time of this transformation
   * @param x1        The initial x-position of the shape
   * @param y1        The initial y-position of the shape
   * @param w1        The initial width of the shape
   * @param h1        The initial height of the shape
   * @param r1        The initial red color-value of the shape
   * @param g1        The initial green color-value of the shape
   * @param b1        The initial blue color-value of the shape
   * @param a1        The initial clockwise angle of rotation of the shape, in degrees.
   * @param t2        The end time of this transformation
   * @param x2        The final x-position of the shape
   * @param y2        The final y-position of the shape
   * @param w2        The final width of the shape
   * @param h2        The final height of the shape
   * @param r2        The final red color-value of the shape
   * @param g2        The final green color-value of the shape
   * @param b2        The final blue color-value of the shape
   * @param a2        The final clockwise angle of rotation of the shape, in degrees.
   * @throws IllegalArgumentException if there is no shape with the given name in the model, if the
   *                                  specified motion would overlap with the shape's current
   *                                  motions, if the given start time is not before the given end
   *                                  time, if the specified shape's adjacent motions' endpoints do
   *                                  not match the specified start and end state, if the given
   *                                  widths, heights, and ticks are not all positive, or if the
   *                                  given RGB values are not all within 0-255.
   */
  void addMotion(String shapeName,
                 int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1, int a1,
                 int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2, int a2)
          throws IllegalArgumentException;

  /**
   * Removes the Nth motion in time from the shape with the given name.
   *
   * @param shapeName the name of the shape whose motion is getting removed
   * @param motionNum the place where the intended motion falls in the shape's chronological motions
   *                  (e.g. first motion in time has a motionNum of 1)
   * @throws IllegalArgumentException if there is no shape with the given name in the model, or the
   *                                  given motionNum does not refer to any of that shape's motions
   */
  void removeMotion(String shapeName, int motionNum) throws IllegalArgumentException;

  /**
   * Removes the shape with the given name from the model.
   *
   * @param name the name of the shape to be removed
   * @throws IllegalArgumentException if there is no shape with the given name in the model
   */
  void removeShape(String name) throws IllegalArgumentException;

  /**
   * Gets this animation's shapes in a read-only form.
   *
   * @return this animation's shapes in a read-only form
   */
  List<IReadableShape> getShapes();

  /**
   * Gets the animation's list of layers of shapes in read-only form.
   * @return the animation's list of layers of shapes in read-only form
   */
  List<List<IReadableShape>> getShapeLayers();

  /**
   * Moves the layer at the given index one position closer to the back, if it is not already all
   * the way at the back.
   * @param i the index of the layer to be moved
   * @throws IllegalArgumentException if there is no layer at the given index
   */
  void moveLayerBack(int i) throws IllegalArgumentException;

  /**
   * Moves the layer at the given index one position closer to the front, if it is not already
   * all the way at the front.
   * @param i the index of the layer to be moved
   * @throws IllegalArgumentException if there is no layer at the given index
   */
  void moveLayerForward(int i) throws IllegalArgumentException;

  /**
   * Removes the keyframe at the given time of the shape with the given name, by replacing the two
   * motions adjacent to it with one motion.
   *
   * @param shapeName the name of the shape whose keyframe is being deleted
   * @param t         the time in ticks at which the keyframe which will be deleted occurs
   * @throws IllegalArgumentException if the given time and shape name do not match an existing
   *                                  keyframe
   */
  void removeKeyFrame(String shapeName, int t) throws IllegalArgumentException;

  /**
   * Adds a keyframe at the given time to the shape with the given name, by replacing the motion
   * occurring during that time with two separate motions divided by the keyframe state. The
   * keyframe is initialized to whatever state the shape would already be in at this time based on
   * the surrounding motion.
   *
   * @param shapeName the name of the shape to which a keyframe is being added
   * @param t         the time in ticks at which the keyframe which will be added
   * @throws IllegalArgumentException if the specified shape does not exist, or a keyframe cannot be
   *                                  added to it at the given time.
   */
  void insertKeyFrame(String shapeName, int t) throws IllegalArgumentException;

  /**
   * Edits the keyframe at the given time for the shape with the given name to have the specified
   * values for color, position, and dimensions.
   *
   * @param shapeName the name of the shape to which a keyframe is being added
   * @param t         the time in ticks at which the keyframe which will be added
   * @param x         the x-position of the keyframe state
   * @param y         the y-position of the keyframe state
   * @param w         the width of the keyframe state
   * @param h         the height of the keyframe state
   * @param a         the angle of clockwise rotation in degrees of the keyframe state
   * @param r         the amount of red in the color of the keyframe state
   * @param g         the amount of green in the color of the keyframe state
   * @param b         the amount of blue in the color of the keyframe state
   * @throws IllegalArgumentException if any of the given keyframe specifications are invalid, the
   *                                  specified shape does not exist, or it has no keyframe at the
   *                                  given time.
   */
  void editKeyFrame(String shapeName, int t, int x, int y, int w, int h, int a, int r, int g, int b)
          throws IllegalArgumentException;

  /**
   * Finds the tick at which the animation ends.
   *
   * @return the latest end time of any of the motions of any of the shapes in the animation
   */
  int finalAnimationTime();

  /**
   * Adds a new empty layer to the animation on top of the existing layers.
   */
  void addLayer();

  /**
   * Removes the layer at the given index, along with all the shapes it contains.
   * @param i the index of the layer to be deleted
   * @throws IllegalArgumentException if there is no layer at the given index
   */
  void removeLayer(int i) throws IllegalArgumentException;

}
