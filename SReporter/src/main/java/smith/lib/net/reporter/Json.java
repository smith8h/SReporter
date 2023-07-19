package smith.lib.net.reporter;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class Json {

    private final HashMap<String, Object> map = new HashMap<>();

    public void put(String key, Object value) {
        if (value != null) map.put(key, value);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        builder.append("{");

        int i = 0;
        for (Map.Entry<String, Object> entry : entrySet) {
            Object val = entry.getValue();
            builder.append(q(entry.getKey())).append(":");

            if (val instanceof String) {
                builder.append(q(String.valueOf(val)));
            } else if (val instanceof Integer) {
                builder.append(Integer.valueOf(String.valueOf(val)));
            } else if (val instanceof Boolean) {
                builder.append(val);
            } else if (val instanceof Json) {
                builder.append(val.toString());
            } else if (val.getClass().isArray()) {
                builder.append("[");
                int len = Array.getLength(val);
                for (int j = 0; j < len; j++) {
                    builder.append(Array.get(val, j).toString()).append(j != len - 1 ? "," : "");
                }
                builder.append("]");
            }
            builder.append(++i == entrySet.size() ? "}" : ",");
        }
        return builder.toString();
    }

    private String q(String string) {
        return "\"" + string + "\"";
    }
}
