package Boris;

import java.util.Scanner;
import java.util.ArrayList;
import Boris.exceptions.DuplicateViewMatchException;

public abstract class View {
    protected static Global global = Global.getInstance();
    protected static ViewActionFactories actions = new ViewActionFactories();
    protected ViewTerminalAcces terminal;
    public static Scanner scanner;
    public static Interface __base;
    public String viewPrompt;
    public String viewSignature;
    public String helperText;

    public abstract Boolean init();
    public abstract void draw();
    public abstract Action onCommand(String command);
    public abstract Boolean onSelection(String userInputRaw);

    public void initBase(Interface base) {
        View.__base = base;
        this.terminal = new ViewTerminalAcces(base);
    }

    public static View instantiate(Class<? extends View> viewClass) {
        try {
            return viewClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Could not instantiate view: " + viewClass.getName(), e);
        }
    }

    public static View signatureParse(String userInputRaw) {
        ArrayList<View> matched = new ArrayList<View>();

        for (Class<? extends View> ViewClass : global.registeredViews) {
            View viewInstance = View.instantiate(ViewClass);
            if (viewInstance.onSelection(userInputRaw)) {
                matched.add(viewInstance);
            }
        }

        if (matched.size() > 1) {
            throw new DuplicateViewMatchException(userInputRaw, matched.size());
        }

        return matched.isEmpty() ? null : matched.get(0);
    }
}
