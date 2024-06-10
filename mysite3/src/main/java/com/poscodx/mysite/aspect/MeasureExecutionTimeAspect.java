package com.poscodx.mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class MeasureExecutionTimeAspect {

	@Around("execution(* *..*.repository.*.*(..)) || execution(* *..*.service.*.*(..)) || execution(* *..*.controller.*.*(..))")
	public Object adviceAround(ProceedingJoinPoint pjp) throws Throwable {
		// before
		StopWatch sw = new StopWatch();
		sw.start();
		
		Object result = pjp.proceed();
		
		// after
		sw.stop();
		long totalTime = sw.getTotalTimeMillis();
		
		String className = pjp.getTarget().getClass().getName();		// 실행되는 빈의 클래스 이름 얻기
		String methodName = pjp.getSignature().getName();				// 실행되는 빈의 클래스의 메서드 이름 얻기
		String taskName = className + "." + methodName;
		
		System.out.println("[Execution Time][" + taskName + "]" + totalTime + "millis");
		
		return result;
	}
	
}
