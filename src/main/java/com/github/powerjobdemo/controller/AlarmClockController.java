package com.github.powerjobdemo.controller;

import com.github.powerjobdemo.entity.AlarmClock;
import com.github.powerjobdemo.entity.Response;
import com.github.powerjobdemo.service.ClockService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Jiang Jining
 * @since 2021/1/17 22:50
 */
@RestController
@CrossOrigin
public class AlarmClockController {
    
    @Resource
    private ClockService clockService;
    
    @PostMapping(value = "/api/v1/alarm/clock/add")
    public Response addAlarmClock(@RequestBody @Validated AlarmClock alarmClock) {
        return Response.success(clockService.addAlarmClock(alarmClock));
    }
    
    @PostMapping(value = "/api/v1/alarm/clock/cancel")
    public Response cancelAlarmClock(@RequestBody AlarmClock alarmClock) {
        return Response.success(clockService.cancelAlarmClock(alarmClock));
    }
    
    @GetMapping(value = "/api/v1/alarm/clock/query")
    public Response queryAlarmClock(@RequestParam String username) {
        return Response.success(clockService.queryAll(username));
    }
}
