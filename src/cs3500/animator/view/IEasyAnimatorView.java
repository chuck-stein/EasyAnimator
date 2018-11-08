package cs3500.animator.view;

import cs3500.animator.model.hw05.ReadableShape;
import java.util.List;

public interface IEasyAnimatorView {


  /**
   * Signals the view to show the animation.
   */
  void animate();

  /**
   * Gets the shapes that it needs to draw with.
   * @param shapes
   */
  void setShapes(List<ReadableShape> shapes);






}
