import static org.junit.Assert.*;

import cs3500.animator.model.hw05.EasyAnimatorModel;
import cs3500.animator.model.hw05.IEasyAnimatorModel;
import cs3500.animator.model.hw05.IReadableShape;
import cs3500.animator.model.hw05.ShapeType;
import java.awt.List;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class IEasyAnimatorModelTest {

  IEasyAnimatorModel m1;
  IEasyAnimatorModel m2;

  @Before
  public void setUp() {
    m1 = new EasyAnimatorModel();
    m2 = new EasyAnimatorModel();
  }

  @Test
  public void canvasMethods() {
    m1.setCanvas(1, 2, 3, 4);
    assertEquals(1, m1.getCanvasX());
    assertEquals(2, m1.getCanvasY());
    assertEquals(3, m1.getCanvasWidth());
    assertEquals(4, m1.getCanvasHeight());

    m1.setCanvas(5, 6, 7, 8);
    assertEquals(5, m1.getCanvasX());
    assertEquals(6, m1.getCanvasY());
    assertEquals(7, m1.getCanvasWidth());
    assertEquals(8, m1.getCanvasHeight());
  }

  @Test
  public void testBadCanvasSetting() {
    try {
      m1.setCanvas(2, 2, -2, 2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Width and Height must be positive numbers", e.getMessage());

    }

    try {
      m1.setCanvas(2, 2, 2, -2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Width and Height must be positive numbers", e.getMessage());
    }
  }


  @Test
  public void addShape() {

    assertEquals(new ArrayList<>(), m1.getShapes());
    assertEquals(new ArrayList<>(), m2.getShapes());
    m1.addShape(ShapeType.RECTANGLE, "R");
    m1.addShape(ShapeType.ELLIPSE, "E");
    m2.addShape(ShapeType.RECTANGLE, "disk1");
    assertEquals(1, m2.getShapes().size());
    assertEquals(2, m1.getShapes().size());
  }

  @Test
  public void shapeSameNameAdding() {
    m1.addShape(ShapeType.ELLIPSE, "E");
    try {
      m1.addShape(ShapeType.RECTANGLE, "E");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Shape name already exists.", e.getMessage());
    }
  }

  @Test
  public void addMotion() {
    m1.addShape(ShapeType.RECTANGLE, "R");
    m1.addMotion("R", 1, 200, 200, 50, 100, 255, 0, 0, 10,
        200, 200, 50, 100, 255, 0, 0);
    assertEquals(1, m1.getShapes().get(0).getMotions().size());
    m1.addMotion("R", 10, 200, 200, 50, 100, 255, 0, 0, 11,
        200, 200, 50, 100, 255, 0, 0);
    assertEquals(2, m1.getShapes().get(0).getMotions().size());
  }

  @Test
  public void addMotionToShapeNoThere() {
    try {
      m1.addMotion("R1", 1, 200, 200, 50, 100, 255, 0, 0, 10,
          200, 200, 50, 100, 255, 0, 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("There are no shapes with the given name.", e.getMessage());
    }
  }

  @Test
  public void addMotionThatOverlaps() {
    m1.addShape(ShapeType.RECTANGLE, "R1");
    m1.addMotion("R1", 1, 200, 200, 50, 100, 255, 0, 0, 10,
        200, 200, 50, 100, 255, 0, 0);

    try {
      m1.addMotion("R1", 1, 200, 200, 50, 100, 255, 0, 0, 10,
          200, 200, 50, 100, 255, 0, 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Motions cannot overlap.", e.getMessage());
    }
  }

  @Test
  public void getShapes() {
    assertEquals(new ArrayList<>(), m1.getShapes());
    assertEquals(new ArrayList<>(), m2.getShapes());
    m1.addShape(ShapeType.RECTANGLE, "R");
    m1.addShape(ShapeType.ELLIPSE, "E");
    m2.addShape(ShapeType.RECTANGLE, "disk1");

    assertEquals("R",m1.getShapes().get(0).getName());
    assertEquals("E",m1.getShapes().get(1).getName());
    assertEquals("disk1",m2.getShapes().get(0).getName());
  }
}