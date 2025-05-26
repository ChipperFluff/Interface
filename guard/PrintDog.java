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
        // Register your trusted namespaces (framework internals)
        trustedPackages.add("Interface.");
        trustedPackages.add("your.package."); // <- Add your framework pkg here

        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                // swallow byte stream output; not relevant
            }
        }, true) {
            @Override
            public void println(String x) {
                if (isFromTrustedSource() || !strict) {
                    originalOut.println(x);
                } else {
                    throw new IllegalTerminalPrintException("🐶 BARK! Use terminal.print(), not System.out.println()");
                }
            }

            @Override
            public void print(String x) {
                if (isFromTrustedSource() || !strict) {
                    originalOut.print(x);
                } else {
                    throw new IllegalTerminalPrintException("🐶 BARK! Use terminal.print(), not System.out.print()");
                }
            }
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
            start(true); // re-engage the dog
        }
    }
}
