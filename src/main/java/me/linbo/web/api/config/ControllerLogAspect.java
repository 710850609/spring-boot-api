package me.linbo.web.api.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 请求日志切面
 * @author LinBo
 * @date 2019-11-25 15:09
 */
@Aspect
@Component
@Slf4j
public class ControllerLogAspect {

    @Autowired
    private HttpServletRequest request;

    @Around("execution(* me.linbo.web.api.controller.*.*(..))")
    public Object doLog(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object[] args = pjp.getArgs();
        Signature signature = pjp.getSignature();
        Method method = ((MethodSignature) signature).getMethod();
        try {
            log.debug("\n\r" +
                    "==================== HTTP请求\t{}\t{}\n\r" +
                    "==================== 处理方法\t{}\n\r" +
                    "==================== 请求参数\t{}", request.getMethod(), request.getRequestURI(), method, Arrays.toString(args));
            Object proceed = pjp.proceed();
            return proceed;
        } catch (Throwable e) {
            log.error("{} 出现异常", method, e);
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("{} 耗时 {} ms", method, endTime - startTime);
        }
    }

}
