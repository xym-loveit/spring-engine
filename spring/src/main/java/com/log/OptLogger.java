package hello.spring.aop.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * fuquanemail@gamil.com
 * Date: 14-6-25 下午3:58
 * 切面
 */

// 只写 @Aspect 也可以，表明这是个切面.

/*@Component("optLogger")
@Aspect*/
public class OptLogger {

    private static  final Logger logger = LoggerFactory.getLogger(OptLogger.class);


    // @Before(value = "servicePointcut()")
    @Before(value = "execution(* com.service.* ..*(..))")
    public void beforeLogger(){

        logger.info("Before =========记录日志操作Before. 在目标方法调用乊前执行。不能阻止后续执行，除非抛异常..");
    }

    @After(value = "servicePointcut()")
    public void afterLogger(){
        logger.info("After =========记录日志操作After..在目标方法调用乊后执行。目标方法正常戒异常都执行。.");
    }

    //异常通知:发生异常执行，如果在目标方法中进行try{} catch(){} 的话，异常通知也不会执行
    @AfterThrowing(value = "servicePointcut()" , throwing = "e")
    public void afterThrowingLogger(Exception e){
        logger.info("afterThrowingLogger =========记录日志操作afterThrowingLogger..在目标方法调用发生异常乊后执行。。.");
        e.printStackTrace();; //可以捕获异常
        logger.info("异常通知捕获异常 [{}]" ,e.getMessage());
    }


    // 1.后置通知是在运行目标方法执行成功后调用的，如果目标方法执行失败（出现异常），后置通知将不被调用
    // 2.后置通知可以得到目标方法的返回值
    @AfterReturning(value = "servicePointcut()" ,returning="returnValue")
    public void afterReturningLogger(Object returnValue){
        logger.info("After =========记录日志操作afterReturningLogger..在目标方法调用乊后执行。目标方法正常结束才执行。。.");
        logger.info("returnValue = [{}]", returnValue);
    }
    @Around(value = "servicePointcut()") //在目标方法调用乊前和乊后执行
    public Object aroundLogger(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("Around 方法执行之前");
        Object obj = pjp.proceed();  // proceed()   Proceed with the next advice or target method invocation
        logger.info("Around 方法执行之后");
        String methodName = pjp.getSignature().getName();
        String className = pjp.getSignature().getClass().getSimpleName();
        logger.info("在" + className + "执行" + methodName + "操作");
        return obj;
    }


    // @Pointcut(value = "切入点表达式或命名切入点" ,argNames = "参数名称")
    /**
     * 利用空的方法来完成切入点表达式
     */
    @Pointcut("execution(* com.service.* ..*(..))")
    public void servicePointcut(){

    }
}
