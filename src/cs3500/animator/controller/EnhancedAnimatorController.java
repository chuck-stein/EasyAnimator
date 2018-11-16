package cs3500.animator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import cs3500.animator.model.hw05.IEasyAnimatorModel;
import cs3500.animator.view.IEasyAnimatorView;
import cs3500.animator.view.InteractiveAnimatorView;

/**
 * Represents a controller for an editable animation, passing information between the model and
 * the interactive view.
 */
public class EnhancedAnimatorController implements IEnhancedAnimatorController, ActionListener {

  private InteractiveAnimatorView view;
  private IEasyAnimatorModel model;
  private Timer timer;
  private int ticksPerSecond;

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
    ticksPerSecond = speed;
    TimerTask advanceTime = new TimerTask() {
      @Override
      public void run() {
        view.update();
      }
    };
    timer.schedule(advanceTime, 0, 1000 / ticksPerSecond);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String cmd = e.getActionCommand();
    switch (cmd) {
      case "toggle playback":
        togglePlayback();
        break;
      case "restart":
        break;
      case "slow down":
        break;
      case "speed up":
        break;
      case "toggle looping":
        break;
      case "add shape":
        break;
      case "remove shape":
        break;
      case "insert keyframe":
        break;
      case "edit keyframe":
        break;
      case "remove keyframe":
        break;
      default:

    }
  }

  private void togglePlayback() {
    // pause timer schedule?
  }

}
