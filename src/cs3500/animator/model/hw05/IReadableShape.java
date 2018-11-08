package cs3500.animator.model.hw05;

import java.util.List;

/**
 * Represents a shape that can be animated.
 */
public interface IReadableShape {

  /**
   * Gets the name of this shape.
   *
   * @return the name of this shape.
   */
  String getName();

  /**
   * Gets the type of this shape.
   *
   * @return the type of this shape.
   */
  ShapeType getType();

  /**
   * Gets all of this shape's motions.
   *
   * @return a list of all of this shape's motions
   */
  List<IMotion> getMotions();

  /**
   * Returns the state of this shape at the given time.
   *
   * @param t the time at which this shape's state will be returned
   * @return the state of this shape at the given time
   */
  IState getCurrentState(int t);

}
