package Boris;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TerminalSize {
    public static int[] getTerminalSize() {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"sh", "-c", "stty size < /dev/tty"});
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();

            if (line != null) {
                String[] parts = line.trim().split(" ");
                int rows = Integer.parseInt(parts[0]);
                int cols = Integer.parseInt(parts[1]);
                return new int[]{rows, cols};
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to get terminal size: " + e.getMessage());
        }

        return new int[]{69, 69}; // Soggy UwU fallback
    }
}
