package interpreter.debugger.ui;

import interpreter.debugger.Debugger;

public abstract class DebuggerCommand {

    protected Debugger debugger;

    public DebuggerCommand(Debugger debugger) {
        this.debugger = debugger;
    }

    public abstract void execute();
}