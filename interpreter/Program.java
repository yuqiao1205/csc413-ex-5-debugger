package interpreter;

import interpreter.bytecode.AddressLabelCode;
import interpreter.bytecode.ByteCode;
import interpreter.bytecode.LabelCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Program {
    private final List<ByteCode> codeList;

    private Map<String, Integer> addresses = new HashMap<>();

    public Program() {
        codeList = new ArrayList<>();
    }

    public Program(List<ByteCode> loadedByteCodes) {
        this.codeList = loadedByteCodes;
        resolveAddrs();
    }

    public ByteCode getCode(int programCounter) {
        return this.codeList.get(programCounter);
    }

    // Store addresses of labels in HashMap
    private void resolveAddrs() {

        for (int i = 0; i < codeList.size(); i++) {
            if (codeList.get(i) instanceof LabelCode) {
                addresses.put(((LabelCode) codeList.get(i)).getLabel(), i);
            }
        }

        for (ByteCode bytecode : codeList) {
            if (bytecode instanceof AddressLabelCode) {
                AddressLabelCode bytecodeWithLabelAddress = ((AddressLabelCode) bytecode);
                bytecodeWithLabelAddress.setAddress(addresses.get(bytecodeWithLabelAddress.getLabel()));
            }

        }
    }
}
