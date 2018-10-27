package EasyAnimator.model;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class EasyAnimatorModel implements IEasyAnimatorModel {

  final List<Shape> shapes;

  public EasyAnimatorModel() {
    shapes = new ArrayList<Shape>();
  }

  @Override
  public void createShape(ShapeType type, String name, Color color, Position2D position, double w,
                          double h)
      throws IllegalArgumentException {
    if (color == null) {
      throw new IllegalArgumentException("Cannot create a shape with a null color.");
    }
    if (position == null) {
      throw new IllegalArgumentException("Cannot create a shape with a null position.");
    }
    if (w <= 0 || h <= 0) {
      throw new IllegalArgumentException("Cannot create a shape with non-positive dimensions.");
    }
    if (duplicateShapeName(name)) {
      throw new IllegalArgumentException("Shape name already exists.");
    }
    switch(type) {
      case ELLIPSE:
        shapes.add(new Ellipse(name, color, position, w, h));
      case RECTANGLE:
        shapes.add(new Rectangle(name, color, position, w, h));
      default:
        // no other possible cases since type is an enum
    }
  }

  private boolean duplicateShapeName(String name)

  @Override
  public void createState(String shapeName, int dt, Color color, Position2D position, double w,
                          double h)
      throws IllegalArgumentException {

  }

  @Override
  public String getAllMotions() {
    return null;
  }

  @Override
  public String getCurrentMotions(int t) {
    return null;
  }
}
