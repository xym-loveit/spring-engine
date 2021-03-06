package spring.rpc.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.rpc.client.RpcProxy;
import spring.rpc.service.HelloService;


/**
 * fuquanemail@gmail.com 2016/5/24 13:02
 * description:
 * 1.0.0
 */
public class ClientStart {
    public static void main(String[] args) {
        //http://my.oschina.net/huangyong/blog/361751?fromerr=50U1CUbH
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-rpc-client.xml");
        RpcProxy rpcProxy = context.getBean(RpcProxy.class);
        HelloService helloService = rpcProxy.create(HelloService.class);
        String result = helloService.sayHello("hihi");
        System.err.println(result);

    }
}
