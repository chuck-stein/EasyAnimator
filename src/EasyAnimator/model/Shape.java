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
    states.add(new State(color, position, w, h, 0));
  }

  public String getName() {
    return name;
  }

  public void addState(Color color, Position2D position, double w, double h, int dt) {
    int oldT = states.get(states.size() - 1).getTick();
    int newT = dt - oldT;
    states.add(new State(color, position, w, h, newT));
  }

  /**
   * Out puts the motion that starts with the state determined by i.
   *
   * @param i the index of the state that starts the motion
   * @return a string builder containing the motion.
   * @throws IllegalArgumentException if the index is too large.
   */
  private StringBuilder getMotion(int i) throws IllegalArgumentException {
    if (i >= this.states.size() - 2) {
      throw new IllegalArgumentException("No more motions.");
    }
    StringBuilder builder = new StringBuilder();
    builder.append("motion");
    builder.append(" ");
    builder.append(this.name);
    builder.append(" ");
    builder.append(states.get(i).getState().toString());
    builder.append(" ");
    builder.append(states.get(i + 1).getState().toString());
    return builder;
  }

  /**
   * Gets the motion that conatins the current time. If the given time is equal to one of the states
   * it gives the motion that gets to that state. If the shape has no motions or states at the given
   * time it returns an empty string builder to signify nothing.
   *
   * @param t the time the motion happens.
   * @return the current motion at time t.
   */
  public StringBuilder getCurrentMotion(int t) {
    StringBuilder builder = new StringBuilder();

    for (int i = 1; i < this.states.size() - 1; i++) {
      if (this.states.get(i).getTick() >= t) {
        builder = this.getMotion(i - 1);
      }
    }
    builder.append("\n");
    return builder;
  }

   /**
    * Gets all the motions this shape will make.
    * @return The string builder containing all the motions of this shape.
    */
   public StringBuilder getAllMotions() {
     StringBuilder builder = new StringBuilder();
     for (int i = 0; i < this.states.size() - 2; i++) {
       builder.append(this.getMotion(i));
       builder.append("\n");
     }

     return builder;
   }


}
