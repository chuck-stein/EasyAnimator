package cs3500.animator.model.hw05;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

abstract class AShape implements IShape {

  protected final String name;
  protected final ShapeType type;
  protected final List<IMotion> motions;

  AShape (ShapeType type, String name, List<IMotion> motions) {
    if (Objects.isNull(name)) {
      throw new IllegalArgumentException("Name cannot be null");
    }
    this.type = type;
    this.name = name;
    this.motions = motions;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public ShapeType getType() {
    return type;
  }

  @Override
  public List<IMotion> getMotions() {
    return motions;
  }

  @Override
  public IState getCurrentState(int t) {

  }


}
