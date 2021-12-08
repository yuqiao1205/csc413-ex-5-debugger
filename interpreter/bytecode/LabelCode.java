package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.List;

public class LabelCode extends ByteCode{
    private String label;

    public LabelCode() {
    }

    @Override
    public void init(List<String> args) {
        CodeUtils.checkArgs(args, "LABEL", 1);
        label = args.get(0);
    }

    @Override
    public void execute(VirtualMachine vm) {

    }

    @Override
    public void dump(VirtualMachine vm) {
        System.out.println("LABEL " + label);
    }

    public String getLabel() {
        return label;
    }
}
