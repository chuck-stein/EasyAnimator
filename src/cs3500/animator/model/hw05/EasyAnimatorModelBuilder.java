package cs3500.animator.model.hw05;

import cs3500.animator.util.AnimationBuilder;

public class EasyAnimatorModelBuilder implements AnimationBuilder<EasyAnimatorModel> {

  EasyAnimatorModel model;

  public EasyAnimatorModelBuilder() {
    model = new EasyAnimatorModel();
  }

  @Override
  public EasyAnimatorModel build() {
    return model;
  }

  @Override
  public AnimationBuilder<EasyAnimatorModel> setBounds(int x, int y, int width, int height) {
    model.setCanvas(x, y, width, height);
    return this;
  }

  @Override
  public AnimationBuilder<EasyAnimatorModel> declareShape(String name, String type) {

    model.addShape(determineShapeType(type), name);
    return this;
  }

  @Override
  public AnimationBuilder<EasyAnimatorModel> addMotion(String name, int t1, int x1, int y1, int w1,
      int h1, int r1, int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2, int g2,
      int b2) {

    model.addMotion(name, t1, x1, y1, w1, h1, r1, g1, b1, t2, x2, y2, w2, h2, r2, g2, b2);

    return this;
  }

  @Override
  public AnimationBuilder<EasyAnimatorModel> addKeyframe(String name, int t, int x, int y, int w,
      int h, int r, int g, int b) {
    return this;
  }

  private ShapeType determineShapeType(String string) {
    switch (string) {
      case ("rectangle"):
        return ShapeType.RECTANGLE;
      case ("ellipse"):
        return ShapeType.ELLIPSE;
      default:
        throw new IllegalArgumentException(String.format("%d not a supported shape."));
    }
  }
}
