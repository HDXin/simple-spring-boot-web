package top.atstudy.framework.kit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class HttpHeadKit {
    private static final String X_REAL_IP = "X_Real_Ip";
    private static final String X_FORWARDED_FOR = "X-Forwarded-For";
    protected static final Logger logger = LoggerFactory.getLogger(HttpRenderKit.class);

    private HttpHeadKit() {
    }

    public static String getRequestIp(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        String originRemoteIp = "";
        String realIpKey = "";
        String forwordedForKey = "";

        String ipGroup;
        while(headerNames.hasMoreElements() && !StringUtils.isNotEmpty(realIpKey)) {
            ipGroup = (String)headerNames.nextElement();
            if ("X_Real_Ip".equalsIgnoreCase(ipGroup)) {
                realIpKey = ipGroup;
            } else if ("X-Forwarded-For".equalsIgnoreCase(ipGroup)) {
                forwordedForKey = ipGroup;
            }
        }

        if (StringUtils.isNotEmpty(realIpKey)) {
            originRemoteIp = request.getHeader(realIpKey);
        } else if (StringUtils.isNotEmpty(forwordedForKey)) {
            ipGroup = request.getHeader(forwordedForKey);
            originRemoteIp = ipGroup.split(",")[0];
        } else {
            originRemoteIp = request.getRemoteAddr();
        }

        return originRemoteIp;
    }
}
