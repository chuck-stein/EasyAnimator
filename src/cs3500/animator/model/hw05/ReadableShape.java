package cs3500.animator.model.hw05;

import java.util.List;
import java.util.Objects;

/**
 * A general implementation of IReadableShape, with a name, type, and list of motions, as well as methods to
 * read the shape's information. INVARIANTS: The shape's fields are never null, the shape's motions
 * are always listed chronologically
 */
public class ReadableShape implements IReadableShape{

  protected final String name;
  protected final ShapeType type;
  protected final List<IMotion> motions;




  /**
   * General constructor for all shapes, to assign the fields to the given parameters.
   *
   * @param type the type of the shape being constructed
   * @param name the name of the shape being constructed
   * @param motions a list of the motions of the shape being constructed
   * @throws IllegalArgumentException if any of the parameters are null
   */
  ReadableShape(ShapeType type, String name, List<IMotion> motions) throws IllegalArgumentException {
    if (Objects.isNull(type) || Objects.isNull(name) || Objects.isNull(motions)) {
      throw new IllegalArgumentException("The shape's type, name, and motions cannot be null");
    }
    this.type = type;
    this.name = name;
    this.motions = motions;
  }

  /**
   * Constructs a read-only version of the given shape.
   * @param s the shape to be copied in read-only form
   */
  ReadableShape(IReadableShape s) {
    this(s.getType(), s.getName(), s.getMotions());
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
              "There can be no gaps in a Shapes Motions. There is a gap between time %d and %d for shape: ",
              currentTime, nextTime) + this.getName());
        }
      }
    }
    return motions;
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

}
