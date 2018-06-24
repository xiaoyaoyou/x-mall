/*
 * Copyright © 上海庆谷豆信息科技有限公司.
 */

package io.xiaoyaoyou.xmall.core.common;


import io.xiaoyaoyou.xmall.core.common.consts.ApiResponseConstants;

public class BaseResponse<T> implements ApiResponse<T> {
    public static final ApiResponse<String> SUCCESS = new BaseResponse<String>(null);
    public static final ApiResponse<String> ACCESS_DENIED = new BaseResponse<String>(ApiResponseConstants.CODE_ACCESS_DENIED, "Access Denied", null);
    public static final ApiResponse<String> INTERNAL_ERROR = new BaseResponse<String>(ApiResponseConstants.CODE_INTERNAL_ERROR, "Internal Error", null);

    private int code = ApiResponseConstants.CODE_SUCCESS;
    private String message = "success";
    private T result;
    private long serverTime = System.currentTimeMillis();

    public BaseResponse(T result) {
        this.result = result;
    }

    public BaseResponse(int code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public static <T> BaseResponse<T> success(T result) {
        return new BaseResponse<>(result);
    }

    public static BaseResponse<String> error(int code, String message) {
        return new BaseResponse<String>(code, message, null);
    }

    public static BaseResponse<String> genericServiceError(String message) {
        return error(ApiResponseConstants.CODE_GENERIC_SERVICE_ERROR, message);
    }

    public static BaseResponse<String> parameterError(String message) {
        return error(ApiResponseConstants.CODE_PARAM_ERROR, message);
    }

    @Override
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public long getServerTime() {
        return serverTime;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }
}
