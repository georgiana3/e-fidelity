package ro.efidelity.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.efidelity.dao.efidelity.AutentificariEsuateDAO;
import ro.efidelity.model.domain.efidelity.AutentificariEsuate;

@Service
public class AutentificariEsuateService {

	@Autowired
	private AutentificariEsuateDAO autesdao;

	public static final Logger logger = Logger
			.getLogger(AutentificariEsuateService.class);

	@Transactional
	public void increment(String ip) {
		AutentificariEsuate autentificare = autesdao.findOne(ip);
		if (autentificare == null) {
			AutentificariEsuate newAutentificare = new AutentificariEsuate();
			newAutentificare.setIp(ip);
			newAutentificare.setNrIncercariEsuate(1);
			autesdao.save(newAutentificare);
		} else {
			autentificare.setNrIncercariEsuate(autentificare
					.getNrIncercariEsuate() + 1);
			autesdao.save(autentificare);
		}
	}

	@Transactional
	public void remove(String ip) {
		AutentificariEsuate autentificare = autesdao.findOne(ip);
		if (autentificare != null) {
			autesdao.delete(autentificare);
		}
	}

	@Transactional
	public AutentificariEsuate getByIp(String ip) {
		return autesdao.findOne(ip);
	}

}