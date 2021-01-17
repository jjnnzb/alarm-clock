package com.github.powerjobdemo.service;

import com.alibaba.fastjson.JSONObject;
import com.github.powerjobdemo.entity.AlarmClock;

/**
 * @author Jiang Jining
 * @since 2021/1/17 21:40
 */
public interface ClockService {
    /**
     * Add alarm clock.
     *
     * @param alarmClock alarm clock
     * @return json
     */
    JSONObject addAlarmClock(AlarmClock alarmClock);
    
    /**
     * Cancel alarm clock.
     *
     * @param alarmClock alarm clock
     */
    JSONObject cancelAlarmClock(AlarmClock alarmClock);
    
    /**
     * Query all alarm clocks.
     *
     * @param username username
     * @return list
     */
    JSONObject queryAll(String username);
}
