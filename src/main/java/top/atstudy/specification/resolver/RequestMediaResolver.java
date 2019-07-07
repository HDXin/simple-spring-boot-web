package top.atstudy.specification.resolver;

import org.apache.commons.lang3.StringUtils;
import top.atstudy.specification.exception.RequestMediaNotFoundException;

import javax.servlet.http.HttpServletRequest;

public class RequestMediaResolver {
    public static final String SGW_REQUEST_ID = "X-SGW-REQUEST-ID";

    public RequestMediaResolver() {
    }

    public static String getGatewayRequestId(HttpServletRequest request) throws RequestMediaNotFoundException {
        String rid = request.getHeader("X-SGW-REQUEST-ID");
        if (StringUtils.isEmpty(rid)) {
            throw new RequestMediaNotFoundException();
        } else {
            return rid;
        }
    }
}
