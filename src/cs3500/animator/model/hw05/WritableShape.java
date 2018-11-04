package cs3500.animator.model.hw05;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a shape in the animation model. Shapes have a name (their identifier) and a list of
 * motions. INVARIANTS: - The shape's name will never be null.
 */
class WritableShape extends AShape {

  WritableShape (ShapeType type, String name) {
    super(type, name, new ArrayList<IMotion>());
  }

  void addMotion(int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
                 int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2)
          throws IllegalArgumentException {
    if (overlaps(t1, t2)) {
      throw new IllegalArgumentException("Motions cannot overlap.");
    }
    int i = findNewIndex(t1);
    motions.add(i, new Motion(type, t1, x1, y1, w1, h1, r1, g1, b1, t2, x2, y2, w2, h2, r2, g2,
            b2));
  }

  private boolean overlaps(int newStartT, int newEndT) {
    for (IMotion m : motions) {
      if (m.getStartTime() < newEndT && m.getEndTime() > newStartT) {
        return true;
      }
    }
    return false;
  }

  private int findNewIndex(int newStartT) {
    for (int i = 0; i < motions.size(); i++) {
      if (newStartT < motions.get(i).getStartTime()) {
        return i;
      }
    }
    return motions.size();
  }

}

