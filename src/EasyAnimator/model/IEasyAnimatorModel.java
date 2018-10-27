package EasyAnimator.model;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * Represents for the model for an Easy Animator, which can create shapes and their states, and
 * output motions of shapes.
 */
public interface IEasyAnimatorModel {

  /**
   * Adds a Shape with the given characteristics to the animation.
   *
   * @param type     the {@link ShapeType}
   * @param name     the name of the Shape
   * @param color    the starting {@link Color} of the Shape
   * @param position the starting {@link Point2D position} of the Shape
   * @param w        the starting width of the Shape
   * @param h        the starting height of the Shape
   * @throws IllegalArgumentException if any of the given Shape characteristics are invalid:
   *                                  <p>-shape name already exists</p><p>-null color</p><p>-null
   *                                  position</p><p>-dimensions are not positive</p>
   */
  void createShape(ShapeType type, String name, Color color, Position2D position, double w, double h)
          throws IllegalArgumentException;

  /**
   * Creates a State for a Shape to end up at after the given period of time.
   *
   * @param shapeName the name of the Shape
   * @param dt        the change in time since the Shape's last State
   * @param color     the updated {@link Color} of the Shape
   * @param position  the updated {@link Point2D position} of the Shape
   * @param w         the updated width of the Shape
   * @param h         the updated height of the Shape
   * @throws IllegalArgumentException if any of the given state characteristics are invalid:
   *                                  <p>-negative delta time</p><p>-null color</p><p>-null
   *                                  position</p><p>-dimensions are not positive</p>
   */
  void createState(String shapeName, int dt, Color color, Position2D position, double w, double h)
          throws IllegalArgumentException;

  /**
   * Creates a textual representation of all motions from one State to the next for each Shape in
   * the animation.
   *
   * @return a String listing all motions from one State to the next for each Shape in the
   * animation.
   */
  String getAllMotions();

  /**
   * Creates a textual representation of all Shape motions occurring at the given time.
   *
   * @param t the time in ticks at which motions should be returned
   * @return a String listing all Shape motions occurring at the given time.
   */
  String getCurrentMotions(int t);

}
