package ro.fidelity.apicontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ro.efidelity.config.RootConfig;
import ro.efidelity.service.AuthService;
import ro.efidelity.service.exception.generic.ResourceNotFoundException;
import ro.efidelity.util.restmessage.RestMessage;

@RestController
@RequestMapping("/")
public class ApiRootController {

	@Autowired
	private AuthService auth;

	@Autowired
	private RootConfig config;

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "")
	public RestMessage root() {
		return new RestMessage("AWB Posta Romana (API version: "
				+ config.getApplicationVersion() + ")",
				"Bine ati venit! Sunteti autentificat ca '"
						+ auth.getCurrentUtilizator().getUsername() + "'.");
	}

	@RequestMapping(value = "/**", method = { RequestMethod.GET,
			RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH,
			RequestMethod.DELETE })
	public void catchUnmappedPaths() {
		throw new ResourceNotFoundException(
				"API-ul nu defineste calea accesata (in corelatie cu metoda HTTP a request-ului).");
	}

}
