package interpreter.bytecode.debuggercodes;

import interpreter.VirtualMachine;
import interpreter.bytecode.ByteCode;
import interpreter.bytecode.CodeUtils;

import java.util.List;

public class FormalCode extends ByteCode {
        private String name;
        private int offset;

        public FormalCode() {
        }

    public String getName() {
        return name;
    }

    public int getOffset() {
        return offset;
    }

    @Override
        public void init(List<String> args) {
            CodeUtils.checkArgs(args, "FORMAL", 2);
            name = args.get(0);
            offset = Integer.parseInt(args.get(1));
        }

        @Override
        public void execute(VirtualMachine vm) {

        }

        @Override
        public void dump(VirtualMachine vm) {
        }
    }

