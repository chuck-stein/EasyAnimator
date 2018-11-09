package cs3500.animator.model.hw05;

import java.awt.*;
import java.util.ArrayList;

/**
 * An implementation of {@link IReadableShape} whose list of motions can be added to.
 */
class WritableShape extends ReadableShape implements IWritableShape {

  /**
   * Constructs a new WritableShape with the given type and name, and an empty list of motions.
   *
   * @param type the type of the shape being created
   * @param name the name of the shape being created
   */
  WritableShape(ShapeType type, String name) {
    super(type, name, new ArrayList<IMotion>());
  }

  @Override
  public void addMotion(int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
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

  @Override
  public void removeMotion(int motionNum) throws IllegalArgumentException {
    try {
      motions.remove(motionNum - 1);
    } catch (IndexOutOfBoundsException e) {
      throw new  IllegalArgumentException("Shape does not contain the indicated motion.");
    }
  }

  /**
   * Returns true if a motion with the given start and end times would overlap with one of this
   * shape's motions.
   *
   * @param newStartT the start time of the hypothetical motion being checked for overlaps
   * @param newEndT the end time of the hypothetical motion being checked for overlaps
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

