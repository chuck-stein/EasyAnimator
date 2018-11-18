package cs3500.animator.controller;

import cs3500.animator.model.hw05.ShapeType;

public interface EditorListener {

  void togglePlayback();

  void restart();

  void toggleLooping();

  void slowDown();

  void speedUp();

  void addShape(String name, ShapeType type) throws IllegalArgumentException;

  void removeShape(String name) throws IllegalArgumentException;

  void removeKeyframe(String shapeName, int t) throws IllegalArgumentException;

  void insertKeyframe(String shapeName, int t) throws IllegalArgumentException;

  void editKeyframe(String shapeName, int t) throws IllegalArgumentException;

}
