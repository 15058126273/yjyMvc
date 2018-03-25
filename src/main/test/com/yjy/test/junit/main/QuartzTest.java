package com.yjy.test.junit.main;

import com.yjy.test.junit.scheduler.AddJob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

/**
 * desc
 *
 * @Author yjy
 * @Date 2018-03-25 21:32
 */
public class QuartzTest {

    private static final Logger log = LogManager.getLogger(QuartzTest.class);

    /**
     * 加载配置的定时任务
     */
    @Test
    public void initConfigJob() {
        try {
            new ClassPathXmlApplicationContext("spring-quartz.xml");
            Thread.sleep(1000000L);
        } catch (Exception e) {
            log.error(Thread.currentThread().getName(), e);
        }
    }

    /**
     * 加载配置的定时任务 并 向调度容器中加入一个定时任务
     */
    @Test
    public void addJob() {
        try {
            ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-quartz.xml");
            SchedulerFactoryBean quartzsScheduler = applicationContext.getBean(SchedulerFactoryBean.class);
            Scheduler scheduler = quartzsScheduler.getScheduler();
            JobKey jobKey = new JobKey("test", "test");
            if (scheduler.checkExists(jobKey)) {
                return;
            }
            JobDetail jobDetail = JobBuilder.newJob(AddJob.class).withIdentity(jobKey).build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("test", "test")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever()).build();
            scheduler.scheduleJob(jobDetail, trigger);

            log.info("currentThread: {}" , Thread.currentThread());
            Thread.sleep(1000000L);
        } catch (Exception e) {
            log.error("execute error", e);
        }

    }
}
