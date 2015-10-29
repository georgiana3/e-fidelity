package ro.efidelity.controller;

import java.security.Principal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ro.efidelity.config.security.CaptchaCaptureFilter;
import ro.efidelity.model.domain.efidelity.PersoanaFizica;
import ro.efidelity.model.domain.efidelity.PersoanaJuridica;
import ro.efidelity.model.domain.efidelity.Utilizator;
import ro.efidelity.model.domain.efidelity.Utilizator.UtilizatorType;
import ro.efidelity.model.dto.ModificareParolaForm;
import ro.efidelity.model.dto.UtilizatorEditForm;
import ro.efidelity.model.dto.UtilizatorForm;
import ro.efidelity.model.dto.UtilizatorSearchForm;
import ro.efidelity.service.AuthService;
import ro.efidelity.service.UtilizatorService;
import ro.efidelity.service.exception.EFidelityException;
import ro.efidelity.service.exception.generic.ResourceAlreadyExistsException;
import ro.efidelity.service.exception.generic.ResourceNotFoundException;
import ro.efidelity.util.ArrayHelper;
import ro.efidelity.util.ServletHelper;
import ro.efidelity.util.ValidationHelper;
import ro.efidelity.util.message.MessageHelper;

import com.octo.captcha.module.servlet.image.SimpleImageCaptchaServlet;

@SuppressWarnings("all")
@Controller
@RequestMapping("utilizatori")
public class UtilizatorController {

	private static final boolean publiclyAccessible = true;
	private static final String MSG_PUBLIC_ACCESS_DISABLED = "Inregistrarea utilizatorilor este momentan dezactivata pentru clientii fara contract cu Posta Romana.";

	@Autowired
	private AuthService authserv;

	@Autowired
	private UtilizatorService userv;

	@Autowired
	private CaptchaCaptureFilter captchaCaptureFilter;

