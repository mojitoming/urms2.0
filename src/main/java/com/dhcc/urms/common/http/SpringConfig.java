package com.dhcc.urms.common.http;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
public class SpringConfig implements WebMvcConfigurer {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.stream()
            .filter(converter -> converter instanceof MappingJackson2CborHttpMessageConverter)
            .findFirst()
            .ifPresent(converter -> ((MappingJackson2HttpMessageConverter) converter).setDefaultCharset(UTF_8));
    }
}
