package EasyAnimator.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of the model for an Easy Animator, which can create shapes and their states,
 * and output motions of shapes.
 */
public class EasyAnimatorModel implements IEasyAnimatorModel {

  private final List<Shape> shapes;

  /**
   * Constructs an EasyAnimatorModel with an empty list of shapes.
   */
  public EasyAnimatorModel() {
    shapes = new ArrayList<Shape>();
  }

  @Override
  public void createShape(ShapeType type, String name, int startT, Color color, Position2D position,
                          double w, double h) throws IllegalArgumentException {
    if (duplicateShapeName(name)) {
      throw new IllegalArgumentException("Shape name already exists.");
    }
    switch (type) {
      case ELLIPSE:
        shapes.add(new Ellipse(name, startT, color, position, w, h));
        break;
      case RECTANGLE:
        shapes.add(new Rectangle(name, startT, color, position, w, h));
        break;
      default:
        // no other possible cases, since type is an enum
    }
  }

  @Override
  public void createShape(ShapeType type, String name, Color color, Position2D position, double w,
                          double h) throws IllegalArgumentException {
    createShape(type, name, 1, color, position, w, h);
  }

  private boolean duplicateShapeName(String name) {
    try {
      findShape(name);
    } catch (IllegalArgumentException e) {
      return false;
    }
    return true;
  }

  @Override
  public void createState(String shapeName, int dt, Color color, Position2D position, double w,
                          double h)
          throws IllegalArgumentException {
    findShape(shapeName).addState(color, position, w, h, dt);
  }

  @Override
  public void createStatePars(String shapeName, String specifications)
          throws IllegalArgumentException {


  }

  @Override
  public String getAllMotions() {
    StringBuilder motions = new StringBuilder();
    for (int i = 0; i < shapes.size(); i++) {
      motions.append(shapes.get(i).getAllMotions());
      if (i < shapes.size() - 1) {
        motions.append("\n\n");
      }
    }
    return motions.toString();
  }

  @Override
  public String getCurrentMotions(int t) {
    StringBuilder motions = new StringBuilder();
    for (int i = 0; i < shapes.size(); i++) {
      motions.append(shapes.get(i).getCurrentMotion(t));
      if (i < shapes.size() - 1) {
        motions.append("\n");
      }
    }
    return motions.toString();
  }

  /**
   * Returns the shape in this model with the given name.
   *
   * @param name the name of the shape being searched for
   * @return the shape in this model with the given name
   * @throws IllegalArgumentException if there is no shape with the given name in this model
   */
  private Shape findShape(String name) throws IllegalArgumentException {
    for (Shape s : shapes) {
      if (s.getName().equals(name)) {
        return s;
      }
    }
    throw new IllegalArgumentException("There are no shapes with the given name.");
  }

}
