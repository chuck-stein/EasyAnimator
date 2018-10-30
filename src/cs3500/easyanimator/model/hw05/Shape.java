package cs3500.easyanimator.model.hw05;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

/**
 * Represents a shape in the animation model. Shapes have a name (their identifier) and a list of
 * states of appearances. A motion is one state to the next.
 * Invariant: The list of states of a shape will never be empty.
 */
abstract class Shape {

  private final String name;
  private final List<State> states;

  /**
   * Constructs a shape with the given name, giving it a starting state with the given
   * characteristics.
   *
   * @param name the name of the shape
   * @param startT the time in ticks when the shape first appears
   * @param color the color of the shape
   * @param position where the shape is
   * @param w the width of the shape
   * @param h the height of the shape
   * @throws IllegalArgumentException if the inputs are null, or if the dimensions of the shape are
   *         less than zero
   */
  Shape(String name, int startT, Color color, Position2D position, double w, double h) throws
      IllegalArgumentException {
    if (Objects.isNull(color)) {
      throw new IllegalArgumentException("Shape color cannot be null.");
    }
    if (Objects.isNull(position)) {
      throw new IllegalArgumentException("Shape position must be non-null.");
    }
    if (w <= 0 || h <= 0 || startT <= 0) {
      throw new IllegalArgumentException("Shape dimensions and start time must be positive.");
    }
    if (Objects.isNull(name)) {
      throw new IllegalArgumentException("Name of shape cannot be null.");
    }
    this.name = name;
    states = new ArrayList<>();
    states.add(new State(color, position, w, h, 1));
  }

  /**
   * Gets the name of the shape.
   *
   * @return the name of the shape.
   */
  String getName() {
    return name;
  }

  /**
   * Adds a state to the list of states the shape contains.
   *
   * @param color The color of the shape at this state
   * @param position the position of the shape at this state
   * @param w the width of the shape at this state
   * @param h the height of the shape at this state
   * @param dt the time it takes to get to this state from the previous.
   * @throws IllegalArgumentException if delta t is less than 1 or if a valid state cannot be made.
   */
  void addState(Color color, Position2D position, double w, double h, int dt)
      throws IllegalArgumentException {
    if (dt <= 0) {
      throw new IllegalArgumentException("Delta T must be 1 or greater.");
    }
    int newT = states.get(states.size() - 1).getTick() + dt;
    states.add(new State(color, position, w, h, newT));
  }

  /**
   * Adds a state to the shape according to the specified commands. The duration or DeltaT is
   * required to be included in the specifications otherwise all others are optional, and order does
   * not matter. The options are as follows:
   * <p>"-deltaT #"(REQUIRED) specifies the duration of the motion that will result. </p>
   * <p>"-move # #" specifies how far the shapes moves in x y respectively.</p>
   * <p>"-changeColor # # #" specifies the new color the shape changes to by r g b
   * respectively.</p>
   * <p>"-changeSize # #" specifies the factor to multiply the width and height by
   * respectively.</p>
   *
   * @param specifications the options for creating the motion
   * @throws IllegalArgumentException if deltaT is not specified, or if there are faulty strings in
   *         the specifications
   */
  void addStatePars(String specifications) throws IllegalArgumentException {
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


  /**
   * Returns all the motions this shape has.
   *
   * @return the motions of the shape. Which is each state as a start and end of a motion.
   */
  String getAllMotions() {
    StringBuilder motions = new StringBuilder();
    if (states.size() > 1) {
      motions.append("Shape ");
      motions.append(name);
      motions.append(" ");
      motions.append(getShapeType());
      motions.append("\n");
      for (int i = 0; i < states.size() - 1; i++) {
        motions.append(getMotion(i));
        if (i < states.size() - 2) {
          motions.append("\n");
        }
      }
    }
    return motions.toString();
  }

  /**
   * Gets only the motion that contains the given tick.
   *
   * @param t the tick that specifies which motion to find
   * @return a motion in the given time.
   */
  String getCurrentMotion(int t) {
    StringBuilder motion = new StringBuilder();

    for (int i = 1; i <= states.size() - 1; i++) {
      if (states.get(i).getTick() >= t) {
        motion.append("Shape ");
        motion.append(name);
        motion.append(" ");
        motion.append(getShapeType());
        motion.append("\n");
        motion.append(getMotion(i - 1));
        break;
      }
    }
    return motion.toString();
  }

  /**
   * Creates a motion starting from the index of the specified state.
   *
   * @param i index of the starting state.
   * @return the motion starting from the index state.
   * @throws IllegalArgumentException if no motions happen after the index state given.
   */
  private StringBuilder getMotion(int i) throws IllegalArgumentException {
    if (i > states.size() - 2) {
      throw new IllegalArgumentException("No more motions.");
    }
    StringBuilder motion = new StringBuilder();
    motion.append("motion");
    motion.append(" ");
    motion.append(name);
    motion.append("   ");
    motion.append(states.get(i).getState());
    motion.append("    ");
    motion.append(states.get(i + 1).getState());
    return motion;
  }

  /**
   * Gets a String representation of which type of shape this is.
   *
   * @return a String saying which type of shape this is
   */
  protected abstract String getShapeType();

  /**
   * Represents a builder for a state.
   */
  private class StateBuilder {

    private Color color;
    private Position2D position;
    private double width;
    private double height;
    private int tick;

    /**
     * Defaults the state to the given state.
     *
     * @param state the given state to default to.
     */
    private StateBuilder(State state) {
      this.color = state.getColor();
      this.position = state.getPosition();
      this.width = state.getWidth();
      this.height = state.getHeight();
      this.tick = state.getTick();
    }

    /**
     * Builds and returns a state.
     *
     * @return the state that this builder was created to build
     */
    private State build() {
      return new State(this.color, this.position, this.width, this.height, this.tick);
    }

    /**
     * Sets the color of the state.
     *
     * @param r Red color value.
     * @param g green color value.
     * @param b blue color value.
     */
    private void setColor(int r, int g, int b) {
      this.color = new Color(r, g, b);
    }

    /**
     * Sets the location of this state.
     *
     * @param x amount to move x.
     * @param y amount to move y.
     */
    private void setPosition(double x, double y) {
      this.position = new Position2D(position.getX() + x, position.getY() + y);
    }

    /**
     * Sets the size of the state.
     *
     * @param widthMultiplier the factor to multiply the width by.
     * @param heightMultiplier the factor to multiply the height by.
     */
    private void setSize(double widthMultiplier, double heightMultiplier) {
      this.width = this.width * widthMultiplier;
      this.height = this.height * heightMultiplier;
    }

    /**
     * Sets the tick time of the state.
     *
     * @param deltaT the amount to add to the default time.
     */
    private void setTick(int deltaT) {
      this.tick += deltaT;
    }
  }

}

