package interpreter.debugger.commands;

import interpreter.debugger.Debugger;
import interpreter.debugger.ui.DebuggerCommand;

public class DisplaySourceCommand extends DebuggerCommand {

    public DisplaySourceCommand(Debugger debugger) {
        super(debugger);
    }

    @Override
    public void execute() {
        int currentLineNo = debugger.getCurrentFunctionEnvironmentRecord().getCurrentLineNumber();
        for (Debugger.Entry entry : debugger.getSourceLines()) {
            if (currentLineNo == entry.getLineNumber()) {
                System.out.print("->");
            } else {
                System.out.print("  ");
            }

            if (entry.getBreakpointLine()) {
                System.out.printf(" * %3d: %s%n", entry.getLineNumber(), entry.getSourceLine());
            } else {
                System.out.printf("   %3d: %s%n", entry.getLineNumber(), entry.getSourceLine());
            }
        }
    }
}
