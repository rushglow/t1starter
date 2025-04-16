package org.batukhtin.t1starter.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

@Aspect
@Slf4j
public class LoggingAspect {

    private final String root;

    public LoggingAspect(String root) {
        this.root = root;
    }

    @Before("@annotation(org.batukhtin.t1starter.aspect.annotation.LogExecution)")
    public void loggingBefore(JoinPoint joinPoint) {
        loggingWithRoot("Called method: " + joinPoint.getSignature().getName() + "with parameters: " + Arrays.toString(joinPoint.getArgs()));

    }

    @AfterThrowing(
            pointcut = "@annotation(org.batukhtin.t1starter.aspect.annotation.LogException)",
            throwing = "exception"
    )
    public void loggingAfterThrowing(JoinPoint joinPoint, Exception exception) {
        loggingWithRoot("Exception in method" + joinPoint.getSignature().getName() + " with parametr: " + Arrays.toString(joinPoint.getArgs()));
        loggingWithRoot("Exception is : " + exception.getClass().getName() + " with message: " + exception.getMessage());
    }

    @AfterReturning(
            pointcut = "@annotation(org.batukhtin.t1starter.aspect.annotation.HandlingResult)",
            returning = "result"
    )
    public void afterReturning(JoinPoint joinPoint, Object result) {
        loggingWithRoot("Method: " + joinPoint.getSignature().getName() + " with parameters: " + Arrays.toString(joinPoint.getArgs()));
        loggingWithRoot("Result is : " + result);
    }

    @Around("@annotation(org.batukhtin.t1starter.aspect.annotation.PerfomanceTracking)")
    public Object loggingAround(ProceedingJoinPoint joinPoint)  {
        Object proceed;


        loggingWithRoot("Perfomance test START: " + joinPoint.getSignature().getName());

        long start = System.currentTimeMillis();

        try {
            proceed = joinPoint.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException("Aspect failed");
        }

        long end = System.currentTimeMillis();

        loggingWithRoot("Perfomance test FINISH: " + joinPoint.getSignature().getName());
        loggingWithRoot("Time of perfomans test: " + (end - start));

        return proceed;
    }

    public void loggingWithRoot( String message) {
        switch (root) {
            case "DEBUG" -> log.warn(message);
            case "WARN" -> log.warn(message);
            case "ERROR" -> log.error(message);
            default -> log.info(message);
        }
    }
}
