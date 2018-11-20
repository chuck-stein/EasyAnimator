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
   * @param shapes the shapes tha this view will store to be animated
   * @throws IllegalArgumentException if the given list of shapes is null
   */
  void setShapes(List<IReadableShape> shapes) throws IllegalArgumentException;

  /**
   * Sets the current time of the animation. Not used by all view types.
   * @param tick the time of the animation.
   */
  void setTime(int tick);

  /**
   * Sets the ticksPerSecond of the animation. Not used by all view types.
   * @param ticksPerSecond the ticksPerSecond of the animation.
   */
  void setTicksPerSecond(int ticksPerSecond);

  /**
   * Sets the listener for the view to receive what to do. Only used with interactive animations.
   * @param listener the listener to the view.
   */
  void setListener(EditorListener listener);

  /**
   * Signals that this view no longer has anything to animate.
   * @return if this view is done animating.
   */
  boolean doneAnimating();

  /**
   * Resizes the canvas for the view.
   * @param canvasWidth the new width
   * @param canvasHeight the new height
   * @param canvasX the new originX
   * @param canvasY the new originY
   */
  void reSizeCanvas(int canvasWidth, int canvasHeight, int canvasX, int canvasY);

}
