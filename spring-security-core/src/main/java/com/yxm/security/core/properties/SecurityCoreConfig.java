package com.yxm.security.core.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author yexinming
 * @date 2020/2/23
 **/
@Configuration//配置类
@EnableConfigurationProperties(SecurityProperties.class)//让配置类生效
public class SecurityCoreConfig {
}
