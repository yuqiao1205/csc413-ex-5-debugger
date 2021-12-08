package interpreter.debugger.commands;

import interpreter.debugger.Debugger;
import interpreter.debugger.FunctionEnvironmentRecord;
import interpreter.debugger.RunTimeStack;
import interpreter.debugger.ui.DebuggerCommand;

public class LocalsVariablesCommand extends DebuggerCommand {

    public LocalsVariablesCommand(Debugger debugger) {
        super(debugger);
    }

    @Override
    public void execute() {
        FunctionEnvironmentRecord fer = debugger.getCurrentFunctionEnvironmentRecord();
        RunTimeStack runTimeStack = debugger.getDebuggerVirtualMachine().getRunTimeStack();

        fer.getSymbols().forEach((name, binder) -> System.out.printf("%s:%d%n", name, runTimeStack.getValue((int) binder.getValue())));
    }

}
