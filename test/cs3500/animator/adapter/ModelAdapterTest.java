package cs3500.animator.adapter;

import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import cs3500.animator.model.hw05.EasyAnimatorModel;
import cs3500.animator.model.hw05.IEasyAnimatorModel;
import cs3500.animator.model.hw05.IReadableShape;
import cs3500.animator.model.hw05.ShapeType;
import cs3500.animator.provider.model.Posn;
import cs3500.animator.provider.model.Shapes;
import cs3500.animator.provider.model.Size;

import static org.junit.Assert.*;

/**
 * Tests for the pseudo-adapter from our representation of a read-only model (IReadableShapes and
 * canvas settings) to the provided representation of a read-only model (the interface
 * IEasyAnimatorViewer).
 */
public class ModelAdapterTest {

  private ModelAdapter adapter;
  private List<IReadableShape> shapes;

  @Before
  public void init() {
    adapter = new ModelAdapter();
    IEasyAnimatorModel originalModel = new EasyAnimatorModel();
    originalModel.addShape(ShapeType.RECTANGLE, "rectangle");
    originalModel.addShape(ShapeType.ELLIPSE, "ellipse");
    originalModel.addMotion("rectangle", 1, 10, 15, 50, 80, 255, 200, 100, 25, 30, 50, 100, 250,
            100, 55, 172);
    originalModel.addMotion("ellipse", 1, 433, 206, 95, 82, 50, 60, 200, 40, 101, 102, 103, 104,
            105, 106, 107);
    shapes = originalModel.getShapes();
  }

  @Test
  public void testSetModelInfo() {
    assertEquals(new ArrayList<String>(), adapter.getAllShapeNames());
    assertEquals(new Rectangle(0, 0, 0, 0), adapter.getDimensions());

    adapter.setModelInfo(shapes, new Rectangle(100, 200, 300, 400));

    List<String> expectedNames = new ArrayList<String>();
    expectedNames.add("rectangle");
    expectedNames.add("ellipse");

    assertEquals(expectedNames, adapter.getAllShapeNames());
    assertEquals(new Rectangle(100, 200, 300, 400), adapter.getDimensions());
  }

  @Test(expected=IllegalArgumentException.class)
  public void testSetModelInfoNullShapes() {
    adapter.setModelInfo(null, new Rectangle(30, 20, 40, 5));
  }

  @Test(expected=IllegalArgumentException.class)
  public void testSetModelInfoNullCanvas() {
    adapter.setModelInfo(shapes, null);
  }

  @Test
  public void testGetCurrentColor() {
    initAdapter();
    assertEquals(new Color(100, 55, 172), adapter.getCurrentColor("rectangle", 25));
  }

  @Test(expected=IllegalArgumentException.class)
  public void testGetCurrentColorInvalidShape() {
    initAdapter();
    adapter.getCurrentColor("this shape does not exist", 7);
  }

  @Test(expected=IllegalArgumentException.class)
  public void testGetCurrentColorInvalidTime() {
    initAdapter();
    adapter.getCurrentColor("ellipse", 41);
  }

  @Test(expected=IllegalArgumentException.class)
  public void testGetCurrentColorNullName() {
    initAdapter();
    adapter.getCurrentColor(null, 6);
  }

  @Test
  public void testGetCurrentPosn() {
    initAdapter();
    assertEquals(new Posn(433, 206), adapter.getCurrentPosn("ellipse", 1));
  }

  @Test(expected=IllegalArgumentException.class)
  public void testGetCurrentPosnNullName() {
    initAdapter();
    adapter.getCurrentPosn(null, 3);
  }

  @Test(expected=IllegalArgumentException.class)
  public void testGetCurrentPosnInvalidShape() {
    initAdapter();
    adapter.getCurrentPosn("asdfghjkl;", 5);
  }

  @Test(expected=IllegalArgumentException.class)
  public void testGetCurrentPosnInvalidTime() {
    initAdapter();
    adapter.getCurrentPosn("rectangle", -12);
  }

  @Test
  public void testGetCurrentSize() {
    initAdapter();
    assertEquals(new Size(103, 104), adapter.getCurrentSize("ellipse", 40));
  }

