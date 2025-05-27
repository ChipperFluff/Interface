package Boris.intern;

import Boris.View;
import Boris.Action;

public class v_help extends View {
    @Override
    public Boolean init() {
        viewPrompt = "HELP";
        viewSignature = "help";
        helperText = "help view most likely not implemented 😂 u noob";
        return true;
    }

    @Override
    public void draw() {
        return;
    }

    @Override
    public Action onCommand(String command) {
        return null;

    }

    @Override
    public Boolean onSelection(String userInputRaw) {
        return false;
    }
}
