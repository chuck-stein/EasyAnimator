import static org.junit.Assert.*;

import cs3500.animator.controller.EasyAnimatorSimpleController;
import cs3500.animator.controller.IEasyAnimatorController;
import cs3500.animator.model.hw05.EasyAnimatorModel;
import cs3500.animator.model.hw05.IEasyAnimatorModel;
import cs3500.animator.model.hw05.ShapeType;
import cs3500.animator.view.IEasyAnimatorView;
import cs3500.animator.view.SimpleTextBasedEasyAnimatorView;
import org.junit.Test;

public class IEasyAnimatorControllerTest {

  IEasyAnimatorModel m = new EasyAnimatorModel();
  StringBuffer out = new StringBuffer();
  IEasyAnimatorView v = new SimpleTextBasedEasyAnimatorView();


  IEasyAnimatorController c;

  @Test
  public void go() {
    v.setOutput(out);
    m.addShape(ShapeType.RECTANGLE, "R1");
    m.addMotion("R1", 1, 340, 155, 10, 17, 0, 0, 255, 41,
        400, 100, 10, 17, 0, 0, 255);
    m.addMotion("R1",41,1,1,1,1,1,1,1,43,1,1,1,1,1,1,1);
    c = new EasyAnimatorSimpleController(v, m);
    c.go();
    assertEquals("Stuff", out);
  }
}