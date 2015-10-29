package ro.efidelity;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import ro.efidelity.config.RootConfig;
import bitronix.tm.TransactionManagerServices;

@RunWith(value = SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value = "e-fidelity-web/src/main/webapp")
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class, classes = { RootConfig.class //
// , ro.posta.efidelity.config.ApiMvcConfig.class //
// ,ro.posta.efidelity.config.SiteMvcConfig.class //
})
public abstract class AbstractEFidelitySpringTest {

	@BeforeClass
	public static void beforeClass() {

		TransactionManagerServices.getConfiguration().setServerId("btm-junit");
		TransactionManagerServices.getConfiguration()
				.setResourceConfigurationFilename(
						System.getenv("CATALINA_HOME")
								+ "/conf/resources.properties");
		TransactionManagerServices.getResourceLoader().init();
	}

}
