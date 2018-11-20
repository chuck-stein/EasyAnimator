package cs3500.animator.controller;


import cs3500.animator.model.hw05.IEasyAnimatorModel;
import cs3500.animator.view.IEasyAnimatorView;

import java.util.Objects;

/**
 * A very simple controller that just passes the model info into the view and tells the view to
 * animate.
 */
public class SimpleAnimatorController implements IEasyAnimatorController {

  private IEasyAnimatorView view;
  private IEasyAnimatorModel model;

  /**
   * Creates the controller to run the animation.
   *
   * @param view  the view that will display the model information.
   * @param model the model that contains the animation information.
   */
  public SimpleAnimatorController(IEasyAnimatorView view, IEasyAnimatorModel model) {
    if (Objects.isNull(view) || Objects.isNull(model)) {
      throw new IllegalArgumentException("View and Model cannot be null.");
    }
    this.view = view;
    this.model = model;
  }

  @Override
  public void go() {
    view.setShapes(model.getShapes());
    view.animate();
  }

}
