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

  public String getName() {
    return name;
  }

  public void addState(Color color, Position2D position, double w, double h, int dt) {
    int oldT =  states.get(states.size()-1).getTick();
    int newT = dt - oldT;
    states.add(new State(color, position, w, h, newT));
  }

  private StringBuilder getMotion(int i) throws IllegalArgumentException {
    if (i >= this.states.size()-2) {
      throw new IllegalArgumentException("No more motions.");
    }
    StringBuilder builder = new StringBuilder();
    builder.append("motion");
    builder.append(" ");
    builder.append(this.name);
    builder.append(" ");
    builder.append(states.g)
    return builder;
  }

}
