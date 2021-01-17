package com.github.powerjobdemo.entity;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author Jiang Jining
 * @since 2021/1/16 0:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlarmClock {
    private Long id;
    
    @NotBlank(message = "username should not be blank.")
    private String username;
    
    private String clockName;
    
    @NotNull(message = "Delay should not be null.")
    private Long delayMillis;
    
    private Long instanceId;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    
    @Override
    public String toString() {
        return JSON.toJSONStringWithDateFormat(this, JSON.DEFFAULT_DATE_FORMAT);
    }
    
}
