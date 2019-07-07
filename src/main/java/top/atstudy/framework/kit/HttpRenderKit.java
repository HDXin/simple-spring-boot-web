package top.atstudy.framework.kit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class HttpRenderKit {
    protected static final Logger logger = LoggerFactory.getLogger(HttpRenderKit.class);

    public HttpRenderKit() {
    }

    public static String readData(HttpServletRequest request) {
        BufferedReader br = null;

        try {
            StringBuilder result = new StringBuilder();
            br = request.getReader();
            String line = null;

            while((line = br.readLine()) != null) {
                result.append(line).append("\n");
            }

            line = result.toString();
            String var4 = line;
            return var4;
        } catch (IOException var13) {
            throw new RuntimeException(var13);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException var12) {
                    logger.error(var12.getMessage());
                }
            }

        }
    }

    public static void flushJson(HttpServletResponse response, byte[] jsonBytes) {
        response.setCharacterEncoding("UTF-8");
        ServletOutputStream outputStream = null;

        try {
            outputStream = response.getOutputStream();
            outputStream.write(jsonBytes);
            outputStream.flush();
        } catch (IOException var12) {
            logger.error(var12.getMessage());
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException var11) {
                    logger.error(var11.getMessage());
                }
            }

        }

    }

    public static void flushJson(HttpServletResponse response, Object record) {
        LoggerKit.pushResponseData(JSON.toJSONString(record, new SerializerFeature[]{SerializerFeature.DisableCircularReferenceDetect}));
        byte[] jsonBytes = JSON.toJSONString(record, new SerializerFeature[]{SerializerFeature.DisableCircularReferenceDetect}).getBytes();
        response.setContentType("application/json");
        flushJson(response, jsonBytes);
    }
}
