package ro.efidelity.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.efidelity.dao.efidelity.RolDAO;
import ro.efidelity.dao.efidelity.UtilizatorDAO;
import ro.efidelity.model.domain.efidelity.PersoanaFizica;
import ro.efidelity.model.domain.efidelity.PersoanaJuridica;
import ro.efidelity.model.domain.efidelity.Rol;
import ro.efidelity.model.domain.efidelity.Utilizator;
import ro.efidelity.model.domain.efidelity.Utilizator.UtilizatorType;
import ro.efidelity.model.dto.ModificareParolaForm;
import ro.efidelity.model.dto.UtilizatorEditForm;
import ro.efidelity.model.dto.UtilizatorForm;
import ro.efidelity.service.exception.EFidelityException;
import ro.efidelity.service.exception.generic.ResourceAlreadyExistsException;
import ro.efidelity.service.exception.generic.ResourceNotFoundException;
import ro.efidelity.util.business.UsernameUtil;

@Service
public class UtilizatorService {

	@Autowired
	private UtilizatorDAO udao;

	@Autowired
	private RolDAO rdao;

	@Autowired
	private MailService mserv;

	@Autowired
	private AuthService authserv;

	@Resource(name = "standardPasswordEncoder")
	private PasswordEncoder passwordEncoder;

	/*
	 * Clase de citit
	 */

	@Transactional(readOnly = true)
	public List<Rol> getToateRoluri() {
		// fara rolul programmer
		List<Rol> lista = rdao.findAll();
		List<Rol> returnat = new ArrayList<Rol>();
		for (Rol rol : lista) {
			if (!(rol.getDenumire().equals("PROGRAMMER")
					|| rol.getDenumire().equals("POSTA") || rol.getDenumire()
					.equals("POSTA_OFICIU")))
				returnat.add(rol);
		}
		return returnat;
	}

	@Transactional(readOnly = true)
	public List<Utilizator> getTotiUtilizatori() {
		return udao.findAll();
	}

	/**
	 * Returneaza un utilizator dupa id.
	 */
	@Transactional(readOnly = true)
	public Utilizator getUtilizatorById(Long id) {
		Utilizator user = udao.findOne(id);

		if (user == null)
			throw new ResourceNotFoundException(
					"Nu s-a gasit un utilizator cu id-ul " + id + ".");
		return user;
	}

	@Transactional(readOnly = true)
	public Utilizator getUtilizatorByEmail(String email) {
		Utilizator user = udao.findByEmail(email);
		if (user == null)
			throw new ResourceNotFoundException(
					"Nu s-a gasit un utilizator cu adresa de email " + email
							+ ".");
		return user;
	}

	@Transactional(readOnly = true)
	public List<Utilizator> getToatePersoaneleJuridice() {
		List<Rol> r = rdao.findByDenumireIn(Arrays.asList(new String[] {
				"PERSOANA_JURIDICA", "DFMT", "DGP", "CEC" }));
		return udao.findByRolIn(r);
	}

	@Transactional(readOnly = true)
	public List<PersoanaJuridica> getToatePersoaneleJuridiceOrdonate() {
		List<Rol> r = rdao.findByDenumireIn(Arrays.asList(new String[] {
				"PERSOANA_JURIDICA", "DFMT", "DGP", "CEC" }));
		return udao.findPersoanaJuridicaByRolInOrderByDenumire(r);
	}

	@Transactional(readOnly = true)
	public List<Utilizator> getTotiClientii() {
		List<Rol> roluri = rdao.findByDenumireIn(Arrays.asList(
				"PERSOANA_JURIDICA", "PERSOANA_FIZICA", "DGP", "CEC"));

		List<Utilizator> useri = udao.findByRolIn(roluri);
		Collections.sort(useri, new Comparator<Utilizator>() {

			@Override
			public int compare(Utilizator o1, Utilizator o2) {
				return UsernameUtil.getFullName(o1).toLowerCase()
						.compareTo(UsernameUtil.getFullName(o2).toLowerCase());
			}

		});

		return useri;
	}

	@Transactional(readOnly = true)
	public List<Utilizator> getToateConturilePostale() {
		List<Rol> roluri = rdao.findByDenumireIn(Arrays.asList("POSTA",
				"POSTA_OFICIU"));

		List<Utilizator> useri = udao.findByRolIn(roluri);
		Collections.sort(useri, new Comparator<Utilizator>() {

			@Override
			public int compare(Utilizator o1, Utilizator o2) {
				return UsernameUtil.getFullName(o1).toLowerCase()
						.compareTo(UsernameUtil.getFullName(o2).toLowerCase());
			}

		});

		return useri;
	}

	/*
	 * Clase de scris
	 */
	public void saveUtilizatorAndSendConfirmMail(UtilizatorForm form,
			String baseUrl) {
		Utilizator utilizator = saveUtilizator(form);

		mserv.sendMail(utilizator.getConfirmat(), form.getEmail(), baseUrl,
				form.getUsername());
	}

