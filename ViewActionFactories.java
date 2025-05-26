package Interface;

import Interface.Interface;
import Interface.View;
import Interface.Action;
import Interface.action.a_redirect;
import Interface.action.a_stop;
import Interface.action.a_nop;

public class ViewActionFactories {
    public Action redirect(Class<? extends View> target) {
        return new a_redirect(target);
    }

    public Action redirect(View target) {
        return new a_redirect(target);
    }

    public Action stop() {
        return new a_stop();
    }

    public Action nop() {
        return new a_nop();
    }
}
