package cs3500.animator.view;

import cs3500.animator.model.hw05.IShape;
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




}
