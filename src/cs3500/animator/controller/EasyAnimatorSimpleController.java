package cs3500.animator.controller;


import cs3500.animator.model.hw05.IEasyAnimatorModel;
import cs3500.animator.view.IEasyAnimatorView;

/**
 * A very simple controller that just passes the model info into the view and starts it.
 */
public class EasyAnimatorSimpleController implements IEasyAnimatorController{
 IEasyAnimatorView view;
 IEasyAnimatorModel model;


  /**
   * Creates the controller to run the animation.
   * @param view the view that will display the model information.
   * @param model the model that contains the animations information.
   */
  public EasyAnimatorSimpleController(IEasyAnimatorView view, IEasyAnimatorModel model) {
    this.view = view;
    this.model = model;



  }

  @Override
  public void go() {
    view.setShapes(model.getShapes());
  view.animate();



  }


}
