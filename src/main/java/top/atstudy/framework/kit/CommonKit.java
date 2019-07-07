package top.atstudy.framework.kit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;

public class CommonKit {
    private static final String ADD = "+";
    private static final String SUBTRACTION = "-";
    private static final String MULTIPLICATION = "*";
    private static final String DIVISION = "/";

    public CommonKit() {
    }

    public static Timestamp convertDate(Date date) {
        long time = date == null ? 0L : date.getTime();
        return new Timestamp(time);
    }

    public static Date parseDate(String strDate, String format) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        try {
            date = sdf.parse(strDate);
        } catch (ParseException var5) {
            var5.printStackTrace();
        }

        return date;
    }

    public static String formatDate(Date strDate, String format) {
        String date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        date = sdf.format(strDate);
        return date;
    }

    public static Double doubleCalculate(Double a, String calculator, Double b) {
        BigDecimal ba = BigDecimal.valueOf(a);
        BigDecimal bb = BigDecimal.valueOf(b);
        BigDecimal bc = null;
        if ("+".equals(calculator)) {
            bc = ba.add(bb);
        } else if ("-".equals(calculator)) {
            bc = ba.subtract(bb);
        } else if ("*".equals(calculator)) {
            bc = ba.multiply(bb);
        } else if ("/".equals(calculator)) {
            bc = ba.divide(bb, 2, 4);
        }

        return bc.doubleValue();
    }

    public static int calculateAge(Date birthday) {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthday)) {
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        } else {
            int yearNow = cal.get(1);
            int monthNow = cal.get(2) + 1;
            int dayOfMonthNow = cal.get(5);
            cal.setTime(birthday);
            int yearBirth = cal.get(1);
            int monthBirth = cal.get(2) + 1;
            int dayOfMonthBirth = cal.get(5);
            int age = yearNow - yearBirth;
            if (monthNow <= monthBirth) {
                if (monthNow == monthBirth) {
                    if (dayOfMonthNow < dayOfMonthBirth) {
                        --age;
                    }
                } else {
                    --age;
                }
            }

            return age;
        }
    }

    public static <T extends Enum<T>> T valueOf(Class<T> enumType, String value) {
        return StringUtils.isEmpty(value) ? null : Enum.valueOf(enumType, value);
    }

    public static String replaceSpecialStr(String str) {
        return str.replaceAll("[^a-zA-Z_0-9一-龥]", "");
    }

    public static boolean isPhoneNum(String str) {
        boolean isEleven = str.length() == 11;
        boolean isNumber = isNumeric(str);
        boolean isBeginOfOne = "1".equals(str.substring(0, 1));
        return isEleven && isNumber && isBeginOfOne;
    }

    public static boolean isNumeric(String str) {
        for(int i = 0; i < str.length(); ++i) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static String generateRandomStr(int digits) {
        String randomStr = UUID.randomUUID().toString().substring(0, digits);
        return randomStr;
    }

    public static Integer generateRandomInt(int min, int max) {
        Random rand = new Random();
        int tmp = Math.abs(rand.nextInt());
        return tmp % (max - min + 1) + min;
    }

    public static String convertToSecretPhone(String phoneNum) {
        int end = phoneNum.length() - 2;
        int begin = end - 6;
        String secret = phoneNum.substring(begin, end);
        return phoneNum.replace(secret, "*****");
    }

    public static String readInputStreamToString(InputStream inputStream) throws IOException {
        String result = null;

        try {
            result = new String(readInputStreamToBytes(inputStream), "UTF-8");
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
        }

        return result;
    }

    public static byte[] readInputStreamToBytes(InputStream inputStream) throws IOException {
        byte[] resultBytes = new byte[0];
        ByteArrayOutputStream bufferStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];

        try {
            int len;
            while((len = inputStream.read(bytes)) != -1) {
                bufferStream.write(bytes, 0, len);
            }

            resultBytes = bufferStream.toByteArray();
        } finally {
            bufferStream.close();
            inputStream.close();
        }

        return resultBytes;
    }
}