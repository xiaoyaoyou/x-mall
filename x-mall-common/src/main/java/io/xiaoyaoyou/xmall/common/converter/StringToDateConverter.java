/*
 * Copyright © 上海庆谷豆信息科技有限公司.
 */

package io.xiaoyaoyou.xmall.common.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 */
public class StringToDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        } else {
            return LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(source));
        }
    }
}
