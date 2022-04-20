package com.sillycat.authcenter.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebServerConfiguration {

    @Bean
    fun addCorsConfig(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                val allowedOrigins = "*".split(",").toTypedArray()
                registry.addMapping("/**")
                    .allowedMethods("*")
                    .allowedOriginPatterns(*allowedOrigins)
                    .allowCredentials(true)
            }
        }
    }
}