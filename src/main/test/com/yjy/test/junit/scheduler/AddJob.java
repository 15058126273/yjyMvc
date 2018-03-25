package com.yjy.test.junit.scheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * desc
 *
 * @Author yjy
 * @Date 2018-03-25 21:54
 */
public class AddJob implements Job {

    private static final Logger log = LogManager.getLogger(AddJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("AddJob success >>>>>>>>>>>>>>>>>>>>>>");
    }

}
