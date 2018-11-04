package cs3500.animator.controller;

public interface IEasyAnimatorController {

  /**
   * Process a given string command and returns whether it performed or not.
   *
   * @param command the command given, including any parameters
   * @return status or error message
   */
  String processCommand(String command);

  /**
   * Start the program, i.e. give control to the controller
   */
  void go();
}
