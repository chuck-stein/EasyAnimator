package cs3500.animator.view;

import cs3500.animator.model.hw05.IReadableShape;
import java.util.List;

public interface IEasyAnimatorView {

  /**
   * Signals the view to output the animation.
   */
  void animate();

  /**
   * Sets the shapes that that the view will need to animate.
   * @param shapes the shapes tha this view will store to be animated
   */
  void setShapes(List<IReadableShape> shapes);

}
