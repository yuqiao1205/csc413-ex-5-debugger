package interpreter.debugger.commands;

import interpreter.debugger.Debugger;
import interpreter.debugger.DebuggerVirtualMachine;
import interpreter.debugger.ui.DebuggerCommand;

/**
 * Step over one byte code.
 */
public class StepCommand extends DebuggerCommand {

    public StepCommand(Debugger debugger) {
        super(debugger);
    }

    @Override
    public void execute() {
        debugger.getDebuggerVirtualMachine().debugExecution(DebuggerVirtualMachine.DebugExecutionEnum.STEP);
    }
}
