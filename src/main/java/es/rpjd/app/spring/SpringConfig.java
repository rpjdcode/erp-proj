package es.rpjd.app.spring;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import es.rpjd.app.constants.Constants;
import jakarta.annotation.PostConstruct;

@Configuration
@ComponentScan(basePackages = "es.rpjd.app")
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class SpringConfig {

	private final Environment env;

	public SpringConfig(Environment env) {
		this.env = env;
	}

	@Bean
	public DataSource dataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(env.getProperty("spring.datasource.url"));
		dataSource.setUsername(env.getProperty("spring.datasource.username"));
		dataSource.setPassword(env.getProperty("spring.datasource.password"));
		dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
		dataSource.setMaximumPoolSize(Integer.parseInt(env.getProperty("spring.datasource.hikari.maximum-pool-size")));
		dataSource.setMinimumIdle(Integer.parseInt(env.getProperty("spring.datasource.hikari.minimum-idle")));
		dataSource.setIdleTimeout(Long.parseLong(env.getProperty("spring.datasource.hikari.idle-timeout")));
		dataSource.setMaxLifetime(Long.parseLong(env.getProperty("spring.datasource.hikari.max-lifetime")));
		dataSource.setConnectionTimeout(Long.parseLong(env.getProperty("spring.datasource.hikari.connection-timeout")));
		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		//sessionFactory.setAnnotatedClasses(Product.class);
		sessionFactory.setPackagesToScan("es.rpjd.app.hibernate.entity");
		sessionFactory.setConfigLocation(new ClassPathResource("/hibernate/hibernate.cfg.xml"));
		return sessionFactory;
	}

	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}
	
	/**
	 * Este método ejecuta el script de schema.sql, que creará las tablas en la BBDD
	 * en caso de no existir. La ejecución de este método se realiza después de que Spring
	 * haya terminado de configurar los Beans
	 */
    @PostConstruct
    public void initializeDatabase() {
    	String prop = env.getProperty("bbdd.schema.import");
    	// Se verifica si se debe realizar la importación del script schema.sql
    	if (prop != null && (!prop.isEmpty() && !prop.isBlank()) && Boolean.TRUE.equals(Boolean.valueOf(prop))) {
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
            populator.addScript(new ClassPathResource(Constants.SQL.SCHEMA_FILE));
            populator.execute(dataSource());
    	}

    }

}
