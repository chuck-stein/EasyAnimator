package cs3500.animator.controller;

import cs3500.animator.model.hw05.EasyAnimatorModel.EasyAnimatorModelBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.IEasyAnimatorView;
import cs3500.animator.view.SvgEasyAnimatorView;
import cs3500.animator.view.TextEasyAnimatorView;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Flushable;
import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import cs3500.animator.model.hw05.IEasyAnimatorModel;
import cs3500.animator.model.hw05.ShapeType;

/**
 * Represents a controller for an editable animation, passing information between the model and the
 * interactive view. Is also a listener for edit commands. It passes these to the model.
 */
public class EasyAnimatorController implements IEasyAnimatorController, EditorListener {

  protected int tick;
  private IEasyAnimatorView view;
  private IEasyAnimatorModel model;
  private Timer timer;
  private TimerTask advanceTime;
  private int finalTick;
  private int ticksPerSecond;
  private boolean paused;
  private boolean looping;
  private boolean modelChanged;

  /**
   * Creates the controller to run the animation editor.
   *
   * @param view           the view that will display the model information.
   * @param model          the model that contains the animations information.
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
  public void commence() {
    timer.schedule(advanceTime, 0, 1000 / ticksPerSecond);
    while (!view.doneAnimating()) {
      if (modelChanged) {
        view.setLayers(model.getShapeLayers());
        finalTick = model.finalAnimationTime();
        modelChanged = false;
      }
      if (tick >= finalTick && looping) {
        tick = 1;
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
    addShape(name, type, 0);
  }

  @Override
  public void addShape(String name, ShapeType type, int layer) {
    try {
      model.addShape(type, name, layer);
      modelChanged = true;
    } catch (IllegalArgumentException e) {
      view.popUp(e.getMessage(), true);
    }
  }

  @Override
  public void removeShape(String name) {
    try {
      model.removeShape(name);
      modelChanged = true;

      view.setShapes(model.getShapes(), true);


    } catch (IllegalArgumentException e) {
      view.popUp(e.getMessage(), true);
    }
  }

  @Override
  public void removeKeyframe(String shapeName, int t) {
    try {
      model.removeKeyFrame(shapeName, t);
      modelChanged = true;
    } catch (IllegalArgumentException e) {
      view.popUp(e.getMessage(), true);
    }
  }

  @Override
  public void insertKeyframe(String shapeName, int t) {
    try {
      model.insertKeyFrame(shapeName, t);
      modelChanged = true;
    } catch (IllegalArgumentException e) {
      view.popUp(e.getMessage(), true);
    }
  }

  @Override
  public void editKeyframe(String shapeName, int t, int x, int y, int w, int h,
                           int a, int r, int g, int b) {
    try {
      model.editKeyFrame(shapeName, t, x, y, w, h, a, r, g, b);
      modelChanged = true;
    } catch (IllegalArgumentException e) {
      view.popUp(e.getMessage(), true);
    }
  }

  @Override
  public void saveFile(String fileName, String fileType) throws IllegalArgumentException {
    Appendable output;
    if (Objects.isNull(fileName) || Objects.isNull(fileType)) {
      view.popUp("Need to specify a file name and type.", true);
    } else {
      String saveDirectory = "animations";
      if (fileType.equals("txt")) {
        saveDirectory += "/text/";
      } else if (fileType.equals("svg")) {
        saveDirectory += "/svg/";
      } else {
        view.popUp("Can not create this file type. Supported types: '.svg', '.txt'", true);
      }
      try {
        output = new FileWriter(saveDirectory + fileName + "." + fileType);
        this.executeSave(fileType, output);
      } catch (IOException e) {
        view.popUp("Could not write to or create file " + fileName + "." + fileType + " in " +
                "directory " + saveDirectory + ".", true);
      }
    }
  }

  /**
   * Creates the text based view to create and output the file for saving. Closes the file after.
   *
   * @param fileType the type of file to create.
   * @param output   where the output will be sent.
   */
  private void executeSave(String fileType, Appendable output) {
    IEasyAnimatorView savingView;
    if (fileType.equals("txt")) {
      savingView = new TextEasyAnimatorView(model.getCanvasX(), model.getCanvasY(),
              model.getCanvasWidth(), model.getCanvasHeight(), output);
    } else {
      savingView = new SvgEasyAnimatorView(model.getCanvasX(), model.getCanvasY(),
              model.getCanvasWidth(), model.getCanvasHeight(), output);
    }

    savingView.setTicksPerSecond(ticksPerSecond);
    savingView.setLayers(model.getShapeLayers());
    savingView.animate();

    try {
      ((Flushable) output).flush();
      view.popUp("Save Successful!", false);
    } catch (IOException e) {
      view.popUp("File was unable to save properly", true);
    }
  }

  @Override
  public void loadFile(File fileName) throws IllegalArgumentException {
    if (Objects.isNull(fileName)) {
      view.popUp("Need to specify file name.",false);
    } else {
      Readable input;
      try {
        input = new FileReader(fileName);
        model = AnimationReader.parseFile(input, new EasyAnimatorModelBuilder());
        view.resizeCanvas(model.getCanvasWidth(), model.getCanvasHeight(), model.getCanvasX(),
            model.getCanvasY());
        modelChanged = true;
        this.tick = 0;
      } catch (IOException e) {
        view.popUp("Could not read from file with this name.", true);
      }
    }
  }

  @Override
  public void setTime(int time) {
    this.tick = time;
  }

  @Override
  public void addLayer() {
    model.addLayer();
    modelChanged = true;
  }

  @Override
  public void removeLayer(int i) throws IllegalArgumentException {
    model.removeLayer(i);
    modelChanged = true;
  }

  @Override
  public void moveLayerBack(int i) throws IllegalArgumentException {
    model.moveLayerBack(i);
    modelChanged = true;
  }

  @Override
  public void moveLayerForward(int i) throws IllegalArgumentException {
    model.moveLayerForward(i);
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
    timer.schedule(advanceTime, 0, 1000 / ticksPerSecond);
  }

}