package cs3500.animator.model.hw05;

public class ReadableShape extends AShape {

  ReadableShape(IShape s) {
    super(s.getType(), s.getName(), s.getMotions());
  }

}
