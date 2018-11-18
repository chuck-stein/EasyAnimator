package cs3500.animator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import cs3500.animator.model.hw05.IEasyAnimatorModel;
import cs3500.animator.model.hw05.ShapeType;
import cs3500.animator.view.IEasyAnimatorView;
import cs3500.animator.view.InteractiveAnimatorView;

/**
 * Represents a controller for an editable animation, passing information between the model and
 * the interactive view.
 */
public class EnhancedAnimatorController implements IEnhancedAnimatorController, EditorListener {

  private InteractiveAnimatorView view;
  private IEasyAnimatorModel model;
  private Timer timer;

  /**
   * Creates the controller to run the animation editor.
   *
   * @param view  the view that will display the model information.
   * @param model the model that contains the animations information.
   * @param speed the starting speed of the animation, in ticks per second.
   */
  public EnhancedAnimatorController(InteractiveAnimatorView view, IEasyAnimatorModel model,
                                    int speed) {
    if (Objects.isNull(view) || Objects.isNull(model)) {
      throw new IllegalArgumentException("View and Model cannot be null.");
    }
    this.view = view;
    this.model = model;
    timer = new Timer();
    TimerTask advanceTime = new TimerTask() {
      @Override
      public void run() {
        view.update();
      }
    };
    timer.schedule(advanceTime, 0, 1000 / speed);
  }

  @Override
  public void togglePlayback() {
    // pause timer schedule?
  }

  @Override
  public void restart() {

  }

  @Override
  public void toggleLooping() {

  }

  @Override
  public void slowDown() {

  }

  @Override
  public void speedUp() {

  }

  @Override
  public void addShape(String name, ShapeType type) throws IllegalArgumentException {

  }

  @Override
  public void removeShape(String name) throws IllegalArgumentException {

  }

  @Override
  public void removeKeyframe(String shapeName, int t) throws IllegalArgumentException {

  }

  @Override
  public void insertKeyframe(String shapeName, int t) throws IllegalArgumentException {

  }

  @Override
  public void editKeyframe(String shapeName, int t) throws IllegalArgumentException {

  }

  @Override
  public void go() {
    view.setShapes(model.getShapes());
    view.animate();
  }

}
