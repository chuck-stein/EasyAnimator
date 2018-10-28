package EasyAnimator.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class EasyAnimatorModel implements IEasyAnimatorModel {

  private final List<Shape> shapes;

  public EasyAnimatorModel() {
    shapes = new ArrayList<Shape>();
  }

  @Override
  public void createShape(ShapeType type, String name, Color color, Position2D position, double w,
      double h)
      throws IllegalArgumentException {
    if (color == null) {
      throw new IllegalArgumentException("Shape color cannot be null.");
    }
    if (position == null) {
      throw new IllegalArgumentException("Shape position must be non-null.");
    }
    if (w <= 0 || h <= 0) {
      throw new IllegalArgumentException("Shape dimensions must be positive.");
    }
    if (duplicateShapeName(name)) {
      throw new IllegalArgumentException("Shape name already exists.");
    }
    switch (type) {
      case ELLIPSE:
        shapes.add(new Ellipse(name, color, position, w, h));
      case RECTANGLE:
        shapes.add(new Rectangle(name, color, position, w, h));
      default:
        // no other possible cases since type is an enum
    }
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
  public String getAllMotions() {
    StringBuilder builder = new StringBuilder();
    for (Shape sh : shapes) {
      builder.append(sh.getAllMotions());
      builder.append("\n");
      builder.append("\n");
    }

    return builder.toString();
  }

  @Override
  public String getCurrentMotions(int t) {
    StringBuilder builder = new StringBuilder();
    for (Shape sh : shapes) {
      builder.append(sh.getCurrentMotion(t));
      builder.append("\n");
      builder.append("\n");
    }

    return builder.toString();
  }


  private Shape findShape(String name) throws IllegalArgumentException {
    for (Shape s : shapes) {
      if (s.getName().equals(name)) {
        return s;
      }
    }
    throw new IllegalArgumentException("There are no shapes with the given name.");
  }

}
