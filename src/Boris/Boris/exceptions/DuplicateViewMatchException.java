package Boris.exceptions;

public class DuplicateViewMatchException extends RuntimeException {
    public DuplicateViewMatchException(String input, int count) {
        super("Input \"" + input + "\" matched " + count + " views. View signatures must be unique for this input.");
    }
}
