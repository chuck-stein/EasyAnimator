import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import cs3500.animator.controller.EasyAnimatorSimpleController;
import cs3500.animator.controller.IEasyAnimatorController;
import cs3500.animator.model.hw05.EasyAnimatorModel.EasyAnimatorModelBuilder;
import cs3500.animator.model.hw05.IEasyAnimatorModel;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.IEasyAnimatorView;
import cs3500.animator.view.SvgEasyAnimatorView;
import cs3500.animator.view.TextEasyAnimatorView;
import java.io.StringReader;
import org.junit.Test;

/**
 * Tests for the interface representing an Easy Animator controller.
 */
public class IEasyAnimatorControllerTest {


  StringBuilder output = new StringBuilder();
  StringBuilder output2 = new StringBuilder();
  StringReader smallDemo = new StringReader("canvas 200 70 360 360\n" + "shape R rectangle\n"
      + "motion R 1  200 200 50 100 255 0  0    10  200 200 50 100 255 0  0\n"
      + "motion R 10 200 200 50 100 255 0  0    50  300 300 50 100 255 0  0\n"
      + "motion R 50 300 300 50 100 255 0  0    51  300 300 50 100 255 0  0\n"
      + "motion R 51 300 300 50 100 255 0  0    70  300 300 25 100 255 0  0\n"
      + "motion R 70 300 300 25 100 255 0  0    100 200 200 25 100 255 0  0\n"
      + "\n"
      + "shape C ellipse\n"
      + "motion C 6  440 70 120 60 0 0 255 \n"
      + "         20 440 70 120 60 0 0 255 \n"
      + "motion C 20 440 70 120 60 0 0 255      50 440 250 120 60 0 0 255\n"
      + "motion C 50 440 250 120 60 0 0 255     70 440 370 120 60 0 170 85\n"
      + "motion C 70 440 370 120 60 0 170 85    80 440 370 120 60 0 255 0\n"
      + "motion C 80 440 370 120 60 0 255 0     100 440 370 120 60 0 255 0"
  );
  IEasyAnimatorView textView = new TextEasyAnimatorView(200, 70, 360,
      360, 50, output);
  IEasyAnimatorModel model = AnimationReader.parseFile(smallDemo, new EasyAnimatorModelBuilder());

  IEasyAnimatorView svgView = new SvgEasyAnimatorView(200, 70, 360,
      360, 50, output2);

  IEasyAnimatorController controller = new EasyAnimatorSimpleController(textView, model);
  IEasyAnimatorController controller2 = new EasyAnimatorSimpleController(svgView, model);

  @Test
  public void controlWithTextView() {
    assertEquals("", output.toString());
    controller.startControlling();
    assertEquals("canvas 200 70 360 360\n"
        + "shape R rectangle\n"
        + "motion R 1 200 200 50 100 255 0 0    10 200 200 50 100 255 0 0\n"
        + "motion R 10 200 200 50 100 255 0 0    50 300 300 50 100 255 0 0\n"
        + "motion R 50 300 300 50 100 255 0 0    51 300 300 50 100 255 0 0\n"
        + "motion R 51 300 300 50 100 255 0 0    70 300 300 25 100 255 0 0\n"
        + "motion R 70 300 300 25 100 255 0 0    100 200 200 25 100 255 0 0\n"
        + "shape C ellipse\n"
        + "motion C 6 440 70 120 60 0 0 255    20 440 70 120 60 0 0 255\n"
        + "motion C 20 440 70 120 60 0 0 255    50 440 250 120 60 0 0 255\n"
        + "motion C 50 440 250 120 60 0 0 255    70 440 370 120 60 0 170 85\n"
        + "motion C 70 440 370 120 60 0 170 85    80 440 370 120 60 0 255 0\n"
        + "motion C 80 440 370 120 60 0 255 0    100 440 370 120 60 0 255 0", output.toString());
  }

  @Test
  public void nullConstructor() {
    try {
      new EasyAnimatorSimpleController(null, null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("View and Model must not be null", e.getMessage());
    }
  }

  @Test
  public void controlWithSVGView() {
    assertEquals("", output2.toString());
    controller2.startControlling();
    assertEquals(
        "<svg width=\"360\" height=\"360\" version=\"1.1\""
            + " xmlns=\"http://www.w3.org/2000/svg\">\n"
            + "<rect id=\"R\" x=\"0.0\" y=\"130.0\" width=\"50.0\""
            + " height=\"100.0\" fill=\"rgb(255,0,0)\" visibility=\"hidden\" >\n"
            + "<animate attributeType=\"xml\" begin=\"20.0ms\" dur=\"1ms\""
            + " attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\" />\n"
            + "<animate attributeType=\"xml\" begin=\"200.0ms\" dur=\"800.0ms\""
            + " attributeName=\"x\" from=\"0.0\" to=\"100.0\" fill=\"freeze\" />\n"
            + "<animate attributeType=\"xml\" begin=\"200.0ms\" dur=\"800.0ms\" "
            + "attributeName=\"y\" from=\"130.0\" to=\"230.0\" fill=\"freeze\" />\n"
            + "<animate attributeType=\"xml\" begin=\"1020.0ms\" dur=\"380.0ms\" "
            + "attributeName=\"width\" from=\"50.0\" to=\"25.0\" fill=\"freeze\" />\n"
            + "<animate attributeType=\"xml\" begin=\"1400.0ms\" dur=\"600.0ms\" "
            + "attributeName=\"x\" from=\"100.0\" to=\"0.0\" fill=\"freeze\" />\n"
            + "<animate attributeType=\"xml\" begin=\"1400.0ms\" dur=\"600.0ms\" "
            + "attributeName=\"y\" from=\"230.0\" to=\"130.0\" fill=\"freeze\" />\n"
            + "</rect>\n"
            + "<ellipse id=\"C\" cx=\"240.0\" cy=\"0.0\" rx=\"60.0\" ry=\"30.0\" "
            + "fill=\"rgb(0,0,255)\" visibility=\"hidden\" >\n"
            + "<animate attributeType=\"xml\" begin=\"120.0ms\" dur=\"1ms\" "
            + "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\" />\n"
            + "<animate attributeType=\"xml\" begin=\"400.0ms\" dur=\"600.0ms\" "
            + "attributeName=\"cy\" from=\"0.0\" to=\"180.0\" fill=\"freeze\" />\n"
            + "<animate attributeType=\"xml\" begin=\"1000.0ms\" dur=\"400.0ms\" "
            + "attributeName=\"cy\" from=\"180.0\" to=\"300.0\" fill=\"freeze\" />\n"
            + "<animate attributeType=\"xml\" begin=\"1000.0ms\" dur=\"400.0ms\" "
            + "attributeName=\"fill\" values=\"rgb(0,0,255);rgb(0,170,85)\" fill=\"freeze\" />\n"
            + "<animate attributeType=\"xml\" begin=\"1400.0ms\" dur=\"200.0ms\" "
            + "attributeName=\"fill\" values=\"rgb(0,170,85);rgb(0,255,0)\" fill=\"freeze\" />\n"
            + "</ellipse>\n"
            + "</svg>", output2.toString());
  }

}