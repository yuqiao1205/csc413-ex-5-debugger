package interpreter;

import java.io.*;

import interpreter.debugger.Debugger;

public class Interpreter {

    protected ByteCodeLoader byteCodeLoader;
    protected VirtualMachine vm;

    public Interpreter(String codeFile) {
        try {
            CodeTable.init();
            byteCodeLoader = new ByteCodeLoader(codeFile);
        } catch (IOException e) {
            System.out.println("**** " + e);
        }
    }

    public void run() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Program program = byteCodeLoader.loadCodes();
        vm = new VirtualMachine(program);
        vm.executeProgram();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (args.length == 0 || args.length > 2) {
            System.out.println("*** Incorrect usage, try: java interpreter.Interpreter <file>");
            System.exit(1);
        } else if (args.length == 1) {
            (new Interpreter(args[0])).run();
        } else if (args.length == 2) {
            if (args[0].equals("-d")) {
                (new Debugger(args[1])).run();
            } else {
                System.out.println("*** Incorrect usage, try: java interpreter.Interpreter -d <basefilename>");
                System.exit(1);
            }
        }
    }
}