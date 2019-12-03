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

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Method;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Map;

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
            if (log.isDebugEnabled()) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                Class<?> clazz = pjp.getSignature().getDeclaringType();
                String curTime = format.format(new Date());
                StringBuffer logBuf = new StringBuffer();
                Map<String, String[]> parameterMap = request.getParameterMap();

                logBuf.append("\n\r--------------------------------- ").append(curTime).append(" --------------------------\n\r");
                logBuf.append("Url         : ").append(request.getMethod()).append(" ").append(request.getRequestURI()).append("\n\r");
                logBuf.append("UrlPara     : ").append(JSON.toJSON(parameterMap)).append("\n\r");
                logBuf.append("Controller  : ").append(clazz.getName()).append(".(").append(clazz.getSimpleName()).append(".java:1)").append("\n\r");
                logBuf.append("Method      : ").append(method.getName()).append("\n\r");
                logBuf.append("Parameters  : ").append(JSON.toJSON(args)).append("\n\r");
                logBuf.append("------------------------------------------------------------------------------------\n\r");
                log.debug(logBuf.toString());
            }
            Object proceed = pjp.proceed();
            return proceed;
        } catch (Throwable e) {
            log.error("{} 出现异常", method.getName(), e);
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            if (log.isDebugEnabled()) {
                log.debug("{} 耗时 {} ms", method.getName(), endTime - startTime);
            }
        }
    }

}
