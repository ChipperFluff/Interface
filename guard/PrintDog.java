package Interface.guard;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import java.util.Set;
import java.util.HashSet;

import Interface.exceptions.IllegalTerminalPrintException;

public class PrintDog {
    private static final Set<String> trustedPackages = new HashSet<>();
    private static boolean strict = true;
    private static PrintStream originalOut;
    private static PrintStream mirroredOut;

    public static void start(boolean strictMode) {
        strict = strictMode;

        originalOut = System.out;

        // Register your actual source packages only
        trustedPackages.add("Interface.");
        trustedPackages.add("Interface.action.");
        trustedPackages.add("Interface.exceptions.");
        trustedPackages.add("Interface.guard.");
        trustedPackages.add("Interface.intern.");

        // Setup mirror output stream
        mirroredOut = new PrintStream(new MirrorOutputStream(originalOut), true);
        System.setOut(mirroredOut);
    }

    private static class MirrorOutputStream extends OutputStream {
        private final OutputStream original;
        private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        public MirrorOutputStream(OutputStream original) {
            this.original = original;
        }

        @Override
        public void write(int b) {
            try {
                original.write(b);
                buffer.write(b);

                if (b == '\n') {
                    flushBuffer();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void flushBuffer() {
            String content = buffer.toString();
            buffer.reset();

            if (strict && !isFromTrustedCode()) {
                throw new IllegalTerminalPrintException(
                    "🐶 BARK! Use terminal.print() instead:\n→ " + content.trim()
                );
            }
        }

        private boolean isFromTrustedCode() {
            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
            for (StackTraceElement frame : stack) {
                String cls = frame.getClassName();
                for (String prefix : trustedPackages) {
                    if (cls.startsWith(prefix)) {
                        return true;
                    }
                }
            }
            return false;
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
