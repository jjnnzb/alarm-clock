package com.github.powerjobdemo.config;

import com.github.kfcfans.powerjob.client.OhMyClient;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jjn
 * @since  2020-08-04 8:55
 */
@Configuration
public class PowerJobClientConfig {

    @Value("${powerjob.worker.server-address}")
    private String serverAddress;

    @Value("${powerjob.client.password}")
    private String password;

    @Value("${powerjob.worker.app-name}")
    private String appName;

    @Bean
    public OhMyClient initPowerJobClient() {
        String[] serverArray = StringUtils.split(serverAddress, ",");
        return new OhMyClient(Lists.newArrayList(serverArray), appName, password);
    }
}
