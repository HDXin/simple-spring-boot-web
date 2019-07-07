package top.atstudy.framework.handler;

import top.atstudy.framework.core.Handler;
import top.atstudy.framework.kit.LoggerKit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FrameworkDiagnoseDataHandler extends Handler {
    public FrameworkDiagnoseDataHandler() {
    }

    public void handle(String url, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            this.next.handle(url, request, response);
        } finally {
            response.addHeader("rid", LoggerKit.getRID());
        }

    }
}
