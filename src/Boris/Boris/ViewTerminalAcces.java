package Boris;

public class ViewTerminalAcces {
    private Interface base;

    public ViewTerminalAcces(Interface base) {
        this.base = base;
    }

    public void print(String text) {
        base.__renderCycleLines += 1;
        System.out.print(text);
    }

    public int getWidth() {
        return base.__terminalSize[1];
    }

    public int getHeight() {
        return base.__terminalSize[0];
    }

    public int getCurrentRclCount() {
        return base.__renderCycleLines;
    }

    public void fill(int size) {
        for (int i = 0; i < size; i++) {
            print("\n");
        }
    }
}