  @Test(expected=IllegalArgumentException.class)
  public void testGetCurrentSizeInvalidName() {
    initAdapter();
    adapter.getCurrentSize("invalid", 5);
  }

  @Test(expected=IllegalArgumentException.class)
  public void testGetCurrentSizeInvalidTime() {
    initAdapter();
    adapter.getCurrentSize("rectangle", 0);
  }

  @Test(expected=IllegalArgumentException.class)
  public void testGetCurrentSizeNullName() {
    initAdapter();
    adapter.getCurrentSize(null, 45);
  }

  @Test
  public void testIsVisibleAtTimeAfter() {
    initAdapter();
    assertTrue(adapter.isVisibleAtTime("rectangle", 25));
    assertFalse(adapter.isVisibleAtTime("rectangle", 26));
  }

  @Test
  public void testIsVisibleAtTimeBefore() {
    initAdapter();
    assertFalse(adapter.isVisibleAtTime("ellipse", 0));
    assertTrue(adapter.isVisibleAtTime("ellipse", 1));
  }

  @Test(expected=IllegalArgumentException.class)
  public void testIsVisibleAtTimeNullName() {
    initAdapter();
    adapter.getCurrentSize(null, 4);
  }

  @Test(expected=IllegalArgumentException.class)
  public void testIsVisibleAtTimeInvalidName() {
    initAdapter();
    adapter.getCurrentSize(" ", 9);
  }

  @Test
  public void testGetAllTimes() {
    initAdapter();
    List<Integer> expectedTimes = new ArrayList<Integer>();
    expectedTimes.add(1);
    expectedTimes.add(25);
    assertEquals(expectedTimes, adapter.getAllTimes("rectangle"));
  }

  @Test(expected=IllegalArgumentException.class)
  public void testGetAllTimesNullName() {
    initAdapter();
    adapter.getAllTimes(null);
  }

  @Test(expected=IllegalArgumentException.class)
  public void testGetAllTimesInvalidShape() {
    initAdapter();
    adapter.getAllTimes("help");
  }

  @Test
  public void testGetAllShapeNames() {
    initAdapter();
    List<String> expectedNames = new ArrayList<String>();
    expectedNames.add("rectangle");
    expectedNames.add("ellipse");
    assertEquals(expectedNames, adapter.getAllShapeNames());
  }

  @Test
  public void testGetAllShapeNamesEmpty() {
    assertEquals(new ArrayList<String>(), adapter.getAllShapeNames());
  }

  @Test
  public void getDimensions() {
    initAdapter();
    assertEquals(new Rectangle(100, 200, 300, 400), adapter.getDimensions());
  }

  @Test
  public void getShapeTypeEllipse() {
    initAdapter();
    assertEquals(Shapes.ELLIPSE, adapter.getShapeType("ellipse"));
  }

  @Test
  public void getShapeTypeRectangle() {
    initAdapter();
    assertEquals(Shapes.RECTANGLE, adapter.getShapeType("rectangle"));
  }

  @Test(expected=IllegalArgumentException.class)
  public void getShapeTypeNullName() {
    initAdapter();
    adapter.getShapeType(null);
  }

  @Test(expected=IllegalArgumentException.class)
  public void getShapeTypeInvalidName() {
    initAdapter();
    adapter.getShapeType("goodbye OOD");
  }

  @Test
  public void isAnimationOverAtTime() {
    initAdapter();
    assertFalse(adapter.isAnimationOverAtTime(-1));
    assertFalse(adapter.isAnimationOverAtTime(0));
    assertFalse(adapter.isAnimationOverAtTime(1));
    assertFalse(adapter.isAnimationOverAtTime(26));
    assertFalse(adapter.isAnimationOverAtTime(40));
    assertTrue(adapter.isAnimationOverAtTime(41));
  }

  /**
   * Sets the test ModelAdapter's info to two simple shapes and a default canvas for testing
   * purposes.
   */
  private void initAdapter() {
    adapter.setModelInfo(shapes, new Rectangle(100, 200, 300, 400));
  }

}