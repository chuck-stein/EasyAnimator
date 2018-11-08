package cs3500.animator.view;

import cs3500.animator.model.hw05.ReadableShape;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class SwingBasedEasyAnimatorView extends JFrame implements IEasyAnimatorView{
private ShapePanel shapePanel;
  private Timer timer;

  private int ticksPerSecond;


  public SwingBasedEasyAnimatorView(int canvasX, int canvasY, int canvasWidth, int canvasHeight, int ticksPerSecond) {
    super();

    this.timer = new Timer();

this.ticksPerSecond = ticksPerSecond;


    this.setTitle("The Animation!");
    this.setSize(canvasWidth,canvasHeight);

    this.setLayout(new BorderLayout());
    shapePanel = new ShapePanel(-canvasX, -canvasY);
  shapePanel.setPreferredSize(new Dimension(canvasWidth,canvasHeight));

    JScrollPane scrollBarAndPane = new JScrollPane(shapePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    this.add(scrollBarAndPane, BorderLayout.CENTER);
    this.pack();

  }


  @Override
  public void setOutput(Appendable output) {

  }

  @Override
  public void animate() {
    this.setVisible(true);
    TimerTask advanceTime =new TimerTask() {
      @Override
      public void run() {
        updateImage();
      }
    };
    timer.schedule(advanceTime,0,1000/ticksPerSecond);

  }

  @Override
  public void setShapes(List<ReadableShape> shapes) {
shapePanel.setShapes(shapes);
  }


  private void updateImage() {
    this.repaint();
    shapePanel.updateTick();
  }
}
