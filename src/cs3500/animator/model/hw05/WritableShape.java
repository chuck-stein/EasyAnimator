package cs3500.animator.model.hw05;

import java.awt.Color;
import java.util.ArrayList;

/**
 * An implementation of {@link IReadableShape} whose list of motions can be added to. INVARIANTS:
 * The shape's fields are never null, the shape's motions are always listed chronologically, all
 * motions in a shape have the same start state as the previous motion's end state, and vice versa.
 */
final class WritableShape extends ReadableShape implements IWritableShape {

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
    if (r1 < 0 || r2 < 0 || r1 > 255 || r2 > 255 || g1 < 0 || g2 < 0 || g1 > 255 || g2 > 255
            || b1 < 0 || b2 < 0 || b1 > 255 || b2 > 255) {
      throw new IllegalArgumentException("All RGB values must be within the range 0-255.");
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
      throw new IllegalArgumentException("Shape does not contain the indicated motion.");
    }
  }

  @Override
  public void removeKeyFrame(int t) throws IllegalArgumentException {
    int motionIndex = this.findEndpointMotionIndex(t);
    if (motionIndex == 0 || motionIndex == motions.size() - 1) {
      motions.remove(motionIndex);
    } else {
      IMotion start = this.motions.remove(motionIndex);
      IMotion end = this.motions.remove(motionIndex);
      int startTime = start.getStartTime();
      int endTime = end.getEndTime();
      this.motions.add(motionIndex,
              new Motion(start.getIntermediateState(startTime), end.getIntermediateState(endTime)));
    }
  }

  @Override
  public void insertKeyFrame(int t) throws IllegalArgumentException {
    if (t < 1) {
      throw new IllegalArgumentException("Tick number must be positive.");
    }

    IState blankState = new State(new Color(0, 0, 0), new Position2D(0, 0), 5, 5, t);

    // if the keyframe goes at the beginning:
    if (t > finalTick()) {
      if (motions.size() == 0) {
        this.motions.add(new Motion(blankState, blankState));
      } else {
        IMotion lastMotion = motions.get(motions.size() - 1);
        IState lastState = lastMotion.getIntermediateState(lastMotion.getEndTime());
        motions.add(new Motion(lastState, blankState));
      }
    }
    // if the keyframe goes at the end:
    else if (motions.size() > 0 && t < motions.get(0).getStartTime()) {
      IMotion firstMotion = motions.get(0);
      IState firstState = firstMotion.getIntermediateState(firstMotion.getStartTime());
      motions.add(0, new Motion(blankState, firstState));
    }
    // if the keyframe goes in the middle:
    else {
      int motionIndex;
      try {
        motionIndex = this.findEncapsulatingMotionIndex(t);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("This shape already has a keyframe at the given time.");
      }
      IMotion m = motions.remove(motionIndex);
      IState keyframe = m.getIntermediateState(t);
      motions.add(motionIndex, new Motion(keyframe, m.getIntermediateState(m.getEndTime())));
      motions.add(motionIndex, new Motion(m.getIntermediateState(m.getStartTime()), keyframe));
    }
  }

  @Override
  public void editKeyFrame(int t, int x, int y, int w, int h, int r, int g, int b)
          throws IllegalArgumentException {
    int motionIndex = this.findEndpointMotionIndex(t);
    IState stateToAdd = new State(new Color(r, g, b), new Position2D(x, y), w, h, t);
    IMotion motionStart;
    IMotion motionEnd;

    //if the frame is last
    if (motionIndex == motions.size() - 1) {
      motionEnd = motions.remove(motionIndex);
      motions.add(new Motion(motionEnd.getIntermediateState(motionEnd.getStartTime()), stateToAdd));
      return;
    }

    //if the frame is the first
    if (motionIndex == -1) {
      motionStart = motions.remove(0);
      motions.add(0, new Motion(stateToAdd, motionStart.getIntermediateState(motionStart.getEndTime())));
      return;
    }

    motionStart = this.motions.remove(motionIndex);
    motionEnd = this.motions.remove(motionIndex);

    motions.add(motionIndex, new Motion(stateToAdd, motionEnd.getIntermediateState(motionEnd.getEndTime())));
    motions.add(motionIndex, new Motion(motionStart.getIntermediateState(motionStart.getStartTime()), stateToAdd));

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
   * @param newEndT   the end time of a motion looking for its spot in the ordered list
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

  /**
   * Returns the index of the first motion which has the given time as an endpoint (start/end
   * time).
   *
   * @param time the time in ticks to check against.
   * @return the index of the motion that ends with the given time.
   * @throws IllegalArgumentException if this shape does not contain a keyframe at the given time.
   */
  private int findEndpointMotionIndex(int time) throws IllegalArgumentException {
    for (int i = 0; i < motions.size(); i++) {
      if (motions.get(i).getStartTime() == time || motions.get(i).getEndTime() == time) {
        return i;
      }
    }
    throw new IllegalArgumentException("This shape does not contain a keyframe at the given time.");
  }

  /**
   * Returns the index of the motion whose start time is before the given time and end time is after
   * the given time, or -1 if no such index is found.
   *
   * @param time the time at which an encapsulating motion index is being searched for
   * @return the index of the encapsulating motion
   * @throws IllegalArgumentException if the given time is not inside a motion, but rather at an
   *                                  endpoint (start/end time) of a motion.
   */
  private int findEncapsulatingMotionIndex(int time) throws IllegalArgumentException {
    for (int i = 0; i < motions.size(); i++) {
      int startT = motions.get(i).getStartTime();
      int endT = motions.get(i).getEndTime();
      if (startT == time || endT == time) {
        throw new IllegalArgumentException("The given time is an endpoint of a motion.");
      }
      if (time > startT && time < endT) {
        return i;
      }
    }
    return -1;
  }

}

