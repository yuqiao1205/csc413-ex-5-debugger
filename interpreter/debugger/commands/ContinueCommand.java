package interpreter.debugger.commands;

import interpreter.debugger.Debugger;
import interpreter.debugger.DebuggerVirtualMachine;
import interpreter.debugger.ui.DebuggerCommand;

public class ContinueCommand extends DebuggerCommand {

    public ContinueCommand(Debugger debugger) {
        super(debugger);
    }

    @Override
    public void execute() {
        debugger.getDebuggerVirtualMachine().debugExecution(DebuggerVirtualMachine.DebugExecutionEnum.CONTINUE);
    }

}
