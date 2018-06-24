/*
 * Copyright © 上海庆谷豆信息科技有限公司.
 */

package io.xiaoyaoyou.xmall.common.util;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public final class Formatters {
    private static ThreadLocal<Map<String, Object>> nfHolder = ThreadLocal.withInitial(HashMap::new);

    public static DecimalFormat getDecimalFormat(String format) {
        Map<String, Object> map = nfHolder.get();
        DecimalFormat result = (DecimalFormat) map.get(format);
        if (result == null) {
            result = new DecimalFormat(format);
            map.put(format, result);
        }
        return result;
    }
}
