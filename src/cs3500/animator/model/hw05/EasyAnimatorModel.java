package cs3500.animator.model.hw05;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The implementation of the model for an Easy Animator, which contains shapes with motions, and can
 * output readable forms of those shapes. INVARIANT: None of the shapes in the animation will have
 * the same name.
 */
public final class EasyAnimatorModel implements IEasyAnimatorModel {

  private final List<WritableShape> shapes;
  private  int canvasWidth;
  private int canvasHeight;
  private int canvasX;
  private int canvasY;

  /**
   * Constructs an EasyAnimatorModel with an empty list of shapes.
   */
  public EasyAnimatorModel() {
    shapes = new ArrayList<WritableShape>();
    canvasWidth = 500; // default width
    canvasHeight = 500; // default height
    canvasX = 200;
    canvasY = 200;
  }

  @Override
  public void setCanvas(int x, int y, int w, int h) {
    canvasX = x;
    canvasY = y;
    canvasWidth = w;
    canvasHeight = h;
  }

  @Override
  public int getCanvasWidth() {
    return canvasWidth;
  }

  @Override
  public int getCanvasHeight() {
    return canvasHeight;
  }

  @Override
  public int getCanvasX() {
    return getCanvasX();
  }

  @Override
  public int getCanvasY() {
    return getCanvasY();
  }

  @Override
  public void addShape(ShapeType type, String shapeName) throws IllegalArgumentException {
    if (duplicateShapeName(shapeName)) {
      throw new IllegalArgumentException("Shape name already exists.");
    }
    if (Objects.isNull(type)) {
      throw new IllegalArgumentException("Shape type cannot be null.");
    }
    shapes.add(new WritableShape(type, shapeName));
  }

  /**
   * Checks to see if this model already has a shape with the given name.
   *
   * @param name the name to check for prior existence
   * @return whether or not there is already a shape with the given name
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
  public void addMotion(String name, int t1, int x1, int y1, int w1, int h1, int r1, int g1,
                        int b1, int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2)
          throws IllegalArgumentException {
    findShape(name).addMotion(t1, x1, y1, w1, h1, r1, g1, b1,
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

  @Override
  public List<ReadableShape> getShapes() {
    List<ReadableShape> readableShapes = new ArrayList<ReadableShape>();
    for (WritableShape s : shapes) {
      readableShapes.add(new ReadableShape(s));
    }
    return readableShapes;
  }

}
