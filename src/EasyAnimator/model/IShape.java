package EasyAnimator.model;

import java.awt.Color;

public interface IShape {

  String getName();
  void addState(Color color, Position2D position, double w, double h, int dt);
  String getAllMotions();
  String getCurrentMotion(int t);

}
