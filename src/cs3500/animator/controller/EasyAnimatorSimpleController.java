package cs3500.animator.controller;


import cs3500.animator.model.hw05.IEasyAnimatorModel;
import cs3500.animator.view.IEasyAnimatorView;
import java.util.Timer;
import java.util.TimerTask;

public class EasyAnimatorSimpleController implements IEasyAnimatorController{
 IEasyAnimatorView view;
 IEasyAnimatorModel model;

 int ticksPerSecond;


  public EasyAnimatorSimpleController(IEasyAnimatorView view, IEasyAnimatorModel model, int ticksPerSecond) {
    this.view = view;
    this.model = model;

    this.ticksPerSecond = ticksPerSecond;

  }

  @Override
  public void go() {
    view.setShapes(model.getShapes());
    view.setCanvas(model.getCanvasX(),model.getCanvasY(),model.getCanvasWidth(),model.getCanvasHeight());
    view.startTicking(ticksPerSecond);
    while (true) {


      view.refresh();
    }

  }


}
