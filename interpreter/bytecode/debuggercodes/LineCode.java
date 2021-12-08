package interpreter.bytecode.debuggercodes;

import interpreter.VirtualMachine;
import interpreter.bytecode.ByteCode;
import interpreter.bytecode.CodeUtils;

import java.util.List;

public class LineCode extends ByteCode {
    private int lineNumber;

    public LineCode() {
    }

    @Override
    public void init(List<String> args) {
        CodeUtils.checkArgs(args, "LINE", 1);
        lineNumber = Integer.parseInt(args.get(0));
    }

    @Override
    public void execute(VirtualMachine vm) {
    }

    @Override
    public void dump(VirtualMachine vm) {
    }

    public int getLineNumber() {
        return lineNumber;
    }

}