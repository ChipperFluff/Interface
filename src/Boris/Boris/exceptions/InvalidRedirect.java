package Boris.exceptions;

import Boris.View;

public class InvalidRedirect extends RuntimeException {
    public InvalidRedirect(Class<? extends View> target) {
        super(target == null
            ? "invalid use of null in redirect"
            : "View" + target.getName() + " is not registered in base");
    }
}
