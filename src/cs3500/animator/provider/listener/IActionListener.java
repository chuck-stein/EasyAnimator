/**
 * interface for action listneres. All action listeners share actionPerformed method.
 */
public interface IActionListener {

  /**
   * Performs action dictated by actionlistener.
   * @param description is the action that was performed.
   */
  void actionPerformed(String description);
}