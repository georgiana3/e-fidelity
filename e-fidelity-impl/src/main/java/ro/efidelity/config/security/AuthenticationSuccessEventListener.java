package ro.efidelity.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import ro.efidelity.service.AutentificariEsuateService;

/*
 * Clasa AuthenticationSuccessEventListener asculta dupa evenimentul
 * de autentificare reusita si implemtenteaza o metoda, care in cazul
 * unei autentificari reusite scoate adresa ip de la care s-a facut
 * autentificarea din tabela autentificari_esuate.
 */
@Component
public class AuthenticationSuccessEventListener implements
		ApplicationListener<AuthenticationSuccessEvent> {

	@Autowired
	private AutentificariEsuateService authesserv;

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent ev) {

		// Preluarae ip-ul recent autentificat si scoaterea lui din tabela
		// autentificari_esuate in cazul in care exista acolo.
		String ip = ((WebAuthenticationDetails) ev.getAuthentication()
				.getDetails()).getRemoteAddress();
		authesserv.remove(ip);

	}
}