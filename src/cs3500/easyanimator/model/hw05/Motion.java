package cs3500.easyanimator.model.hw05;

import java.awt.Color;

public class Motion {

  private State start;
  private State end;

  public Motion(int t1, int  x1, int y1, int w1, int h1, int r1, int g1, int b1,
                int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2) {
    start = new State(new Color(r1, g1, b1), new Position2D(x1, y1), w1, h1, t1);
    end = new State(new Color(r2, g2, b2), new Position2D(x2, y2), w2, h2, t2);
  }

}