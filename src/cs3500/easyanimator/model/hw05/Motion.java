package cs3500.easyanimator.model.hw05;

import java.awt.Color;

class Motion {

  private final ShapeType type;
  private final State start;
  private final State end;

  Motion(ShapeType type, int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
      int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2) {
    this.type = type;
    start = new State(new Color(r1, g1, b1), new Position2D(x1, y1), w1, h1, t1);
    end = new State(new Color(r2, g2, b2), new Position2D(x2, y2), w2, h2, t2);
  }

  StringBuilder getMotionAsString() {
    StringBuilder builder = new StringBuilder();
    builder.append(start.getState());
    builder.append("    ");
    builder.append(end.getState());

    return builder;
  }

  int getStartTime() {
    return start.getTick();
  }

  int getEndTime() {
    return end.getTick();
  }

  IState getIntermediateState(int t) throws IllegalArgumentException {
    if (t < start.getTick() || t > end.getTick()) {
      throw new IllegalArgumentException("The given tick does not occur during this motion.");
    }
    int red, green, blue;
    int x, y;
    int height, width;

    return new State(new Color(red, green, blue), new Position2D(x, y), height, width, t);
  }

}
