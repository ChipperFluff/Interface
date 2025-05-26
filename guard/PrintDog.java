package Interface.guard;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Set;
import java.util.HashSet;

import Interface.exceptions.IllegalTerminalPrintException;

public class PrintDog {
    private static final PrintStream originalOut = System.out;
    private static final Set<String> trustedPackages = new HashSet<>();
    private static boolean strict = true;

    public static void start(boolean strictMode) {
        strict = strictMode;
        trustedPackages.add("Interface.");
        trustedPackages.add("views."); // add more if needed

        System.setOut(new PrintStream(new OutputSniffer(originalOut), true));
    }

    private static class OutputSniffer extends OutputStream {
        private final PrintStream passthrough;

        public OutputSniffer(PrintStream passthrough) {
            this.passthrough = passthrough;
        }

        @Override
        public void write(int b) {
            checkCaller();
            passthrough.write(b);
        }

        @Override
        public void write(byte[] b, int off, int len) {
            checkCaller();
            passthrough.write(b, off, len);
        }

        private void checkCaller() {
            if (!strict) return;
            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
            for (StackTraceElement elem : stack) {
                String cls = elem.getClassName();
                for (String prefix : trustedPackages) {
                    if (cls.startsWith(prefix)) return; // trusted, allow
                }
            }

            throw new IllegalTerminalPrintException("🐶 BARK! Raw output detected! Use terminal.print()");
        }
    }

    public static void bypass(Runnable r) {
        System.setOut(originalOut);
        try {
            r.run();
        } finally {
            start(strict);
        }
    }
}
