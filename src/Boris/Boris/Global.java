package Boris;

import java.util.ArrayList;
import java.util.List;

public class Global {
    private static Global instance;
    public List<Class<? extends View>> registeredViews = new ArrayList<>();

    private Global() {}

    public static synchronized Global getInstance() {
        if (instance == null) {
            instance = new Global();
        }
        return instance;
    }
}
