/*
 * Copyright © 上海庆谷豆信息科技有限公司.
 */

package io.xiaoyaoyou.xmall.common.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 */
public class StringToDateTimeConverter implements Converter<String, LocalDateTime> {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Override
    public LocalDateTime convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        } else {
            return LocalDateTime.from(DATE_TIME_FORMATTER.parse(source));
        }
    }
}
