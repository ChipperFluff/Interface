package Boris;

public abstract class Action {
    protected Global global = Global.getInstance();
    public abstract Boolean execute(Interface base);
}
