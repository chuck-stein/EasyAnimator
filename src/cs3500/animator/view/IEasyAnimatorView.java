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
   * Signals the view to output the animation.
   */
  void animate();

  /**
   * Sets the shapes that that the view will need to animate.
   * @param shapes the shapes tha this view will store to be animated
   * @throws IllegalArgumentException if the given list of shapes is null
   */
  void setShapes(List<IReadableShape> shapes) throws IllegalArgumentException;

  void setTime(int tick);

  void setTicksPerSecond(int ticksPerSecond);

  void setListener(EditorListener listener);

  boolean doneAnimating();

}
