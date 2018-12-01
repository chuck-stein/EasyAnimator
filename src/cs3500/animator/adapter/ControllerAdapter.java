package cs3500.animator.adapter;

import cs3500.animator.controller.EasyAnimatorController;
import cs3500.animator.controller.EditorListener;
import cs3500.animator.controller.IEasyAnimatorController;
import cs3500.animator.model.hw05.IEasyAnimatorModel;
import cs3500.animator.model.hw05.ShapeType;
import cs3500.animator.provider.controller.Commands;
import cs3500.animator.provider.model.IEasyAnimator;
import cs3500.animator.view.IEasyAnimatorView;
import java.io.File;


public class ControllerAdapter extends EasyAnimatorController implements Commands{


  /**
   * Creates the controller to run the animation editor.
   *
   * @param view the view that will display the model information.
   * @param model the model that contains the animations information.
   * @param ticksPerSecond the starting ticksPerSecond of the animation, in ticks per second.
   */
  public ControllerAdapter(IEasyAnimatorView view,
      IEasyAnimatorModel model, int ticksPerSecond) {
    super(view, model, ticksPerSecond);
  }

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

  }

  @Override
  public void changeKeyFrame(String name, int time, int x, int y, int width, int height, int red,
      int green, int blue) {

  }

  @Override
  public void createShape(String name, String type) {

  }

  @Override
  public void createKeyFrame(String name, int time) {

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
