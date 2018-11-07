package cs3500.animator.view;

import cs3500.animator.model.hw05.ReadableShape;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JFrame;

public class SwingBasedEasyAnimatorView extends JFrame implements IEasyAnimatorView{
private ShapePanel shapePanel;

  public SwingBasedEasyAnimatorView() {
    super();
    this.setTitle("The Animation!");
    this.setSize(1000,1500);

    this.setLayout(new BorderLayout());
    shapePanel = new ShapePanel();
    shapePanel.setPreferredSize(new Dimension(1000, 1000));
    this.add(shapePanel, BorderLayout.CENTER);
    this.pack();
    this.setVisible(true);
  }


  @Override
  public void setOutput(Appendable output) {

  }

  @Override
  public void refresh() {
this.repaint();
  }

  @Override
  public void setShapes(List<ReadableShape> shapes) {
shapePanel.setShapes(shapes);
  }

  @Override
  public void setCanvas(int x, int y, int w, int h) {

    this.setSize(w,h);
    shapePanel.setPreferredSize(new Dimension(w,h));
  }

  @Override
  public void updateTick() {
    shapePanel.updateTick();
  }
}
