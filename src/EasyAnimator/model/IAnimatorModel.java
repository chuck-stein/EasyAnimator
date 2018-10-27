package EasyAnimator.model;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * Represents for the model for an Easy Animator, which can create shapes and their states, and
 * output motions of shapes.
 */
public interface IAnimatorModel {

  /**
   * Adds a Shape with the given characteristics to the model.
   * @param type the {@link ShapeType}
   * @param name the name of the Shape
   * @param color the starting {@link Color} of the Shape
   * @param position the starting {@link Point2D position} of the Shape
   * @param w the starting width of the Shape
   * @param h the starting height of the Shape
   * @throws IllegalArgumentException if any of the given Shape characteristics are invalid:
   * <p>-shape name already exists</p><p>-null color</p><p>-null position</p><p>-negative
   * dimensions</p>
   */
  void createShape(ShapeType type, String name, Color color, Point2D position, int w, int h)
          throws IllegalArgumentException;

  /**
   * Creates a State for a Shape to end up at after the given period of time.
   * @param shapeName the name of the Shape
   * @param dt the change in time since the Shape's last State
   * @param color the new {@link Color} of the Shape
   * @param position
   * @param w
   * @param h
   * @throws IllegalArgumentException if any of the given state characteristics are invalid:
   * <p>-negative delta time</p><p>-null color</p><p>-null position</p><p>-negative dimensions</p>
   */
  void createState(String shapeName, int dt, Color color, Point2D position, int w, int h)
          throws IllegalArgumentException;

  String getAllMotions();

  String getCurrentMotions(int t);

}
