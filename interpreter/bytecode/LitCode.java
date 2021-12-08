package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.List;

public class LitCode extends ByteCode {

    private int value;

    private String variable = "";

    public LitCode() {
    }

    public int getValue() {
        return value;
    }

    public String getVariable() {
        return variable;
    }

    @Override
    public void init(List<String> args) {
        // Check the number of args
        CodeUtils.checkArgs(args, "LIT", 1, 2);

        // Set the values
        value = Integer.parseInt(args.get(0));

        if (args.size() == 2) {
            variable = args.get(1);
        }
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.pushRunStack(value);
    }

    @Override
    public void dump(VirtualMachine vm) {
        if (variable != null && variable.length() > 0) {
            System.out.printf("%-25s%s%n", "LIT " + value + " " + variable, "int " + variable);
        } else {
            System.out.printf("%-25s%n", "LIT " + value);
        }
    }
}
