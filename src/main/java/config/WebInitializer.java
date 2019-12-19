package config;

import java.nio.charset.StandardCharsets;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
/**
 * sevrlet����������ʱ�򣬻��ҵ����ಢ����onStartup����
 * �����൱��web��������ڷ�������ȡ��web.xml�ļ�
 * (1). tomcat��Web������������ʱ����Լ���WebApplicationInitializer�ӿڵ�ʵ���ࣩ
 *      ���ҵ��ø����onStartup���� 
 * (2). ����Spring����
 * (3). ��ServletContext����Spring����������
 * (4). ��ServletContext�����һ��Servlet,����SpringMVC�ĺ���Servlet
 *      ��Ҳ��MVCģʽ�е�C��������
 *      �Ժ����ǾͲ�дServelt��дSpringMVC��Controller��д��Controller
 *      SpringMVC��ܾͻ����Լ����Ǹ����Ŀ�����(Servlet)����ת����Ӧ������
 *      ��·�ɣ�
 * @author RanJi
 *
 */
public class WebInitializer implements WebApplicationInitializer {
	
	public void onStartup(ServletContext servletContext) throws ServletException {
    	//-- 1. ����Spring����
        AnnotationConfigWebApplicationContext ctx = 
        		new AnnotationConfigWebApplicationContext();
        //-- 2. Spring������������
        ctx.register(AppConfig.class);
        //-- 3. Spring�����ӹ�ServletContextӦ�������Ķ���
        ctx.setServletContext(servletContext);
        //-- 4. ���Servlet(�������һ��Servlet��SpringMVC���ʵ�ֵ����Servlet)
        ServletRegistration.Dynamic servlet = 
        		servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
        servlet.addMapping("/");  
        servlet.setLoadOnStartup(1);  
        //-- 5. ��ӹ�����(�����ַ�����)
        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
        encodingFilter.setInitParameter("encoding", String.valueOf(StandardCharsets.UTF_8));
        encodingFilter.setInitParameter("forceEncoding", "true");
        encodingFilter.addMappingForUrlPatterns(null, false, "/*");
        
    }  
    
	
}