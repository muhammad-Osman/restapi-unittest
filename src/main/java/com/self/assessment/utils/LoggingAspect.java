package com.self.assessment.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.self.assessment.controller..*(..))")
    public Object logRequestsAndResponses(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Incoming request: " + joinPoint.getSignature());
        Object result = joinPoint.proceed();
        logger.info("Response: " + result);
        return result;
    }

}