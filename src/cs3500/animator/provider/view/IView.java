package cs3500.animator.provider.view;

import cs3500.animator.provider.controller.Commands;
import cs3500.animator.provider.model.IEasyAnimatorViewer;

/**
 * Represents a view for the data stored in an {@code IEasyAnimatorViewer}. Currently, three views
 * are supported, one based on a text log, an SVG view, and one using Java Swing. The two methods
 * supported are to display the model that is given, and sets the speed of the output (in
 * ticks/second), and what file this View will output to.
 */
public interface IView {

  /**
   * Displays the given {@code IEasyAnimatorViewer} model.
   *
   * @param model the model to be displayed
   */
  void display(IEasyAnimatorViewer model);

  /**
   * Refreshes this view to be repainted at the given time.
   *
   * @param time the time that this view will be repainted at
   */
  void refresh(int time);

  /**
   * Hooks up the buttons in this view to the controller.
   *
   * @param controller the controller to be hooked up to
   * @param model the model that will be used to extract data
   */
  void setListener(Commands controller, IEasyAnimatorViewer model);

  /**
   * Sets the model of this view to the one passed in.
   * @param model the model to be set
   */
  void setModel(IEasyAnimatorViewer model);

}
