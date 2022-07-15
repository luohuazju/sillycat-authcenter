package com.sillycat.authcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.config.NetworkConfig;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.extern.slf4j.Slf4j;

@OpenAPIDefinition(
		servers = { 
				@Server(url = "/authcenter", description = "Default Server URL") })
@SpringBootApplication
@Slf4j
public class AuthCenterApplication {

	public static void main(String[] args) {
		log.info("SpringBootOpenAPIApplication init! ");
		SpringApplication.run(AuthCenterApplication.class, args);
		log.info("SpringBootOpenAPIApplication started! ");

	}
	
	@Bean
    public Config hazelCastConfig() {
        ManagementCenterConfig centerConfig = new ManagementCenterConfig();
        centerConfig.addTrustedInterface("192.168.56.3");
        centerConfig.setScriptingEnabled(true);
        Config config = new Config()
                .setInstanceName("hazelcast-instance")
                .setManagementCenterConfig(centerConfig);
        
        NetworkConfig network = config.getNetworkConfig();
        network.setPort(5701).setPortCount(20);
        network.setPortAutoIncrement(true);
        JoinConfig join = network.getJoin();
        join.getMulticastConfig().setEnabled(false);
        join.getTcpIpConfig().addMember("192.168.56.4").setEnabled(true);
        return config;
    }
	

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/v1/api-docs").allowedOrigins("*");
				registry.addMapping("/users/*").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "OPTIONS");
			}
		};
	}
	
	@Bean
	public TimedAspect timedAspect(MeterRegistry registry) {
	    return new TimedAspect(registry);
	}

}
