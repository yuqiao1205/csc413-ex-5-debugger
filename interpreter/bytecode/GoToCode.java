package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.List;

public class GoToCode extends AddressLabelCode {
    public GoToCode() {
    }

    @Override
    public void init(List<String> args) {
        CodeUtils.checkArgs(args, "GOTO", 1);

        label = args.get(0);
    }

    @Override
    public void execute(VirtualMachine vm) {
        // Offset by 1 to allow printing during dump
        vm.setPC(address - 1);
    }

    @Override
    public void dump(VirtualMachine vm) {
        System.out.println( "GOTO " + label);
    }
}