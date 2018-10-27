package EasyAnimator.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public abstract class Shape {
  protected final String name;
  protected final List<State> states;

  public Shape(String name, Color color, Position2D position, double w, double h) {
    this.name = name;
    states = new ArrayList<State>();
    states.add(new State(color, position, w, h, 0));
  }

  String getName() {
    return name;
  }
}
