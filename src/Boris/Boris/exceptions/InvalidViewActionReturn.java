package Boris.exceptions;

public class InvalidViewActionReturn extends RuntimeException {
    public InvalidViewActionReturn() {
        super("Please only return a Child of Action or null from a View");
    }
}
