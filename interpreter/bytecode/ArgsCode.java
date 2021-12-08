package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.List;

public class ArgsCode extends ByteCode {
    private int argsCount;

    public ArgsCode() {
    }

    @Override
    public void init(List<String> args) {
        CodeUtils.checkArgs(args, "ARGS", 1);
        argsCount = Integer.parseInt(args.get(0));
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.newFrameAt(argsCount);
    }

    @Override
    public void dump(VirtualMachine vm) {
        System.out.println("ARGS " + argsCount);
        vm.setArgsCount(argsCount);
    }
}
