package com.github.powerjobdemo.service;

import com.alibaba.fastjson.JSONObject;
import com.github.kfcfans.powerjob.client.OhMyClient;
import com.github.kfcfans.powerjob.common.response.ResultDTO;
import com.github.powerjobdemo.entity.AlarmClock;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * @author Jiang Jining
 * @since 2021/1/17 21:41
 */
@Service
public class ClockServiceImpl implements ClockService {
    
    public static final DateTimeFormatter CLOCK_NAME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
    
    private static final List<AlarmClock> alarmClockList = new CopyOnWriteArrayList<>();
    
    private final AtomicLong clockCount = new AtomicLong();
    
    @Resource
    private OhMyClient ohMyClient;
    
    @Value("${powerjob.task.id}")
    private Long taskId;
    
    @Override
    public JSONObject addAlarmClock(AlarmClock alarmClock) {
        String formattedName = CLOCK_NAME_FORMATTER.format(LocalDateTime.now());
        alarmClock.setClockName("Clock-" + formattedName);
        long id = clockCount.addAndGet(1L);
        alarmClock.setCreateTime(LocalDateTime.now());
        Long delayMillis = alarmClock.getDelayMillis();
        ResultDTO<Long> longResultDTO = ohMyClient.runJob(taskId, alarmClock.toString(), delayMillis);
        alarmClock.setInstanceId(longResultDTO.getData());
        alarmClock.setId(id);
        alarmClockList.add(alarmClock);
        JSONObject data = new JSONObject();
        data.put("id", id);
        return data;
    }
    
    @Override
    public JSONObject cancelAlarmClock(AlarmClock alarmClock) {
        Long instanceId = alarmClock.getInstanceId();
        assert instanceId != null;
        ohMyClient.cancelInstance(instanceId);
        alarmClockList.removeIf(clock -> Objects.equals(instanceId, clock.getInstanceId()));
        JSONObject data = new JSONObject();
        data.put("instanceId", instanceId);
        return data;
    }
    
    @Override
    public JSONObject queryAll(String username) {
        List<AlarmClock> clockList = alarmClockList.stream()
                .filter(alarmClock -> StringUtils.equals(alarmClock.getUsername(), username))
                .collect(Collectors.toList());
        JSONObject data = new JSONObject();
        data.put("data", clockList);
        data.put("count", clockList.size());
        return data;
    }
}
