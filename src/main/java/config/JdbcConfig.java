package config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * Jdbc����
 * @author RanJi
 *
 */

@Configuration
@EnableTransactionManagement
@MapperScan({"mapper"})
@PropertySource({"classpath:jdbc.properties","classpath:mybatis.properties"})
public class JdbcConfig {
	//-- JDBC����
	@Value("${jdbc.driverClass}")
    private String driver;
 
    @Value("${jdbc.url}")
    private String url;
 
    @Value("${jdbc.username}")
    private String user;
 
    @Value("${jdbc.password}")
    private String password;
    
    //-- MyBatis����
    @Value("${mybatis.config.path}")
    private String myBatisConfigPath;
    @Value("${mybatis.mapper.xml.config.path}")
    private String mapperXMLConfigPath;
    @Value("${mybatis.alias.package.path}")
    private String aliasPackagePath;
    
    /**
     * Beanע�⣺��ע��ֻ��д�ڷ����ϣ�����ʹ�ô˷�������һ�����󣬲��ҷ���spring������
     * name���ԣ�����ǰ@Beanע�ⷽ�������Ķ���ָ��һ������(��bean��id����
     * @return
     */
    @Bean(name="dataSource")
    public DataSource createDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setInitialSize(5);
        dataSource.setMaxActive(20);
        dataSource.setMaxWait(3000);
        return dataSource;
    }
    
    @Bean(name="jdbcTemplate")
    public JdbcTemplate createJdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
    
    @Bean(name="sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
    	//-- 1. SqlSessionFactoryBean��SqlSessionFactory�Ĵ�����
    	SqlSessionFactoryBean sqlSessionFactoryBean = 
    			new SqlSessionFactoryBean();
    	//-- 2. ����ӳ���ļ���·��
        PathMatchingResourcePatternResolver resolver = 
        		new PathMatchingResourcePatternResolver();
        //-- 3. ӳ���ļ��ĵ�ַ
        String packageXMLConfigPath = 
        		PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + 
        		mapperXMLConfigPath;
        
        // ����MyBatis �����ļ���·��
        sqlSessionFactoryBean.setConfigLocation(
        		new ClassPathResource(myBatisConfigPath));
        // ����mapper ��Ӧ��XML �ļ���·��
        sqlSessionFactoryBean.setMapperLocations(
        		resolver.getResources(packageXMLConfigPath));
        // ��������Դ
        sqlSessionFactoryBean.setDataSource(dataSource);
        // ����ʵ�������·��
        sqlSessionFactoryBean.setTypeAliasesPackage(aliasPackagePath);
        
        return sqlSessionFactoryBean.getObject();
    }
    
    @Bean   //-- ���������
	public PlatformTransactionManager platformTransactionManager(
			DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
