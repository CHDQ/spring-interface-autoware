package org.dq.test.ex.framework;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import java.util.Map;
import java.util.Set;

public class HttpRequestRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware, ResourceLoaderAware {

    private Environment environment;
    private ResourceLoader resourceLoader;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        ClassPathScanningCandidateComponentProvider scanner = getScanner();
        scanner.setResourceLoader(resourceLoader);
        AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(HttpService.class);
        scanner.addIncludeFilter(annotationTypeFilter);
        String packageName = ClassUtils.getPackageName(annotationMetadata.getClassName());
        Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(packageName);
        candidateComponents.forEach(candidateComponent -> {
            if (candidateComponent instanceof AnnotatedBeanDefinition) {
                registerHttpClient(beanDefinitionRegistry, (AnnotatedBeanDefinition) candidateComponent);
            }
        });
    }

    private void registerHttpClient(BeanDefinitionRegistry registry, AnnotatedBeanDefinition beanDefinition) {
        AnnotationMetadata metadata = beanDefinition.getMetadata();
        Map<String, Object> allAnnotationAttributes = metadata.getAnnotationAttributes(HttpService.class.getCanonicalName());
        String url = (String) allAnnotationAttributes.get("value");
        url = this.environment.resolvePlaceholders(url);
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(HttpFactoryBean.class);
        beanDefinitionBuilder.addPropertyValue("url", url);
        try {
            beanDefinitionBuilder.addPropertyValue("type", Class.forName(metadata.getClassName()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        beanDefinitionBuilder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        AbstractBeanDefinition beanDefinition1 = beanDefinitionBuilder.getBeanDefinition();
        BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(beanDefinition1, metadata.getClassName(), null);
        BeanDefinitionReaderUtils.registerBeanDefinition(beanDefinitionHolder, registry);
    }

    /**
     * 构造Class扫描器
     */
    private ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, this.environment) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                if (beanDefinition.getMetadata().isInterface()) {
                    return !beanDefinition.getMetadata().isAnnotation();
                }
                return false;
            }
        };
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
