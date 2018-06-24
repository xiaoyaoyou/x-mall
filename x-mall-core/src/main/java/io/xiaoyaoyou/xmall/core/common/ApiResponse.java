/*
 * Copyright © 上海庆谷豆信息科技有限公司.
 */

package io.xiaoyaoyou.xmall.core.common;

/**
 *
 */
public interface ApiResponse<T> {
    int getCode();

    String getMessage();

    long getServerTime();

    T getResult();
}
