/**
 * DO NOT provide a method that returns components contained WITHIN the VM (this
 * is the exact situation that will break encapsulation) - you should request
 * that the VM performs operations on its components. This implies that the VM
 * owns the components and is free to change them, as needed, without breaking
 * clients' code (e.g., suppose I decide to change the name of the variable that
 * holds my runtime stack - if your code had referenced that variable then your
 * code would break. This is not an unusual situation - you can consider the
 * names of methods in the Java libraries that have been deprecated).
 * <p>
 * Consider that the VM calls the individual ByteCodes' execute method and
 * passes itself as a parameter. For the ByteCode to execute, it must invoke
 * one or more methods in the runStack. It can do this by executing
 * VM.runStack.pop(); however, this does break encapsulation. To avoid this,
 * you'll need to have a corresponding set of methods within the VM that do
 * nothing more than pass the call to the runStack. e.g., you would want to
 * define a VM method:
 * public int popRunStack() {
 * return runStack.pop();
 * }
 * called by, e.g.,
 * int temp = VM.popRunStack();
 */
package interpreter;

import interpreter.bytecode.ByteCode;
import interpreter.bytecode.DumpCode;
import interpreter.debugger.RunTimeStack;

import java.util.Stack;

public class VirtualMachine {
  protected int pc;
  protected RunTimeStack runTimeStack;
  protected Stack<Integer> returnAddresses;
  protected boolean isRunning;
  protected Program program;

  protected boolean dumpFlag = false;

  public VirtualMachine(Program program) {
    this.program = program;
  }

  public void executeProgram() {

    init();

    isRunning = true;

    while (isRunning) {
      ByteCode code = program.getCode(pc);
      code.execute(this);

      if (dumpFlag && !(code instanceof DumpCode)) {
        code.dump(this);
        runTimeStack.dump();
      }
      pc++;
    }
  }

  /**
   * Initialize the virtual machine for restart
   */
  private void init() {
    pc = 0;
    runTimeStack = new RunTimeStack();
    returnAddresses = new Stack<>();
  }

  public RunTimeStack getRunTimeStack() {
    return runTimeStack;
  }

  public void setDump(boolean flag) {
    dumpFlag = flag;
  }

  // Terminate the program from running
  public void halt() {
    isRunning = false;

    init();
  }

  public int peekRunStack() {
    return runTimeStack.peek();
  }

  public void pushRunStack(Integer value) {
    runTimeStack.push(value);
  }

  public int popRunStack() {
    return runTimeStack.pop();
  }

  public void storeRunStack(int offset) {
    runTimeStack.store(offset);
  }

  public void loadRunStack(Integer offset) {
    runTimeStack.load(offset);
  }

  public void newFrameAt(int offset) {
    runTimeStack.newFrameAt(offset);
  }

  public void popFrame() {
    runTimeStack.popFrame();
  }

  public void pushPC(int value) {
    returnAddresses.add(value);
  }

  public void returnPC() {
    if (!returnAddresses.isEmpty()) {
      setPC(returnAddresses.pop());
    }
  }

  public void savePC() {
    pushPC(pc);
  }

  public void setPC(int value) {
    pc = value;
  }

  public void setArgsCount(int count) {
    runTimeStack.setArgsCount(count);
  }
}

