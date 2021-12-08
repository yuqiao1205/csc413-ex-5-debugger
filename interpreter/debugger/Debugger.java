package interpreter.debugger;

import interpreter.Interpreter;
import interpreter.Program;
import interpreter.debugger.ui.DebuggerCommand;
import interpreter.debugger.ui.DebuggerShell;
import interpreter.debugger.commands.ExitCommand;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Debugger extends Interpreter {

    /**
     * Entry for source entry.
     */
    public class Entry {
        private int lineNumber;
        private String sourceLine;
        private Boolean isBreakpointLine;

        Entry(int lineNumber, String sourceLine, Boolean isBreakpointLine) {
            this.lineNumber = lineNumber;
            this.sourceLine = sourceLine;
            this.isBreakpointLine = isBreakpointLine;
        }

        public int getLineNumber() {
            return lineNumber;
        }

        public String getSourceLine() {
            return sourceLine;
        }

        public Boolean getBreakpointLine() {
            return isBreakpointLine;
        }

        public void setBreakpointLine(Boolean breakpointLine) {
            isBreakpointLine = breakpointLine;
        }
    }

    private DebuggerVirtualMachine debuggerVirtualMachine;

    public DebuggerVirtualMachine getDebuggerVirtualMachine() {
        return debuggerVirtualMachine;
    }

    private List<Entry> sourceLines = new ArrayList<>();

    public List<Entry> getSourceLines() {
        return sourceLines;
    }

    public void pushEmptyFunctionEnvironmentRecord() {
        functionEnvironmentRecordStack.push(new FunctionEnvironmentRecord());
    }

    public FunctionEnvironmentRecord popFunctionEnvironmentRecord() {
        return functionEnvironmentRecordStack.pop();
    }

    public FunctionEnvironmentRecord getCurrentFunctionEnvironmentRecord() {
        return this.functionEnvironmentRecordStack.peek();
    }

    private Stack<FunctionEnvironmentRecord> functionEnvironmentRecordStack;

    private DebuggerShell shell;

    private String sourceFileName;

    public Debugger(String baseFileName) {
        super(baseFileName + ".x.cod");

        // Update to add the correct extensions to the base file name to
        // load the byte code file, as well as to load the source file
        this.sourceFileName = baseFileName + ".x";

        init();
    }

    private void init() {
        setupSource();
    }

    @Override
    public void run()
            throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        // Print source first
        displaySource();

        // Initialize and create main environment record stack
        this.functionEnvironmentRecordStack = new Stack<>();
        this.functionEnvironmentRecordStack.push(new FunctionEnvironmentRecord());

        // Initialize the debug shell
        this.shell = new DebuggerShell(this);

        Program program = byteCodeLoader.loadCodes();

        this.debuggerVirtualMachine = new DebuggerVirtualMachine(program, this);

        while (true) {
            DebuggerCommand command = shell.prompt();

            command.execute();

            if (command instanceof ExitCommand) {
                break;
            }
        }
    }

    private void setupSource() {
        try (LineNumberReader lineNumberReader = new LineNumberReader(
                new FileReader(this.sourceFileName))) {

            String line = lineNumberReader.readLine();
            while (line != null) {
                Entry entry = new Entry(lineNumberReader.getLineNumber(), line, false);
                sourceLines.add(entry);

                line = lineNumberReader.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Source file " + this.sourceFileName + " doesn't exists!");
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read source file " + this.sourceFileName + "!");
        }
    }

    private void displaySource() {
        sourceLines.forEach(e -> System.out.printf("%5d: %s%n", e.lineNumber, e.sourceLine));
    }

}