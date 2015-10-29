package ro.efidelity.config.persistence;

import java.util.Properties;

import org.apache.commons.lang.BooleanUtils;
import org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory;

public class Hibernate2ndLevelCacheConfig {

	public static final boolean hibernate2ndLevelCacheEnabled = true;
	public static final boolean hibernateQueryCacheEnabled = true;
	public static final String hibernateDefaultCacheConcurrencyStrategy = "transactional";

	/**
	 * Leaga proprietatile necesare configurarii cache-ului Hibernate de nivel
	 * secund.
	 */
	public static void tailorProperties(Properties jpaProperties) {

		jpaProperties.setProperty("hibernate.cache.use_second_level_cache",
				BooleanUtils.toString(hibernate2ndLevelCacheEnabled, "true",
						"false"));

		jpaProperties.setProperty(
				"hibernate.cache.default_cache_concurrency_strategy",
				hibernateDefaultCacheConcurrencyStrategy);

		jpaProperties.setProperty("hibernate.cache.use_query_cache",
				BooleanUtils.toString(hibernateQueryCacheEnabled, "true",
						"false"));

		jpaProperties.setProperty("hibernate.cache.region.factory_class",
				SingletonEhCacheRegionFactory.class.getName());

	}

}
