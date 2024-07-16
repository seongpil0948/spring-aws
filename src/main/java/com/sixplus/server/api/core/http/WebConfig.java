package com.sixplus.server.api.core.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sixplus.server.api.core.http.interceptor.UserIdInterceptor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public UserIdInterceptor userIdInterceptor() {
        return new UserIdInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userIdInterceptor());
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder
                .json()
                .featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .featuresToEnable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .featuresToEnable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                .featuresToEnable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
                .build();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        objectMapper.setDateFormat(df);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // 우선순위를 지키자.
        converters.add(0, new ByteArrayHttpMessageConverter());
        converters.add(1, new StringHttpMessageConverter());
        converters.add(2, new MappingJackson2HttpMessageConverter(objectMapper));
    }


    @Bean("objectMapper")
    public ObjectMapper objectMapper() {
        return Jackson2ObjectMapperBuilder.json()
                .featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .featuresToEnable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .featuresToEnable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                .featuresToEnable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
                .modules(new JavaTimeModule())
                .build();
    }

    /**
     * MessageSource Bean
     * @return
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource bean = new ReloadableResourceBundleMessageSource();
        bean.setBasename("classpath:properties/messages");
        bean.setDefaultEncoding("UTF-8");
        bean.setDefaultLocale(java.util.Locale.KOREA);
        return bean;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }
}
