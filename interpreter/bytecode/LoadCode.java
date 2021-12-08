package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.List;

public class LoadCode extends ByteCode {
    private int offset;
    private String variable = "";

    public LoadCode() {
    }

    @Override
    public void init(List<String> args) {
        CodeUtils.checkArgs(args, "LOAD", 2);

        offset = Integer.parseInt(args.get(0));
        variable = args.get(1);
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.loadRunStack(offset);
    }

    @Override
    public void dump(VirtualMachine vm) {
        if (variable != null && variable.length() > 0) {
            System.out.printf("%-25s%s%n", "LOAD " + offset + " " + variable, "<load " + variable + ">");
        } else {
            System.out.printf("%-25s%n", "LOAD " + offset);
        }
    }
}
