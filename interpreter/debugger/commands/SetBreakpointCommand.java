package interpreter.debugger.commands;

import interpreter.debugger.Debugger;
import interpreter.debugger.ui.DebuggerCommand;

import java.util.Scanner;

public class SetBreakpointCommand extends DebuggerCommand {

    public SetBreakpointCommand(Debugger debugger) {
        super(debugger);
    }

    @Override
    public void execute() {
        Scanner Object = new Scanner(System.in);
        System.out.println("Enter line number:");
        String input = Object.nextLine();

        int lineNumber = Integer.parseInt(input);

        for (Debugger.Entry entry : debugger.getSourceLines()) {
            if (lineNumber == entry.getLineNumber()) {
                entry.setBreakpointLine(!entry.getBreakpointLine());
            }
        }
    }
}
