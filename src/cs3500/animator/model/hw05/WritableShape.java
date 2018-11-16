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
  public void addKeyFrame(int t) {
    int motionIndex = this.whichMotionEncapsulatesFrame(t);
    IState blankState = new State(new Color(0,0,0),new Position2D(0,0),1,1,t);

    //if this key frame goes at the end of the motion list, if its empty or not. Then break out.
    if (motionIndex == motions.size()) {

      if (motions.size() == 0) {
        this.motions.add(new Motion(blankState, blankState));
      } else{
        IMotion start = motions.get(motionIndex-1);
        motions.add(new Motion(start.getIntermediateState(start.getEndTime()),blankState));
      }
      return;
    }

    //if key frame goes at the front of the list.
    if (motionIndex == -1) {
      IMotion end = motions.get(0);
      motions.add(0,new Motion(blankState,end.getIntermediateState(end.getStartTime())));
      return;
    }

    IMotion middle = motions.remove(motionIndex);
    motions.add(motionIndex,new Motion(middle.getIntermediateState(t), middle.getIntermediateState(middle.getEndTime())));
    motions.add(motionIndex, new Motion(middle.getIntermediateState(middle.getStartTime()), middle.getIntermediateState(t)));


  }

  @Override
  public void editKeyFrame(int t, int x, int y, int w, int h, int r, int g, int b) {
    int motionIndex = this.findKeyFrameIndex(t);
    IState stateToAdd = new State(new Color(r, g, b), new Position2D(x, y), w, h, t);
  IMotion motionStart;
  IMotion motionEnd;

  //if the frame is last
  if (motionIndex == motions.size()-1){
   motionEnd = motions.remove(motionIndex);
   motions.add(new Motion(motionEnd.getIntermediateState(motionEnd.getStartTime()), stateToAdd));
   return;
  }

    //if the frame is the first
    if (motionIndex == -1) {
      motionStart = motions.remove(0);
     motions.add(0, new Motion(stateToAdd,motionStart.getIntermediateState(motionStart.getEndTime())));
      return;
    }

    motionStart = this.motions.remove(motionIndex);
    motionEnd = this.motions.remove(motionIndex);

    motions.add(motionIndex,new Motion(stateToAdd, motionEnd.getIntermediateState(motionEnd.getEndTime())));
    motions.add(motionIndex,new Motion(motionStart.getIntermediateState(motionStart.getStartTime()), stateToAdd));

  }

  @Override
  public void removeKeyFrame(int t) throws IllegalArgumentException {
    int motionIndex = this.findKeyFrameIndex(t);

    //if the frame is the last remove it and stop as nothing needs to be added back in.
    if (motionIndex == motions.size()-1) {
      motions.remove(motionIndex);
      return;
    }

    //if the frame is the first remove the motion and stop
    if (motionIndex == -1) {
      motions.remove(0);
      return;
    }

    IMotion start = this.motions.remove(motionIndex);
    int startTime = start.getStartTime();
    IMotion end = this.motions.remove(motionIndex);
    int endTime = end.getEndTime();

    this.motions.add(motionIndex,
        new Motion(start.getIntermediateState(startTime), end.getIntermediateState(endTime)));


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

  /**
   * Returns the index of the motion that ends with this time.
   *
   * @param time the time to check against.
   * @return the index of the motion that ends with the given time.
   */
  private int findKeyFrameIndex(int time) throws IllegalArgumentException {
    if (motions.size() > 0) {
      if (motions.get(0).getStartTime() == time) {
        return -1;
      }
    }

    for (int i = 0; i < motions.size(); i++) {
      if (motions.get(i).getEndTime() == time) {
        return i;
      }
    }
    throw new IllegalArgumentException("This shape does not contain this keyFrame.");
  }

  private int whichMotionEncapsulatesFrame(int time) throws IllegalArgumentException {

    if (motions.size() > 0) {
      if (motions.get(0).getStartTime() > time) {
        return -1;
      }
    }

    for (int i = 0; i < motions.size(); i++) {
      if (motions.get(i).getEndTime() > time) {
        return i;
      }
    }
    return motions.size();
  }

}

