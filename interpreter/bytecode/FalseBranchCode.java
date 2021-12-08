package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.List;

public class FalseBranchCode extends AddressLabelCode {
    public FalseBranchCode() {
    }

    @Override
    public void init(List<String> args) {
        CodeUtils.checkArgs(args, "FALSEBRANCH", 1);

        label = args.get(0);
    }

    @Override
    public void execute(VirtualMachine vm) {
        if (vm.popRunStack() == 0) {
            // Offset by 1 to allow printing during dump
            vm.setPC(address - 1);
        }
    }

    @Override
    public void dump(VirtualMachine vm) {
        System.out.println("FALSEBRANCH " + label);
    }
}
