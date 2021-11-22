package interpreter.debugger;

import interpreter.Interpreter;
import interpreter.Program;
import interpreter.debugger.ui.DebuggerShell;

public class Debugger extends Interpreter {
  private DebuggerShell shell;

  public Debugger(String baseFileName) {
    super(baseFileName);
    // Update to add the correct extensions to the base file name to
    // load the byte code file, as well as to load the source file
  }

  @Override
  public void run() {
    shell = new DebuggerShell(this);

    Program program = byteCodeLoader.loadCodes();
    DebuggerVirtualMachine vm = new DebuggerVirtualMachine(program, this);

    shell.prompt().execute();
  }
}