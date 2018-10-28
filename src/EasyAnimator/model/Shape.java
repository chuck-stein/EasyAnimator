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

  public StringBuilder getAllMotions() {
    StringBuilder motions = new StringBuilder();
    for (int i = 0; i < states.size()-2; i++) {
     motions.append(this.getMotion(i));
      }
        return motions;
  }

  public StringBuilder getCurrentMotion(int t) {
    StringBuilder motion = new StringBuilder();
    for (int i = 1; i < states.size() - 1; i++) {
      if (states.get(i).getTick() >= t) {
        motion = this.getMotion(i);
      }
    }


    return motion;
  }

  private StringBuilder getMotion(int i) throws IllegalArgumentException {
    if (i >= this.states.size()-2) {
      throw new IllegalArgumentException("No more motions.");
    }
    StringBuilder motion = new StringBuilder();
    motion.append("motion");
    motion.append(" ");
    motion.append(this.name);
    motion.append(" ");
    motion.append(states.get(i).getState());
    motion.append(" ");
    motion.append(states.get(i+1).getState());
    return motion;
  }

}
