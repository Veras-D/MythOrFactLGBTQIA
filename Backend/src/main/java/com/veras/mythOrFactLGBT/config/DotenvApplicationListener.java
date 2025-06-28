package com.veras.mythOrFactLGBT.config;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Properties;

public class DotenvApplicationListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        Properties props = new Properties();
        dotenv.entries().forEach(entry -> props.setProperty(entry.getKey(), entry.getValue()));
        
        ConfigurableEnvironment environment = event.getEnvironment();
        environment.getPropertySources().addFirst(new PropertiesPropertySource("dotenv", props));
    }
}
