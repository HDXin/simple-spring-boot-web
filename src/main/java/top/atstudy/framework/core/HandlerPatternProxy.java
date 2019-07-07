package top.atstudy.framework.core;

import java.util.Iterator;
import java.util.regex.PatternSyntaxException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerPatternProxy extends Handler {
    Handler target;

    public HandlerPatternProxy(Handler target) {
        this.target = target;
        this.patternType = this.target.patternType;
        this.patternConsts = this.target.patternConsts;
    }

    private boolean isDoHandler(String url) {
        boolean flag = false;
        Iterator var3 = this.patternConsts.iterator();

        while(var3.hasNext()) {
            String pattern = (String)var3.next();
            boolean suffixMode = pattern.indexOf("*.") == 0;
            boolean tailLikeMode = pattern.indexOf("*") == pattern.length() - 1;
            boolean completeMode = !pattern.contains("*");
            if (suffixMode) {
                flag = url.indexOf(pattern.substring(1)) == url.length() - pattern.length();
            } else if (tailLikeMode) {
                flag = url.indexOf(pattern.substring(0, pattern.length() - 1)) == 0;
            } else {
                if (!completeMode) {
                    throw new PatternSyntaxException(url, pattern, 0);
                }

                flag = url.equalsIgnoreCase(pattern);
            }

            boolean isBreak = false;
            switch(this.patternType) {
                case CONTAIN:
                    flag = flag;
                    isBreak = flag;
                    break;
                case EXCEPT:
                    flag = !flag;
                    isBreak = !flag;
            }

            if (isBreak) {
                break;
            }
        }

        return flag;
    }

    public void handle(String url, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (this.isDoHandler(url)) {
            this.target.handle(url, request, response);
        } else {
            this.target.next.handle(url, request, response);
        }

    }
}
