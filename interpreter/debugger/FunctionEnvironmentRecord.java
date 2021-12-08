package interpreter.debugger;

import javax.xml.bind.Binder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FunctionEnvironmentRecord {

    private String functionName;
    private int startLineNumber;
    private int endLineNumber;
    private int currentLineNumber;
    private Binder tail;

    public int getCurrentLineNumber() {
        return currentLineNumber;
    }

    private final Map<String, Binder> symbols = new HashMap<>();

    public Map<String, Binder> getSymbols() {
        return symbols;
    }

    public static class Binder {
        private final Object value;   // Actually the offset of the symbol
        private final String symbol;  // prior symbol in same scope
        private final Binder prev;    // referenced prior binder of the same symbol
        private final Binder tail;    // last binder entered(used to pop)

        // restore this when closing scope
        Binder(Object v, String n, Binder p, Binder t) {
            value = v;
            symbol = n;
            prev = p;
            tail = t;
        }

        public Object getValue() {
            return value;
        }

        public String getSymbol() {
            return symbol;
        }

        public Binder getPreviousBinder() {
            return prev;
        }

        public Binder getTail() {
            return tail;
        }
    }

    public void beginScope() {
    }

    public void setFunctionInfo(String functionName, int startingLineNumber, int endingLineNumber) {
        this.functionName = functionName;
        this.startLineNumber = startingLineNumber;
        this.endLineNumber = endingLineNumber;
    }

    public void setCurrentLineNumber(int currentLineNumber) {
        this.currentLineNumber = currentLineNumber;
    }
 
    public void enter(String symbol, int value) {
        Binder binder = new Binder(value, symbol, symbols.get(symbol), tail);
        symbols.put(symbol, binder);
        tail = binder;
    }

    public void pop(int count) {
        for (int i = 0; i < count; i++) {
            if (tail.getPreviousBinder() != null) {
                // revert value for symbol
                symbols.put(tail.getSymbol(), tail.getPreviousBinder());
            } else {
                // remove symbol from table
                symbols.remove(tail.getSymbol());
            }
            tail = tail.getTail();
        }
    }

    public String symbolTableToString() {
        // (<b, 2>, <a, 1>, <c,7>), g, 1, 20, 5)
        List<String> bindings = symbols
                .entrySet()
                .stream()
                .map(s -> {
                    StringBuilder buffer = new StringBuilder();

                    String name = s.getKey();
                    Binder current = s.getValue();

                    boolean visited = false;
                    while (current != null) {
                        if (visited) {
                            buffer.append("-");
                        } else {
                            visited = true;
                        }

                        buffer.append("<").append(name).append(",").append(current.getValue()).append(">");

                        current = current.getPreviousBinder();
                    }

                    return buffer.toString();
                })
                .collect(Collectors.toList());

        return "(" + String.join(", ", bindings) + ")";
    }



    @Override
    public String toString() {
        //(<a/4>, 1, 20, g, 5)
        String fn = (functionName == null) ? "-" : functionName;

        return "< " + symbolTableToString() +
                ", " + printHelper(startLineNumber, 0) +
                ", " + printHelper(endLineNumber, 0) +
                ", " + fn +
                ", " + printHelper(currentLineNumber, 0)
                + " >";
    }

    private String printHelper(Object x, Object noValue) {
        return (x == noValue) ? "-" : x.toString();
    }

    /**
     * Utility method to test your implementation. The output should be:
     * (<>, -, -, -, -)
     * (<>, g, 1, 20, -)
     * (<>, g, 1, 20, 5)
     * (<a/4>, g, 1, 20, 5)
     * (<b/2, a/4>, g, 1, 20, 5)
     * (<b/2, a/4, c/7>, g, 1, 20, 5)
     * (<b/2, a/1, c/7>, g, 1, 20, 5)
     * (<b/2, a/4>, g, 1, 20, 5)
     * (<a/4>, g, 1, 20, 5)
     */
    public static void main(String[] args) {
        FunctionEnvironmentRecord record = new FunctionEnvironmentRecord();

        record.beginScope();
        System.out.println(record);

        record.setFunctionInfo("g", 1, 20);
        System.out.println(record);

        record.setCurrentLineNumber(5);
        System.out.println(record);

        record.enter("a", 4);
        System.out.println(record);

        record.enter("b", 2);
        System.out.println(record);

        record.enter("c", 7);
        System.out.println(record);

        record.enter("a", 1);
        System.out.println(record);

        record.pop(2);
        System.out.println(record);

        record.pop(1);
        System.out.println(record);
    }
}