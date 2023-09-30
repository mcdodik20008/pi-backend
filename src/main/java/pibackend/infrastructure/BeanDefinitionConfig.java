package pibackend.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanDefinitionConfig {

    @Bean
    public MapperBeanDefinitionRegistrar mapperBeanDefinitionRegistrar() {
        return new MapperBeanDefinitionRegistrar();
    }
}
