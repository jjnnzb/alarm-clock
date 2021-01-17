package com.github.powerjobdemo.task;

import com.alibaba.fastjson.JSON;
import com.github.kfcfans.powerjob.worker.core.processor.ProcessResult;
import com.github.kfcfans.powerjob.worker.core.processor.TaskContext;
import com.github.kfcfans.powerjob.worker.core.processor.sdk.BasicProcessor;
import com.github.kfcfans.powerjob.worker.log.OmsLogger;
import com.github.powerjobdemo.entity.AlarmClock;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Jiang Jining
 * @since 2021/1/17 22:19
 */
@Component
public class AlarmClockTask implements BasicProcessor {
    
    public static final DateTimeFormatter STANDARD_DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Override
    public ProcessResult process(TaskContext taskContext) throws Exception {
        OmsLogger omsLogger = taskContext.getOmsLogger();
        String instanceParams = taskContext.getInstanceParams();
        omsLogger.info("instance params:{}", instanceParams);
        AlarmClock alarmClock = JSON.parseObject(instanceParams, AlarmClock.class);
        assert alarmClock != null;
        String username = alarmClock.getUsername();
        omsLogger.info("Current time is:{}", STANDARD_DATE_TIME.format(LocalDateTime.now()));
        omsLogger.info("Clock info: id:{}, name:{}, creator:{}", alarmClock.getId(), alarmClock.getClockName(), username);
        return new ProcessResult(true, String.format("User: %s running an alarm clock.", username));
    }
}
