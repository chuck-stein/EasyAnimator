package cs3500.animator.model.hw05;

import cs3500.animator.util.AnimationBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The implementation of the model for an Easy Animator, which can create shapes and their states,
 * and output motions of shapes.
 * INVARIANT: None of the shapes in the animation will have the same name.
 */
public final class EasyAnimatorModel implements IEasyAnimatorModel {

  private final List<WritableShape> shapes;
  public static final class Builder implements AnimationBuilder<IEasyAnimatorModel> {

    @Override
    public IEasyAnimatorModel build() {
      return null;
    }

    @Override
    public AnimationBuilder<IEasyAnimatorModel> setBounds(int x, int y, int width, int height) {
      return null;
    }
    @Override
    public AnimationBuilder<IEasyAnimatorModel> declareShape(String name, String type) {
      return null;
    }

    @Override
    public AnimationBuilder<IEasyAnimatorModel> addMotion(String name, int t1, int x1, int y1,
        int w1, int h1, int r1, int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2,
        int g2, int b2) {
      return null;
    }

    @Override
    public AnimationBuilder<IEasyAnimatorModel> addKeyframe(String name, int t, int x, int y, int w,
        int h, int r, int g, int b) {
      return null;
    }


  }

  /**
   * Constructs an EasyAnimatorModel with an empty list of shapes.
   */
  public EasyAnimatorModel() {
    shapes = new ArrayList<>();
  }


  /**
   * Checks to see if there is a shape with a duplicate name.
   *
   * @param name the name to check.
   * @return whether or not a shape has the same name.
   */
  private boolean duplicateShapeName(String name) {
    try {
      findShape(name);
    } catch (IllegalArgumentException e) {
      return false;
    }
    return true;
  }

  @Override
  public String getAllMotions() {
    StringBuilder motions = new StringBuilder();
    for (int i = 0; i < shapes.size(); i++) {
      motions.append(shapes.get(i).getMotions());
      if (i < shapes.size() - 1) {
        motions.append("\n\n");
      }
    }
    return motions.toString();
  }

  @Override
  public void createShape(ShapeType type, String shapeName) {
    if (duplicateShapeName(shapeName)) {
      throw new IllegalArgumentException("Shape name already exists.");
    }
    if (Objects.isNull(type)) {
      throw new IllegalArgumentException("Shape type cannot be null.");
    }
    shapes.add(new WritableShape(type, shapeName));
  }

  @Override
  public void createMotion(String name, int t1, int x1, int y1, int w1, int h1, int r1, int g1,
                         int b1, int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2) {

    findShape(name).addMotion(t1, x1, y1, w1, h1, r1,g1, b1,
    t2, x2, y2, w2, h2, r2, g2, b2);

  }

  /**
   * Returns the shape in this model with the given name.
   *
   * @param name the name of the shape being searched for
   * @return the shape in this model with the given name
   * @throws IllegalArgumentException if there is no shape with the given name in this model
   */
  private WritableShape findShape(String name) throws IllegalArgumentException {
    for (WritableShape s : shapes) {
      if (s.getName().equals(name)) {
        return s;
      }
    }
    throw new IllegalArgumentException("There are no shapes with the given name.");
  }

}
