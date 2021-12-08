package interpreter.debugger.ui;

import interpreter.debugger.Debugger;
import interpreter.debugger.commands.ContinueCommand;
import interpreter.debugger.commands.DisplaySourceCommand;
import interpreter.debugger.commands.ExitCommand;
import interpreter.debugger.commands.HelpCommand;
import interpreter.debugger.commands.ListBreakpointsCommand;
import interpreter.debugger.commands.LocalsVariablesCommand;
import interpreter.debugger.commands.SetBreakpointCommand;
import interpreter.debugger.commands.StepCommand;

import java.util.Scanner;

public class DebuggerShell {

    private static Scanner SCANNER = new Scanner(System.in);

    private Debugger debugger;

    public DebuggerShell(Debugger debugger) {
        this.debugger = debugger;
    }

    public DebuggerCommand prompt() {

        while (true) {
            // Step 1: Print help message
            System.out.printf("Type ? for help%n>");

            // Step 2: Scanner
            String line = SCANNER.nextLine().trim();

            // Step 3: Parse the input and validate, show error and return step 1 if not correct. Create command and return
            switch (line) {
                case "?":
                    return new HelpCommand(debugger);
                case "set":
                    return new SetBreakpointCommand(debugger);
                case "list":
                    return new ListBreakpointsCommand(debugger);
                case "locals":
                    return new LocalsVariablesCommand(debugger);
                case "source":
                    return new DisplaySourceCommand(debugger);
                case "step":
                    return new StepCommand(debugger);
                case "continue":
                    return new ContinueCommand(debugger);
                case "exit":
                    return new ExitCommand(debugger);

                default:
                    System.out.printf("Invalid input " + line + "!");
            }
        }


    }
}