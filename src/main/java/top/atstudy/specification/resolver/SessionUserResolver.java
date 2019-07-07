package top.atstudy.specification.resolver;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import top.atstudy.specification.exception.SessionUserNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class SessionUserResolver {
    public static final String SESSION_USER = "X-SGW-SESSION-USER";
    public static final String SESSION_USER_ENCODED = "X-SGW-SESSION-USER-ENCODED";

    public SessionUserResolver() {
    }

    public static SessionUser getSessionUserFromGateway(HttpServletRequest request) throws SessionUserNotFoundException {
        return (SessionUser)getSessionUserFromGateway(request, SessionUser.class);
    }

    public static <T> T getSessionUserFromGateway(HttpServletRequest request, Class<T> clazz) throws SessionUserNotFoundException {
        String encoded = request.getHeader("X-SGW-SESSION-USER-ENCODED");
        String sessionToken;
        if (StringUtils.isNotEmpty(encoded)) {
            try {
                sessionToken = URLDecoder.decode(encoded, "UTF-8");
                return JSON.parseObject(sessionToken, clazz);
            } catch (UnsupportedEncodingException var4) {
                ;
            }
        }

        sessionToken = request.getHeader("X-SGW-SESSION-USER");
        if (StringUtils.isEmpty(sessionToken)) {
            throw new SessionUserNotFoundException();
        } else {
            return JSON.parseObject(sessionToken, clazz);
        }
    }
}
