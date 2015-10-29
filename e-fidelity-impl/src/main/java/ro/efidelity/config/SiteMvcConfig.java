package ro.efidelity.config;

import java.util.List;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

/**
 * Clasa prin care se configureaza ViewResolver-ul pentru servirea de pagini JSP
 * clasice si se fac setari aditionale pentru alte componente specifice
 * platformei Spring MVC.
 * 
 * @see WebMvcConfigurationSupport
 * @author Andrei Pietrusel
 */

@Configuration
@ComponentScan(basePackages = { "ro.efidelity.controller" })
public class SiteMvcConfig extends WebMvcConfigurationSupport {

	private static final String TILES = "/WEB-INF/views/tiles.xml";
	private static final String VIEWS = "/WEB-INF/views/**/views.xml";

	private static final String RESOURCES_HANDLER = "/resources/";
	private static final String RESOURCES_LOCATION = RESOURCES_HANDLER + "**";

	@Override
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		RequestMappingHandlerMapping requestMappingHandlerMapping = super
				.requestMappingHandlerMapping();
		requestMappingHandlerMapping.setUseSuffixPatternMatch(false);
		requestMappingHandlerMapping.setUseTrailingSlashMatch(false);
		return requestMappingHandlerMapping;
	}

	/**
	 * ViewResolverul pentru servirea paginilor JSP clasice. S-a ales Apache
	 * Tiles, care ofera functionalitati de templating in afisarea paginilor
	 * web.
	 * 
	 * @see TilesViewResolver
	 */

	@Bean
	public TilesViewResolver configureTilesViewResolver() {
		TilesViewResolver configurer = new TilesViewResolver();
		configurer.setOrder(1); /*
								 * Ordinea de rezolvare trebuie sa fie 1
								 * intrucat ordinea 0 este folosita de
								 * ViewResolver-ul pentru JasperReports care
								 * necesita prioritate.
								 */
		return configurer;
	}

	/**
	 * Bean folosit la specificarea directoarelor unde vor fi stocate
	 * view-urile, respectiv fisierele XML de configurare a facilitatilor de
	 * templating pentru Apache Tiles.
	 */

	@Bean
	public TilesConfigurer configureTilesConfigurer() {
		TilesConfigurer configurer = new TilesConfigurer();
		configurer.setDefinitions(new String[] { TILES, VIEWS });
		return configurer;
	}

	@Override
	public Validator getValidator() {
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		return validator;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(RESOURCES_HANDLER).addResourceLocations(
				RESOURCES_LOCATION);
	}

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	protected void addArgumentResolvers(
			List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new UserDetailsHandlerMethodArgumentResolver());
	}

	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(2097152);
		return multipartResolver;
	}

	/**
	 * Clasa locala ajutatoare folosita in configurarea
	 * {@link HandlerMethodArgumentResolver}
	 */

	private static class UserDetailsHandlerMethodArgumentResolver implements
			HandlerMethodArgumentResolver {

		public boolean supportsParameter(MethodParameter parameter) {
			return UserDetails.class.isAssignableFrom(parameter
					.getParameterType());
		}

		public Object resolveArgument(MethodParameter parameter,
				ModelAndViewContainer modelAndViewContainer,
				NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
				throws Exception {
			Authentication auth = (Authentication) webRequest
					.getUserPrincipal();
			return auth != null && auth.getPrincipal() instanceof UserDetails ? auth
					.getPrincipal() : null;
		}
	}

	/*
	 * Configurari pentru internationalizare
	 */

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		String[] resources = { "classpath:languages/labels",
				"classpath:languages/messages" };
		messageSource.setBasenames(resources);
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}

	@Bean(name = "localeResolver")
	public LocaleResolver getLocaleResolver() {
		CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		cookieLocaleResolver.setDefaultLocale(new Locale("ro"));
		return cookieLocaleResolver;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

}