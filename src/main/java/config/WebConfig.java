package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;


/**
 * WebConfig����
 * @author RanJi
 *
 */
@Configuration
//@EnableWebMvc		
public class WebConfig extends WebMvcConfigurationSupport {
	/**
     * jsp��ͼ��������bean
     * @return
     */
    @Bean
    public UrlBasedViewResolver setupViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/WEB-INF/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }
    
    
    /**
     * ���ô���̬��Դ
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
    	super.addResourceHandlers(registry);
    }
    
    /**
     * ���������ʵ�����
     */
    @Override
    protected void addCorsMappings(CorsRegistry registry) {
    	registry.addMapping("/**") 		//-- ���������ʵ�·��
        .allowedOrigins("*")	//-- ���������ʵ�Դ
        .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")		//-- �������󷽷�
        .maxAge(168000)	//--  Ԥ����ʱ��
        .allowCredentials(true);	//--  �Ƿ���cookie
    	super.addCorsMappings(registry);
    }
}
