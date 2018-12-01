package cs3500.animator.provider.view;

import cs3500.animator.provider.controller.Commands;
import cs3500.animator.provider.model.IEasyAnimatorViewer;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * A visual representation of an {@code IEasyAnimatorViewer} using the Java library Swing. When
 * display is run, it displays the model passed in using 'tweening, where all of the heavy lifting
 * of 'tweening is done in the model. This class uses a {@code Timer} to set how quickly the panel
 * inside repaints itself.
 */
public final class SwingView extends JFrame implements IView {

  private CellPanel canvas;

  /**
   * A constructor that sets the name of the JFrame as well as the default behaviours for the Frame
   * and default CellPane.
   */
  public SwingView() {
    super();
    this.setTitle("ExCELLence");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    canvas = new CellPanel();
    canvas.setBackground(Color.WHITE);

  }

  @Override
  public void display(IEasyAnimatorViewer model) {

    Rectangle rect = model.getDimensions();
    this.setPreferredSize(new Dimension(rect.width, rect.height));
    canvas = new CellPanel();
    canvas.setBackground(Color.WHITE);
    canvas.setPreferredSize(new Dimension(rect.width, rect.height));

    JViewport view = new JViewport();
    view.setSize(rect.width, rect.height);
    view.setView(canvas);
    view.setViewPosition(new Point(rect.x, rect.y));

    JScrollPane scrollBoy = new JScrollPane(view);
    scrollBoy.setWheelScrollingEnabled(true);
    scrollBoy.setBackground(Color.WHITE);
    scrollBoy.setPreferredSize(new Dimension(rect.width, rect.height));
    scrollBoy.setViewport(view);

    this.add(scrollBoy);
    this.pack();
    this.setVisible(true);

    canvas.setModel(model);

  }

  @Override
  public void refresh(int time) {
    canvas.setTime(time);
    canvas.repaint();
  }

  @Override
  public void setListener(Commands controller, IEasyAnimatorViewer model) {
    controller.pause();
  }

  @Override
  public void setModel(IEasyAnimatorViewer model) {
    // cannot load mid-animation
  }


}
