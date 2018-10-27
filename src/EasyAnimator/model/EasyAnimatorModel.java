package EasyAnimator.model;

import java.awt.Color;
import java.awt.geom.Point2D;

public class EasyAnimatorModel implements IEasyAnimatorModel {

  @Override
  public void createShape(ShapeType type, String name, Color color, Point2D position, int w, int h)
      throws IllegalArgumentException {

  }

  @Override
  public void createState(String shapeName, int dt, Color color, Point2D position, int w, int h)
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