	@PreAuthorize("hasAnyRole('PROGRAMMER','POSTA')")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getUtilizatori(
			@ModelAttribute("utilizatorSearchForm") UtilizatorSearchForm form,
			Model model, Principal principal) {

		if (form.isFilled()) {
//			model.addAttribute("utilizatori", userv.searchUsers(form));
		}
		return "utilizatori/list";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String formNewUtilizator(
			@ModelAttribute("utilizatorForm") UtilizatorForm form, Model model) {

		model.addAttribute("roluri", userv.getToateRoluri());

		return "utilizatori/new";
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public String saveUtilizator(
			@Valid @ModelAttribute("utilizatorForm") UtilizatorForm form,
			Errors errors, RedirectAttributes ra, HttpServletRequest request) {

		if (!publiclyAccessible) {
			MessageHelper.addErrorAttribute(ra, MSG_PUBLIC_ACCESS_DISABLED);
			return "redirect:/";
		}
		/* Verificare Captcha inainte de creare cont */
		if (captchaCaptureFilter != null) {
			if (StringUtils.isBlank(captchaCaptureFilter
					.getUserCaptchaResponse())) {
				ra.addFlashAttribute("utilizatorForm", form);
				MessageHelper
						.addErrorAttribute(ra,
								"Nu ati completat Captcha care este un camp obligatoriu.");
				return "redirect:/utilizatori/new";
			}
		}

		boolean captchaPassed = SimpleImageCaptchaServlet.validateResponse(
				captchaCaptureFilter.getRequest(),
				captchaCaptureFilter.getUserCaptchaResponse());

		// Check if valid
		if (captchaPassed) {
			// reseteaza raspuns captcha
			captchaCaptureFilter.setUserCaptchaResponse(null);
		} else {
			// reseteaza raspuns captcha
			captchaCaptureFilter.setUserCaptchaResponse(null);
			ra.addFlashAttribute("utilizatorForm", form);
			MessageHelper.addErrorAttribute(ra,
					"Ati gresit la introducerea textului din captcha.");
			return "redirect:/utilizatori/new";

		}

		/* Valideaza doar field-urile specifice */
		Collection<FieldError> fe;
		if (form.getTipPersoana() == UtilizatorType.JURIDICA) {
			fe = ValidationHelper.filterFields(errors,
					UtilizatorForm.CAMPURI_FIZICA);
		} else {
			fe = ValidationHelper.filterFields(errors,
					UtilizatorForm.CAMPURI_JURIDICA);
		}
		if (fe.size() > 0) {
			ra.addFlashAttribute("utilizatorForm", form);
			MessageHelper.addErrorAttribute(ra, fe.iterator().next()
					.getDefaultMessage());
			return "redirect:/utilizatori/new";
		}

		if (!form.isPasswordMatching()) {
			MessageHelper.addErrorAttribute(ra, "Parola nu coincide.");
			ra.addFlashAttribute("utilizatorForm", form);
			form.setParola(null);
			form.setConfParola(null);

			return "redirect:/utilizatori/new";
		}

		try {
			userv.saveUtilizatorAndSendConfirmMail(form,
					ServletHelper.getBaseUrl(request));

			MessageHelper
					.addSuccessAttribute(
							ra,
							"Cererea a fost inregistrata cu succes! Veti primi un mail de confirmare  pe adresa: "
									+ form.getEmail()
									+ ". Username-ul este: "
									+ form.getUsername());
		} catch (EFidelityException | ResourceAlreadyExistsException e) {
			MessageHelper.addErrorAttribute(ra, e.getMessage());
			ra.addFlashAttribute("utilizatorForm", form);

			return "redirect:/utilizatori/new";
		}

		return "redirect:/";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editForm(
			@ModelAttribute("utilizatorForm") UtilizatorForm form,
			@ModelAttribute("modificareParolaForm") ModificareParolaForm modificareParolarForm,

			Model model) {

		// Trebuie luat utilizatorul curent
		Utilizator utilizatorCurent = authserv.getCurrentUtilizator();
		if (utilizatorCurent instanceof PersoanaFizica) {
			PersoanaFizica u = (PersoanaFizica) utilizatorCurent;
			form.setNume(u.getNume());
			form.setPrenume(u.getPrenume());
			form.setCnp(u.getCnp());
		} else if (utilizatorCurent instanceof PersoanaJuridica) {
			PersoanaJuridica u = (PersoanaJuridica) utilizatorCurent;
			form.setDenumire(u.getDenumire());
			form.setCui(u.getCui());
			form.setContBancar(u.getContBancar());
			form.setSucursala(u.getSucursala());
		} else
			throw new RuntimeException("Utilizator invalid: "
					+ utilizatorCurent);

		form.setUsername(utilizatorCurent.getUsername());
		form.setTelefon(utilizatorCurent.getTelefon());
		form.setEmail(utilizatorCurent.getEmail());
		form.setLocalitate(utilizatorCurent.getLocalitate());
		form.setCodPostal(utilizatorCurent.getCodPostal());
		form.setAdresa(utilizatorCurent.getAdresa());
		form.setTipPersoana(utilizatorCurent.getTipUtilizator());
		form.setLocalitate(utilizatorCurent.getLocalitate());
		form.setJudet(utilizatorCurent.getJudet());
		form.setCodPostal(utilizatorCurent.getCodPostal());

		model.addAttribute("utilizatorCurent", utilizatorCurent);
		return "utilizatori/edit";
	}

	@RequestMapping(value = "/editdata", method = RequestMethod.POST)
	public String editdata(
			@Valid @ModelAttribute("utilizatorForm") UtilizatorForm form,
			Errors errors,
			@ModelAttribute("modificareParolaForm") ModificareParolaForm modificareParolarForm,
			Model model, RedirectAttributes ra) {

		Utilizator utilizatorCurent = authserv.getCurrentUtilizator();

		/* Valideaza doar field-urile specifice */
		Collection<FieldError> fe;
		if (utilizatorCurent instanceof PersoanaJuridica) {
			fe = ValidationHelper.filterFields(errors, ArrayHelper.arrayMerge(
					UtilizatorForm.CAMPURI_FIZICA,
					UtilizatorForm.CAMPURI_NEEDITABILE));
		} else {
			fe = ValidationHelper.filterFields(errors, ArrayHelper.arrayMerge(
					UtilizatorForm.CAMPURI_JURIDICA,
					UtilizatorForm.CAMPURI_NEEDITABILE));
		}

		if (fe.size() > 0) {
			ra.addFlashAttribute("utilizatorForm", form);
			MessageHelper.addErrorAttribute(ra, fe.iterator().next()
					.getDefaultMessage());
			return "redirect:/utilizatori/edit";
		}

		// Trebuie luat utilizatorul curent
		form.setTipPersoana(utilizatorCurent.getTipUtilizator());

		userv.modficaDateUtilizator(form);
		MessageHelper.addSuccessAttribute(ra,
				"Datele au fost modificate cu succes.");

		return "redirect:/utilizatori/edit";
	}

	@RequestMapping(value = "/editparola", method = RequestMethod.POST)
	public String editparola(
			Model model,
			@ModelAttribute("utilizatorForm") UtilizatorForm form,
			@Valid @ModelAttribute("modificareParolaForm") ModificareParolaForm modificareParolaForm,
			Errors errors, RedirectAttributes ra) {

		/* Validari */
		if (errors.hasErrors()) {
			MessageHelper.addErrorAttribute(ra, errors.getFieldError()
					.getDefaultMessage());
			return "redirect:/utilizatori/edit";
		}

		try {
			userv.modficaParolaUtilizator(modificareParolaForm);
			MessageHelper.addSuccessAttribute(ra,
					"Parola a fost modificata cu succes.");
		} catch (EFidelityException e) {
			MessageHelper.addErrorAttribute(ra, e.getMessage());
			return "redirect:/utilizatori/edit";
		}

		return "redirect:/utilizatori/edit";
	}

	@RequestMapping(value = "/confirm", method = RequestMethod.GET)
	public String confirmEmail(@RequestParam("cod") String cod, Model model,
			HttpServletRequest request, RedirectAttributes ra) {

		userv.confirmRegistration(cod);

		MessageHelper
				.addSuccessAttribute(ra,
						"Confirmarea s-a realizat cu succes. Va rugam sa va autentificati.");

		return "redirect:/";
	}

	// @RequestMapping(value = "/change-image", method = RequestMethod.POST)
	// public String uploadFile(
	// @ModelAttribute("changeImageForm") ChangeImageForm form,
	// Model model, Error error, RedirectAttributes ra) throws Exception {
	// if (form.getFile() != null) {
	// if (form.getFile().isEmpty()) {
	// MessageHelper.addErrorAttribute(ra,
	// "Nu a fost incarcat nici un fisier");
	// return "redirect:/utilizatori/edit";
	//
	// }
	// try {
	// userv.schimbaSigla(form.getFile().getBytes());
	// } catch (EFidelity Exception e) {
	// MessageHelper.addErrorAttribute(ra,
	// "Sigla nu a fost schimbata.");
	// }
	// }
	// MessageHelper.addSuccessAttribute(ra,
	// "Sigla a fost schimbata cu succes.");
	// return "redirect:/utilizatori/edit";
	// }

	// @ResponseBody
	// @RequestMapping(value = "/sigla", method = RequestMethod.GET, produces =
	// MediaType.IMAGE_JPEG_VALUE)
	// public byte[] getSigla() throws IOException {
	// Utilizator user = authserv.getCurrentUtilizator();
	// if (user instanceof PersoanaFizica)
	// return null;
	// else if (user instanceof PersoanaJuridica)
	// return ((PersoanaJuridica) user).getSigla();
	// else
	// throw new RuntimeException("Utilizator invalid: " + user);
	// }

	@RequestMapping(value = "/forgot-password", method = RequestMethod.GET)
	public String forgotPasswordForm(Model model) {
		return "utilizatori/forgot-password";
	}

	@RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
	public String upload(@RequestParam String email, Model model,
			RedirectAttributes ra, HttpServletRequest request) {
		if (email == null || email.isEmpty()) {
			MessageHelper.addErrorAttribute(ra,
					"Va rugam completati adresa de email.");
			return "redirect:/utilizatori/forgot-password";
		}

		try {
			userv.getUtilizatorByEmail(email);
			userv.sendNewPasswordMail(email, ServletHelper.getBaseUrl(request));

		} catch (ResourceNotFoundException re) {
			MessageHelper.addErrorAttribute(ra, re.getMessage());
			return "redirect:/utilizatori/forgot-password";
		}

		MessageHelper.addSuccessAttribute(ra,
				"Va rugam sa verificati adresa de email.");
		return "redirect:/";

	}

	@RequestMapping(value = "/renew-password", method = RequestMethod.GET)
	public String renewPasswrodForm(@RequestParam("cod") String cod,
			Model model, RedirectAttributes ra) {

		if (userv.isCodOk(cod)) {
			model.addAttribute("codreset", cod);

			return "utilizatori/renew-password";
		} else {
			MessageHelper.addErrorAttribute(ra,
					"Codul a expirat, ori nu a fost generat niciodata.");
			// eventual trebuie returnat asta in pagina de eroare
			return "redirect:/utilizatori";
		}
	}

	@RequestMapping(value = "/renew-password", method = RequestMethod.POST)
	public String renewPasswrod(@RequestParam("parola") String parola,
			@RequestParam("parola1") String parola1,
			@RequestParam("codreset") String codReset, Model model,
			RedirectAttributes ra) {

		if (StringUtils.isBlank(parola)
				|| !parola
						.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%_-]).{8,20})")) {
			MessageHelper.addErrorAttribute(ra, "Parola trebuie sa contina: "
					+ "cel putin o cifra, o litera mica, "
					+ "o litera mare, un caracter special @ # $ % _ - "
					+ "si o dimensiune cuprinsa intre 8 si 20 de caractere.");
			return "redirect:/utilizatori/renew-password?cod=" + codReset;
		}

		if (userv.isCodOk(codReset)) {
			if (!StringUtils.equals(parola, parola1)) {
				MessageHelper.addErrorAttribute(ra,
						"Parolele trebuie sa coincida.");
				return "redirect:/utilizatori/renew-password?cod=" + codReset;
			}
			userv.renewPassword(codReset, parola);
			MessageHelper
					.addSuccessAttribute(ra, "Parola schimbata cu succes.");
			return "redirect:/";
		} else {
			MessageHelper.addErrorAttribute(ra,
					"Codul a expirat ori este invalid.");
			return "redirect:/utilizatori";
		}
	}

