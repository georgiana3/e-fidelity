package ro.efidelity.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;

/**
 * Clasa prin care se configureaza ViewResolver-ul pentru servirea de pagini JSP
 * clasice si se fac setari aditionale pentru alte componente specifice
 * platformei Spring MVC.
 * 
 * @see WebMvcConfigurationSupport
 * @author Andrei Pietrusel
 */
@Configuration
@ComponentScan(basePackages = { "ro.efidelity.apicontroller" })
public class ApiMvcConfig extends WebMvcConfigurationSupport {

	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		converters.add(mappingJacksonHttpMessageConverter());
	}

	@Bean
	public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
		MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
		mappingJacksonHttpMessageConverter.setSupportedMediaTypes(Arrays
				.asList(new MediaType[] { MediaType.TEXT_PLAIN,
						MediaType.APPLICATION_JSON }));
		mappingJacksonHttpMessageConverter.getObjectMapper().registerModule(
				new JodaModule());
		mappingJacksonHttpMessageConverter.getObjectMapper().disable(
				SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		return mappingJacksonHttpMessageConverter;
	}

	@Override
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		RequestMappingHandlerMapping requestMappingHandlerMapping = super
				.requestMappingHandlerMapping();
		requestMappingHandlerMapping.setUseSuffixPatternMatch(false);
		requestMappingHandlerMapping.setUseTrailingSlashMatch(false);
		return requestMappingHandlerMapping;
	}

	@Override
	public Validator getValidator() {
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		return validator;
	}

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void configureContentNegotiation(
			ContentNegotiationConfigurer configurer) {
		configurer.defaultContentType(MediaType.APPLICATION_JSON);
	}

	@Override
	protected void addArgumentResolvers(
			List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new UserDetailsHandlerMethodArgumentResolver());
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

}