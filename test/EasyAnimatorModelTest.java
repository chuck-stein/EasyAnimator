import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import EasyAnimator.model.EasyAnimatorModel;
import EasyAnimator.model.IEasyAnimatorModel;
import EasyAnimator.model.Position2D;
import EasyAnimator.model.ShapeType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

public class EasyAnimatorModelTest {

  IEasyAnimatorModel m;
  IEasyAnimatorModel m2;

  @Before
  public void init() {
    m = new EasyAnimatorModel();
    m.createShape(ShapeType.RECTANGLE, "R1", Color.BLUE, new Position2D(340, 155), 10, 17);
    m.createShape(ShapeType.RECTANGLE, "R2", Color.RED, new Position2D(512, 400), 91, 36);
    m.createShape(ShapeType.ELLIPSE, "E1", Color.ORANGE, new Position2D(110, 246), 50, 50);
    m.createState("R1", 40, Color.BLUE, new Position2D(400, 100), 10, 17);
    m.createState("R1", 9, Color.GREEN, new Position2D(400, 100), 10, 17);
    m.createState("R1", 17, Color.BLACK, new Position2D(430, 120), 10, 17);
    m.createState("R2", 26, Color.YELLOW, new Position2D(590, 483), 91, 36);
    m.createState("R2", 31, Color.YELLOW, new Position2D(590, 483), 30, 10);
    m.createState("E1", 50, Color.GREEN, new Position2D(110, 246), 100, 150);

    m2 = new EasyAnimatorModel();
  }

  @Test
  public void testGetMotions1() {
    assertEquals(
        "motion R1   1 340 155 10 17 0 0 255    41 400 100 10 17 0 0 255\n"
            + "motion R1   41 400 100 10 17 0 0 255    50 400 100 10 17 0 255 0\n"
            + "motion R1   50 400 100 10 17 0 255 0    67 430 120 10 17 0 0 0\n\n"
            + "motion R2   1 512 400 91 36 255 0 0    27 590 483 91 36 255 255 0\n"
            + "motion R2   27 590 483 91 36 255 255 0    58 590 483 30 10 255 255 0\n\n"
            + "motion E1   1 110 246 50 50 255 200 0    51 110 246 100 150 0 255 0",
        m.getAllMotions());
  }

  @Test
  public void testGetCurrentMotions() {
    assertEquals("motion R1   1 340 155 10 17 0 0 255    41 400 100 10 17 0 0 255\n" +
            "motion R2   1 512 400 91 36 255 0 0    27 590 483 91 36 255 255 0\n" +
            "motion E1   1 110 246 50 50 255 200 0    51 110 246 100 150 0 255 0",
        m.getCurrentMotions(10)
    );
    assertEquals("motion R1   50 400 100 10 17 0 255 0    67 430 120 10 17 0 0 0\n" +
            "motion R2   27 590 483 91 36 255 255 0    58 590 483 30 10 255 255 0\n",
        m.getCurrentMotions(56)
    );
  }

  @Test
  public void testAddShapesAndAddMotions() {
    assertEquals("", m2.getAllMotions());
    m2.createShape(ShapeType.RECTANGLE, "R", 6, Color.BLACK, new Position2D(1, 1), 2, 2);
    assertEquals("",m2.getAllMotions());
    m2.createStatePars("R", "-deltaT 10 -move 1 0");
    assertEquals("motion R   1 1 1 2 2 0 0 0    11 2 1 2 2 0 0 0", m2.getAllMotions());
  }

  @Test
  public void faultyAddShapes() {
    try {
m2.createShape(null, "R", 6, Color.BLACK, new Position2D(1,1), 2, 1);
  fail();
    } catch (IllegalArgumentException e) {

    }
  }
}
