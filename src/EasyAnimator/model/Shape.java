package EasyAnimator.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

/**
 * Represents a shape in the animation model. Shapes have a name, their identifier and a list of
 * states. A motion is how a
 *
 * Invariant: The list of states of a shape will never be empty.
 */
abstract class Shape {

  protected final String name;
  protected final List<State> states;

  /**
   * Contructs a shape.
   *
   * @param name the name of the shape
   * @param color the color of the shape
   * @param position where the shape is
   * @param w the width of the shape
   * @param h the height of the shape
   * @throws IllegalArgumentException if the inputs are null, or if the dimensions of the shape are
   * less than zero
   */
  public Shape(String name, Color color, Position2D position, double w, double h) throws
      IllegalArgumentException {
    if (Objects.isNull(color)) {
      throw new IllegalArgumentException("Shape color cannot be null.");
    }
    if (Objects.isNull(position)) {
      throw new IllegalArgumentException("Shape position must be non-null.");
    }
    if (w <= 0 || h <= 0) {
      throw new IllegalArgumentException("Shape dimensions must be positive.");
    }
    if (Objects.isNull(name)) {
      throw new IllegalArgumentException("Name of shape cannot be null.");
    }
    this.name = name;
    states = new ArrayList<State>();
    states.add(new State(color, position, w, h, 1));
  }

  /**
   * Gets the name of the shape.
   * @return the name of the shape.
   */
  public String getName() {
    return name;
  }

  /**
   * Adds a state to the list of states the shape contains.
   * @param color
   * @param position
   * @param w
   * @param h
   * @param dt
   */
  public void addState(Color color, Position2D position, double w, double h, int dt) {
    int newT = states.get(states.size() - 1).getTick() + dt;
    states.add(new State(color, position, w, h, newT));
  }

  public void addStatePars(String specifications) throws IllegalArgumentException {
    Scanner scanner = new Scanner(specifications);
    boolean hasSetDeltaT = false;
    StateBuilder builder = new StateBuilder(states.get(states.size() - 1));
    try {
      while (scanner.hasNext()) {
        switch (scanner.next()) {
          case ("-deltaT"):
            hasSetDeltaT = true;
            builder.setTick(scanner.nextInt());
            break;
          case ("-move"):
            builder.setPosition(scanner.nextDouble(), scanner.nextDouble());
            break;
          case ("-changeColor"):
            builder.setColor(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
            break;
          case ("-changeSize"):
            builder.setSize(scanner.nextDouble(), scanner.nextDouble());
            break;
          default:
            throw new IllegalArgumentException(
                "Specifications must follow the javaDoc guidelines.");
        }
      }
    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException("Specifications must follow the javaDoc guidelines.");
    }
    if (!hasSetDeltaT) {
      throw new IllegalArgumentException("DeltaT must be set");
    }
    states.add(builder.build());
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

    for (int i = 1; i <= states.size() - 1; i++) {
      if (states.get(i).getTick() >= t) {
        motion = getMotion(i - 1);
        break;
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

  private class StateBuilder {

    private Color color;
    private Position2D position;
    private double height;
    private double width;
    private int tick;

    private StateBuilder(State state) {
      this.color = state.getColor();
      this.position = state.getPosition();
      this.height = state.getHeight();
      this.width = state.getWidth();
      this.tick = state.getTick();
    }

    private State build() {
      return new State(this.color, this.position, this.height, this.width, this.tick);
    }

    private void setColor(int r, int g, int b) {
      this.color = new Color(r, g, b);
    }

    private void setPosition(double x, double y) {
      this.position = new Position2D(position.getX() + x, position.getY() + y);
    }

    private void setSize(double width, double height) {
      this.height = this.height * height;
      this.width = this.width * width;
    }

    private void setTick(int deltaT) {
      this.tick += deltaT;
    }
  }

}