	@Transactional
	public Utilizator saveUtilizator(UtilizatorForm form) {

		if (udao.findByUsername(form.getUsername()) != null)
			throw new ResourceAlreadyExistsException(
					"Numele de utilizator ales deja exista.");

		if (udao.findByEmail(form.getEmail()) != null)
			throw new ResourceAlreadyExistsException(
					"Mailul introdus apartine unui utilizator existent.");

		Utilizator user;

		if (form.getTipPersoana() == UtilizatorType.FIZICA) {
			user = new PersoanaFizica();
			PersoanaFizica u = (PersoanaFizica) user;
			u.setNume(form.getNume());
			u.setPrenume(form.getPrenume());
			u.setCnp(form.getCnp());
			u.setRol(rdao.findByDenumire("PERSOANA_FIZICA"));
		} else if (form.getTipPersoana() == UtilizatorType.JURIDICA) {
			user = new PersoanaJuridica();
			PersoanaJuridica u = (PersoanaJuridica) user;
			u.setDenumire(form.getDenumire());
			u.setCui(form.getCui());
			u.setContBancar(StringUtils.deleteWhitespace(form.getContBancar()));
			u.setSucursala(form.getSucursala());
			u.setRol(rdao.findByDenumire("PERSOANA_JURIDICA"));
		} else
			throw new RuntimeException("Tipul contului este invalid.");

		/* EMAIL CONFIRMATION */

		String confirmCode = passwordEncoder.encode(form.getUsername());
		user.setConfirmat(confirmCode);

		/* Adresa */
		user.setJudet(form.getJudet());
		user.setLocalitate(form.getLocalitate());
		user.setAdresa(form.getAdresa());
		user.setCodPostal(form.getCodPostal());
		user.setEmail(form.getEmail());
		user.setTelefon(form.getTelefon());
		user.setUsername(form.getUsername());

		user.setValid(false);

		user.setParola(passwordEncoder.encode(form.getParola()));

		return saveUtilizator(user);
	}

	@Transactional
	public Utilizator saveUtilizator(Utilizator utilizator) {

		return udao.save(utilizator);
	}

	@Transactional
	public void modficaDateUtilizator(UtilizatorForm form) {

		Utilizator u = authserv.getCurrentUtilizator();
		// u.setEmail(form.getEmail());
		u.setTelefon(form.getTelefon());
		u.setJudet(form.getJudet());
		u.setCodPostal(form.getCodPostal());
		u.setLocalitate(form.getLocalitate());
		u.setAdresa(form.getAdresa());

		if (form.getTipPersoana() == UtilizatorType.FIZICA) {

			PersoanaFizica p = (PersoanaFizica) u;
			p.setCnp(form.getCnp());
			p.setNume(form.getNume());
			p.setPrenume(form.getPrenume());
		} else if (form.getTipPersoana() == UtilizatorType.JURIDICA) {

			PersoanaJuridica p = (PersoanaJuridica) u;
			p.setDenumire(form.getDenumire());
			p.setContBancar(StringUtils.deleteWhitespace(form.getContBancar()));
			p.setCui(form.getCui());
			p.setSucursala(form.getSucursala());
		}
		udao.save(u);
	}

	@Transactional
	public void modficaParolaUtilizator(
			ModificareParolaForm modificareParolaForm) {
		Utilizator u = authserv.getCurrentUtilizator();
		String parolaNoua = modificareParolaForm.getParola();
		String parolaVeche = modificareParolaForm.getParolaVeche();
		String parolaVecheReala = u.getParola();

		if (!passwordEncoder.matches(parolaVeche, parolaVecheReala)) {
			throw new EFidelityException("Parola veche incorecta.");
		}

		if (!modificareParolaForm.isPasswordMatching()) {
			throw new EFidelityException("Parola nu coincide.");
		}

		String pa = passwordEncoder.encode(parolaNoua);
		u.setParola(pa);
		udao.save(u);
	}

	@Transactional
	public boolean confirmRegistration(String cod) {

		Utilizator user = udao.findByConfirmat(cod);
		if (user == null)
			throw new ResourceNotFoundException(
					"Cod de confirmare inregistrare incorect.");
		user.setConfirmat(null);
		user.setValid(true);
		udao.save(user);

		return true;

	}

	@Transactional
	public void sendNewPasswordMail(String email, String baseUrl) {
		String p = passwordEncoder.encode(email);
		Utilizator u = udao.findByEmail(email);
		mserv.sendMailRenewPassword(p, email, baseUrl, u.getUsername());
		u.setConfirmat(p);
		udao.save(u);

	}

