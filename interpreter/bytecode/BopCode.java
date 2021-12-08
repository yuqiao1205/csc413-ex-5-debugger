package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.List;

public class BopCode extends ByteCode {
    private String operator;

    public BopCode() {
    }

    @Override
    public void init(List<String> args) {
        CodeUtils.checkArgs(args, "BOP", 1);
        operator = args.get(0);
    }

    @Override
    public void execute(VirtualMachine vm) {
        int op2 = vm.popRunStack();
        int op1 = vm.popRunStack();

        if (operator.equals("+")) {
            vm.pushRunStack(op1 + op2);
        } else if (operator.equals("-")) {
            vm.pushRunStack(op1 - op2);
        } else if (operator.equals("/")) {
            vm.pushRunStack(op1 / op2);
        } else if (operator.equals("*")) {
            vm.pushRunStack(op1 * op2);
        } else if (operator.equals("==")) {
            vm.pushRunStack((op1 == op2) ? 1 : 0);
        } else if (operator.equals("!=")) {
            vm.pushRunStack((op1 != op2) ? 1 : 0);
        } else if (operator.equals("<=")) {
            vm.pushRunStack((op1 <= op2) ? 1 : 0);
        } else if (operator.equals(">")) {
            vm.pushRunStack((op1 > op2) ? 1 : 0);
        } else if (operator.equals(">=")) {
            vm.pushRunStack((op1 >= op2) ? 1 : 0);
        } else if (operator.equals("<")) {
            vm.pushRunStack((op1 < op2) ? 1 : 0);
        } else if (operator.equals("|")) {
            // At least one operand is false
            vm.pushRunStack(((op1 != 0) || (op2 != 0)) ? 1 : 0);
        } else if (operator.equals("&")) {
            // Both operands are false
            vm.pushRunStack(((op1 != 0) && (op2 != 0)) ? 1 : 0);
        } else {
            throw new IllegalArgumentException("Invalid operator=" + operator + "!");
        }
    }

    @Override
    public void dump(VirtualMachine vm) {
        System.out.print("BOP " + operator);
        System.out.println();
    }
}
