package cs3500.animator.model.hw05;

/**
 * A read-only implementation of {@link IShape}.
 */
final public class ReadableShape extends AShape {

  /**
   * Constructs a read-only version of the given shape.
   * @param s the shape to be copied in read-only form
   */
  ReadableShape(IShape s) {
    super(s.getType(), s.getName(), s.getMotions());
  }

}
