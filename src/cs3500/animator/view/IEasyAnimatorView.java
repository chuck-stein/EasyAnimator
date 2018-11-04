package cs3500.animator.view;

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
   * Gets all of the motions
   */
  void setMotions();




}
