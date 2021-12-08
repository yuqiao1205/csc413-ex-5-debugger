package interpreter;

import interpreter.bytecode.ByteCode;
import interpreter.debugger.DebuggerCodeTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ByteCodeLoader {
  private String byteCodeFile;

  public ByteCodeLoader(String byteCodeFile) throws IOException {
    this.byteCodeFile = byteCodeFile;
  }

  public Program loadCodes() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
    List<ByteCode> loadedByteCodes = new ArrayList<>();
    String line;

    try (BufferedReader byteCodes = new BufferedReader(new FileReader(byteCodeFile))) {
      while ((line = byteCodes.readLine()) != null) {
        // Tokenize line into an ArrayList of strings, the first token will be the byte code class key
        List<String> tokens = new ArrayList<>(Arrays.asList(line.split(" ")));

        // Create new byte code object from class name
        String codeClass = DebuggerCodeTable.getClassName(tokens.get(0));
        ByteCode byteCode = (ByteCode) Class.forName("interpreter.bytecode." + codeClass).newInstance();

        tokens.remove(0);
        byteCode.init(tokens);

        loadedByteCodes.add(byteCode);
      }
    }

    Program loadedProgram = new Program(loadedByteCodes);

    return loadedProgram;
  }
}
