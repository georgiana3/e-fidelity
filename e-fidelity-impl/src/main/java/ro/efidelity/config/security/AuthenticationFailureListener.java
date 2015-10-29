package ro.efidelity.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import ro.efidelity.service.AutentificariEsuateService;

/*
 * Clasa AuthenticationFailureListener asculta dupa evenimentul
 * de autentificare esuata si implemtenteaza o metoda, care in cazul
 * unei autentificari esuate, adauga adresa ip de la care s-a incercat autentificarea.
 * In cazul in care exista o intrare cu acea adresa ip numarul de autentificari
 * esuate va fi incrementat.
 */
@Component
public class AuthenticationFailureListener implements
		ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

	@Autowired
	private AutentificariEsuateService authesserv;

	@Override
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent ev) {

		// Adagaut ip-ul / incrementat numarul de incercari din care
		// autentificarea a esuat.
		String ip = ((WebAuthenticationDetails) ev.getAuthentication()
				.getDetails()).getRemoteAddress();
		authesserv.increment(ip);

	}
}