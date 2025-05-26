package Interface.action;

import Interface.Action;
import Interface.Interface;
import Interface.View;
import java.util.Map;
import java.util.List;
import java.util.Set;

public class a_dd extends Action {
    private final Object[] data;

    public a_dd(Object... data) {
        this.data = data;
    }

    @Override
    public Boolean execute(Interface base) {
        System.out.println("===== Laravel-style Dump & Die =====");

        for (int i = 0; i < data.length; i++) {
            System.out.println("[" + i + "] => " + formatValue(data[i]));
        }

        System.out.println("============ END ============");
        return false;
    }

    private String formatValue(Object obj) {
        if (obj == null) return "null";

        if (obj instanceof Map) {
            StringBuilder sb = new StringBuilder("Map {\n");
            ((Map<?, ?>) obj).forEach((k, v) ->
                sb.append("  ").append(k).append(" => ").append(formatValue(v)).append("\n")
            );
            sb.append("}");
            return sb.toString();
        }

        if (obj instanceof List) {
            StringBuilder sb = new StringBuilder("List [\n");
            int i = 0;
            for (Object val : (List<?>) obj) {
                sb.append("  ").append(i++).append(" => ").append(formatValue(val)).append("\n");
            }
            sb.append("]");
            return sb.toString();
        }

        if (obj instanceof Set) {
            StringBuilder sb = new StringBuilder("Set {\n");
            for (Object val : (Set<?>) obj) {
                sb.append("  ").append(formatValue(val)).append("\n");
            }
            sb.append("}");
            return sb.toString();
        }

        if (obj.getClass().isArray()) {
            StringBuilder sb = new StringBuilder("Array [\n");
            Object[] arr = (Object[]) obj;
            for (int i = 0; i < arr.length; i++) {
                sb.append("  ").append(i).append(" => ").append(formatValue(arr[i])).append("\n");
            }
            sb.append("]");
            return sb.toString();
        }

        return obj.toString();
    }
}