	@Transactional
	public void renewPassword(String cod, String parola) {
		Utilizator user = udao.findByConfirmat(cod);
		if (user == null)
			throw new ResourceNotFoundException(
					"Cod de confirmare resetare parola invalid.");

		user.setParola(passwordEncoder.encode(parola));
		user.setConfirmat("");
	}

	@Transactional
	public void valideazaUtilizator(Long idUtilizator) {
		Utilizator user = udao.findOne(idUtilizator);

		/* Seteaza utilizatorul ca fiind valid */
		user.setValid(true);
	}

	@Transactional
	public void invalideazaUtilizator(Long idUtilizator) {
		Utilizator user = udao.findOne(idUtilizator);
		/* Seteaza utilizatorul ca fiind invalid */
		user.setValid(false);
	}

	// @Transactional
	// public Object searchUsers(UtilizatorSearchForm form) {
//	 QUtilizator quser = QUtilizator.utilizator;
	//
	// BooleanExpression predicat = null;
	//
	// if (form.getUsername() != null
	// && StringUtils.isNotBlank(form.getUsername())) {
	// BooleanExpression userp = (form.getUsername() != null) ? quser.username
	// .upper().trim()
	// .like("%" + form.getUsername().toUpperCase().trim() + "%")
	// : null;
	// predicat = userp.and(predicat);
	// }
	//
	// if (form.getTipUtilizator() != null
	// && StringUtils.isNotBlank(form.getTipUtilizator())) {
	// UtilizatorType t;
	// if (form.getTipUtilizator().equals("FIZICA"))
	// t = UtilizatorType.FIZICA;
	// else
	// t = UtilizatorType.JURIDICA;
	//
	// BooleanExpression tipUtilizatorp = (form.getTipUtilizator() != null) ?
	// quser.tipUtilizator
	// .eq(t) : null;
	// predicat = tipUtilizatorp.and(predicat);
	// }
	//
	// /* Cautare dupa nume / denumire firma */
	// if (form.getNume() != null) {
	// BooleanExpression namePredicate = null;
	// for (String term : StringUtils.split(form.getNume())) {
	// BooleanExpression iterPredicate = quser.numeSearchTemp.lower()
	// .trim().like(("%" + term.toLowerCase().trim()) + "%");
	// namePredicate = BooleanExpression.allOf(namePredicate,
	// iterPredicate);
	// }
	// predicat = BooleanExpression.allOf(predicat, namePredicate);
	// }
	//
	// List<Utilizator> users = (List<Utilizator>) udao.findAll(predicat);
	//
	// return users;
	// }

	@Transactional
	public void modificaUtilizatorAdmin(UtilizatorEditForm form,
			Long idUtilizator) {
		Utilizator u = udao.findOne(idUtilizator);

		u.setTelefon(form.getTelefon());
		u.setJudet(form.getJudet());
		if (StringUtils.isBlank(form.getCodPostal())) {
			u.setCodPostal(form.getCodIndrumare());
		} else {
			u.setCodPostal(form.getCodPostal());
		}

		u.setLocalitate(form.getLocalitate());
		u.setAdresa(form.getAdresa());

		udao.save(u);

	}

	/*
	 * Helpers
	 */
	public void setUtilizator(UtilizatorEditForm form, Utilizator utilizator) {
		form.setUsername(utilizator.getUsername());
		form.setTelefon(utilizator.getTelefon());
		form.setJudet(utilizator.getJudet());
		form.setCodPostal(utilizator.getCodPostal());
		form.setLocalitate(utilizator.getLocalitate());
		form.setAdresa(utilizator.getAdresa());
	}

	public boolean isCodOk(String cod) {
		if (udao.findByConfirmat(cod) != null)
			return true;
		else
			return false;
	}

	@Transactional(readOnly = false)
	public Utilizator getUtilizatorByUsername(String username) {
		Utilizator user = udao.findByUsernameActiv(username);
		if (user == null)
			throw new ResourceNotFoundException(
					"Nu s-a gasit un utilizator cu username-ul " + username
							+ ".");
		return user;
	}

	@Transactional(readOnly = false)
	public List<Utilizator> getUtilizatoriByRol(Rol rol) {
		List<Utilizator> useri = udao.findByRole(rol);
		if (useri.size() < 1)
			throw new ResourceNotFoundException(
					"Nu s-a gasit un utilizator cu rol-ul " + rol.getDenumire()
							+ ".");
		return useri;
	}

//	@Transactional(readOnly = true)
//	public Object getClientiCuConfirmariScanate() {
//
//		List<Utilizator> useri = udao
//				.findPersoanaJuridicaByContractCuConfirmariScanate();
//
//		Collections.sort(useri, new Comparator<Utilizator>() {
//			@Override
//			public int compare(Utilizator o1, Utilizator o2) {
//				return UsernameUtil.getFullName(o1).toLowerCase()
//						.compareTo(UsernameUtil.getFullName(o2).toLowerCase());
//			}
//		});
//
//		return useri;
//	}

}
