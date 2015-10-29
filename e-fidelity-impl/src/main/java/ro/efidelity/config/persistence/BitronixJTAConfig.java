package ro.efidelity.config.persistence;

import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.hibernate.engine.transaction.jta.platform.internal.BitronixJtaPlatform;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.jndi.BitronixInitialContextFactory;

/**
 * @author Andrei Pietrusel
 */

@Configuration
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ, order = 100)
public class BitronixJTAConfig {

	private static final String JNDI_URL_TRANSACTION = "java:comp/UserTransaction";
	private static final String JNDI_URL_EFIDELITY_DS = "jdbc/efidelityDS";

	private Context jndiContext;

	public BitronixJTAConfig() throws NamingException {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				BitronixInitialContextFactory.class.getName());
		setJndiContext(new InitialContext(env));
	}

	@Bean
	public DataSource efidelityXADataSource() throws NamingException {
		DataSource dataSource = (DataSource) jndiContext
				.lookup(JNDI_URL_EFIDELITY_DS);
		return dataSource;
	}

	public void tailorProperties(Properties properties) {
		properties.setProperty("hibernate.jndi.class",
				BitronixInitialContextFactory.class.getName());
		properties.setProperty("hibernate.transaction.jta.platform",
				BitronixJtaPlatform.class.getName());
		properties.setProperty("javax.persistence.transactionType",
				PersistenceUnitTransactionType.JTA.name());
	}

	@Bean
	public PlatformTransactionManager transactionManager() throws Throwable {

		BitronixTransactionManager btm = (BitronixTransactionManager) jndiContext
				.lookup(JNDI_URL_TRANSACTION);

		UserTransaction userTransaction = btm;
		TransactionManager transactionManager = btm;
		JtaTransactionManager jtaTM = new JtaTransactionManager(
				userTransaction, transactionManager);
		jtaTM.setAllowCustomIsolationLevels(true);

		return jtaTM;

	}

	public Context getJndiContext() {
		return jndiContext;
	}

	public void setJndiContext(Context jndiContext) {
		this.jndiContext = jndiContext;
	}
}
