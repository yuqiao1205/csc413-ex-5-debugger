package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.List;

public class PopCode extends ByteCode {
    private int levelsToPop;

    public PopCode() {
    }

    @Override
    public void init(List<String> args) {
        CodeUtils.checkArgs(args, "POP", 1);
        levelsToPop = Integer.parseInt(args.get(0));
    }

    @Override
    public void execute(VirtualMachine vm) {
        for (int i = 0; i < levelsToPop; i++) {
            vm.popRunStack();
        }
    }

    @Override
    public void dump(VirtualMachine vm) {
        System.out.println("POP " + levelsToPop);
    }

    public int getLevelsToPop() {
        return levelsToPop;
    }
}
