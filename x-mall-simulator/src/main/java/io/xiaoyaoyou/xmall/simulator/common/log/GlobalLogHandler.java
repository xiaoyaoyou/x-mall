package io.xiaoyaoyou.xmall.simulator.common.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class GlobalLogHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalLogHandler.class);

//    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * io.xiaoyaoyou.xmall.simulator.*.controller..*.*(..))")
    public void apiLog(){}

    @Before("apiLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        logger.info("api request, " + request.getRequestURL().toString());
    }

    @AfterReturning(returning = "ret", pointcut = "apiLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        logger.info("api response, " + ret);
    }
}

