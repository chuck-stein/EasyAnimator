import org.junit.Before;
import org.junit.Test;

import cs3500.animator.model.hw05.EasyAnimatorModel;
import cs3500.animator.model.hw05.IEasyAnimatorModel;
import cs3500.animator.model.hw05.ShapeType;
import cs3500.animator.view.IEasyAnimatorView;
import cs3500.animator.view.SvgEasyAnimatorView;

import static org.junit.Assert.assertEquals;

public class SvgEasyAnimatorViewTest {

  private StringBuilder output;
  private IEasyAnimatorView svgView;
  private IEasyAnimatorModel testModel;
  private String expectedOut;
  private String noShapesOut;

  @Before
  public void init() {
    output = new StringBuilder();
    svgView = new SvgEasyAnimatorView(200, 70, 360, 360, 50, output);
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
    expectedOut = "<svg width=\"360\" height=\"360\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "<rect id=\"R\" x=\"0.0\" y=\"130.0\" width=\"50.0\" height=\"100.0\" fill=\"rgb(255,0,0)\" visibility=\"hidden\" >\n" +
            "<animate attributeType=\"xml\" begin=\"20.0ms\" dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"200.0ms\" dur=\"800.0ms\" attributeName=\"x\" from=\"0.0\" to=\"100.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"200.0ms\" dur=\"800.0ms\" attributeName=\"y\" from=\"130.0\" to=\"230.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1020.0ms\" dur=\"380.0ms\" attributeName=\"width\" from=\"50.0\" to=\"25.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1400.0ms\" dur=\"600.0ms\" attributeName=\"x\" from=\"100.0\" to=\"0.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1400.0ms\" dur=\"600.0ms\" attributeName=\"y\" from=\"230.0\" to=\"130.0\" fill=\"freeze\" />\n" +
            "</rect>\n" +
            "<ellipse id=\"C\" cx=\"240.0\" cy=\"0.0\" rx=\"60.0\" ry=\"30.0\" fill=\"rgb(0,0,255)\" visibility=\"hidden\" >\n" +
            "<animate attributeType=\"xml\" begin=\"120.0ms\" dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"400.0ms\" dur=\"600.0ms\" attributeName=\"cy\" from=\"0.0\" to=\"180.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1000.0ms\" dur=\"400.0ms\" attributeName=\"cy\" from=\"180.0\" to=\"300.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1000.0ms\" dur=\"400.0ms\" attributeName=\"fill\" values=\"rgb(0,0,255);rgb(0,170,85)\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1400.0ms\" dur=\"200.0ms\" attributeName=\"fill\" values=\"rgb(0,170,85);rgb(0,255,0)\" fill=\"freeze\" />\n" +
            "</ellipse>\n" +
            "</svg>";
    noShapesOut = "<svg width=\"360\" height=\"360\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "</svg>";
  }

  @Test
  public void animate() {
    assertEquals("", output.toString());
    svgView.setShapes(testModel.getShapes());
    svgView.animate();
    assertEquals(expectedOut, output.toString());
  }

  @Test
  public void testSetShapes() {
    assertEquals("", output.toString());
    svgView.animate();
    assertEquals(noShapesOut, output.toString());
    init(); // refresh the output appendable
    svgView.setShapes(testModel.getShapes());
    svgView.animate();
    assertEquals(expectedOut, output.toString());
  }

}