package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.List;
import java.util.stream.Collectors;

public class CallCode extends AddressLabelCode {

  private String baseId;

  public CallCode() {
  }

  @Override
  public void init(List<String> args) {
    label = args.get(0);
    baseId = label.split("<<", 2)[0];
  }

  @Override
  public void execute(VirtualMachine vm) {
    vm.savePC();

    //Offset by 1 to allow printing during dump
    vm.setPC(address - 1);
  }

  @Override
  public void dump(VirtualMachine vm) {
    System.out.printf("%-25s%s%n",
        "CALL " + label,
        baseId + "(" + vm
            .getRunTimeStack()
            .getCurrentStackFrame()
            .stream()
            .map(i -> i.toString())
            .collect(Collectors.joining(","))
            + ")");
  }

}
