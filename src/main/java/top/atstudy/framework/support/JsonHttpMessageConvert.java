package top.atstudy.framework.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import top.atstudy.framework.kit.LoggerKit;

public class JsonHttpMessageConvert extends FastJsonHttpMessageConverter {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public JsonHttpMessageConvert() {
        super.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8, MediaType.ALL));
        this.init();
    }

    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        this.logger.debug("=> InputStream Convert To Json");
        return super.read(type, contextClass, inputMessage);
    }

    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return super.readInternal(clazz, inputMessage);
    }

    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        this.logger.debug("=> OutputStream Convert To Json");
        outputMessage.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        LoggerKit.pushResponseData(JSON.toJSONString(o, new SerializerFeature[]{SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty}));
        super.writeInternal(o, outputMessage);
    }

    private void init() {
        super.getFastJsonConfig().setSerializerFeatures(new SerializerFeature[]{SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty});
    }
}