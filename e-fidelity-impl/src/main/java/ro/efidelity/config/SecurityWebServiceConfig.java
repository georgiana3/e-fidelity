//package ro.efidelity.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
//
///**
// * Clasa de configurare pentru Spring Security. Aceasta, prin intermediul
// * XML-ului importat prin anotatia {@literal @}ImportResource preia dintr-un XML
// * din classpath setarile de securitate (Spring Security) ale aplicatiei.
// * 
// * @author Andrei Pietrusel
// */
//
//@EnableWebSecurity
//@Configuration
//public class SecurityWebServiceConfig extends WebSecurityConfigurerAdapter {
//
//	private static final String[] WEBSERVICE_URL_PATTERNS = { "/webservice/**" };
//
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth)
//			throws Exception {
//
//		auth.inMemoryAuthentication().withUser("andrei").password("qwer")
//				.roles("PROGRAMMER");
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//
//		/* Obtine configuratorul de securitate */
//		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry reg = http
//				.authorizeRequests();
//
//		/* Restrictii pentru URL-urile web service-ului */
//		reg.antMatchers(WEBSERVICE_URL_PATTERNS).hasAnyRole("PROGRAMMER",
//				"PERSOANA_JURIDICA", "PERSOANA_FIZICA", "POSTA", "POSTA_OFICIU");
//
//		/* Nici o restrictie pe celelalte URL-uri */
//		reg.anyRequest().anonymous();
//
//		/*
//		 * Necesita HTTP Basic security header la fiecare request al
//		 * webservice-ului pentru autentificare
//		 */
//		reg.and().httpBasic().and().csrf().disable();
//	}
//}