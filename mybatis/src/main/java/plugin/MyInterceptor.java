package plugin;


import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;

/**
 * spring-demo 2015/6/6 22:17
 * fuquanemail@gmail.com
 */

/**Intercepts : 这是一个拦截器
 * Signature : 具体的拦截点
 * 拦截Executor接口中参数类型为MappedStatement、Object、RowBounds和ResultHandler的query方法
 */
@Intercepts({
        @Signature(method = "query", type = Executor.class, args = {
                MappedStatement.class, Object.class, RowBounds.class,
                ResultHandler.class})
})
public class MyInterceptor implements Interceptor {
    static Logger log = LoggerFactory.getLogger(MyInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //intercept方法就是要进行拦截的时候要执行的方法

        Object result = invocation.proceed();
        System.err.println("intercept args:" + Arrays.toString(invocation.getArgs()));
        System.err.println("intercept method name:" + invocation.getMethod().getName());
        System.err.println("intercept target:" + invocation.getTarget());


        return result;
    }

    /**
     * plugin方法中我们可以决定是否要进行拦截进而决定要返回一个什么样的目标对象.
     * Plugin.wrap(target, this) : 会把 this包装成一个代理对象。
     * @param target 目标对象
     * @return
     */
    @Override
    public Object plugin(Object target) {
        System.err.println("target class name:"+target.getClass().getSimpleName());
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        String prop1 = properties.getProperty("prop1");
        String prop2 = properties.getProperty("prop2");
        log.info(" setProperties  .... prop1  = [{}]", prop1);
        log.info(" setProperties  .... prop2  = [{}]", prop2);
    }
}
