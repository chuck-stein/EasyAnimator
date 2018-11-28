import Listener.IActionListener;

/**
 * Methods that all interfaces will implement. All interfaces will be able to display. As well as
 * receive input and clear input.
 */
public interface IView {


  /**
   * Get user text field.
   *
   * @return user text field.
   */
  String getInputString();

  /**
   * Clear user text field.
   */
  void clearInputString();

  /**
   * Set the listener for any actions.
   */
  void setListener(IActionListener listener);

  /**
   * Display this view.
   */
  void display();
}




