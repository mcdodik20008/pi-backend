package pibackend.infrastructure;

import org.mapstruct.Mapper;
import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import java.util.List;

public class MapperBeanDefinitionRegistrar implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        Reflections scanner = new Reflections("pibackend.domain");
        List<Class<?>> mappers = scanner.getTypesAnnotatedWith(Mapper.class).stream().filter(x -> !x.isInterface()).toList();
        for (Class<?> mapper : mappers) {
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setScope(GenericBeanDefinition.SCOPE_SINGLETON);
            beanDefinition.setBeanClass(mapper);
            beanDefinitionRegistry.registerBeanDefinition(mapper.getSimpleName(), beanDefinition);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
