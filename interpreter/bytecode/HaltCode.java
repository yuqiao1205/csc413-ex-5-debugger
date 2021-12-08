package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.List;

public class HaltCode extends ByteCode {
    public HaltCode() {
    }

    @Override
    public void init(List<String> args) {
        CodeUtils.checkArgs(args, "HALT", 0);
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.halt();
    }

    @Override
    public void dump(VirtualMachine vm) {
        System.out.println("HALT");
    }
}
