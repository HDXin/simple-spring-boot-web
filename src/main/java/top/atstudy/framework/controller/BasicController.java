package top.atstudy.framework.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import top.atstudy.framework.enums.Enum401Error;
import top.atstudy.framework.exception.APIException;
import top.atstudy.framework.kit.HttpRenderKit;
import top.atstudy.framework.kit.LoggerKit;
import top.atstudy.specification.exception.SessionUserNotFoundException;
import top.atstudy.specification.resolver.SessionUser;
import top.atstudy.specification.resolver.SessionUserResolver;

@Scope("prototype")
public class BasicController {
    private static final String STRICT_EVN_KEY = "STRICT";
    private static final String STRICT_EVN_VALUE = "KUAICTO";
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected String httpBody;

    public BasicController() {
    }

    protected SessionUser getSessionUserFromGateway() throws APIException {
        return this.getSessionUserFromGateway(this.request);
    }

    protected SessionUser getSessionUserFromGateway(SessionUser defaultSessionUser) throws APIException {
        return this.getSessionUserFromGateway(this.request, defaultSessionUser);
    }

    protected SessionUser getSessionUserFromGateway(HttpServletRequest request) throws APIException {
        SessionUser defaultSessionUser = new SessionUser();
        defaultSessionUser.setUserName("tester");
        defaultSessionUser.setUserId(-99L);
        return this.getSessionUserFromGateway(request, defaultSessionUser);
    }

    protected SessionUser getSessionUserFromGateway(HttpServletRequest request, SessionUser defaultSessionUser) throws APIException {
        SessionUser sessionUserFromGateway = null;

        try {
            sessionUserFromGateway = SessionUserResolver.getSessionUserFromGateway(request);
        } catch (SessionUserNotFoundException var7) {
            String strictEvnValue = System.getenv("STRICT");
            boolean isStrictMode = StringUtils.isNotEmpty(strictEvnValue) && "KUAICTO".equalsIgnoreCase(strictEvnValue);
            if (isStrictMode) {
                throw new APIException(Enum401Error.SGW_SESSION_USER_NOT_FOUND);
            }

            sessionUserFromGateway = defaultSessionUser;
        }

        sessionUserFromGateway.setOperationId(LoggerKit.getRID());
        return sessionUserFromGateway;
    }

    public HttpServletRequest getRequest() {
        return this.request;
    }

    public BasicController setRequest(HttpServletRequest request) {
        this.request = request;
        return this;
    }

    public HttpServletResponse getResponse() {
        return this.response;
    }

    public BasicController setResponse(HttpServletResponse response) {
        this.response = response;
        return this;
    }

    public BasicController setHttpBody(String httpBody) {
        this.httpBody = httpBody;
        return this;
    }

    public synchronized String getHttpBody() {
        if (StringUtils.isEmpty(this.httpBody)) {
            this.httpBody = HttpRenderKit.readData(this.getRequest()).trim();
        }

        return this.httpBody;
    }
}