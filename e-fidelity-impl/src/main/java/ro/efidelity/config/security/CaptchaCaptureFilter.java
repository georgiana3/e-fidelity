package ro.efidelity.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/*
 * Clasa care la fiecare request va prelua jcaptcha in cazul in care exista.
 * 
 */
public class CaptchaCaptureFilter extends OncePerRequestFilter {

	private String userCaptchaResponse;
	private HttpServletRequest request;

	@Override
	public void doFilterInternal(HttpServletRequest req,
			HttpServletResponse res, FilterChain chain) throws IOException,
			ServletException {

		// Assign values only when user has submitted a Captcha value.
		// Without this condition the values will be reset due to redirection
		// and CaptchaVerifierFilter will enter an infinite loop
		if (req.getParameter("jcaptcha") != null) {
			request = req;
			userCaptchaResponse = req.getParameter("jcaptcha");
		}

		// Proceed with the remaining filters
		chain.doFilter(req, res);
	}

	public String getUserCaptchaResponse() {
		return userCaptchaResponse;
	}

	public void setUserCaptchaResponse(String userCaptchaResponse) {
		this.userCaptchaResponse = userCaptchaResponse;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
}