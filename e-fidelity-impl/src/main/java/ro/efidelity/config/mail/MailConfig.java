package ro.efidelity.config.mail;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

	@Bean(name = "mailSender")
	public JavaMailSenderImpl mailSender() throws Exception {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost("10.12.0.2");
		javaMailSender.setPort(465);
		javaMailSender.setUsername("platforma.cnpr");
		javaMailSender.setPassword("Mail1234#");
		javaMailSender.setProtocol("smtps");

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtps.ssl.checkserveridentity", "false");
		props.put("mail.smtps.ssl.trust", "*");
		// props.put("mail.debug", "true");

		javaMailSender.setJavaMailProperties(props);
		return javaMailSender;
	}

}
