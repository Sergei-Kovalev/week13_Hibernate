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
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

@Getter
@Configuration
@ComponentScan("ru.clevertec.home")
@PropertySource("classpath:application.yml")
public class SpringConfig {

    @Bean
    public static BeanFactoryPostProcessor beanFactoryPostProcessor() {
        PropertySourcesPlaceholderConfigurer configure = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(new ClassPathResource(getProfilePath()));
        Properties yamlObj = Objects.requireNonNull(yamlPropertiesFactoryBean.getObject(), "Yaml not found");
        configure.setProperties(yamlObj);
        return configure;
    }

    public static String getProfilePath() {
        try (InputStream inputStream = new FileInputStream("src/main/resources/application.yml")) {
            Yaml yaml = new Yaml();
            Map<String, Map<String, Map<String, String>>> profile = yaml.load(inputStream);
            String postfix = profile.get("spring").get("profiles").get("active");
            return "application-%s.yml".formatted(postfix);
        } catch (IOException e) {
            try (InputStream inputStream = SpringConfig.class.getClassLoader().getResourceAsStream("application.yml")) {
                Yaml yaml = new Yaml();
                Map<String, Map<String, Map<String, String>>> profile = yaml.load(inputStream);
                String postfix = profile.get("spring").get("profiles").get("active");
                return "application-%s.yml".formatted(postfix);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    }
}
