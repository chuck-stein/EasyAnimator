package cs3500.animator.model.hw05;

import java.util.List;

/**
 * Represents a shape that can be animated, but not necessarily modified.
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
   * Returns the layer number at which this shape should be drawn, with 0 being the background and
   * the higher the number the closer it is to the foreground.
   *
   * @return this shape's layer number
   */
  int getLayer();

  /**
   * Returns the state of this shape at the given time.
   *
   * @param t the time at which this shape's state will be returned
   * @return the state of this shape at the given time
   * @throws IllegalArgumentException if this shape has no state at the given time
   */
  IState getCurrentState(int t) throws IllegalArgumentException;

  /**
   * Finds the last tick at which this shape exists.
   *
   * @return the last tick of the last motion of this shape, or 0 if this shape has no
   * motions/ticks.
   */
  int finalTick();
}
