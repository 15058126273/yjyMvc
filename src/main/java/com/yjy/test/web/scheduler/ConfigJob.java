package com.yjy.test.web.scheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * desc
 *
 * @Author yjy
 * @Date 2018-03-25 21:10
 */
public class ConfigJob implements Job {

    private static final Logger log = LogManager.getLogger(ConfigJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("ConfigJob success >>>>>>>>>>>>>>>>");
    }


}
