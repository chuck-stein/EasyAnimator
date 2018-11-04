package cs3500.animator.view;

import cs3500.animator.model.hw05.IShape;
import java.util.List;

public interface IEasyAnimatorView {
  /**
   * Make the view visible. This is usually called
   * after the view is constructed
   */
  void makeVisible();

  /**
   * Signal the view to draw itself
   */
  void refresh();

  /**
   * Gets the shapes that it needs to draw with.
   * @param shapes
   */
  void setShapes(List<IShape> shapes);




}
