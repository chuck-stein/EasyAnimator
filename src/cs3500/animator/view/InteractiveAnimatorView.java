package cs3500.animator.view;

import cs3500.animator.controller.EditorListener;

public interface InteractiveAnimatorView extends IEasyAnimatorView {

  void setListener(EditorListener listener);

  void update();


}
