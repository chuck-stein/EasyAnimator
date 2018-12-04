package cs3500.animator.adapter;

import cs3500.animator.controller.EasyAnimatorController;
import cs3500.animator.controller.EditorListener;
import cs3500.animator.controller.IEasyAnimatorController;
import cs3500.animator.model.hw05.IEasyAnimatorModel;
import cs3500.animator.model.hw05.ShapeType;
import cs3500.animator.provider.controller.Commands;
import cs3500.animator.provider.model.IEasyAnimator;
import cs3500.animator.view.IEasyAnimatorView;
import java.io.File;


public class ControllerAdapter extends EasyAnimatorController implements Commands {


  /**
   * Creates the controller to run the animation editor.
   *
   * @param view the view that will display the model information.
   * @param model the model that contains the animations information.
   * @param ticksPerSecond the starting ticksPerSecond of the animation, in ticks per second.
   */
  public ControllerAdapter(IEasyAnimatorView view,
      IEasyAnimatorModel model, int ticksPerSecond) {
    super(view, model, ticksPerSecond);
  }



  @Override
  public void startProgram() {
    //Not used in the adaptor as we adapted to their listener,
    // but this was for the controller class.

  }
}
