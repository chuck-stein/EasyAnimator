package cs3500.animator.model.hw05;

public interface IMotion {


  String toString();

  int getStartTime();

  int getEndTime();

  IState getIntermediateState(int t);

}
