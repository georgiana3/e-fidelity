package ro.efidelity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.view.XmlViewResolver;

/**
 * JasperReportsConfig este o clasa de configurare a platformei Spring MVC in
 * care se defineste ViewResolver-ul pentru rapoarte Jasper. Prin intermediul
 * XML-ului importat prin anotatia {@literal @}ImportResource se definesc denumirile ce
 * vor identifica toate rapoartele &nbsp;.jasper corespunzatoare aplicatiilor.
 * Definirea unui ViewResolver este necesara pentru ca view-urile corespunzatoare
 * rapoartelor Jasper sunt generate diferit fata de un clasic view JSP.
 * 
 * @author Andrei Pietrusel
 */

@ImportResource(value = "classpath:jasperreports/jasper-views.xml")
@Configuration
public class JasperReportsConfig {
	@Bean
	public XmlViewResolver configureXmlViewResolver() {
		XmlViewResolver configurer = new XmlViewResolver();
		configurer.setLocation(new ClassPathResource(
				"jasperreports/jasper-views.xml"));
		configurer.setOrder(0);
		return configurer;
	}
}
