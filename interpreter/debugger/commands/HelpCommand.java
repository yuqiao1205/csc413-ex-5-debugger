package interpreter.debugger.commands;

import interpreter.debugger.Debugger;
import interpreter.debugger.ui.DebuggerCommand;

public class HelpCommand extends DebuggerCommand {

  public HelpCommand(Debugger debugger) {
    super(debugger);
  }

  @Override
  public void execute() {
    System.out.printf("set %nlist %nlocals %nsource %nstep %ncontinue %nexit %n");
  }
}
