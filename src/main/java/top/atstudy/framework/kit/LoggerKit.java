package top.atstudy.framework.kit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.util.UUID;

public class LoggerKit {
    public static final String X_RID = "X-RID";
    public static final String X_REMOTE = "X-REMOTE";
    public static final String X_RESPONSE_DATA = "X-RESPONSE-DATA";
    public static final String LOG_FILE_PATH = "LOG-FILE-PATH";
    public static final String X_HTTP_STATUS = "X_HTTP_STATUS";
    public static final String LOG_MAX_HISTORY = "LOG-MAX-HISTORY";

    public LoggerKit() {
    }

    public static void pushLogFilePath(String logFilePath) {
        String property = System.getProperty("LOG-FILE-PATH");
        if (StringUtils.isEmpty(property)) {
            System.setProperty("LOG-FILE-PATH", logFilePath);
        }

    }

    public static void pushLogMaxHistory(String logMaxHistory) {
        String property = System.getProperty("LOG-MAX-HISTORY");
        if (StringUtils.isEmpty(property)) {
            System.setProperty("LOG-MAX-HISTORY", logMaxHistory);
        }

    }

    public static void pushRemoteAddr(String remoteAddr) {
        MDC.put("X-REMOTE", remoteAddr);
    }

    public static void pushRID(String rid) {
        MDC.put("X-RID", StringUtils.isEmpty(rid) ? UUID.randomUUID().toString() : rid);
    }

    public static void pushResponseData(String dataStr) {
        MDC.put("X-RESPONSE-DATA", dataStr);
    }

    public static void pushHttpStatus(String status) {
        MDC.put("X_HTTP_STATUS", status);
    }

    public static String getRemoteAddr() {
        String result = MDC.get("X-REMOTE");
        return result == null ? "" : result;
    }

    public static String getRID() {
        String result = MDC.get("X-RID");
        return result == null ? "" : result;
    }

    public static String getResponseData() {
        String result = MDC.get("X-RESPONSE-DATA");
        return result == null ? "" : result;
    }

    public static String getHttpStatus() {
        String result = MDC.get("X_HTTP_STATUS");
        return result == null ? "" : result;
    }

    public static void release() {
        MDC.remove("X-RESPONSE-DATA");
        MDC.remove("X-REMOTE");
        MDC.remove("X-RID");
        MDC.remove("X_HTTP_STATUS");
    }
}
