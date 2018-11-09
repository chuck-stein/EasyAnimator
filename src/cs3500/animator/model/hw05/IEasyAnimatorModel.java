package cs3500.animator.model.hw05;

import java.util.List;

/**
 * Represents for the model for an Easy Animator, which can create shapes and their states, and
 * output motions of shapes.
 */
public interface IEasyAnimatorModel {

  /**
   * Sets the canvas of the animation model.
   * @param x the x location of the canvas
   * @param y the y location of the canvas
   * @param w the width of the canvas
   * @param h the height of the canvas
   * @throws IllegalArgumentException if width or height are less than 1.
   */
  void setCanvas(int x, int y, int w, int h)throws  IllegalArgumentException;

  /**
   * Gets the width of the canvas.
   * @return returns the width of the canvas.
   */
  int getCanvasWidth();

  /**
   * Gets the height of the canvas.
   * @return the height of the canvas.
   */
  int getCanvasHeight();

  /**
   * Gets the x location of the origin.
   * @return the x location of the origin.
   */
  int getCanvasX();


  /**
   * Gets the y location of the origin.
   * @return the y location of the origin.
   */
  int getCanvasY();

  /**
   * Adds a shape to the model of the given type with the given name.
   *
   * @param type the type of shape being added
   * @param name the name of the shape being added
   * @throws IllegalArgumentException if a shape with the given name already exists, or the given
   *                                  type is null
   */
  void addShape(ShapeType type, String name) throws IllegalArgumentException;

  /**
   * Adds a motion specified by the given characteristics to the shape with the given name in the
   * model.
   *
   * @param shapeName The name of the shape this motion is added to
   * @param t1   The start time of this transformation
   * @param x1   The initial x-position of the shape
   * @param y1   The initial y-position of the shape
   * @param w1   The initial width of the shape
   * @param h1   The initial height of the shape
   * @param r1   The initial red color-value of the shape
   * @param g1   The initial green color-value of the shape
   * @param b1   The initial blue color-value of the shape
   * @param t2   The end time of this transformation
   * @param x2   The final x-position of the shape
   * @param y2   The final y-position of the shape
   * @param w2   The final width of the shape
   * @param h2   The final height of the shape
   * @param r2   The final red color-value of the shape
   * @param g2   The final green color-value of the shape
   * @param b2   The final blue color-value of the shape
   * @throws IllegalArgumentException if there is no shape with the given name in the model, if the
   *                                  specified motion would overlap with the shape's current
   *                                  motions, if the given start time is not before the given end
   *                                  time, if the specified shape's adjacent motions' endpoints do
   *                                  not match the specified start and end state, or if the given
   *                                  widths, heights, and ticks are not all positive
   */
  void addMotion(String shapeName,
                 int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
                 int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2)
          throws IllegalArgumentException;

  /**
   * Removes the Nth motion in time from the shape with the given name.
   *
   * @param shapeName the name of the shape whose motion is getting removed
   * @param motionNum the place where the intended motion falls in the shape's chronological
   *                  motions (e.g. first motion in time has a motionNum of 1)
   * @throws IllegalArgumentException if there is no shape with the given name in the model, or
   * the given motionNum does not refer to any of that shape's motions
   */
  void removeMotion(String shapeName, int motionNum) throws IllegalArgumentException;

  /**
   * Removes the shape with the given name from the model.
   * @param name the name of the shape to be removed
   * @throws IllegalArgumentException if there is no shape with the given name in the model
   */
  void removeShape(String name) throws IllegalArgumentException;

  /**
   * Gets this model's shapes in a read-only form.
   *
   * @return this model's shapes in a read-only form
   */
  List<IReadableShape> getShapes();

}
