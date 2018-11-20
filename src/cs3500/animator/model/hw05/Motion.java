package cs3500.animator.model.hw05;

import java.awt.Color;
import java.util.Objects;

/**
 * Represents the implementation for a motion of an animated shape, from one state to another.
 * INVARIANTS: start and end states will not be null.
 */
final class Motion implements IMotion {

  private final IState start;
  private final IState end;

  /**
   * Constructs a motion from the given start state to the given end state.
   *
   * @param start the state at which this motion begins
   * @param end the state at which this motion ends
   * @throws IllegalArgumentException if the start state occurs after the end state, or they occur
   *         at the same time but the states are not the same, or the states are null
   */
  Motion(IState start, IState end) throws IllegalArgumentException {
    if (Objects.isNull(start) || Objects.isNull(end)) {
      throw new IllegalArgumentException("Motion's start and end States must be non-null.");
    }
    if (start.getTick() > end.getTick()) {
      throw new IllegalArgumentException("Start time cannot be after end time.");
    }
    if (start.getTick() == end.getTick() && !start.equals(end)) {
      throw new IllegalArgumentException("If the start and end states have the same tick number, " +
          "then they must be the same state.");
    }
    this.start = start;
    this.end = end;
  }

  @Override
  public int getStartTime() {
    return start.getTick();
  }

  @Override
  public int getEndTime() {
    return end.getTick();
  }

  @Override
  public IState getIntermediateState(int t) throws IllegalArgumentException {
    if (t < getStartTime() || t > getEndTime()) {
      throw new IllegalArgumentException("The given tick does not occur during this motion.");
    }
    int red = (int) interpolate(t, start.getColorR(), end.getColorR());
    int green = (int) interpolate(t, start.getColorG(), end.getColorG());
    int blue = (int) interpolate(t, start.getColorB(), end.getColorB());
    double x = interpolate(t, start.getPositionX(), end.getPositionX());
    double y = interpolate(t, start.getPositionY(), end.getPositionY());
    double height = interpolate(t, start.getHeight(), end.getHeight());
    double width = interpolate(t, start.getWidth(), end.getWidth());
    return new State(new Color(red, green, blue), new Position2D(x, y), width, height, t);
  }

  /**
   * Uses linear interpolation to find the value in between the given start and end values at the
   * given time in this motion.
   *
   * @param t the time at which an interpolated value should be calculated
   * @param start the starting value of the attribute being calculated
   * @param end the ending value of the attribute being calculated
   * @return the interpolated value between the given start/end value at the given time
   * @throws IllegalArgumentException if the given tick is not within this motion
   */
  private double interpolate(double t, double start, double end) throws IllegalArgumentException {
    double startT = getStartTime();
    double endT = getEndTime();
    if (t < startT || t > endT) {
      throw new IllegalArgumentException("The given tick does not occur during this motion.");
    }
    if (startT == endT) {
      return start;
    }
    return start * ((endT - t) / (endT - startT)) + end * ((t - startT) / (endT - startT));
  }

  @Override
  public boolean startEquals(IState other) {
    return start.equals(other);
  }

  @Override
  public boolean endEquals(IState other) {
    return end.equals(other);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(start.getState());
    builder.append("    ");
    builder.append(end.getState());

    return builder.toString();
  }

}
