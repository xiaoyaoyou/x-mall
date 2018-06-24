/*
 * Copyright © 上海庆谷豆信息科技有限公司.
 */

package io.xiaoyaoyou.xmall.common.exception;

/**
 *
 */
public class CommonException extends RuntimeException {
    public CommonException(String message) {
        super(message);
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonException(Throwable cause) {
        super(cause);
    }
}
