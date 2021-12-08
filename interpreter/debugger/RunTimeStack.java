package interpreter.debugger;

import java.util.List;
import java.util.Stack;
import java.util.Vector;

public class RunTimeStack {
    private Stack<Integer> framePointers;

    private Vector<Integer> runStack;

    private int argsCount;

    public RunTimeStack() {

        framePointers = new Stack<>();
        runStack = new Vector<>();

        framePointers.push(0);

    }

    /**
     * The purpose of this function is to dump the RunTimeStack for the
     * purpose of debugging.
     */
    public void dump() {
        int index = 0;
        if (framePointers.size() > 1) {
            for (int i = 1; i < framePointers.size(); i++) {
                System.out.print(runStack.subList(index, framePointers.get(i)) + " ");
                index = framePointers.get(i);
            }
        }
        System.out.println(runStack.subList(index, runStack.size()) + "\n");
    }

    /**
     * Returns the top item on the runtime stack.
     */
    public int peek() {
        if (!runStack.isEmpty()) {
            return runStack.get(runStack.size() - 1);
        }
        return 0;
    }

    /**
     * Pops the top item from the runtime stack, returning the item.
     */
    public int pop() {
        if (!runStack.isEmpty() && framePointers.peek() < runStack.size()) {
            return runStack.remove(runStack.size() - 1);
        }
        return 0;
    }

    /**
     * Push an item on to the runtime stack, returning the item that was just
     * pushed.
     *
     * @param item to push
     * @return the item pushed
     */
    public int push(int item) {
        runStack.add(item);
        return item;
    }

    /**
     * Get the current offset in the current frame.
     *
     * @return the offset, 0 if no frame.
     */
    public int getCurrentOffset() {
        if (framePointers.isEmpty()) {
            return 0;
        } else {
            return runStack.size() - 1 - framePointers.peek();
        }
    }

    /**
     * Get the value in the current frame by offset.
     *
     * @param offset offset in the frame.
     * @return value in the stack.
     */
    public int getValue(int offset) {
        return runStack.get(framePointers.peek() + offset);
    }

    /**
     * Start a new frame, where the parameter offset is the number of slots
     * down from the top of the RunTimeStack for starting the new frame.
     */
    public void newFrameAt(int offset) {
        framePointers.push(runStack.size() - offset);
    }

    /**
     * We pop the top frame when we return from a function; before popping, the
     * functions’ return value is at the top of the stack so we’ll save the value,
     * pop the top frame, and then push the return value.
     */
    public void popFrame() {
        int top = this.peek();
        int bottom = framePointers.pop();

        for (int i = runStack.size() - 1; i >= bottom; i--) {
            this.pop();
        }

        this.push(top);
    }

    /**
     * Used to store into variables.
     * Store will pop the top value of the stack and replace the value at the
     * given offset in the current frame. The value stored is returned.
     */
    public int store(int offset) {
        int top = pop();
        runStack.set(framePointers.peek() + offset, top);

        return top;
    }

    /**
     * Used to load variables onto the stack.
     */
    public int load(int offset) {
        return push(runStack.get(framePointers.peek() + offset));
    }

    // Only used for dumping CallCode
    public void setArgsCount(int count) {
        argsCount = count;
    }

    public List<Integer> getCurrentStackFrame() {
        return runStack.subList(framePointers.peek(), runStack.size());
    }
}
