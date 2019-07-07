package top.atstudy.framework.handler;

import com.alibaba.fastjson.JSON;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import top.atstudy.framework.core.Handler;
import top.atstudy.framework.kit.ThreadLocalKit;

public class GatewayMediaFetchHandler extends Handler {
    public GatewayMediaFetchHandler() {
    }

    public void handle(String s, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        String xSgwRequestId = httpServletRequest.getHeader("X-SGW-REQUEST-ID");
        String xSgwSessionUser = httpServletRequest.getHeader("X-SGW-SESSION-USER");
        String xSgwSessionUserEncoded = httpServletRequest.getHeader("X-SGW-SESSION-USER-ENCODED");
        this.logger.debug("request  header from sgw param, {}:{}, {}:{}, {}:{}", new Object[]{"X-SGW-REQUEST-ID", xSgwRequestId, "X-SGW-SESSION-USER", xSgwSessionUser, "X-SGW-SESSION-USER-ENCODED", xSgwSessionUserEncoded});
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        HashMap headers = new HashMap(16);

        while(headerNames.hasMoreElements()) {
            String key = (String)headerNames.nextElement();
            headers.put(key, httpServletRequest.getHeader(key));
        }

        this.logger.debug("=> Header :{}", JSON.toJSONString(headers));
        Map<String, String> headerMap = new HashMap(2);
        if (StringUtils.isNotBlank(xSgwRequestId)) {
            headerMap.put("X-SGW-REQUEST-ID", xSgwRequestId);
        }

        if (StringUtils.isNotBlank(xSgwSessionUser)) {
            headerMap.put("X-SGW-SESSION-USER", xSgwSessionUser);
        }

        if (StringUtils.isNotBlank(xSgwSessionUserEncoded)) {
            headerMap.put("X-SGW-SESSION-USER-ENCODED", xSgwSessionUserEncoded);
        }

        ThreadLocalKit.GLOBAL_THREAD_LOCAL.set(headerMap);
        super.next.handle(s, httpServletRequest, httpServletResponse);
        ThreadLocalKit.GLOBAL_THREAD_LOCAL.remove();
    }
}
