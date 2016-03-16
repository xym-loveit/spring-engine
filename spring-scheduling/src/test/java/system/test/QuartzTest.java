package system.test;

import org.quartz.SchedulerException;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.CollectionUtils;
import system.core.quartz.QuartzManager;
import system.core.quartz.config.QuartzParameter;

import java.util.List;

/**
 * fuquanemail@gmail.com 2016/3/11 15:37
 * description:
 * 1.0.0
 */
public class QuartzTest {
    public static void main(String[] args) throws SchedulerException {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        context.start();
        System.err.println(" quartz 启动成功");

        testAllJobs(context);


    }

    public static void testAllJobs(AbstractApplicationContext context) throws SchedulerException {
        QuartzManager quartzManager = context.getBean(QuartzManager.class);
        List<QuartzParameter> quartzParameterList =  quartzManager.getAllJobs();
        if(CollectionUtils.isEmpty(quartzParameterList)){
            System.err.println(" not found job.");
        }else {

            for( QuartzParameter quartzParameter : quartzParameterList){
                System.err.println(quartzParameter);
            }
        }

    }

    public void testSaveJobs(AbstractApplicationContext context){

        QuartzManager quartzManager = context.getBean(QuartzManager.class);
        quartzManager.saveJob("测试调度器1", "job-1", "job-group-1", "system.core.busi.job.TestJob1",
                "测试job-1", false, "trigger-1", "trigger-group-1", true, "0/3 * * * * ?", null, null, null);

        quartzManager.saveJob("测试调度器2", "job-2", "job-group-2", "system.core.busi.job.TestJob2",
                "测试job-2", false, "trigger-2", "trigger-group-2", true, "0/5 * * * * ?", null, null, null);
    }
}
