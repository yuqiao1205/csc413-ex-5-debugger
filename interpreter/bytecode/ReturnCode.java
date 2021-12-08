package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.List;

public class ReturnCode extends ByteCode {
    private String function = "";
    private String baseId = "";

    public ReturnCode() {
    }

    @Override
    public void init(List<String> args) {

        CodeUtils.checkArgs(args, "RETURN", 0, 1);

        if (!args.isEmpty()) {
            function = args.get(0);
            baseId = args.get(0).split("<<", 2)[0];
        }

    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.popFrame();
        vm.returnPC();
    }

    @Override
    public void dump(VirtualMachine vm) {
        if (baseId != null && baseId.length() > 0) {
            System.out.printf("%-25s%s%n", "RETURN " + function, "exit " + baseId + ": " + vm.getRunTimeStack().peek());
        } else {
            System.out.printf("%-25s%s%n", "RETURN", "exit " + vm.getRunTimeStack().peek());
        }
    }
}
