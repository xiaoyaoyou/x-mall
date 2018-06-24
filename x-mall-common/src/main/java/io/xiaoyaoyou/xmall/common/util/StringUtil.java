package io.xiaoyaoyou.xmall.common.util;

import java.util.Random;

/**
 * Created by xiaoyaoyou on 2018/5/12.
 */
public class StringUtil {
    /**
     * 获取一定长度的随机字符串，只包含英文字母与数字
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 获取一定长度的随机字符串，只包含数字
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomNumByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
