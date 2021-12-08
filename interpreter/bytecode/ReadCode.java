package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ReadCode extends ByteCode {

    private static BufferedReader CONSOLE;

    static {
        CONSOLE = new BufferedReader(new InputStreamReader(System.in));
    }

    public ReadCode() {
    }

    @Override
    public void init(List<String> args) {
        CodeUtils.checkArgs(args, "READ", 0);
    }


    @Override
    public void dump(VirtualMachine vm) {
        System.out.println("READ");
    }

    @Override
    public void execute(VirtualMachine vm) {
        System.out.print("Please input an integer: ");

        String line = "";
        try {
            line = CONSOLE.readLine();

            vm.pushRunStack(Integer.parseInt(line));
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Integer is required!  Invalid input=" + line);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new IllegalStateException("Failed to read integer!");
        }
    }
}
