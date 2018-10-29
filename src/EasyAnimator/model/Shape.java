package EasyAnimator.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

abstract class Shape {
  protected final String name;
  protected final List<State> states;

  public Shape(String name, Color color, Position2D position, double w, double h) {
    this.name = name;
    states = new ArrayList<State>();
    states.add(new State(color, position, w, h, 1));
  }

  public String getName() {
    return name;
  }

  public void addState(Color color, Position2D position, double w, double h, int dt) {
    int newT = states.get(states.size() - 1).getTick() + dt;
    states.add(new State(color, position, w, h, newT));
  }

  public String getAllMotions() {
    StringBuilder motions = new StringBuilder();
    for (int i = 0; i < states.size() - 1; i++) {
      motions.append(getMotion(i));
      if (i < states.size() - 2) {
        motions.append("\n");
      }
    }
    return motions.toString();
  }

  public String getCurrentMotion(int t) {
    StringBuilder motion = new StringBuilder();

    for (int i = 1; i < states.size() - 1; i++) {
      if (states.get(i).getTick() >= t) {
        motion = getMotion(i);
      }
    }
    return motion.toString();
  }

  private StringBuilder getMotion(int i) throws IllegalArgumentException {
    if (i > states.size() - 2) {
      throw new IllegalArgumentException("No more motions.");
    }
    StringBuilder motion = new StringBuilder();
    motion.append("motion");
    motion.append(" ");
    motion.append(name);
    motion.append(" ");
    motion.append(states.get(i).getState());
    motion.append("     ");
    motion.append(states.get(i + 1).getState());
    return motion;
  }

}
