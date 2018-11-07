package cs3500.animator.model.hw05;

import java.awt.*;
import java.util.ArrayList;

/**
 * An implementation of {@link IShape} whose list of motions can be added to.
 */
class WritableShape extends AShape {

  /**
   * Constructs a new WritableShape with the given type and name, and an empty list of motions.
   *
   * @param type the type of the shape being created
   * @param name the name of the shape being created
   */
  WritableShape(ShapeType type, String name) {
    super(type, name, new ArrayList<IMotion>());
  }

  /**
   * Adds a motion specified by the given characteristics to this shape.
   *
   * @param t1 The start time of this motion
   * @param x1 The initial x-position of the shape
   * @param y1 The initial y-position of the shape
   * @param w1 The initial width of the shape
   * @param h1 The initial height of the shape
   * @param r1 The initial red color-value of the shape
   * @param g1 The initial green color-value of the shape
   * @param b1 The initial blue color-value of the shape
   * @param t2 The end time of this motion
   * @param x2 The final x-position of the shape
   * @param y2 The final y-position of the shape
   * @param w2 The final width of the shape
   * @param h2 The final height of the shape
   * @param r2 The final red color-value of the shape
   * @param g2 The final green color-value of the shape
   * @param b2 The final blue color-value of the shape
   * @throws IllegalArgumentException if the specified motion would overlap with the shape's current
   *                                  motions, if the given start time is not before the given end
   *                                  time, if the specified shape's adjacent motions' endpoints do
   *                                  not match the specified start and end state, or if the given
   *                                  widths, heights, and ticks are not all positive
   */
  void addMotion(int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
                 int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2)
          throws IllegalArgumentException {
    if (overlaps(t1, t2)) {
      throw new IllegalArgumentException("Motions cannot overlap.");
    }
    IState start = new State(new Color(r1, g1, b1), new Position2D(x1, y1), w1, h1, t1);
    IState end = new State(new Color(r2, g2, b2), new Position2D(x2, y2), w2, h2, t2);

    int i = findNewIndex(t1, t2);
    if (i > 0 && motions.get(i - 1).getEndTime() == t1 && !motions.get(i - 1).endEquals(start)) {
      throw new IllegalArgumentException("Starting state must match the adjacent previous " +
              "motion's end state, if one exists.");
    }
    if (i < motions.size() && motions.get(i).getStartTime() == t2
            && !motions.get(i).startEquals(end)) {
      throw new IllegalArgumentException("Ending state must match the adjacent next motion's " +
              "start state, if one exists.");
    }
    motions.add(i, new Motion(start, end));
  }

  /**
   * Returns true if a motion with the given start and end times would overlap with one of this
   * shape's motions.
   *
   * @param newStartT the start time of the hypothetical motion being checked for overlaps
   * @param newEndT   the end time of the hypothetical motion being checked for overlaps
   * @return true if a motion with the given start and end times would overlap with one of this
   * shape's motions
   */
  private boolean overlaps(int newStartT, int newEndT) {
    for (IMotion m : motions) {
      if (m.getStartTime() < newEndT && m.getEndTime() > newStartT) {
        return true;
      }
    }
    return false;
  }

  /**
   * Finds the index of this shape's list of motions where a new motion with the given start time
   * should be added, to maintain chronological order.
   *
   * @param newStartT the start time of a motion looking for its spot in the ordered list
   * @param newEndT the end time of a motion looking for its spot in the ordered list
   * @return the index of this shape's list of motions where a new motion with the given start time
   * should be added
   */
  private int findNewIndex(int newStartT, int newEndT) {
    for (int i = 0; i < motions.size(); i++) {
      if (newStartT <= motions.get(i).getStartTime() && newEndT <= motions.get(i).getEndTime()) {
        return i;
      }
    }
    return motions.size();
  }

}

