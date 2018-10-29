import static org.junit.Assert.assertEquals;

import cs3500.easyanimator.model.hw05.Position2D;
import org.junit.Test;

public class Position2DTest {

  Position2D p1 = new Position2D(1, 2);
  Position2D p2 = new Position2D(3, 1);

  @Test
  public void getX() {
    assertEquals(1, p1.getX(), 0.0001);
    assertEquals(3, p2.getX(), 0.0001);
  }

  @Test
  public void getY() {
    assertEquals(2, p1.getY(), 0.0001);
    assertEquals(1, p2.getY(), 0.0001);
  }
}