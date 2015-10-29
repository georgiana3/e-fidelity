package ro.efidelity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;

@Configuration
public class FTPConfig {

	public static final String CNAS_FTP_HOST = "cnas.posta.ro";

	@Bean
	public DefaultFtpSessionFactory defaultFtpSessionFactory() {
		DefaultFtpSessionFactory defaultFtpSessionFactory = new DefaultFtpSessionFactory();
		defaultFtpSessionFactory.setPort(21);
		defaultFtpSessionFactory.setHost(CNAS_FTP_HOST);
		defaultFtpSessionFactory.setUsername("anonymous");
		defaultFtpSessionFactory.setPassword("");

		return defaultFtpSessionFactory;
	}
}
