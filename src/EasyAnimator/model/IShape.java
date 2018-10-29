package EasyAnimator.model;

import java.awt.Color;

public interface IShape {

  String getName();
  void addState(Color color, Position2D position, double w, double h, int dt);
  void addStatePars(String specifications)throws IllegalArgumentException;
  String getAllMotions();
  String getCurrentMotion(int t);

}
