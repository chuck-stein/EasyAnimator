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
    int motionIndex = this.findKeyframeMotionIndex(t);
    if (motionIndex == -1) {
      // the following is guaranteed in-bounds because motionIndex is only -1 if size > 0:
      IMotion removed = motions.remove(0);
      if (this.isOneKeyframe(removed) && motions.size() > 0) {
<<<<<<< Updated upstream
        removed = motions.remove(0);
        motions.add(0, new Motion(removed.getIntermediateState(removed.getEndTime()),
            removed.getIntermediateState(removed.getEndTime())));
=======
        IMotion m = motions.remove(0);
        IState s = m.getIntermediateState(m.getEndTime());
        motions.add(new Motion(s, s));
>>>>>>> Stashed changes
      }
    } else if (motionIndex == motions.size() - 1) {
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
    if (motions.size() == 0) {
      IState defaultKeyframe = new State(new Color(0, 0, 0), new Position2D(0, 0), 1, 1, t);
      motions.add(new Motion(defaultKeyframe, defaultKeyframe));
    } else if (t > finalTick()) {
      insertKeyframeAtEnd(t);
    } else if (t < motions.get(0).getStartTime()) {
      insertKeyframeAtStart(t);
    } else {
      insertIntermediateKeyframe(t);
    }
  }

  /**
   * Inserts a new keyframe at the end of this shape's list of motions, at the given time in ticks.
   *
   * @param t the time at which the keyframe should be inserted
   */
  private void insertKeyframeAtEnd(int t) {
    IMotion lastMotion = motions.get(motions.size() - 1);
    if (isOneKeyframe(lastMotion)) {
      IMotion m = motions.get(0); // the one and only motion
      IState s = m.getIntermediateState(m.getEndTime()); // the one and only state
      motions.set(0, new Motion(s, copyToNewTime(s, t)));
    } else {
      // get the last existing state before adding the new keyframe:
      IState s = lastMotion.getIntermediateState(lastMotion.getEndTime());
      motions.add(new Motion(s, copyToNewTime(s, t)));
    }
  }

  /**
   * Inserts a new keyframe at the beginning of this shape's list of motions, at the given time in
   * ticks.
   *
   * @param t the time at which the keyframe should be inserted
   */
  private void insertKeyframeAtStart(int t) {
    if (isOneKeyframe(motions.get(0))) {
      IMotion m = motions.get(0); // the one and only motion
      IState s = m.getIntermediateState(m.getStartTime()); // the one and only state
      motions.set(0, new Motion(copyToNewTime(s, t), s));
    } else {
      IMotion firstMotion = motions.get(0);
      // get the first existing state before adding the new keyframe:
      IState s = firstMotion.getIntermediateState(firstMotion.getStartTime());
      motions.add(0, new Motion(copyToNewTime(s, t), s));
    }
  }

  /**
   * Inserts a new keyframe in the middle of this shape's list of motions, by interpolating the
   * start and end states of the encapsulating motion with the given time where the keyframe should
   * be inserted.
   *
   * @param t the time at which the keyframe should be inserted
   */
  private void insertIntermediateKeyframe(int t) {
    int motionIndex = this.findMotionIndexContainingNewKF(t);
    IMotion m = motions.remove(motionIndex);
    IState keyframe = m.getIntermediateState(t);
    motions.add(motionIndex, new Motion(keyframe, m.getIntermediateState(m.getEndTime())));
    motions.add(motionIndex, new Motion(m.getIntermediateState(m.getStartTime()), keyframe));
  }

  /**
   * Gets a new state identical to the given one but at the given time instead.
   *
   * @param s the state to be copied
   * @param t the time to be copied to
   * @return a new state identical to the given one but at the given time instead
   */
  private IState copyToNewTime(IState s, int t) {
    return new State(new Color(s.getColorR(), s.getColorG(), s.getColorB()),
            new Position2D(s.getPositionX(), s.getPositionY()), s.getWidth(), s.getHeight(), t);
  }

  @Override
  public void editKeyFrame(int t, int x, int y, int w, int h, int r, int g, int b)
          throws IllegalArgumentException {
    int motionIndex = this.findKeyframeMotionIndex(t);
    IState keyframe = new State(new Color(r, g, b), new Position2D(x, y), w, h, t);

    if (motionIndex == -1) {
      editFirstKeyframe(keyframe);
    } else if (isOneKeyframe(motions.get(motionIndex))) {
      replaceKeyframeMotion(motionIndex, keyframe);
    }
    //if the keyframe is last:
    else if (motionIndex == motions.size() - 1) {
      IMotion lastMotion = motions.remove(motionIndex);
      motions.add(new Motion(lastMotion.getIntermediateState(lastMotion.getStartTime()), keyframe));
    } else {
      IMotion motionStart = this.motions.remove(motionIndex);
      IMotion motionEnd = this.motions.remove(motionIndex);
      motions.add(motionIndex,
              new Motion(keyframe, motionEnd.getIntermediateState(motionEnd.getEndTime())));
      motions.add(motionIndex,
              new Motion(motionStart.getIntermediateState(motionStart.getStartTime()), keyframe));
    }
  }

  /**
   * Replaces the first keyframe of this shape with the given keyframe.
   *
   * @param keyframe the keyframe that the first keyframe will become
   */
  private void editFirstKeyframe(IState keyframe) {
    IMotion firstMotion = motions.get(0);
    if (isOneKeyframe(firstMotion)) {
      replaceKeyframeMotion(0, keyframe);
    } else {
      motions.set(0, new Motion(keyframe,
              firstMotion.getIntermediateState(firstMotion.getEndTime())));
    }
  }

  /**
   * Returns true if the given motion's start and end time are the same, which means its start and
   * end states are the same according to the way motions are constructed, which means the motion
   * really only consists of one keyframe.
   *
   * @param motion the motion to check for having only one keyframe
   * @return whether or not the motion has only one keyframe
   */
  private boolean isOneKeyframe(IMotion motion) {
    IState start = motion.getIntermediateState(motion.getStartTime());
    IState end = motion.getIntermediateState(motion.getEndTime());
    return start.getTick() == end.getTick();
  }

  /**
   * Replaces the motion at the given index with a motion that just consists of the given keyframe,
   * and adjusts the adjacent motions accordingly so their shared endpoints match up.
   *
   * @param i        the index of the motion to be replaced with the keyframe
   * @param keyframe the keyframe to replace the start and end states of the motion
   */
  private void replaceKeyframeMotion(int i, IState keyframe) {
    motions.set(i, new Motion(keyframe, keyframe));
    if (i > 0) {
      // get the previous motion's starting state:
      IState s = motions.get(i - 1).getIntermediateState(motions.get(i - 1).getStartTime());
      motions.set(i - 1, new Motion(s, keyframe));
    }
    if (i < motions.size() - 1) {
      // get the next motion's ending state:
      IState s = motions.get(i + 1).getIntermediateState(motions.get(i + 1).getEndTime());
      motions.set(i + 1, new Motion(keyframe, s));
    }
  }

  /**
   * Returns true if a motion with the given start and end times would overlap with one of this
   * shape's motions.
   *
   * @param newStartT the start time of the hypothetical motion being checked for overlaps
   * @param newEndT   the end time of the hypothetical motion being checked for overlaps
   * @return true if a motion with the given start and end times would overlap with one of this
   *         shape's motions
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
   *         should be added
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
   * Returns the index of the motion which ends at the given time, or -1 if the first motion starts
   * with that time.
   *
   * @param time the time in ticks to check for.
   * @return the index of the motion that ends with the given time, or -1 if the first motion starts
   *         with it.
   * @throws IllegalArgumentException if this shape does not contain a keyframe at the given time.
   */
  private int findKeyframeMotionIndex(int time) throws IllegalArgumentException {
    if (motions.size() > 0 && motions.get(0).getStartTime() == time) {
      return -1;
    }
    for (int i = 0; i < motions.size(); i++) {
      if (motions.get(i).getEndTime() == time) {
        return i;
      }
    }
    throw new IllegalArgumentException("This shape does not contain a keyframe at the given time.");
  }

  /**
   * Returns the index of the motion whose start time is before the given time and end time is after
   * the given time, meaning a new keyframe at the given time will divide that motion. Return -1 if
   * no such index is found.
   *
   * @param time the time at which an encapsulating motion index is being searched for
   * @return the index of the encapsulating motion
   * @throws IllegalArgumentException if the given time is not inside a motion, but rather at an
   *                                  endpoint (start/end time) of a motion, meaning no keyframe can
   *                                  be added there.
   */
  private int findMotionIndexContainingNewKF(int time) throws IllegalArgumentException {
    for (int i = 0; i < motions.size(); i++) {
      int startT = motions.get(i).getStartTime();
      int endT = motions.get(i).getEndTime();
      if (startT == time || endT == time) {
        throw new IllegalArgumentException("This shape already has a keyframe at the given time.");
      }
      if (time > startT && time < endT) {
        return i;
      }
    }
    return -1;
  }

}

