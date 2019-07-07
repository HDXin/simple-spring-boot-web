package top.atstudy.framework.kit;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalKit {
    public static final ThreadLocal<Map<String, String>> GLOBAL_THREAD_LOCAL = new ThreadLocal();

    public ThreadLocalKit() {
    }

    private static void set(String key, String value) {
        if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)) {
            Map<String, String> entry = (Map)GLOBAL_THREAD_LOCAL.get();
            if (entry == null) {
                entry = new HashMap(10);
            }

            ((Map)entry).put(key, value);
            GLOBAL_THREAD_LOCAL.set(entry);
        } else {
            throw new IllegalArgumentException(String.format("ThreadLocal argument empty for set , key : {%s}, value : {%s}", key, value));
        }
    }

    private void remove(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException(String.format("ThreadLocal argument empty for remove method , key : {%s}, value : {%s}", key));
        } else {
            Map<String, String> entry = (Map)GLOBAL_THREAD_LOCAL.get();
            if (entry != null) {
                entry.remove(key);
                if (entry.size() == 0) {
                    clean();
                } else {
                    GLOBAL_THREAD_LOCAL.set(entry);
                }
            }

        }
    }

    private String get(String key) {
        String result = null;
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException(String.format("ThreadLocal argument empty for remove method , key : {%s}, value : {%s}", key));
        } else {
            Map<String, String> entry = (Map)GLOBAL_THREAD_LOCAL.get();
            if (entry != null && entry.containsKey(key)) {
                result = (String)entry.get(key);
            }

            return result;
        }
    }

    public static void clean() {
        GLOBAL_THREAD_LOCAL.remove();
    }
}
