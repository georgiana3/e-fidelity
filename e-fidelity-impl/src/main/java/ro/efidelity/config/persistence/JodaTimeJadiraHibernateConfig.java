package ro.efidelity.config.persistence;

import java.util.Properties;

public class JodaTimeJadiraHibernateConfig {

	public static void tailorProperties(Properties jpaProperties) {
		jpaProperties.put("jadira.usertype.autoRegisterUserTypes", "true");
		jpaProperties.put("jadira.usertype.databaseZone", "jvm");
		jpaProperties.put("jadira.usertype.javaZone", "jvm");
	}

}
