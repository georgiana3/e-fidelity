package ro.efidelity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import ro.efidelity.dao.efidelity.UtilizatorDAO;
import ro.efidelity.model.domain.efidelity.Utilizator;
import ro.efidelity.util.SecurityHelper;

/**
 * Clasa care implementeaza, prin intermediul facilitatilor oferite de Spring
 * Security, serviciul de autentificare pentru utilizatori (logare-delogare).
 * 
 * @author andrei.pietrusel
 */
@Service("authService")
public class AuthService {

	@Autowired
	private UtilizatorDAO udao;

	// @Override
	// public UserDetails loadUserByUsername(String username)
	// throws UsernameNotFoundException {
	// Utilizator utilizator = udao.findByUsernameActiv(username);
	//
	// if (utilizator == null) {
	// throw new UsernameNotFoundException("Utilizatorul nu exista.");
	// }
	// return createSpringSecurityUser(utilizator);
	// }

	public Utilizator getCurrentUtilizator() {
		String username = (String)((UsernamePasswordAuthenticationToken) SecurityHelper
				.getAuthentication().getPrincipal()).getPrincipal();
		Utilizator Utilizator = udao.findByUsernameActiv(username);
		return Utilizator;
	}

	/**
	 * Metoda care autentifica un utilizator prin Spring Security
	 */

	// public void signin(Utilizator Utilizator) {
	// SecurityContextHolder.getContext().setAuthentication();
	// }

	/*
	 * Metode auxiliare private folosite pentru implementarea autentificarii
	 * prin Spring Security
	 */

	// private User createSpringSecurityUser(Utilizator utilizator) {
	// return new User(utilizator.getUsername(), utilizator.getParola(),
	// getAuthorities(utilizator));
	// }

	// private Authentication authenticate(Utilizator utilizator) {
	// return new UsernamePasswordAuthenticationToken(
	// createSpringSecurityUser(utilizator), null,
	// getAuthorities(utilizator));
	// }

}