	/**
	 * Actiunea de vizualizare a unui utilizator
	 */

	@RequestMapping(value = "/{idUtilizator}", method = RequestMethod.GET)
	public String get(@PathVariable Long idUtilizator, Model model,
			RedirectAttributes ra) {

		Utilizator user = userv.getUtilizatorById(idUtilizator);
		if (user.getTipUtilizator().equals(UtilizatorType.FIZICA)) {
			PersoanaFizica u = (PersoanaFizica) user;
			model.addAttribute("persoanaFizica", u);
		} else if (user.getTipUtilizator().equals(UtilizatorType.JURIDICA)) {
			PersoanaJuridica u = (PersoanaJuridica) user;
			model.addAttribute("persoanaJuridica", u);
		}
		model.addAttribute("utilizator", user);

		return "utilizatori/view";
	}

	@PreAuthorize("hasAnyRole('PROGRAMMER','POSTA')")
	@RequestMapping(value = "/{idUtilizator}/valideaza", method = RequestMethod.POST)
	public String valideaza(@PathVariable Long idUtilizator,
			HttpServletRequest request) {
		userv.valideazaUtilizator(idUtilizator);

		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@PreAuthorize("hasAnyRole('PROGRAMMER','POSTA')")
	@RequestMapping(value = "/{idUtilizator}/invalideaza", method = RequestMethod.POST)
	public String invalideaza(@PathVariable Long idUtilizator,
			HttpServletRequest request) {
		userv.invalideazaUtilizator(idUtilizator);

		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	/* Editare destinatar */
	@PreAuthorize("hasAnyRole('PROGRAMMER')")
	@RequestMapping(value = "/{idUtilizator}/edit", method = RequestMethod.GET)
	public String editDestinatar(@PathVariable Long idUtilizator,
			@ModelAttribute("utilizatorForm") UtilizatorEditForm form,
			Model model, RedirectAttributes ra) {

		Utilizator utilizator;
		try {
			utilizator = userv.getUtilizatorById(idUtilizator);
			userv.setUtilizator(form, utilizator);

			model.addAttribute("utilizator", utilizator);
		} catch (EFidelityException | ResourceNotFoundException e) {
			MessageHelper.addErrorAttribute(ra, e.getMessage());
			return "redirect:/utilizatori";
		}

		return "utilizatori/admin/edit";
	}

	/**
	 * Actiune modificare (editare) destinatar.
	 */
	@PreAuthorize("hasAnyRole('PROGRAMMER')")
	@RequestMapping(value = "/{idUtilizator}/edit", method = RequestMethod.PUT)
	public String editForm(@PathVariable Long idUtilizator,
			@Valid @ModelAttribute("utilizatorForm") UtilizatorEditForm form,
			Errors errors, Model model, RedirectAttributes ra) {

		Utilizator utilizator = userv.getUtilizatorById(idUtilizator);

		/* Valideaza doar field-urile specifice */
		Collection<FieldError> fe;
		if (utilizator instanceof PersoanaJuridica) {
			fe = ValidationHelper.filterFields(errors,
					UtilizatorEditForm.CAMPURI_FIZICA);
		} else {
			fe = ValidationHelper.filterFields(errors,
					UtilizatorEditForm.CAMPURI_JURIDICA);
		}
		if (fe.size() > 0) {
			ra.addFlashAttribute("utilizatorForm", form);
			MessageHelper.addErrorAttribute(ra, fe.iterator().next()
					.getDefaultMessage());
			return "redirect:/utilizatori/" + idUtilizator + "/edit";
		}

		try {
			userv.modificaUtilizatorAdmin(form, idUtilizator);
		} catch (EFidelityException | ResourceNotFoundException e) {
			MessageHelper.addErrorAttribute(ra, e.getMessage());
		}

		return "redirect:/utilizatori/" + idUtilizator + "/edit";
	}
}
