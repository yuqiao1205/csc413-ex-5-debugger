package interpreter.debugger.commands;

import interpreter.debugger.Debugger;
import interpreter.debugger.ui.DebuggerCommand;

public class ListBreakpointsCommand extends DebuggerCommand {

    public ListBreakpointsCommand(Debugger debugger) {
        super(debugger);
    }

    @Override
    public void execute() {
        for (Debugger.Entry entry : debugger.getSourceLines()) {
            if (entry.getBreakpointLine()) {
                System.out.printf(" * %5d: %s%n", entry.getLineNumber(), entry.getSourceLine());
            }
        }
    }
}

