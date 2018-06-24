package io.xiaoyaoyou.xmall.core.common.config;

import io.xiaoyaoyou.xmall.common.converter.ObjectMapperFactory;
import io.xiaoyaoyou.xmall.common.converter.StringToDateConverter;
import io.xiaoyaoyou.xmall.common.converter.StringToDateTimeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author xiaoyaoyou
 * @date 2017/11/22
 */
@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter theMappingJackson2HttpMessageConverter
                = new MappingJackson2HttpMessageConverter();

        theMappingJackson2HttpMessageConverter.setObjectMapper(ObjectMapperFactory.createObjectMapper());

        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        theMappingJackson2HttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);

        return theMappingJackson2HttpMessageConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customJackson2HttpMessageConverter());
        super.addDefaultHttpMessageConverters(converters);
    }

    public ConversionService getConversionService() {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        Set<Converter> converters = new HashSet<>();
        converters.add(new StringToDateConverter());
        converters.add(new StringToDateTimeConverter());
        bean.setConverters(converters);
        bean.afterPropertiesSet();
        return bean.getObject();
    }

    @Override
    protected ConfigurableWebBindingInitializer getConfigurableWebBindingInitializer() {
        ConfigurableWebBindingInitializer initializer = super.getConfigurableWebBindingInitializer();
        initializer.setConversionService(getConversionService());

        return initializer;
    }
}
