package cs3500.animator.controller;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

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
  private  int tick;
  private  int finalTick;
  private  int speed;
  private boolean paused;
  private boolean looping;
  private boolean modelChanged;

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
    this.tick = 0;
    this.timer = new Timer();
    this.speed = speed;
    this.advanceTime = new TimerTask() {
      @Override
      public void run() {
        tick++;
      }
    };
    this.finalTick = model.finalAnimationTime();
    this.modelChanged = true;

  }

  @Override
  public void go() {

    timer.schedule(advanceTime, 0, 1000 / speed);
    while (true) {
      if (modelChanged) {
        view.setShapes(model.getShapes());
        modelChanged = false;
      }
      if (tick >= finalTick && looping){
        tick = 0;
      }

    view.setTime(tick);
    view.animate();
  }}

  @Override
  public void togglePlayback() {
    if (paused) {
      timer = new Timer();
      this.advanceTime = new TimerTask() {
        @Override
        public void run() {
          tick++;
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
    tick = 0;
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
  public void addShape(String name, ShapeType type) {
    try {
      model.addShape(type, name);
    } catch (IllegalArgumentException e) {
      JOptionPane.showMessageDialog(new JPanel(), e.getMessage(), "WHOOPSY",
              JOptionPane.ERROR_MESSAGE);
    }
    modelChanged = true;
  }

  @Override
  public void removeShape(String name) {
    try {
      model.removeShape(name);
    } catch (IllegalArgumentException e) {
      JOptionPane.showMessageDialog(new JPanel(), e.getMessage(), "WHOOPSY",
              JOptionPane.ERROR_MESSAGE);
    }
    modelChanged = true;
  }

  @Override
  public void removeKeyframe(String shapeName, int t) {
    model.removeKeyFrame(shapeName, t);
    modelChanged = true;
  }

  @Override
  public void insertKeyframe(String shapeName, int t) {
    model.insertKeyFrame(shapeName, t);
    modelChanged = true;
  }

  @Override
  public void editKeyframe(String shapeName, int t, int x, int y, int w, int h,
                           int r, int g, int b) {
    model.editKeyFrame(shapeName, t, x, y, w, h, r, g, b);
    modelChanged = true;

  }

  /**
   * Resets the ticking of the timer.
   */
  private void restartTimer() {
    timer.cancel();
    timer.purge();
    timer = new Timer();
    this.advanceTime = new TimerTask() {
      @Override
      public void run() {
        tick++;
      }
    };
    timer.schedule(advanceTime, 0, 1000 / speed);
  }


}
