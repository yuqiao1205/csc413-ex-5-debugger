package interpreter.bytecode;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class CodeUtils {

    /**
     * Check the number of args and throw exception if no match.
     *
     * @param args         actual args.
     * @param codeName     codeName.
     * @param expectedArgs expected number of args.
     */
    public static void checkArgs(List<String> args, String codeName, int... expectedArgs) {
        for (int expected : expectedArgs) {
            if (expected == args.size()) {
                return;
            }
        }

        throw new IllegalArgumentException(codeName + " requires "
                + Arrays.stream(expectedArgs).boxed().collect(toList())
                + " arg(s)! Actual=" + args);

    }

}
