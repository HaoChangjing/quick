package com.saveslave.commons.swagger.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.collect.Sets;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Swagger2配置
 */
@Configuration
@EnableWebMvc
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {

    @Value("${swagger.name:openAPI}")
    private String name;

    /**
     * 是否开启swagger，生产环境一般关闭，所以这里定义一个变量
     */
    @Value("${swagger.enable:false}")
    private Boolean enable;

    @Value("${swagger.description:用于开发环境的 RestFul Api 文档}")
    private String description;

    @Value("${swagger.version:v1}")
    private String version;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .enable(enable)
                .protocols(Sets.newHashSet("http"))
                .select()
                .paths(PathSelectors.any())
                /*.apis(RequestHandlerSelectors.basePackage("com.****.controller.*"))*/
                /*.apis(RequestHandlerSelectors.withClassAnnotation(ApiOperation.class))*/
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(name)
                .version(version)
                .description(description)
                .build();
    }

    /**
     * 全局默认参数配置
     */
  /*  private List<Parameter> commonParameters() {
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new ParameterBuilder()
                .name("version")
                .description("全局默认版本号")
                .modelRef(new ModelRef("String"))
                .parameterType("path")
                .defaultValue(dadaodata_app_version)
                .required(true)
                .build());

        return parameters;
    }*/

    /**
     * Spring Boot 2.6.x版本引入依赖 springfox-boot-starter (Swagger 3.0) 后，启动容器会报错：
     *
     * Failed to start bean ‘ documentationPluginsBootstrapper ‘ ; nested exception…
     *
     * 原因
     * Springfox 假设 Spring MVC 的路径匹配策略是 ant-path-matcher，而 Spring Boot 2.6.x版本的默认匹配策略是 path-pattern-matcher，这就造成了上面的报错。
     * @return
     */
    @Bean
    public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return new BeanPostProcessor() {

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
                    customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
                }
                return bean;
            }

            private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
                List<T> copy = mappings.stream()
                        .filter(mapping -> mapping.getPatternParser() == null)
                        .collect(Collectors.toList());
                mappings.clear();
                mappings.addAll(copy);
            }

            @SuppressWarnings("unchecked")
            private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
                try {
                    Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
                    field.setAccessible(true);
                    return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }
        };
    }

}