package cs3500.animator.adapter;

import cs3500.animator.model.hw05.ShapeType;
import cs3500.animator.provider.controller.Commands;

public class ListerningRelay implements Commands {


  @Override
  public void pause() {
    this.togglePlayback();
  }

  @Override
  public void reset() {
    this.restart();
  }

  @Override
  public void loop() {
    this.toggleLooping();
  }

  @Override
  public void faster() {
    this.speedUp();
  }

  @Override
  public void slower() {
    this.slowDown();
  }

  @Override
  public void goTo(int time) {
    this.tick = time;
  }

  @Override
  public void shapeDel(String name) {
    this.removeShape(name);
  }

  @Override
  public void frameDel(String name, int time) {
    this.removeKeyframe(name, time);
  }

  @Override
  public void changeKeyFrame(String name, int time, int x, int y, int width, int height, int red,
      int green, int blue) {
    this.editKeyframe(name, time, x, y, width, height, red, green, blue);

  }

  @Override
  public void createShape(String name, String type) {
    ShapeType theType;
    switch (type) {
      case "RECTANGLE":
        theType = ShapeType.RECTANGLE;
        break;
      case "ELLIPSE":
        theType = ShapeType.ELLIPSE;
        break;
      default:
        throw new IllegalArgumentException(type + "is not a supported type.");
    }
    this.addShape(name, theType);

  }

  @Override
  public void createKeyFrame(String name, int time) {
    this.insertKeyframe(name, time);
  }

  @Override
  public void save(String name, String type) {

  }

  @Override
  public void load(String name) {

  }

  @Override
  public void startProgram() {

  }
}
