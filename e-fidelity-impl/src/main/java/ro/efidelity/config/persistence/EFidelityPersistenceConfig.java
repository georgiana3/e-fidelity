package ro.efidelity.config.persistence;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import ro.efidelity.dao.efidelity.EfidelityBatchGenericDAO;

/**
 * PersistenceConfig este o clasa de configurare a platformei Sping MVC in
 * privinta conexiunilor la baza de date. Prin intermediul bean-urilor
 * configurate in aceasta se implementeaza Hibernate sub standardul JPA 2 ca
 * unealta ORM (Object Relational Mapping).
 * 
 * @author Andrei Pietrusel
 */

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = EFidelityPersistenceConfig.ENTITY_MANAGER_FACTORY, basePackages = { "ro.efidelity.dao.efidelity" })
public class EFidelityPersistenceConfig {

	public static final String PERSISTENCE_UNIT_NAME = "efidelity";
	public static final String ENTITY_MANAGER_FACTORY = PERSISTENCE_UNIT_NAME
			+ "EMF";

	private static final String HIBERNATE_DIALECT = EFidelityHibernateIfxDialect.class
			.getName();
	// private static final String HIBERNATE_HBM2DDL = "validate";
	private static final String HIBERNATE_SHOW_SQL = "false";
	private static final String HIBERNATE_FORMAT_SQL = "true";

	private static final String[] HIBERNATE_PACKAGES_TO_SCAN = { "ro.efidelity.model.domain.efidelity" };

	@Autowired
	private BitronixJTAConfig jtaConfiguration;

	@Bean(name = ENTITY_MANAGER_FACTORY)
	public LocalContainerEntityManagerFactoryBean efidelityEMFactory()
			throws Throwable {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactoryBean.setDataSource(jtaConfiguration
				.efidelityXADataSource());

		entityManagerFactoryBean.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
		entityManagerFactoryBean.setPackagesToScan(HIBERNATE_PACKAGES_TO_SCAN);
		entityManagerFactoryBean
				.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		Properties jpaProperties = new Properties();

		jpaProperties.setProperty("hibernate.dialect", HIBERNATE_DIALECT);
		// jpaProperties.setProperty("hibernate.hbm2ddl.auto",
		// HIBERNATE_HBM2DDL);
		jpaProperties.setProperty("hibernate.show_sql", HIBERNATE_SHOW_SQL);
		jpaProperties.setProperty("hibernate.format_sql", HIBERNATE_FORMAT_SQL);

		/* Batch operations */
		jpaProperties.setProperty("hibernate.order_inserts", "true");
		jpaProperties.setProperty("hibernate.order_updates", "true");
		jpaProperties.setProperty("hibernate.jdbc.batch_size", new Integer(
				EfidelityBatchGenericDAO.SQL_BATCH_SIZE + 1).toString());

		jtaConfiguration.tailorProperties(jpaProperties);
		Hibernate2ndLevelCacheConfig.tailorProperties(jpaProperties);
		JodaTimeJadiraHibernateConfig.tailorProperties(jpaProperties);
		entityManagerFactoryBean.setJpaProperties(jpaProperties);

		return entityManagerFactoryBean;
	}

}
