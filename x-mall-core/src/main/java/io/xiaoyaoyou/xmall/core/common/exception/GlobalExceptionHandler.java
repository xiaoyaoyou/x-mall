package io.xiaoyaoyou.xmall.core.common.exception;

import io.xiaoyaoyou.xmall.common.exception.ServiceException;
import io.xiaoyaoyou.xmall.core.common.BaseResponse;
import io.xiaoyaoyou.xmall.core.common.consts.ApiResponseConstants;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    public BaseResponse<String> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        logger.error(e.getMessage(), e);
        return new BaseResponse<>(ApiResponseConstants.CODE_INTERNAL_ERROR, "internal error", null);
    }

    @ExceptionHandler(value = ServiceException.class)
    @ResponseBody
    public BaseResponse<String> serviceExceptionHandler(HttpServletRequest req, ServiceException e) throws Exception {
        logger.error(e.getMessage(), e);
        return new BaseResponse<>(ApiResponseConstants.CODE_GENERIC_SERVICE_ERROR, e.getMessage(), null);
    }
}

