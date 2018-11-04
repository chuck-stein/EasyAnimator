package cs3500.animator.model.hw05;

import java.awt.Color;

/**
 * Represents for the model for an Easy Animator, which can create shapes and their states, and
 * output motions of shapes.
 */
public interface IEasyAnimatorModel {

  /**
   * Adds a Shape to the model of the given type with the given name.
   * @param type the {@link ShapeType} of the Shape being added
   * @param shapeName the name of the Shape being added
   */
  void createShape(ShapeType type, String shapeName);

  /**
   * Creates a textual representation of all motions from one State to the next for each Shape in
   * the animation.
   *
   * @return a String listing all motions from one State to the next for each Shape in the
   *         animation.
   */
  String getAllMotions();

  /**
   * Adds a motion to the given shape.
   *
   * @param name The name of the shape this motion is added to
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

   */
  void createMotion(String name,
      int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
      int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2);
}
