package cs3500.animator.controller;

import cs3500.animator.model.hw05.EasyAnimatorModel.EasyAnimatorModelBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.IEasyAnimatorView;
import cs3500.animator.view.SvgEasyAnimatorView;
import cs3500.animator.view.TextEasyAnimatorView;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Flushable;
import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

import cs3500.animator.model.hw05.IEasyAnimatorModel;
import cs3500.animator.model.hw05.ShapeType;

/**
 * Represents a controller for an editable animation, passing information between the model and the
 * interactive view.
 */
public class EasyAnimatorController implements IEasyAnimatorController, EditorListener {

  private IEasyAnimatorView view;
  private IEasyAnimatorModel model;
  private Timer timer;
  private TimerTask advanceTime;
  private int tick;
  private int finalTick;
  private int ticksPerSecond;
  private boolean paused;
  private boolean looping;
  private boolean modelChanged;

  /**
   * Creates the controller to run the animation editor.
   *
   * @param view the view that will display the model information.
   * @param model the model that contains the animations information.
   * @param ticksPerSecond the starting ticksPerSecond of the animation, in ticks per second.
   */
  public EasyAnimatorController(IEasyAnimatorView view, IEasyAnimatorModel model,
                                int ticksPerSecond) {
    if (Objects.isNull(view) || Objects.isNull(model)) {
      throw new IllegalArgumentException("View and Model cannot be null.");
    }
    this.view = view;
    view.setListener(this);
    this.model = model;
    this.tick = 0;
    this.timer = new Timer();
    this.ticksPerSecond = ticksPerSecond;
    this.advanceTime = new TimerTask() {
      @Override
      public void run() {
        tick++;
      }
    };
    this.finalTick = model.finalAnimationTime();
    this.modelChanged = true;
    view.setTicksPerSecond(ticksPerSecond);

  }

  @Override
  public void go() {
    timer.schedule(advanceTime, 0, 1000 / ticksPerSecond);
    while (!view.doneAnimating()) {
      if (modelChanged) {
        view.setShapes(model.getShapes());
        finalTick = model.finalAnimationTime();
        modelChanged = false;
      }
      if (tick >= finalTick && looping) {
        tick = 0;
      }
      view.setTime(tick);
      view.animate();
    }

  }

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
      timer.schedule(advanceTime, 0, 1000 / ticksPerSecond);
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
    if (ticksPerSecond > 5) {
      this.ticksPerSecond = ticksPerSecond - 5;

      if (!paused) {
        this.restartTimer();
      }
    }
  }

  @Override
  public void speedUp() {
    this.ticksPerSecond = ticksPerSecond + 5;

    if (!paused) {
      this.restartTimer();
    }
  }

  @Override
  public void addShape(String name, ShapeType type) {
    try {
      model.addShape(type, name);
      modelChanged = true;
    } catch (IllegalArgumentException e) {
      errorPopup(e.getMessage());
    }
  }

  @Override
  public void removeShape(String name) {
    try {
      model.removeShape(name);
      modelChanged = true;
    } catch (IllegalArgumentException e) {
      errorPopup(e.getMessage());
    }
  }

  @Override
  public void removeKeyframe(String shapeName, int t) {
    try {
      model.removeKeyFrame(shapeName, t);
      modelChanged = true;
    } catch (IllegalArgumentException e) {
      errorPopup(e.getMessage());
    }
  }

  @Override
  public void insertKeyframe(String shapeName, int t) {
    try {
      model.insertKeyFrame(shapeName, t);
      modelChanged = true;
    } catch (IllegalArgumentException e) {
      errorPopup(e.getMessage());
    }
  }

  @Override
  public void editKeyframe(String shapeName, int t, int x, int y, int w, int h,
                           int r, int g, int b) {
    try {
      model.editKeyFrame(shapeName, t, x, y, w, h, r, g, b);
      modelChanged = true;
    } catch (IllegalArgumentException e) {
      errorPopup(e.getMessage());
    }
  }

  @Override
  public void saveFile(String fileName, String fileType) throws IllegalArgumentException {
    Appendable output;
    if (Objects.isNull(fileName) || Objects.isNull(fileType)) {
      errorPopup("Need to specify a file name and type.");
    } else {

      if (!(fileType.equals("txt") || fileType.equals("svg"))) {
        errorPopup("Could not create this file type.");
      } else {

        try {
          output = new FileWriter(fileName + "." + fileType);
          this.executeSave(fileType, output);
        } catch (IOException e) {
          errorPopup("Could not write to or create file with this name.");
        }

      }
    }
  }

  /**
   * Creates the text based view to create and output the file for saving. Closes the file after.
   * @param fileType the type of file to create.
   * @param output where the output will be sent.
   */
  private void executeSave(String fileType, Appendable output) {
    IEasyAnimatorView savingView;
    if (fileType.equals("text")) {
      savingView = new TextEasyAnimatorView(model.getCanvasX(), model.getCanvasY(),
          model.getCanvasWidth(), model.getCanvasHeight(), output);
    } else {
      savingView = new SvgEasyAnimatorView(model.getCanvasX(), model.getCanvasY(),
          model.getCanvasWidth(), model.getCanvasHeight(), output);
    }

    savingView.setTicksPerSecond(ticksPerSecond);
    savingView.setShapes(model.getShapes());
    savingView.animate();

    try {
      ((Flushable) output).flush();
    } catch (IOException e) {
      errorPopup("File was unable to save properly");
    }
  }

  @Override
  public void loadFile(String fileName) throws IllegalArgumentException {
    Readable input;
    try {
      input = new FileReader(fileName);
      model = AnimationReader.parseFile(input, new EasyAnimatorModelBuilder());
      view.reSizeCanvas(model.getCanvasWidth(), model.getCanvasHeight(), model.getCanvasX(),
          model.getCanvasY());
      modelChanged = true;
    } catch (IOException e) {
      errorPopup("Could not read from file with this name.");
    }

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
    timer.schedule(advanceTime, 0, 1000 / ticksPerSecond);
  }

  /**
   * Displays the given message as an error popup box.
   *
   * @param msg the error message to be displayed in the popup
   */
  private void errorPopup(String msg) {
    JOptionPane.showMessageDialog((JFrame) view, msg, "WHOOPSY",
        JOptionPane.ERROR_MESSAGE);
  }

}
