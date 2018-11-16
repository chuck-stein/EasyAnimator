package cs3500.animator.view;

import java.awt.event.ActionListener;

/**
 *
 */
public interface InteractiveAnimatorView extends IEasyAnimatorView {

  void setListener(ActionListener listener);

  void update();

//
//  void togglePlay();
//
//  void restart();
//
//  void toggleLooping();
//
//  void slowDown();
//
//  void speedUp();
//
//  void addShape(); //need to add parameters!
//
//  void removeShape(String name);
//
//  void removeKeyframe(String shapeName, int i);


}
