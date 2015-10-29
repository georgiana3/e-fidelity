package ro.efidelity.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import ro.efidelity.service.exception.EFidelityException;
import ro.efidelity.service.exception.generic.ResourceAlreadyExistsException;
import ro.efidelity.service.exception.generic.ResourceNotFoundException;

/**
 * General error handler for the application.
 */
@ControllerAdvice(basePackages = "ro.efidelity.controller")
public final class SiteControllerAdvice {

	private static final String EXCEPTION_LOGGING_MESSAGE = "Exceptie ajunsa in handler-ul global din SiteControllerAdvice.";

	private static final Logger logger = LoggerFactory
			.getLogger(SiteControllerAdvice.class);

	/* Data binders */

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAutoGrowNestedPaths(false);
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	/* Handlere de exceptii */

	/**
	 * Handle 404 NOT FOUND exceptions.
	 */
	@ExceptionHandler(value = ResourceNotFoundException.class)
	public ModelAndView resourceNotFoundException(Exception exception,
			WebRequest request) {

		logger.error(EXCEPTION_LOGGING_MESSAGE, exception);

		ModelAndView modelAndView = new ModelAndView("generalError");
		modelAndView
				.addObject(
						"errorMessage",
						"O resursa accesata in procesarea cererii dumneavoastra nu exista sau a fost stearsa.");

		return modelAndView;
	}

	/**
	 * Handle resource already existing exceptions.
	 */
	@ExceptionHandler(value = ResourceAlreadyExistsException.class)
	public ModelAndView resourceAlreadyExistsException(Exception exception,
			WebRequest request) {

		logger.error(EXCEPTION_LOGGING_MESSAGE, exception);

		ModelAndView modelAndView = new ModelAndView("generalError");
		modelAndView.addObject("errorMessage",
				"Resursa introdusa se identifica cu una deja existenta.");

		return modelAndView;
	}

	/**
	 * Handle database exceptions.
	 */
	@ExceptionHandler(value = DataAccessException.class)
	public ModelAndView dataAccessException(Exception exception,
			WebRequest request) {

		logger.error(EXCEPTION_LOGGING_MESSAGE, exception);

		ModelAndView modelAndView = new ModelAndView("generalError");
		modelAndView
				.addObject("errorMessage",
						"Baza de date a intampinat o problema la procesarea cererii dumneavoastra.");

		return modelAndView;
	}

	/**
	 * Handle security exceptions.
	 */
	@ExceptionHandler(value = AccessDeniedException.class)
	public ModelAndView accessDeniedException(Exception exception,
			WebRequest request) {

		logger.error(EXCEPTION_LOGGING_MESSAGE, exception);

		ModelAndView modelAndView = new ModelAndView("generalError");
		modelAndView.addObject("errorMessage",
				"Nu aveti drepturi de acces la resursa sau actiunea dorita.");

		return modelAndView;
	}

	/**
	 * Handle form binding exceptions
	 */
	// @ExceptionHandler(value = BindException.class)
	// public ModelAndView bindException(Exception exception, WebRequest
	// request) {
	//
	// logger.error(EXCEPTION_LOGGING_MESSAGE, exception);
	//
	// ModelAndView modelAndView = new ModelAndView("generalError");
	// modelAndView.addObject("errorMessage",
	// "Intr-un camp din formular s-a introdus un tip de date necorespunzator: "
	// + ((BindException) exception).getFieldError()
	// .getField() + ".");
	//
	// return modelAndView;
	// }

	/**
	 * Handle locally-uncaught EfidelityException instances.
	 */
	@ExceptionHandler(value = EFidelityException.class)
	public ModelAndView efidelityException(Exception exception,
			WebRequest request) {

		logger.error(EXCEPTION_LOGGING_MESSAGE, exception);

		ModelAndView modelAndView = new ModelAndView("generalError");
		modelAndView.addObject("errorMessage", exception.getMessage());

		return modelAndView;
	}

	/**
	 * Handle all other exceptions.
	 */
	@ExceptionHandler(value = Exception.class)
	public ModelAndView exception(Exception exception, WebRequest request) {

		logger.error(EXCEPTION_LOGGING_MESSAGE, exception);

		ModelAndView modelAndView = new ModelAndView("generalError");
		modelAndView
				.addObject("errorMessage",
						"Oops! S-a intampinat o problema la procesarea cererii dumneavoastra.");

		// modelAndView.addObject("errorMessage",
		// Throwables.getRootCause(exception));
		return modelAndView;
	}
}