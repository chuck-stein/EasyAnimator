import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


import org.junit.Before;
import org.junit.Test;

import cs3500.animator.model.hw05.EasyAnimatorModel;
import cs3500.animator.model.hw05.IEasyAnimatorModel;
import cs3500.animator.model.hw05.ShapeType;
import cs3500.animator.view.IEasyAnimatorView;
import cs3500.animator.view.TextEasyAnimatorView;

public class TextEasyAnimatorViewTest {

  private StringBuilder testModelOutput;
  private IEasyAnimatorView textView;
  private IEasyAnimatorModel testModel;
  private String expectedOut;
  private String noShapesOut;

  private IEasyAnimatorModel modelWithUselessShapes;
  private IEasyAnimatorModel modelWithStillShapes;
  private IEasyAnimatorModel modelWithDelayedShapes;

  @Before
  public void init() {
    testModelOutput = new StringBuilder();
    textView = new TextEasyAnimatorView(200, 70, 360, 360, 50, testModelOutput);
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

    modelWithUselessShapes = new EasyAnimatorModel();
    modelWithUselessShapes.addShape(ShapeType.ELLIPSE, "C");
    modelWithUselessShapes.addShape(ShapeType.RECTANGLE, "R");

    modelWithStillShapes = new EasyAnimatorModel();
    modelWithStillShapes.addShape(ShapeType.ELLIPSE, "C");
    modelWithStillShapes.addShape(ShapeType.RECTANGLE, "R");
    modelWithStillShapes.addMotion("C", 1, 2, 3, 4, 5, 6, 7, 8,
        1, 2, 3, 4, 5, 6, 7, 8);
    modelWithStillShapes.addMotion("R", 1, 2, 3, 4, 5, 6, 7, 8,
        1, 2, 3, 4, 5, 6, 7, 8);



    modelWithDelayedShapes = new EasyAnimatorModel();
    modelWithDelayedShapes.addShape(ShapeType.ELLIPSE, "C");
    modelWithDelayedShapes.addShape(ShapeType.RECTANGLE, "R");
    modelWithDelayedShapes.addMotion("C", 1, 2, 3, 4, 5, 6, 7, 8,
        1, 2, 3, 4, 5, 6, 7, 8);
    modelWithDelayedShapes.addMotion("C", 1, 2, 3, 4, 5, 6, 7, 8,
        10, 2, 3, 4, 5, 6, 7, 8);
    modelWithDelayedShapes.addMotion("R", 8, 2, 3, 4, 5, 6, 7, 8,
        10, 2, 7, 4, 5, 2, 7, 5);


  }

  @Test
  public void testAnimateAndSetShape() {
    assertEquals("", testModelOutput.toString());
    textView.animate();
    assertEquals(noShapesOut, testModelOutput.toString());
    init();
    textView.setShapes(testModel.getShapes());
    textView.animate();
    assertEquals(expectedOut, testModelOutput.toString());
  }

  @Test
  public void testAnimateWithUselessShapes() {
    assertEquals("", testModelOutput.toString());
    textView.setShapes(modelWithUselessShapes.getShapes());
    textView.animate();
    assertEquals("canvas 200 70 360 360", testModelOutput.toString());
  }

  @Test
  public void testAnimateWithStillShapes() {
    assertEquals("", testModelOutput.toString());
    textView.setShapes(modelWithStillShapes.getShapes());
    textView.animate();
    assertEquals("canvas 200 70 360 360\n"
        + "shape C ellipse\n"
        + "motion C 1 2 3 4 5 6 7 8    1 2 3 4 5 6 7 8\n"
        + "shape R rectangle\n"
        + "motion R 1 2 3 4 5 6 7 8    1 2 3 4 5 6 7 8", testModelOutput.toString());
  }

  @Test
  public void testAnimateWithDelayedShapes() {
    assertEquals("", testModelOutput.toString());
    textView.setShapes(modelWithDelayedShapes.getShapes());
    textView.animate();
    assertEquals("canvas 200 70 360 360\n"
        + "shape C ellipse\n"
        + "motion C 1 2 3 4 5 6 7 8    1 2 3 4 5 6 7 8\n"
        + "motion C 1 2 3 4 5 6 7 8    10 2 3 4 5 6 7 8\n"
        + "shape R rectangle\n"
        + "motion R 8 2 3 4 5 6 7 8    10 2 7 4 5 2 7 5", testModelOutput.toString());
  }

  @Test
  public void badCanvas() {
    try {
      IEasyAnimatorView v = new TextEasyAnimatorView(1, 2, -10, 4, 1, new StringBuilder());
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Canvas dimensions must be positive.",e.getMessage());
    }
  }

  @Test
  public void badTicks() {
    try {
      IEasyAnimatorView v = new TextEasyAnimatorView(1, 2, 10, 4, -11, new StringBuilder());
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Ticks per second must be be positive.",e.getMessage());
    }
  }

  @Test
  public void nullOutput() {
    try {
      IEasyAnimatorView v = new TextEasyAnimatorView(1, 2, 10, 4, 1, null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Output cannot be null.",e.getMessage());
    }
  }



}