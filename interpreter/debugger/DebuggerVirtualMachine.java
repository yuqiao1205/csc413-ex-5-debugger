package interpreter.debugger;

import interpreter.Program;
import interpreter.VirtualMachine;
import interpreter.bytecode.ByteCode;
import interpreter.bytecode.CallCode;
import interpreter.bytecode.DumpCode;
import interpreter.bytecode.HaltCode;
import interpreter.bytecode.LitCode;
import interpreter.bytecode.PopCode;
import interpreter.bytecode.ReturnCode;
import interpreter.bytecode.debuggercodes.FormalCode;
import interpreter.bytecode.debuggercodes.FunctionCode;
import interpreter.bytecode.debuggercodes.LineCode;

import java.util.Stack;

public class DebuggerVirtualMachine extends VirtualMachine {

    public enum DebugExecutionEnum {
        STEP,
        CONTINUE
    }

    private Debugger debugger;

    public DebuggerVirtualMachine(Program program, Debugger debugger) {
        super(program);

        this.debugger = debugger;

        reset();

        this.isRunning = true;
    }

    /**
     * This is the method to reset the debug session states.
     */
    public void reset() {
        this.pc = 0;
        this.runTimeStack = new RunTimeStack();
        this.returnAddresses = new Stack<>();
    }

    public void executeProgram() {

        reset();

        isRunning = true;

        while (isRunning) {
            ByteCode code = program.getCode(pc);
            code.execute(this);

            if (dumpFlag && !(code instanceof DumpCode)) {
                code.dump(this);
                runTimeStack.dump();
            }
            pc++;
        }
    }

    //todo encounter breakpoint then stop
    public void debugExecution(DebugExecutionEnum mode) {
        // note: similar to step, but it will stop only at the breakpoints
        // todo handle function environment record when call function

        while (isRunning) {
            ByteCode code = program.getCode(pc);

            if (code instanceof LineCode) {
                int lineNo = ((LineCode) code).getLineNumber();

                debugger.getCurrentFunctionEnvironmentRecord().setCurrentLineNumber(lineNo);

                if (mode == DebugExecutionEnum.STEP) {
                    // Step mode: just return
                    pc++;
                    return;
                } else if (mode == DebugExecutionEnum.CONTINUE) {
                    // Continue mode
                    for (Debugger.Entry entry : debugger.getSourceLines()) {
                        if (entry.getLineNumber() == lineNo && entry.getBreakpointLine()) {
                            pc++;
                            return;
                        }
                    }
                }
            } else if (code instanceof CallCode) {

                // Push a new record for calling a new function
                debugger.pushEmptyFunctionEnvironmentRecord();

            } else if (code instanceof FunctionCode) {

                FunctionCode fc = (FunctionCode) code;

                FunctionEnvironmentRecord fer = debugger.getCurrentFunctionEnvironmentRecord();
                fer.setFunctionInfo(fc.getName(), fc.getStart(), fc.getEnd());

            } else if (code instanceof ReturnCode) {

                debugger.popFunctionEnvironmentRecord();

            }

            code.execute(this);

            if (code instanceof HaltCode) {
                // Reset the debug session state
                reset();
            } else if (code instanceof LitCode) {
                LitCode lc = (LitCode) code;

                if (!lc.getVariable().isEmpty()) {
                    int offset = debugger.getDebuggerVirtualMachine().getRunTimeStack().getCurrentOffset();

                    debugger.getCurrentFunctionEnvironmentRecord().enter(lc.getVariable(), offset);
                }
            } else if (code instanceof FormalCode) {
                FormalCode fc = (FormalCode) code;

                debugger.getCurrentFunctionEnvironmentRecord().enter(fc.getName(), fc.getOffset());
            } else if (code instanceof PopCode) {
                // Handle the case of literals have to be removed accordingly
                PopCode pc = (PopCode) code;
                debugger.getCurrentFunctionEnvironmentRecord().pop(pc.getLevelsToPop());
            }

            // Dump code if turned on
            if (dumpFlag && !(code instanceof DumpCode)) {
                code.dump(this);
                runTimeStack.dump();
            }
            pc++;
        }
    }

}