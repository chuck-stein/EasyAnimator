package cs3500.animator.model.hw05;

/**
 * Represents the methods available on a motion of an animated shape, from one state to another.
 */
public interface IMotion {

  /**
   * Returns the tick number of this motion's starting state.
   *
   * @return the tick number of this motion's starting state
   */
  int getStartTime();

  /**
   * Returns the tick number of this motion's ending state.
   *
   * @return the tick number of this motion's ending state
   */
  int getEndTime();

  /**
   * Finds the state that would be occurring during this motion at the given tick number, using
   * linear interpolation.
   *
   * @param t the tick at which an intermediate state is being searched for
   * @return the state that would be occurring during this motion at the given tick number
   * @throws IllegalArgumentException if the given tick is not within this motion
   */
  IState getIntermediateState(int t) throws IllegalArgumentException;

  /**
   * Returns whether or not this motion's starting state is equal to the given state.
   *
   * @param other the state being checked for equality
   * @return true if this motion's starting state is equal to the given state
   */
  boolean startEquals(IState other);

  /**
   * Returns whether or not this motion's ending state is equal to the given state.
   *
   * @param other the state being checked for equality
   * @return true if this motion's ending state is equal to the given state
   */
  boolean endEquals(IState other);

}
