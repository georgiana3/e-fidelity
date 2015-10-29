package ro.efidelity.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
@EnableSpringConfigured
@PropertySource(value = "classpath:/maven-env.properties", ignoreResourceNotFound = true)
@ComponentScan(basePackages = { "ro.efidelity" }, excludeFilters = //
{ //
@ComponentScan.Filter //
		(type = FilterType.REGEX, pattern = "ro\\.efidelity\\.config\\.SiteMvcConfig"), //
		@ComponentScan.Filter //
		(type = FilterType.REGEX, pattern = "ro\\.efidelity\\.controller\\..*"), //
})
public class RootConfig {

	@Value("${dev.build.version}")
	private String applicationVersion;

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {

		PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
		pspc.setIgnoreUnresolvablePlaceholders(true);
		return pspc;
	}

	public String getApplicationVersion() {
		return applicationVersion;
	}

}
