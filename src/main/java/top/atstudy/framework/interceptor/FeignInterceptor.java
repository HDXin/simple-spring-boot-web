package top.atstudy.framework.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import top.atstudy.framework.kit.ThreadLocalKit;

@Configuration
public class FeignInterceptor implements RequestInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(FeignInterceptor.class);

    public FeignInterceptor() {
    }

    public void apply(RequestTemplate template) {
        Map<String, String> headerMap = (Map) ThreadLocalKit.GLOBAL_THREAD_LOCAL.get();
        logger.debug("feign invoke | HttpMethod : {}, Url : {}", template.method(), template.url());
        logger.debug("feign interceptor 接收的参数：{}", headerMap);
        if (headerMap != null && !headerMap.isEmpty()) {
            if (headerMap.containsKey("X-SGW-REQUEST-ID")) {
                logger.debug("header {}: {}", "X-SGW-REQUEST-ID", headerMap.get("X-SGW-REQUEST-ID"));
                template.header("X-SGW-REQUEST-ID", new String[]{(String)headerMap.get("X-SGW-REQUEST-ID")});
            }

            if (headerMap.containsKey("X-SGW-SESSION-USER")) {
                logger.debug("header {}: {}", "X-SGW-SESSION-USER", headerMap.get("X-SGW-SESSION-USER"));
                template.header("X-SGW-SESSION-USER", new String[]{(String)headerMap.get("X-SGW-SESSION-USER")});
            }

            if (headerMap.containsKey("X-SGW-SESSION-USER-ENCODED")) {
                logger.debug("header {}: {}", "X-SGW-SESSION-USER-ENCODED", headerMap.get("X-SGW-SESSION-USER-ENCODED"));
                template.header("X-SGW-SESSION-USER-ENCODED", new String[]{(String)headerMap.get("X-SGW-SESSION-USER-ENCODED")});
            }
        }

    }
}