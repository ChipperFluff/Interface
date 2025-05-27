package Boris;

import java.util.Scanner;

import Boris.exceptions.InvalidViewActionReturn;
import Boris.intern.v_help;
import Boris.intern.v_select;
import Boris.guard.PrintDog;

public class Interface {
    public final Scanner scanner = new Scanner(System.in);
    private Global global = Global.getInstance();
    private Boolean active = true;
    private Class<? extends View> defaultView = v_select.class;
    private View selectedView;
    public int[] __terminalSize;
    public int __renderCycleLines;

    public Interface() {}

    public Interface(Class<? extends View> defaultView) {
        this.defaultView = defaultView;
    }

    public void registerView(Class<? extends View> viewClass) {
        global.registeredViews.add(viewClass);
    }

    public Boolean selectView(View viewInstance) {
        selectedView = viewInstance;
        return viewInstance.init();
    }

    public Boolean selectView(Class<? extends View> viewClass) {
        View new_view = View.instantiate(viewClass);
        selectedView = new_view;
        return new_view.init();
    }
    
    private Boolean cycle() {
        selectedView.initBase(this);
        System.out.print("\r\n"); // Force a clean break before screen clear
        System.out.flush(); 
        this.__terminalSize = TerminalSize.getTerminalSize();
        this.__renderCycleLines = 0;

        Helper.clearScreen();
        selectedView.draw();
        System.out.flush(); 

        int overheadLines = 1; // prompt + debug (or just prompt if no debug)
        int linesToFill = Math.max(0, this.__terminalSize[0] - __renderCycleLines - overheadLines);

        for (int i = 0; i < linesToFill; i++) {
            System.out.println();
        }

        String view_prompt = (selectedView.viewPrompt==null ? "" : selectedView.viewPrompt);
        System.out.print(view_prompt + "> ");
        String userInputRaw = scanner.nextLine();
        System.out.println();

        Action viewResponse = selectedView.onCommand(userInputRaw);

        if (viewResponse == null) {
            return true;
        }

        if (viewResponse instanceof Action) {
            return viewResponse.execute(this);
        } 

        // should not even come here but nice to have
        throw new InvalidViewActionReturn();
    }

    private void mainLoop() {
        selectView(defaultView);

        while (active) {
            active = cycle();
        }
    }

    public void start() {
        PrintDog.start(true);
        View.scanner = scanner;
        registerView(v_help.class);
        registerView(v_select.class);

        mainLoop();
    }
}
