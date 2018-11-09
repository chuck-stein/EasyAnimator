import static org.junit.Assert.*;

import cs3500.animator.model.hw05.EasyAnimatorModel;
import cs3500.animator.model.hw05.EasyAnimatorModel.EasyAnimatorModelBuilder;
import cs3500.animator.model.hw05.IEasyAnimatorModel;
import cs3500.animator.util.AnimationBuilder;
import org.junit.Before;
import org.junit.Test;

public class AnimationBuilderTest {

  IEasyAnimatorModel m1;

  AnimationBuilder<EasyAnimatorModel> builder;

  @Before
  public void setUp() {
    m1 = new EasyAnimatorModel();

    builder = new EasyAnimatorModelBuilder();
  }


  @Test
  public void setBounds() {
    builder.setBounds(1, 2, 3, 4);
    m1 = builder.build();
    assertEquals(1, m1.getCanvasX());
    assertEquals(2, m1.getCanvasY());
    assertEquals(3, m1.getCanvasWidth());
    assertEquals(4, m1.getCanvasHeight());
  }

  @Test
  public void badWidthAndHeight() {
    try {
      builder.setBounds(1, 2, -30, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Width and Height must be positive numbers", e.getMessage());

    }

    try {
      builder.setBounds(1, 2, 30, -1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Width and Height must be positive numbers", e.getMessage());

    }
  }

  @Test
  public void declareShape() {
    builder.declareShape("R", "rectangle");
    builder.declareShape("E", "ellipse");
    m1 = builder.build();

    assertEquals("R", m1.getShapes().get(0).getName());
    assertEquals("E", m1.getShapes().get(1).getName());

  }

  @Test
  public void shapeNameUsed() {
    builder.declareShape("R", "rectangle");

    try {
      builder.declareShape("R", "rectangle");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Shape name already exists.", e.getMessage());
    }
  }

  @Test
  public void shapeTypeNotSupported() {
    builder.declareShape("R", "rectangle");

    try {
      builder.declareShape("T", "triangle");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("triangle is not a supported shape.", e.getMessage());
    }
  }

  @Test
  public void addMotion() {

    builder.declareShape("R", "rectangle");
    builder.addMotion("R", 1, 200, 200, 50, 100, 255, 0, 0, 10,
        200, 200, 50, 100, 255, 0, 0);
    m1 = builder.build();
    assertEquals("1 200 200 50 100 255 0 0    10 200 200 50 100 255 0 0"
        , m1.getShapes().get(0).getMotions().get(0).toString());

  }

  @Test
  public void addMotionToNoShape() {
    try {
      builder.addMotion("R1", 1, 200, 200, 50, 100, 255, 0, 0, 10,
          200, 200, 50, 100, 255, 0, 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("There are no shapes with the given name.", e.getMessage());
    }
  }

}

