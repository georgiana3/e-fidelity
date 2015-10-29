package ro.efidelity.config;

import java.util.EnumSet;
import java.util.Set;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

/**
 * Clasa care implementeaza initializarea servlet-ul folosit de Spring MVC
 * pentru a oferi functionalitati web. Aceasta clasa poate fi considerata
 * punctul initial de acces la intreaga aplicatie. Realizarea configurarilor
 * dispatcher servlet-ului Spring in aceasta clasa reprezinta o alternativa la
 * clasica configurare a platformei Spring MVC printr-un fisier XML
 * (ApplicationContext.xml).
 * 
 * @see org.springframework.context.annotation.AnnotationConfigApplicationContext
 * @author Andrei Pietrusel
 */

public class WebAppInitializer implements WebApplicationInitializer {

	public static final String API_PATH = "/api/*";
	public static final String SITE_PATH = "/site/*";
	public static final String CAPTCHA_PATH = "/jcaptcha.jpg";

	private static final String URL_REWRITE_FILTER_NAME = "urlRewrite";
	private static final String URL_REWRITE_FILTER_MAPPING = "/*";
	private static final String URL_REWRITE_FILTER_PARAM_LOGLEVEL = "logLevel";
	private static final String URL_REWRITE_FILTER_LOGLEVEL_SLF4J = "slf4j";

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {

		/* Configureaza servlet context global */
		registerHiddenFieldFilter(servletContext);
		servletContext.setInitParameter("defaultHtmlEscape", "true");
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		servletContext.addFilter("characterEncodingFilter",
				characterEncodingFilter);

		/* Configureaza root application context (servicii, dao-uri, etc.) */
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(RootConfig.class);
		servletContext.addListener(new ContextLoaderListener(rootContext));

		/* Configureaza filtru de rescriere URL */
		configureUrlRewriteFilter(servletContext);

		/* Configureaza securitate */
		configureSpringSecurity(servletContext, rootContext);

		/* Configureaza context aplicatie web curenta */
		AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
		appContext.register(SiteMvcConfig.class);

		DispatcherServlet appDispatcherServlet = new DispatcherServlet(
				appContext);
		ServletRegistration.Dynamic siteServlet = servletContext.addServlet(
				"siteServlet", appDispatcherServlet);
		siteServlet.setLoadOnStartup(1);
		siteServlet.setAsyncSupported(true);
		Set<String> mappingConflicts = siteServlet.addMapping(SITE_PATH);
		if (!mappingConflicts.isEmpty()) {
			throw new IllegalStateException(
					"'siteServlet' cannot be mapped to " + SITE_PATH);
		}

		/* Configureaza context aplicatie web service (API) */
		AnnotationConfigWebApplicationContext apiContext = new AnnotationConfigWebApplicationContext();
		apiContext.register(ApiMvcConfig.class);

		DispatcherServlet sdrDispatcherServlet = new DispatcherServlet(
				apiContext);
		ServletRegistration.Dynamic apiServlet = servletContext.addServlet(
				"apiServlet", sdrDispatcherServlet);
		apiServlet.setLoadOnStartup(1);
		apiServlet.setAsyncSupported(true);
		mappingConflicts = apiServlet.addMapping(API_PATH);
		if (!mappingConflicts.isEmpty()) {
			throw new IllegalStateException("'apiServlet' cannot be mapped to "
					+ API_PATH);
		}

		/* Configureaza servlet captcha */
		addCaptchaServlet(servletContext);

	}

	/**
	 * Metoda prin care se configureaza Url Rewrite Filter
	 */
	private void configureUrlRewriteFilter(ServletContext servletContext) {
		FilterRegistration.Dynamic urlRewrite = servletContext.addFilter(
				URL_REWRITE_FILTER_NAME, new UrlRewriteFilter());
		urlRewrite.setInitParameter(URL_REWRITE_FILTER_PARAM_LOGLEVEL,
				URL_REWRITE_FILTER_LOGLEVEL_SLF4J);
		EnumSet<DispatcherType> urlReWriteDispatcherTypes = EnumSet
				.of(DispatcherType.REQUEST);
		urlRewrite.addMappingForUrlPatterns(urlReWriteDispatcherTypes, true,
				URL_REWRITE_FILTER_MAPPING);
	}

	/**
	 * Metoda prin care se configureaza Spring Security
	 */
	private void configureSpringSecurity(ServletContext servletContext,
			WebApplicationContext rootContext) {
		FilterRegistration.Dynamic springSecurity = servletContext.addFilter(
				"springSecurityFilterChain", new DelegatingFilterProxy(
						"springSecurityFilterChain", rootContext));

		EnumSet<DispatcherType> springSecurityDispatcherTypes = EnumSet
				.of(DispatcherType.FORWARD);

		springSecurity.addMappingForUrlPatterns(springSecurityDispatcherTypes,
				true, "/*");
	}

	/**
	 * Metoda prin care se implementeaza protocoalele PUT si DELETE din
	 * standardul REST
	 */
	private void registerHiddenFieldFilter(ServletContext servletContext) {
		servletContext.addFilter("hiddenHttpMethodFilter",
				new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null,
				true, "/*");
	}

	private void addCaptchaServlet(ServletContext servletContext) {
		servletContext
				.addServlet("jcaptcha",
						"com.octo.captcha.module.servlet.image.SimpleImageCaptchaServlet")
				.addMapping(CAPTCHA_PATH);
	}

}