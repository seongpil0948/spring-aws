package com.sixplus.server.api.core.aspect;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

@Component
public class PropertySourceLogger implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment env = event.getEnvironment();
        for (PropertySource<?> propertySource : env.getPropertySources()) {
            if (propertySource.getName().contains("applicationConfig: [classpath:")) {
                System.out.println("Loaded property source: " + propertySource.getName());
            }
        }
    }
}
