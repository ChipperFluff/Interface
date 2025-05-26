package Interface.guard;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Set;
import java.util.HashSet;
import Interface.exceptions.IllegalTerminalPrintException;

public class PrintDog {
    private static final PrintStream originalOut = System.out;
    private static final Set<String> trustedPackages = new HashSet<>();

    public static void start(boolean strict) {
        trustedPackages.add("Interface.");

        System.setOut(new PrintStream(new OutputStream() {
            @Override public void write(int b) {}
        }, true) {
            private void handle(Object data) {
                if (isFromTrustedSource() || !strict) {
                    originalOut.println(data);
                } else {
                    throw new IllegalTerminalPrintException("🐶 BARK! Use terminal.print() instead of System.out.println()");
                }
            }

            @Override public void println(String x)    { handle(x); }
            @Override public void println(int x)       { handle(x); }
            @Override public void println(double x)    { handle(x); }
            @Override public void println(boolean x)   { handle(x); }
            @Override public void println(char x)      { handle(x); }
            @Override public void println(long x)      { handle(x); }
            @Override public void println(Object x)    { handle(x); }
            @Override public void println(char[] x)    { handle(x); }
            @Override public void println(float x)     { handle(x); }
            @Override public void print(String x)      { handle(x); }
            @Override public void print(int x)         { handle(x); }
            @Override public void print(double x)      { handle(x); }
            @Override public void print(boolean x)     { handle(x); }
            @Override public void print(char x)        { handle(x); }
            @Override public void print(long x)        { handle(x); }
            @Override public void print(Object x)      { handle(x); }
            @Override public void print(char[] x)      { handle(x); }
            @Override public void print(float x)       { handle(x); }
        });
    }

    private static boolean isFromTrustedSource() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        for (StackTraceElement frame : stack) {
            String className = frame.getClassName();
            for (String prefix : trustedPackages) {
                if (className.startsWith(prefix)) return true;
            }
        }
        return false;
    }

    public static void bypass(Runnable action) {
        System.setOut(originalOut);
        try {
            action.run();
        } finally {
            start(true);
        }
    }
}
