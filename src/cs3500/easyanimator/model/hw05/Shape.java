package cs3500.easyanimator.model.hw05;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

/**
 * Represents a shape in the animation model. Shapes have a name (their identifier) and a list of
 * motions. INVARIANTS: - The shape's name will never be null.
 */
abstract class Shape {

  protected final String name;
  protected final List<Motion> motions;

  Shape(String name) {
    if (Objects.isNull(name)) {
      throw new IllegalArgumentException("Name cannot be null");
    }
    this.name = name;
    motions = new ArrayList<Motion>();
  }

  /**
   * Gets the name of the shape.
   *
   * @return the name of the shape.
   */
  String getName() {
    return name;
  }


  void addMotion(int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
                 int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2)
          throws IllegalArgumentException {
    //Motion m = new Motion(t1, x1, y1, w1, h1, r1, g1, b1, t2, x2, y2, w2, h2, r2, g2, b2);
    if (overlaps(t1, t2)) {
      throw new IllegalArgumentException("Motions cannot overlap.");
    }
    int i = findNewIndex(t1, t2);
    motions.add(i, new Motion(t1, x1, y1, w1, h1, r1, g1, b1, t2, x2, y2, w2, h2, r2, g2, b2));
  }

  boolean overlaps(int newStartT, int newEndT) {
    for (Motion m : motions) {
      if (m.getStartTime() < newEndT && m.getEndTime() > newStartT) {
        return true;
      }
    }
    return false;
  }

  int findNewIndex(int newStartT, int newEndT) {

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
  /*void addStatePars(String specifications) throws IllegalArgumentException {
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
  }*/


  /**
   * Returns all the motions this shape has.
   *
   * @return the motions of the shape. Which is each state as a start and end of a motion.
   */
  String getAllMotions() throws IllegalStateException {
    StringBuilder motionsForOutput = new StringBuilder();
    if (motions.size() > 0) {
      motionsForOutput.append("Shape ");
      motionsForOutput.append(name);
      motionsForOutput.append(" ");
      motionsForOutput.append(getShapeType());
      motionsForOutput.append("\n");

      int currentTime;
      int nextTime;
      for (int i = 0; i < motions.size() - 1; i++) {
        if (i < motions.size() - 2) {
          currentTime = motions.get(i).getEndTime();
          nextTime = motions.get(i + 1).getStartTime();
          if (currentTime != nextTime) {
            throw new IllegalStateException(String.format(
                "There can be no gaps in a Shapes Motions. There is a gap between time %d and %d.",
                currentTime, nextTime));
          }
        }
        motionsForOutput.append(getMotion(i));
        if (i < motions.size() - 1) {
          motionsForOutput.append("\n");
        }
      }
    }
    return motionsForOutput.toString();
  }


  /**
   * Creates a motion starting from the index of the specified state.
   *
   * @param i index of the starting state.
   * @return the motion starting from the index state.
   * @throws IllegalArgumentException if no motions happen after the index state given.
   */
  private StringBuilder getMotion(int i) throws IllegalArgumentException {
    if (i > motions.size() - 1) {
      throw new IllegalArgumentException("No more motions.");
    }
    StringBuilder motion = new StringBuilder();
    motion.append("motion");
    motion.append(" ");
    motion.append(name);
    motion.append("   ");
    motion.append(motions.get(i).getMotionasString());

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

