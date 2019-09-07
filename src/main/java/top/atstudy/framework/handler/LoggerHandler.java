package top.atstudy.framework.handler;

import org.apache.commons.lang3.StringUtils;
import top.atstudy.framework.config.IApplicationConfig;
import top.atstudy.framework.core.EnumUrlPatternType;
import top.atstudy.framework.core.Handler;
import top.atstudy.framework.kit.CommonKit;
import top.atstudy.framework.kit.HttpHeadKit;
import top.atstudy.framework.kit.LoggerKit;
import top.atstudy.framework.spec.exception.RequestMediaNotFoundException;
import top.atstudy.framework.spec.resolver.RequestMediaResolver;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoggerHandler extends Handler {
    public LoggerHandler() {
    }

    public LoggerHandler(String... patternConsts) {
        super(patternConsts);
    }

    public LoggerHandler(EnumUrlPatternType patternType, String... patternConsts) {
        super(patternType, patternConsts);
    }

    public void handle(String url, HttpServletRequest request, HttpServletResponse response) {
        long startTime = System.currentTimeMillis();
        this.configLogAttr(request);
        this.logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        try {
            this.printRequestLog(request);
            this.next.handle(url, request, response);
        } catch (Exception var10) {
            this.logger.error(var10.getMessage(), var10);
        } finally {
            this.logger.info("<= <<Response-Http-Status>> : {}", response.getStatus());
            this.logger.info("<= <<Response-Content-Type>> : {}", LoggerKit.getResponseData().length() > 0 ? "application/json" : "NULL");
            this.printResponseLog();
            this.logger.info("<= <<Time-Taken>> : {}", System.currentTimeMillis() - startTime);
            this.logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            LoggerKit.release();
        }

    }

    private void configLogAttr(HttpServletRequest request) {
        String remoteAddr = HttpHeadKit.getRequestIp(request);
        LoggerKit.pushRemoteAddr(remoteAddr);
        String requestId = null;

        try {
            requestId = RequestMediaResolver.getGatewayRequestId(request);
        } catch (RequestMediaNotFoundException var5) {
            requestId = UUID.randomUUID().toString();
        }

        LoggerKit.pushRID(requestId);
    }

    private void printResponseLog() {
        if (IApplicationConfig.GLOBAL_CONSTANT.getDebug()) {
            System.out.println("----- <<Response Body>> ----------------------------------");
            System.out.println(LoggerKit.getResponseData());
            System.out.println("--------------------------------------------------------");
        } else {
            this.logger.info("<= {}", LoggerKit.getResponseData());
        }

    }

    private void printRequestLog(HttpServletRequest request) throws IOException {
        String requestURI = request.getRequestURI();
        StringBuilder stringBuilder = new StringBuilder();
        if (request.getQueryString() != null) {
            requestURI = requestURI + "?" + request.getQueryString();
        }

        this.logger.info("=> <<{}>> {}", request.getMethod(), requestURI);
        if (request.getContentType() != null) {
            if (request.getContentType().contains("application/x-www-form-urlencoded")) {
                Map<String, String[]> parameterMap = request.getParameterMap();
                parameterMap.forEach((key, value) -> {
                    stringBuilder.append(key).append(" => ").append(value.length > 1 ? "\"" + value[0] + "\"(more)" : "\"" + value[0] + "\"").append("; ");
                });
            }

            if (request.getContentType().contains("multipart/form-data")) {
                stringBuilder.append(request.getContentLength() / 1000).append("KB");
            }

            if (request.getContentType().contains("application/json") || request.getContentType().contains("application/xml")) {
                String requestBody = CommonKit.readInputStreamToString(request.getInputStream()).replace("\r", "").replace("\n", "");
                stringBuilder.append(requestBody);
            }

            if (!IApplicationConfig.GLOBAL_CONSTANT.getDebug()) {
                this.logger.info("=> <<Request-Content-Type>> : {}", request.getContentType());
                this.logger.info("=> {}", stringBuilder.toString());
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("----- Request Content-Type : ").append(request.getContentType()).append(" ---");
                int headLength = sb.length();
                sb.append("\n");
                String[] var6 = stringBuilder.toString().split(";");
                int var7 = var6.length;

                for(int var8 = 0; var8 < var7; ++var8) {
                    String s = var6[var8];
                    if (StringUtils.isNotEmpty(s.trim())) {
                        sb.append(s.trim()).append("\n");
                    }
                }

                sb.append("\n");

                for(int i = 0; i < headLength; ++i) {
                    sb.append("-");
                }

                System.out.println(sb.toString());
            }
        }

    }
}
