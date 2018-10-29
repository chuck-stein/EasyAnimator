package EasyAnimator.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class EasyAnimatorModel implements IEasyAnimatorModel {

  private final List<Shape> shapes;

  public EasyAnimatorModel() {
    shapes = new ArrayList<Shape>();
  }

  public EasyAnimatorModel(ArrayList<Shape> shapes) {
    this.shapes = shapes;
  }

  @Override
  public void createShape(ShapeType type, String name, Color color, Position2D position, double w,
                          double h)
      throws IllegalArgumentException {

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
      if (i < shapes.size()-1) {
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
      if (i < shapes.size()-1) {
        motions.append("\n");
      }
    }
    return motions.toString();
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
