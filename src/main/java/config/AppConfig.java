package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * �ܵ�Ӧ������
 * @author RanJi
 *
 */
@Configuration
@ComponentScan({"dao","service","controller"})
@Import({JdbcConfig.class,WebConfig.class})
public class AppConfig {
	
}
