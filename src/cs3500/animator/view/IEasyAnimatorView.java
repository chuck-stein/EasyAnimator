package cs3500.animator.view;

import cs3500.animator.controller.EditorListener;
import cs3500.animator.model.hw05.IReadableShape;

import java.util.List;

/**
 * Represents a simple view for our Easy Animator, which can set a list of shapes that must be
 * animated, and animate those shapes.
 */
public interface IEasyAnimatorView {

  /**
   * Signals the view to output the animation. This is different depending on the type of view.
   */
  void animate();

  /**
   * Sets the shapes that that the view will need to animate.
   *
   * @param shapes         the shapes tha this view will store to be animated
   * @param buttonResponse if the call is triggered from a user pressing a button (this is to fix a
   *                       bug in the provided code that they could not fix, which we found a
   *                       workaround for that only works for the provided view but breaks our
   *                       own. Therefore this boolean allows both views to function without error)
   * @throws IllegalArgumentException if the given list of shapes is null
   */
  void setShapes(List<IReadableShape> shapes, boolean buttonResponse)
          throws IllegalArgumentException;

  /**
   * Sets the current time of the animation. Not used by all view types.
   *
   * @param tick the time of the animation.
   */
  void setTime(int tick);

  /**
   * Sets the ticksPerSecond of the animation. Not used by all view types.
   *
   * @param ticksPerSecond the ticksPerSecond of the animation.
   */
  void setTicksPerSecond(int ticksPerSecond);

  /**
   * Sets the listener for the view to receive what to do. Only used with interactive animations.
   *
   * @param listener the listener to the view.
   * @throws IllegalArgumentException if the given listener is null.
   */
  void setListener(EditorListener listener) throws IllegalArgumentException;

  /**
   * Signals that this view no longer has anything to animate.
   *
   * @return if this view is done animating.
   */
  boolean doneAnimating();

  /**
   * Resizes the canvas for the view.
   *
   * @param canvasWidth  the new width
   * @param canvasHeight the new height
   * @param canvasX      the new originX
   * @param canvasY      the new originY
   */
  void resizeCanvas(int canvasWidth, int canvasHeight, int canvasX, int canvasY);

  /**
   * Displays the given message as an error popup box if the view is Swing-based.
   *
   * @param msg the error message to be displayed in the popup
   */
  void popUp(String msg, boolean isError);

}
