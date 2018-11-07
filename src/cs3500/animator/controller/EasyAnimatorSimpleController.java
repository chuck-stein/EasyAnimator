package cs3500.animator.controller;


import cs3500.animator.model.hw05.IEasyAnimatorModel;
import cs3500.animator.view.IEasyAnimatorView;
import java.util.Timer;
import java.util.TimerTask;

public class EasyAnimatorSimpleController implements IEasyAnimatorController{
 IEasyAnimatorView view;
 IEasyAnimatorModel model;
 Timer timer;
 TimerTask advanceTime;
 int ticksPerSecond;


  public EasyAnimatorSimpleController(IEasyAnimatorView view, IEasyAnimatorModel model, int ticksPerSecond) {
    this.view = view;
    this.model = model;
    this.timer = new Timer();
    this.ticksPerSecond = ticksPerSecond;
    this.advanceTime =new TimerTask() {
      @Override
      public void run() {
        view.updateTick();
      }
    };
  }

  @Override
  public void go() {
    view.setShapes(model.getShapes());
    timer.schedule(advanceTime,0,1000/ticksPerSecond);
    while (true) {


      view.refresh();
    }

  }


}
