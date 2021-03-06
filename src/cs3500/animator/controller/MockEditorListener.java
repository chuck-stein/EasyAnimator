package cs3500.animator.controller;

import java.io.File;
import java.io.IOException;

import cs3500.animator.model.hw05.ShapeType;

/**
 * A mock implementation of the EditorListener interface, to facilitate testing of actionPerformed
 * in the AnimationEditorView (which makes calls to EditorListener methods depending on the
 * ActionEvent triggered), to ensure the listeners are triggered correctly upon their corresponding
 * ActionEvent. Uses an Appendable output to create a log of the actions triggered.
 */
public class MockEditorListener implements EditorListener {

  private Appendable output;
  private boolean paused;
  private boolean looping;

  /**
   * Constructs a mock EditorListener which writes to the given Appendable when events have been
   * triggered.
   *
   * @param output the Appendable to log updates to whenever this listener hears events being
   *               triggered.
   */
  public MockEditorListener(Appendable output) {
    this.output = output;
    paused = false;
    looping = false;
  }

  @Override
  public void togglePlayback() {
    if (paused) {
      log("play");
    } else {
      log("pause");
    }
    paused = !paused;
  }

  @Override
  public void restart() {
    log("restart");
  }

  @Override
  public void toggleLooping() {
    if (looping) {
      log("looping off");
    } else {
      log("looping on");
    }
    looping = !looping;
  }

  @Override
  public void slowDown() {
    log("slow down");
  }

  @Override
  public void speedUp() {
    log("speed up");
  }

  @Override
  public void addShape(String name, ShapeType type) {
    log("add shape");
  }

  @Override
  public void addShape(String name, ShapeType type, int layer) {
    log("add shape (with layer specified)");
  }

  @Override
  public void removeShape(String name) {
    log("remove shape");
  }

  @Override
  public void removeKeyframe(String shapeName, int t) {
    log("remove keyframe");
  }

  @Override
  public void insertKeyframe(String shapeName, int t) {
    log("insert keyframe");
  }

  @Override
  public void editKeyframe(String shapeName, int t, int x, int y, int w, int h,
                           int a, int r, int g, int b) {
    log("edit keyframe");
  }

  @Override
  public void saveFile(String fileName, String fileType) {
    log("save file");
  }

  @Override
  public void loadFile(File fileName) {
    log("load file");
  }

  @Override
  public void setTime(int time) {
    log("set time");
  }

  @Override
  public void addLayer() {
    log("add layer");
  }

  @Override
  public void removeLayer(int i) throws IllegalArgumentException {
    log("remove layer");
  }

  @Override
  public void moveLayerBack(int i) throws IllegalArgumentException {
    log("move layer back");
  }

  @Override
  public void moveLayerForward(int i) throws IllegalArgumentException {
    log("move layer forward");
  }

  /**
   * Add the given update message to the output Appendable if it is currently writable.
   *
   * @param update the message to be added to the log of events
   */
  private void log(String update) throws IllegalStateException {
    try {
      output.append(update);
      output.append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Unable to write to output.");
    }
  }

}
