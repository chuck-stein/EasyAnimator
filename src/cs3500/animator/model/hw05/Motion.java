package cs3500.animator.model.hw05;

import java.awt.Color;

final class Motion implements IMotion {

  private final IState start;
  private final IState end;

  Motion(IState start, IState end) throws IllegalArgumentException {
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

  public int getStartTime() {
    return start.getTick();
  }

  public int getEndTime() {
    return end.getTick();
  }

  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(start.getState());
    builder.append("    ");
    builder.append(end.getState());

    return builder.toString();
  }

  public IState getIntermediateState(int t) throws IllegalArgumentException {
    if (t < start.getTick() || t > end.getTick()) {
      throw new IllegalArgumentException("The given tick does not occur during this motion.");
    }
    int red = (int)interpolate(t, start.getColorR(), end.getColorR());
    int green = (int)interpolate(t, start.getColorG(), end.getColorG());
    int blue = (int)interpolate(t, start.getColorB(), end.getColorB());
    double x = interpolate(t, start.getPositionX(), end.getPositionX());
    double y = interpolate(t, start.getPositionY(), end.getPositionY());
    double height = interpolate(t, start.getHeight(), end.getHeight());
    double width = interpolate(t, start.getWidth(), end.getWidth());
    return new State(new Color(red, green, blue), new Position2D(x, y), height, width, t);
  }

  private double interpolate(double t, double start, double end) {
    double startT = getStartTime();
    double endT = getEndTime();
    return start * ((endT - t)/(endT - startT)) + end * ((t - startT)/(endT-startT));
  }

  public boolean startEquals(IState other) {
    return start.equals(other);
  }

  public boolean endEquals(IState other) {
    return end.equals(other);
  }

}
