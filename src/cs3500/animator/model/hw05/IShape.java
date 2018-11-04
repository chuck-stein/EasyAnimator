package cs3500.animator.model.hw05;

import java.util.List;

public interface IShape {

  /**
   * Gets the name of the shape.
   *
   * @return the name of the shape.
   */
  String getName();

  /**
   * Gets the type of the shape.
   *
   * @return the type of the shape.
   */
  ShapeType getType();

  /**
   * Returns all the motions this shape has.
   *
   * @return a list of all of this shape's motions
   */
  List<Motion> getMotions();

  /**
   * Returns the state of the shape at the current time.
   * @param t the time that this state is at
   * @return the state the shape is at
   */
  IState getCurrentState(int t);

}
