package cs3500.animator.model.hw05;

import java.util.List;
import java.util.Objects;

/**
 * A general implementation of IReadableShape, with a name, type, and list of motions, as well as
 * methods to read the shape's information. INVARIANTS: The shape's fields are never null, the
 * shape's motions are always listed chronologically, all motions in a shape have the same start
 * state as the previous motion's end state, and vice versa.
 */
class ReadableShape implements IReadableShape {

  protected final String name;
  protected final ShapeType type;
  protected final List<IMotion> motions;
  protected int layer;

  /**
   * Constructs a readable shape of the given type, name, and motions, and a default layer of 0.
   *
   * @param type    the type of the shape being constructed
   * @param name    the name of the shape being constructed
   * @param motions a list of the motions of the shape being constructed
   * @throws IllegalArgumentException if any of the parameters are null
   */
  ReadableShape(ShapeType type, String name, List<IMotion> motions)
          throws IllegalArgumentException {
    this(type, name, motions, 0);
  }

  /**
   * Constructs a readable shape of the given type, name, motions, and layer number.
   *
   * @param type    the type of the shape being constructed
   * @param name    the name of the shape being constructed
   * @param motions a list of the motions of the shape being constructed
   * @param layer   the layer number at which this shape should be drawn, with 0 being the
   *                background and higher meaning closer to the foreground
   * @throws IllegalArgumentException if the given type, name, or list of motions are null, or the
   *                                  given layer is negative
   */
  ReadableShape(ShapeType type, String name, List<IMotion> motions, int layer)
          throws IllegalArgumentException {
    if (Objects.isNull(type) || Objects.isNull(name) || Objects.isNull(motions)) {
      throw new IllegalArgumentException("The shape's type, name, and motions cannot be null");
    }
    if (layer < 0) {
      throw new IllegalArgumentException("Layer number cannot be negative.");
    }
    this.type = type;
    this.name = name;
    this.motions = motions;
    this.layer = layer;
  }

  /**
   * Constructs a read-only version of the given shape.
   *
   * @param s the shape to be copied in read-only form
   */
  ReadableShape(IReadableShape s) {
    this(s.getType(), s.getName(), s.getMotions(), s.getLayer());
  }


  @Override
  public String getName() {
    return name;
  }

  @Override
  public ShapeType getType() {
    return type;
  }

  @Override
  public List<IMotion> getMotions() {
    int currentTime;
    int nextTime;
    for (int i = 0; i < motions.size() - 1; i++) {
      if (i < motions.size() - 1) {
        currentTime = motions.get(i).getEndTime();
        nextTime = motions.get(i + 1).getStartTime();
        if (currentTime != nextTime) {
          throw new IllegalStateException(String.format(
                  "There can be no gaps in a Shapes Motions. "
                          + "There is a gap between time %d and %d for shape: ",
                  currentTime, nextTime) + this.getName());
        }
      }
    }
    return motions;
  }

  @Override
  public int getLayer() {
    return layer;
  }

  @Override
  public IState getCurrentState(int t) throws IllegalArgumentException {
    for (IMotion m : motions) {
      if (t >= m.getStartTime() && t <= m.getEndTime()) {
        return m.getIntermediateState(t);
      }
    }
    throw new IllegalArgumentException("This shape has no state at the given time.");
  }

  @Override
  public int finalTick() {
    if (motions.size() > 0) {
      return motions.get(motions.size() - 1).getEndTime();
    }
    return 0;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof ReadableShape)) {
      return false;
    }
    ReadableShape otherShape = (ReadableShape) other;
    return name.equals(otherShape.getName()) && type.equals(otherShape.getType());
    // motions are irrelevant to shape equality in the way we use it, because shape A is still
    // shape A if you edit its motions/keyframes
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, type);
  }

  @Override
  public String toString() {
    return name;
  }

}
