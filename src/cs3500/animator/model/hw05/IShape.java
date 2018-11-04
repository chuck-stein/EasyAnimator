package cs3500.animator.model.hw05;

public interface IShape {


  /**
   * Gets the name of the shape.
   *
   * @return the name of the shape.
   */
  String getName();

  /**
   * Returns all the motions this shape has.
   *
   * @return the motions of the shape. Which is each state as a start and end of a motion.
   */
  String getAllMotions();

  /**
   * Returns the state of the shape at the current time.
   * @param t the time that this state is at
   * @return the state the shape is at
   */
  IState getCurrentState(int t);

}
