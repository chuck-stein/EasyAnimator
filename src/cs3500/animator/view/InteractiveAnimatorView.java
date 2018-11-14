package cs3500.animator.view;

/**
 *
 */
public interface InteractiveAnimatorView extends IEasyAnimatorView {

  void togglePlay();

  void restart();

  void toggleLooping();

  void slowDown();

  void speedUp();

  void addShape(); //need to add parameters!

  void removeShape(String name);

  void removeKeyframe(String shapeName, int i);


}
