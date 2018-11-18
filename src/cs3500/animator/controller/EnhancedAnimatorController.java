package cs3500.animator.controller;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import cs3500.animator.model.hw05.IEasyAnimatorModel;
import cs3500.animator.model.hw05.ShapeType;
import cs3500.animator.view.InteractiveAnimatorView;

/**
 * Represents a controller for an editable animation, passing information between the model and
 * the interactive view.
 */
public class EnhancedAnimatorController implements IEnhancedAnimatorController, EditorListener {

  private InteractiveAnimatorView view;
  private IEasyAnimatorModel model;
  private Timer timer;
 private TimerTask advanceTime;
  private  int theTick;
  private  int finalTick;
  private  int speed;
  private boolean paused;
  private boolean looping;

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
    view.setListener(this);
    this.model = model;
    this.theTick = 0;
    timer = new Timer();
    this.speed = speed;
    this.advanceTime = new TimerTask() {
      @Override
      public void run() {
        theTick++;
      }
    };
    this.finalTick = model.finalAnimationTIme();

  }

  @Override
  public void go() {
    view.setShapes(model.getShapes());
    timer.schedule(advanceTime, 0, 1000 / speed);
    while (true) {
      if (theTick >= finalTick && looping){
        theTick = 0;
      }

    view.setTime(theTick);
    view.animate();
  }}

  @Override
  public void togglePlayback() {
    if (paused) {
      timer = new Timer();
      this.advanceTime = new TimerTask() {
        @Override
        public void run() {
          theTick++;
        }
      };
      timer.schedule(advanceTime, 0, 1000 / speed);
      paused = false;
    } else {

      timer.cancel();
      timer.purge();

      paused = true;
    }
  }

  @Override
  public void restart() {
theTick = 0;
  }

  @Override
  public void toggleLooping() {
looping = !looping;
  }

  @Override
  public void slowDown() {
    if (speed > 5) {
      this.speed = speed - 5;

      if(!paused) {
        this.restartTimer();
      }
    }
  }

  @Override
  public void speedUp() {
    this.speed = speed + 5;

    if(!paused) {
      this.restartTimer();
    }
  }

  @Override
  public void addShape(String name, ShapeType type) throws IllegalArgumentException {
    model.addShape(type, name);
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

  private void restartTimer() {
    timer.cancel();
    timer.purge();

    timer = new Timer();
    this.advanceTime = new TimerTask() {
      @Override
      public void run() {
        theTick++;
      }
    };
    timer.schedule(advanceTime, 0, 1000 / speed);
  }


}
