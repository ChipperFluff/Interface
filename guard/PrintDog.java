package Interface.guard;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import java.util.Set;
import java.util.HashSet;

import Interface.Color;
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

        // Setup global uncaught exception handler
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            originalOut.println(Color.ANSI_RED + "🔥 UNCAUGHT EXCEPTION IN THREAD: " + thread.getName() + Color.ANSI_RESET);
            throwable.printStackTrace(originalOut);
            // Optional kill:
            // System.exit(1);
        });

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
                e.printStackTrace(originalOut);
            }
        }

        private void flushBuffer() {
            String content = buffer.toString();
            buffer.reset();

            Throwable t = new Throwable();
            StackTraceElement[] stack = t.getStackTrace();

            if (strict && !isFromTrustedCode(stack)) {
                originalOut.println(
                    Color.ANSI_WHITE + Color.ANSI_RED_BACKGROUND +
                    "🐶 BARK! UNAUTHORIZED TERMINAL PRINT DETECTED!" +
                    Color.ANSI_RESET + "\n" +
                    Color.ANSI_YELLOW + "→ Use terminal.print() instead.\n" +
                    "→ Offending content:\n" +
                    Color.ANSI_WHITE + content.trim() + Color.ANSI_RESET
                );

                // 🚨 HARD CRASH
                originalOut.println(Color.ANSI_RED + "☠️  FATAL: System.out used in untrusted context!" + Color.ANSI_RESET);
                throw new RuntimeException("🐶 TERMINAL VIOLATION → " + content.trim());
            }
        }

        private boolean isFromTrustedCode(StackTraceElement[] stack) {
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
