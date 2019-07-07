package top.atstudy.framework.support;

import top.atstudy.framework.kit.CommonKit;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class ReentrantRequestWrapper extends HttpServletRequestWrapper {
    private HttpServletRequest original;
    private byte[] reqBytes;
    private boolean firstTime = true;

    public ReentrantRequestWrapper(HttpServletRequest request) {
        super(request);
        this.original = request;
    }

    public BufferedReader getReader() throws IOException {
        if (this.firstTime) {
            this.firstTime();
        }

        InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(this.reqBytes));
        return new BufferedReader(isr);
    }

    public ServletInputStream getInputStream() throws IOException {
        if (this.firstTime) {
            this.firstTime();
        }

        ServletInputStream stream = new ServletInputStream() {
            private int readIndex = 0;

            public boolean isFinished() {
                return !ReentrantRequestWrapper.this.firstTime && ReentrantRequestWrapper.this.reqBytes.length == this.readIndex;
            }

            public boolean isReady() {
                boolean readyFlag = false;

                try {
                    readyFlag = ReentrantRequestWrapper.this.original.getInputStream().isReady();
                } catch (IOException var3) {
                    var3.printStackTrace();
                }

                return readyFlag;
            }

            public void setReadListener(ReadListener listener) {
            }

            public int read() throws IOException {
                byte b;
                if (ReentrantRequestWrapper.this.reqBytes.length > this.readIndex) {
                    b = ReentrantRequestWrapper.this.reqBytes[this.readIndex++];
                } else {
                    b = -1;
                }

                return b;
            }
        };
        return stream;
    }

    private synchronized void firstTime() throws IOException {
        if (this.firstTime) {
            this.firstTime = false;
            this.reqBytes = CommonKit.readInputStreamToBytes(this.original.getInputStream());
        }
    }
}
