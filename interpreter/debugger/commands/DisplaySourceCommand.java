package interpreter.debugger.commands;

import interpreter.debugger.Debugger;
import interpreter.debugger.FunctionEnvironmentRecord;
import interpreter.debugger.ui.DebuggerCommand;

public class DisplaySourceCommand extends DebuggerCommand {

    public DisplaySourceCommand(Debugger debugger) {
        super(debugger);
    }

    @Override
    public void execute() {

        // The line number of execution point
        int currentExecutionLineNo = debugger.getCurrentFunctionEnvironmentRecord().getCurrentLineNumber();

        // Prepare for the range info of current function.
        // Print full source if the current frame is not initialized or no range info in the frame.
        FunctionEnvironmentRecord fer = debugger.getCurrentFunctionEnvironmentRecord();
        boolean hasFunctionSourceRange = (fer != null && fer.getStartLineNumber() != 0 && fer.getEndLineNumber() != 0);
        int functionStartLineNo = fer.getStartLineNumber();
        int functionEndLineNo = fer.getEndLineNumber();

        for (Debugger.Entry entry : debugger.getSourceLines()) {

            // Check if the function range is defined and skip current line if it is out of range
            int currentSourceLineNo = entry.getLineNumber();
            if (hasFunctionSourceRange) {
                if (currentSourceLineNo < functionStartLineNo) {
                    continue;
                } else if (currentSourceLineNo > functionEndLineNo) {
                    break;
                }
            }

            if (currentExecutionLineNo == entry.getLineNumber()) {
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
