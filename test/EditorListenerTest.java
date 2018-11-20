import org.junit.Before;
import org.junit.Test;

import cs3500.animator.controller.EditorListener;
import cs3500.animator.controller.EnhancedAnimatorController;
import cs3500.animator.model.hw05.EasyAnimatorModel;
import cs3500.animator.model.hw05.IEasyAnimatorModel;
import cs3500.animator.view.AnimationEditorView;
import cs3500.animator.view.InteractiveAnimatorView;

public class EditorListenerTest {

  private IEasyAnimatorModel m;
  private InteractiveAnimatorView v;
  private EditorListener listener;

  @Before
  public void init() {
    m = new EasyAnimatorModel();
    v = new AnimationEditorView(100, 100, 500, 500);
    listener = new EnhancedAnimatorController(v, m, 60);
  }

  @Test
  public void testTogglePlayback1() {

  }

}
