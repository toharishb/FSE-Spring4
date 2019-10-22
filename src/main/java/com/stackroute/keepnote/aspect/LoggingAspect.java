package com.stackroute.keepnote.aspect;

/* Annotate this class with @Aspect and @Component */

/*public class LoggingAspect {

	
	 * Write loggers for each of the methods of controller, any particular method
	 * will have all the four aspectJ annotation
	 * (@Before, @After, @AfterReturning, @AfterThrowing).
	 
}
*/
import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.stackroute.keepnote.controller.UserController;


@Aspect
@Component
public class LoggingAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Before("execution(* com.stackroute.keepnote.controller.UserController.getUser(..))")
	public void beforegetUser(JoinPoint joinpoint) {
		logger.info("----------@Before-----------");
		logger.debug("Method name:"+joinpoint.getSignature().getName());
		logger.debug("----------------------------------");
	}
	
	@After("execution(* com.stackroute.keepnote.controller.UserController.getUser(..))")
	public void aftergetUser(JoinPoint joinpoint) {
		logger.info("----------@After-----------");
		logger.debug("Method name:"+joinpoint.getSignature().getName());
		logger.debug("Method Args:"+Arrays.toString(joinpoint.getArgs()));
		logger.debug("----------------------------------");
	}
	
	@AfterReturning(pointcut="execution(* com.stackroute.keepnote.controller.UserController.getUser(..))", returning = "result")
	public void afterReturninggetUser(JoinPoint joinpoint, Object result) {
		logger.info("----------@AfterReturning-----------");
		logger.debug("Method name:"+joinpoint.getSignature().getName());
		logger.debug("Method Args:"+Arrays.toString(joinpoint.getArgs()));
		logger.debug("Method ReturnValue:"+result);
		logger.debug("----------------------------------");
	}
	
	@AfterThrowing(pointcut="execution(* com.stackroute.keepnote.controller.UserController.getUser(..))", throwing="error")
	public void afterThrowinggetUser(JoinPoint joinpoint, Throwable error) {
		logger.info("----------@AfterThrowing-----------");
		logger.debug("Method name:"+joinpoint.getSignature().getName());
		logger.debug("Method Args:"+Arrays.toString(joinpoint.getArgs()));
		logger.debug("Exception:"+error);
		logger.debug("----------------------------------");
	}
	
	
}