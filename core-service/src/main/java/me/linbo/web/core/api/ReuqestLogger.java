package me.linbo.web.core.api;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 请求日志切面
 * @author LinBo
 * @date 2019-11-25 15:09
 */
@Aspect
@Component
@Slf4j
public class ReuqestLogger {

    @Autowired
    private HttpServletRequest request;

    @Around("execution(* me.linbo.web.*.rest.*.*(..))")
    public Object doLog(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object[] args = pjp.getArgs();
        Signature signature = pjp.getSignature();
        Method method = ((MethodSignature) signature).getMethod();
        Class<?> clazz = pjp.getSignature().getDeclaringType();
        Field f = clazz.getSuperclass().getDeclaredField("log");
        Logger logger = log;
        if (f != null) {
            f.setAccessible(true);
            logger = (Logger) f.get(pjp.getTarget());
        }
        try {
            if (logger.isDebugEnabled()) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                String curTime = format.format(new Date());
                StringBuffer logBuf = new StringBuffer();

                logBuf.append("\n\r--------------------------------- ").append(curTime).append(" --------------------------\n\r");
                logBuf.append("Url         : ").append(request.getMethod()).append(" ").append(request.getRequestURI()).append("\n\r");
                logBuf.append("UrlPara     : ").append(request.getQueryString()).append("\n\r");
                logBuf.append("Controller  : ").append(clazz.getName()).append(".(").append(clazz.getSimpleName()).append(".java:1)").append("\n\r");
                logBuf.append("Method      : ").append(method.getName()).append("\n\r");
                logBuf.append("Parameters  : ").append(JSON.toJSON(args)).append("\n\r");
                logBuf.append("------------------------------------------------------------------------------------\n\r");

                logger.debug(logBuf.toString());
            }
            Object proceed = pjp.proceed();
            return proceed;
        } catch (Throwable e) {
            logger.error("{} 出现异常", method.getName(), e);
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            if (logger.isDebugEnabled()) {
                logger.debug("{} 耗时 {} ms", method.getName(), endTime - startTime);
            }
        }
    }

}
