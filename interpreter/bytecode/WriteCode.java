package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.List;

public class WriteCode extends ByteCode {
    public WriteCode() {
    }

    @Override
    public void init(List<String> args) {
        CodeUtils.checkArgs(args, "WRITE", 0);
    }

    @Override
    public void execute(VirtualMachine vm) {
        System.out.println(vm.peekRunStack());
    }

    @Override
    public void dump(VirtualMachine vm) {
        System.out.println("WRITE");
    }
}
