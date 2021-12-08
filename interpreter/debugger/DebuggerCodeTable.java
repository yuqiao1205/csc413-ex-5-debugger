package interpreter.debugger;

import java.util.HashMap;

import interpreter.CodeTable;

public class DebuggerCodeTable {
  private static HashMap<String, String> codeMap = new HashMap<>();

  static {
    // Make sure the code table is proactively intialized
    CodeTable.init();

    init();
  }

  public static void init() {
    // Initialize the new byte codes required for debugging:
    // codeMap.put(... etc. ...)
    codeMap.put("LINE","debuggercodes.LineCode");
    codeMap.put("FUNCTION","debuggercodes.FunctionCode");
    codeMap.put("FORMAL","debuggercodes.FormalCode");
  }

  public static String getClassName(String code) {
    // Trim and upper case to make sure that we can look up correctly in the code tables
    code = code.trim().toUpperCase();

    String result = codeMap.get(code);

    if(result == null) {
      return CodeTable.get(code);
    } else {
      return result;
    }
  }
}