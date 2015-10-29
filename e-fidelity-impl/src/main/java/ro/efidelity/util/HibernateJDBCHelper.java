package ro.efidelity.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class HibernateJDBCHelper implements InitializingBean {

	@PersistenceContext(unitName = "efidelity")
	@Qualifier(value = "efidelityEMF")
	private EntityManager globalEM;

	private static HibernateJDBCHelper instance;

	@Override
	public void afterPropertiesSet() throws Exception {
		instance = this;
	}

	/* Prevent instantiation */
	private HibernateJDBCHelper() {
	}

	/**
	 * Metoda care returneaza obiectul conexiune JDBC asociat unui
	 * {@link EntityManager} JPA/Hibernate. A se folosi doar pentru a furniza
	 * sursa de date pentru rapoarte Jasper.
	 */

	public static Connection getJDBCConnection() {
		Session session = (Session) instance.globalEM.getDelegate();
		SessionFactoryImplementor sfi = (SessionFactoryImplementor) session
				.getSessionFactory();
		@SuppressWarnings("deprecation")
		ConnectionProvider cp = sfi.getConnectionProvider();
		Connection cnn = null;
		try {
			cnn = cp.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return cnn;
	}

}
