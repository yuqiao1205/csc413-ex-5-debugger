package interpreter.bytecode.debuggercodes;

import interpreter.VirtualMachine;
import interpreter.bytecode.ByteCode;
import interpreter.bytecode.CodeUtils;

import java.util.List;

public class FunctionCode extends ByteCode {
    private String name;
    private int start;
    private int end;

    public String getName() {
        return name;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public FunctionCode() {
    }

    @Override
    public void init(List<String> args) {
        CodeUtils.checkArgs(args, "FUNCTION", 3);
        name = args.get(0);
        start = Integer.parseInt(args.get(1));
        end = Integer.parseInt(args.get(2));
    }

    @Override
    public void execute(VirtualMachine vm) {

    }

    @Override
    public void dump(VirtualMachine vm) {
    }
}
