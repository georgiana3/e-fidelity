package ro.efidelity.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 * Clasa de configurare pentru Spring Security. Aceasta, prin intermediul
 * XML-ului importat prin anotatia {@literal @}ImportResource preia dintr-un XML
 * din classpath setarile de securitate (Spring Security) ale aplicatiei.
 * 
 * @author Andrei Pietrusel
 */

@Configuration
@ImportResource(value = "classpath:spring-security-context.xml")
public class SecurityConfig {

	// /**
	// * Declaratia bean-ului care ofera acces la serviciul de logare -
	// delogare.
	// */
	// @Bean
	// public AuthService authService() {
	// return new AuthService();
	// }

	/**
	 * Declaratia bean-ului specific Spring Security folosit la optiunea de
	 * logare cu sesiune persistenta (Functionalitatea "remember me").
	 */
	// @Bean
	// public TokenBasedRememberMeServices rememberMeServices() {
	// return new TokenBasedRememberMeServices("remember-me-key",
	// authService());
	// }

	/**
	 * Declaratia unui bean folosit la criptarea si decriptarea parolelor.
	 */
	@Bean(name = "standardPasswordEncoder")
	public PasswordEncoder passwordEncoder() {
		return new StandardPasswordEncoder("DARKINDYedLOxOT6");
	}

	@Bean(name = "captchaCaptureFilter")
	public CaptchaCaptureFilter captchaCaptureFilter() {
		CaptchaCaptureFilter captchaCaptureFilter = new CaptchaCaptureFilter();
		return captchaCaptureFilter;
	}

}