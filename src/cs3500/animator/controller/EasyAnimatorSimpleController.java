package cs3500.animator.controller;


import cs3500.animator.model.hw05.IEasyAnimatorModel;
import cs3500.animator.view.IEasyAnimatorView;

public class EasyAnimatorSimpleController implements IEasyAnimatorController{
 IEasyAnimatorView view;
 IEasyAnimatorModel model;




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
