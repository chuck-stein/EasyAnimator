package cs3500.animator.adapter;

import cs3500.animator.controller.EditorListener;
import cs3500.animator.model.hw05.ShapeType;
import cs3500.animator.provider.controller.Commands;
import cs3500.animator.provider.model.Shapes;

/**
 *
 */
public class ListenerAdapter implements Commands {
 private EditorListener listener;

  /**
   * Constructs a ListeningRelay to delegate the implemented methods from Commands to the given
   * EditorListener.
   * @param listener the EditorListener to be composed for delegation.
   */
  public ListenerAdapter(EditorListener listener) {
    this.listener = listener;
  }

  @Override
  public void pause() {
    listener.togglePlayback();
  }

  @Override
  public void reset() {
    listener.restart();
  }

  @Override
  public void loop() {
    listener.toggleLooping();
  }

  @Override
  public void faster() {
    listener.speedUp();
  }

  @Override
  public void slower() {
    listener.slowDown();
  }

  @Override
  public void goTo(int time) {
    listener.setTime(time);
  }

  @Override
  public void shapeDel(String name) {
    listener.removeShape(name);
  }

  @Override
  public void frameDel(String name, int time) {
    listener.removeKeyframe(name, time);
  }

  @Override
  public void changeKeyFrame(String name, int time, int x, int y, int width, int height, int red,
      int green, int blue) {
    listener.editKeyframe(name, time, x, y, width, height, red, green, blue);
    System.out.println(name + " " + time + " " + x + " " + y+ " " + width + " " + height + " " + red + " " + green + " " + blue);
  }

  @Override
  public void createShape(String name, Shapes type) {
    ShapeType theType;
    switch (type) {
      case RECTANGLE:
        theType = ShapeType.RECTANGLE;
        break;
      case ELLIPSE:
        theType = ShapeType.ELLIPSE;
        break;
      default:
        throw new IllegalArgumentException(type + "is not a supported type.");
    }
    listener.addShape(name, theType);

  }

  @Override
  public void createKeyFrame(String name, int time) {
    listener.insertKeyframe(name, time);
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
