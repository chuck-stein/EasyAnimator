package cs3500.animator.model.hw05;

import java.awt.Color;

final class Motion implements IMotion {

  private final ShapeType type;
  private final IState start;
  private final IState end;

  Motion(ShapeType type, int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
      int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2) {
    this.type = type;
    start = new State(new Color(r1, g1, b1), new Position2D(x1, y1), w1, h1, t1);
    end = new State(new Color(r2, g2, b2), new Position2D(x2, y2), w2, h2, t2);
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

}
