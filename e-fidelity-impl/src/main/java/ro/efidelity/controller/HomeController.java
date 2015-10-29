package ro.efidelity.controller;

import java.math.BigDecimal;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ro.efidelity.model.domain.efidelity.AutentificariEsuate;
import ro.efidelity.service.AutentificariEsuateService;
import ro.efidelity.service.ProgrammerService;
import ro.efidelity.util.message.MessageHelper;

@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
	private ProgrammerService pserv;

	@Autowired
	private AutentificariEsuateService authesserv;

	// @Autowired
	// private AuthService auth;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(HomeController.class);

	@ResponseBody
	@RequestMapping(value = "/test-stuff", method = RequestMethod.GET)
	public String testStuff(HttpServletResponse response) {

		System.err.println("PostaRomana1 "
				+ passwordEncoder.encode("PostaRomana1"));

		// try {
		// Local local = PostaUtil.getLocal(auth.getCurrentUtilizator());
		// return "Raspuns: " + local.getIdUp() + " " + local.getIdDecont();
		// } catch (Exception e) {
		// e.printStackTrace();
		// response.setStatus(500);
		return "Probleme de server.";
		// }
	}

	@RequestMapping("")
	public String home(Principal principal) {
		return principal != null ? "homeSignedIn" : "home/login";
	}

	@RequestMapping("/faq")
	public String faq() {
		return "faq";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, Principal principal,
			HttpServletRequest request) {
		// aici trebuie sa verific daca are mai mult de 3 incercari esutate
		// daca da afisez captcha alfel nu
		String ip = request.getRemoteAddr();
		AutentificariEsuate autes = authesserv.getByIp(ip);
		if (autes != null && autes.getNrIncercariEsuate() > 3)
			model.addAttribute("captcha", "true");

		return "home/login";
	}

	@PreAuthorize("hasAnyRole('PROGRAMMER','POSTA')")
	@RequestMapping(value = "/clearCache", method = RequestMethod.GET)
	public String clearCache(RedirectAttributes ra) {
		MessageHelper.addSuccessAttribute(ra, "A fost curatat cache-ul.");
		pserv.clearCache();
		return "redirect:/";
	}

	@ResponseBody
	@RequestMapping(value = "/clear-awb-cache", method = RequestMethod.GET)
	public String clearAwbCache(HttpServletResponse response) {
		try {
			pserv.clearAwbCache();
			return "Cache-ul de AWB-uri a fost sters cu succes.";
		} catch (Exception e) {
			response.setStatus(500);
			return "Probleme de server la stergerea cache-ului de AWB-uri.";
		}

	}

	@RequestMapping(value = "/cod-postal", method = RequestMethod.GET)
	public String codPostal(Model model, Principal principal) {
		return "cod-postal";
	}

	@SuppressWarnings("unused")
	private BigDecimal getProcentdiscount(int dim) {

		int[] intervalediscount = { 0, 50, 100, 300, 500, 800, 1000, 5000,
				7500, 10000, 25000 };
		int[] discount = { 0, 12, 14, 18, 20, 23, 25, 27, 30, 33, 33 };
		if (dim == 0) {
			return BigDecimal.ZERO;
		}
		for (int i = 0; i < intervalediscount.length - 1; i++) {

			if (dim > intervalediscount[i] && dim <= intervalediscount[i + 1]) {
				return new BigDecimal(discount[i]);
			}
		}
		return new BigDecimal(discount[9]);
	}

}
