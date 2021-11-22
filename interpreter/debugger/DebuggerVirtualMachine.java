package interpreter.debugger;

import interpreter.Program;
import interpreter.VirtualMachine;

public class DebuggerVirtualMachine extends VirtualMachine {
  private Debugger debugger;

  public DebuggerVirtualMachine(Program program, Debugger debugger) {
    super(program);

    this.debugger = debugger;
  }
}