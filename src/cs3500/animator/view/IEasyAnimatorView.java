package cs3500.animator.view;

import cs3500.animator.model.hw05.ReadableShape;
import java.util.List;

public interface IEasyAnimatorView {

  /**
   * Sets the appendable that the View can output to.
   * @param output
   */
  void setOutput(Appendable output);

  /**
   * Signal the view to draw itself
   */
  void refresh();

  /**
   * Gets the shapes that it needs to draw with.
   * @param shapes
   */
  void setShapes(List<ReadableShape> shapes);

  /**
   * Sets the canvas of the view.
   * @param x the x location of the canvas
   * @param y the y location of the canvas
   * @param w
   * @param h
   */
  void setCanvas(int x, int y, int w, int h);



  /**
   * Tells the animator to start with the specified speed.
   * @param ticksPerSecond
   */
  void startTicking(int ticksPerSecond);

}
