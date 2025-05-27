package Boris.action;

import Boris.Action;
import Boris.Interface;
import Boris.Color;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class a_dd extends Action {
    private final Object[] data;

    public a_dd(Object... data) {
        this.data = data;
    }

    @Override
    public Boolean execute(Interface base) {
        System.out.println(Color.ANSI_BLUE + "========== DUMP & DIE ==========" + Color.ANSI_RESET);

        for (int i = 0; i < data.length; i++) {
            Object item = data[i];
            String type = (item == null) ? "null" : item.getClass().getSimpleName();
            String typeColor = getTypeColor(type);

            System.out.println(
                Color.ANSI_WHITE + "[" + i + "] => " +
                typeColor + "(" + type + ") " + formatValue(item) + Color.ANSI_RESET
            );
        }

        System.out.println(Color.ANSI_BLUE + "============ END ============" + Color.ANSI_RESET);
        return false;
    }

    private String formatValue(Object obj) {
        if (obj == null) return Color.ANSI_RED + "null" + Color.ANSI_RESET;

        if (obj instanceof Map) {
            StringBuilder sb = new StringBuilder(Color.ANSI_PURPLE + "Map {\n");
            ((Map<?, ?>) obj).forEach((k, v) -> {
                String type = (v == null) ? "null" : v.getClass().getSimpleName();
                String color = getTypeColor(type);
                sb.append(Color.ANSI_WHITE + "  " + k + " => " + color + "(" + type + ") " + formatValue(v) + "\n");
            });
            sb.append(Color.ANSI_PURPLE + "}");
            return sb.toString();
        }

        if (obj instanceof List) {
            StringBuilder sb = new StringBuilder(Color.ANSI_BLUE + "List [\n");
            int i = 0;
            for (Object val : (List<?>) obj) {
                String type = (val == null) ? "null" : val.getClass().getSimpleName();
                String color = getTypeColor(type);
                sb.append(Color.ANSI_WHITE + "  " + i++ + " => " + color + "(" + type + ") " + formatValue(val) + "\n");
            }
            sb.append(Color.ANSI_BLUE + "]");
            return sb.toString();
        }

        if (obj instanceof Set) {
            StringBuilder sb = new StringBuilder(Color.ANSI_RED + "Set {\n");
            for (Object val : (Set<?>) obj) {
                String type = (val == null) ? "null" : val.getClass().getSimpleName();
                String color = getTypeColor(type);
                sb.append(Color.ANSI_WHITE + "  => " + color + "(" + type + ") " + formatValue(val) + "\n");
            }
            sb.append(Color.ANSI_RED + "}");
            return sb.toString();
        }

        if (obj.getClass().isArray()) {
            StringBuilder sb = new StringBuilder(Color.ANSI_CYAN + "Array [\n");
            int len = java.lang.reflect.Array.getLength(obj);
            for (int i = 0; i < len; i++) {
                Object val = java.lang.reflect.Array.get(obj, i);
                String type = (val == null) ? "null" : val.getClass().getSimpleName();
                String color = getTypeColor(type);
                sb.append(Color.ANSI_WHITE + "  " + i + " => " + color + "(" + type + ") " + formatValue(val) + "\n");
            }
            sb.append(Color.ANSI_CYAN + "]");
            return sb.toString();
        }

        return getTypeColor(obj.getClass().getSimpleName()) + obj.toString();
    }

    private String getTypeColor(String type) {
        if (type == null) return Color.ANSI_RED;

        switch (type) {
            case "String": return Color.ANSI_GREEN;
            case "Integer":
            case "Long":
            case "Double":
            case "Float":
            case "Short":
            case "Byte": return Color.ANSI_YELLOW;
            case "Boolean": return Color.ANSI_CYAN;
            case "Map": return Color.ANSI_PURPLE;
            case "List": return Color.ANSI_BLUE;
            case "Set": return Color.ANSI_RED;
            case "Object[]": return Color.ANSI_WHITE;
            default: return Color.ANSI_WHITE;
        }
    }
}
