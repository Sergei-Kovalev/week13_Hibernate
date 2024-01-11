package ru.clevertec.home.config;


import lombok.Getter;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import java.util.Objects;
import java.util.Properties;

@Getter
@Configuration
@ComponentScan("ru.clevertec.home")
@PropertySource("classpath:properties.yml")
public class SpringConfig {


    @Bean
    public static BeanFactoryPostProcessor beanFactoryPostProcessor() {
        PropertySourcesPlaceholderConfigurer configure = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(new ClassPathResource("properties.yml"));
        Properties yamlObj = Objects.requireNonNull(yamlPropertiesFactoryBean.getObject(), "Yaml not found");
        configure.setProperties(yamlObj);
        return configure;
    }
}
