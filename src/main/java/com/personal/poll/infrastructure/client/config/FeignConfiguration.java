package com.personal.poll.infrastructure.client.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.personal.poll.infrastructure.client.external")
public class FeignConfiguration {
}
