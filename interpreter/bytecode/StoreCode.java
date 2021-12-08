package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.List;

public class StoreCode extends ByteCode {
    private int offset;
    private String variable = "";
    private Integer storedValue;

    public StoreCode() {
    }

    @Override
    public void init(List<String> args) {
        CodeUtils.checkArgs(args, "STORE", 1, 2);

        offset = Integer.parseInt(args.get(0));

        if (args.size() > 1) {
            variable = args.get(1);
        }
    }

    @Override
    public void execute(VirtualMachine vm) {
        // Get the stack top for dump
        storedValue = vm.peekRunStack();

        // Perform store: pop the stack top and store to the offset
        vm.storeRunStack(offset);
    }

    @Override
    public void dump(VirtualMachine vm) {
        if (variable != null && variable.length() > 0) {
            System.out.printf("%-25s%s%n", "STORE " + offset + " " + variable, variable + " = " + storedValue);
        } else {
            System.out.printf("%-25s%n", "STORE " + offset + " " + variable);
        }
    }
}