import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cs3500.animator.model.hw05.EasyAnimatorModel;
import cs3500.animator.model.hw05.IEasyAnimatorModel;
import cs3500.animator.model.hw05.ShapeType;
import cs3500.animator.view.IEasyAnimatorView;
import cs3500.animator.view.SvgEasyAnimatorView;
import cs3500.animator.view.TextEasyAnimatorView;

public class TextEasyAnimatorViewTest {

  private StringBuilder output;
  private IEasyAnimatorView textView;
  private IEasyAnimatorModel testModel;
  private String expectedOut;
  private String noShapesOut;

  @Before
  public void init() {
    output = new StringBuilder();
    textView = new TextEasyAnimatorView(200, 70, 360, 360, 50, output);
    testModel = new EasyAnimatorModel();
    testModel.addShape(ShapeType.RECTANGLE, "R");
    testModel.addMotion("R", 1, 200, 200, 50, 100, 255, 0, 0, 10, 200, 200, 50, 100, 255, 0, 0);
    testModel.addMotion("R", 10, 200, 200, 50, 100, 255, 0, 0, 50, 300, 300, 50, 100, 255, 0, 0);
    testModel.addMotion("R", 50, 300, 300, 50, 100, 255, 0, 0, 51, 300, 300, 50, 100, 255, 0, 0);
    testModel.addMotion("R", 51, 300, 300, 50, 100, 255, 0, 0, 70, 300, 300, 25, 100, 255, 0, 0);
    testModel.addMotion("R", 70, 300, 300, 25, 100, 255, 0, 0, 100, 200, 200, 25, 100, 255, 0, 0);
    testModel.addShape(ShapeType.ELLIPSE, "C");
    testModel.addMotion("C", 6, 440, 70, 120, 60, 0, 0, 255, 20, 440, 70, 120, 60, 0, 0, 255);
    testModel.addMotion("C", 20, 440, 70, 120, 60, 0, 0, 255, 50, 440, 250, 120, 60, 0, 0, 255);
    testModel.addMotion("C", 50, 440, 250, 120, 60, 0, 0, 255, 70, 440, 370, 120, 60, 0, 170, 85);
    testModel.addMotion("C", 70, 440, 370, 120, 60, 0, 170, 85, 80, 440, 370, 120, 60, 0, 255, 0);
    testModel.addMotion("C", 80, 440, 370, 120, 60, 0, 255, 0, 100, 440, 370, 120, 60, 0, 255, 0);
    expectedOut = "canvas 200 70 360 360\n" +
            "shape R rectangle\n" +
            "motion R 1 200 200 50 100 255 0 0    10 200 200 50 100 255 0 0\n" +
            "motion R 10 200 200 50 100 255 0 0    50 300 300 50 100 255 0 0\n" +
            "motion R 50 300 300 50 100 255 0 0    51 300 300 50 100 255 0 0\n" +
            "motion R 51 300 300 50 100 255 0 0    70 300 300 25 100 255 0 0\n" +
            "motion R 70 300 300 25 100 255 0 0    100 200 200 25 100 255 0 0\n" +
            "shape C ellipse\n" +
            "motion C 6 440 70 120 60 0 0 255    20 440 70 120 60 0 0 255\n" +
            "motion C 20 440 70 120 60 0 0 255    50 440 250 120 60 0 0 255\n" +
            "motion C 50 440 250 120 60 0 0 255    70 440 370 120 60 0 170 85\n" +
            "motion C 70 440 370 120 60 0 170 85    80 440 370 120 60 0 255 0\n" +
            "motion C 80 440 370 120 60 0 255 0    100 440 370 120 60 0 255 0";
    noShapesOut = "canvas 200 70 360 360";
  }

  @Test
  public void animate() {
    assertEquals("", output.toString());
    textView.setShapes(testModel.getShapes());
    textView.animate();
    assertEquals(expectedOut, output.toString());
  }

  @Test
  public void testSetShapes() {
    assertEquals("", output.toString());
    textView.animate();
    assertEquals(noShapesOut, output.toString());
    init(); // refresh the output appendable
    textView.setShapes(testModel.getShapes());
    textView.animate();
    assertEquals(expectedOut, output.toString());
  }
}