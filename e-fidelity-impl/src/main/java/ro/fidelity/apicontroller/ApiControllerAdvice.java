package ro.fidelity.apicontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ro.efidelity.service.exception.EFidelityException;
import ro.efidelity.service.exception.generic.ResourceAlreadyExistsException;
import ro.efidelity.service.exception.generic.ResourceNotFoundException;
import ro.efidelity.util.restmessage.RestErrorMessage;
import ro.efidelity.util.restmessage.RestMessage;

@ControllerAdvice(basePackages = "ro.efidelity.apicontroller")
public class ApiControllerAdvice {

	private static final String EXCEPTION_LOGGING_MESSAGE = "Exceptie ajunsa in handler-ul global din ApiControllerAdvice.";

	private static final Logger logger = LoggerFactory
			.getLogger(ApiControllerAdvice.class);

	/**
	 * 500 Eroare de aplicatie restful cu mesaj neafisabil
	 */
	@ExceptionHandler(EFidelityException.class)
	public ResponseEntity<RestMessage> viewableExceptionHandler(
			Exception exception) {

		logger.debug(EXCEPTION_LOGGING_MESSAGE, exception);

		HttpStatus status = HttpStatus.BAD_REQUEST;

		return new ResponseEntity<RestMessage>(new RestErrorMessage(
				"Request invalid", exception.getMessage(), status), status);
	}

	/**
	 * 404 NOT FOUND
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<RestMessage> http404Handler(Exception exception) {

		logger.debug(EXCEPTION_LOGGING_MESSAGE, exception);

		HttpStatus status = HttpStatus.NOT_FOUND;

		return new ResponseEntity<RestMessage>(new RestErrorMessage(
				"Resursa negasita", exception.getMessage(), status), status);
	}

	/**
	 * 409 CONFLICT
	 */
	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<RestMessage> http409Handler(Exception exception) {

		logger.debug(EXCEPTION_LOGGING_MESSAGE, exception);

		HttpStatus status = HttpStatus.CONFLICT;

		return new ResponseEntity<RestMessage>(new RestErrorMessage(
				"Resursa deja exista", exception.getMessage(), status), status);
	}

	/**
	 * 403 FORBIDDEN
	 */
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<RestMessage> http403Handler(Exception exception) {

		logger.debug(EXCEPTION_LOGGING_MESSAGE, exception);

		HttpStatus status = HttpStatus.FORBIDDEN;

		return new ResponseEntity<RestMessage>(
				new RestErrorMessage(
						"Acces restictionat",
						"Nu aveti permisiunile necesare pentru accesarea resursei dorite.",
						status), status);
	}

	/**
	 * 400 BAD REQUEST : JSON parsing exceptions
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<RestMessage> http400JsonParseExceptionHandler(
			Exception exception) {

		logger.debug(EXCEPTION_LOGGING_MESSAGE, exception);

		HttpStatus status = HttpStatus.BAD_REQUEST;

		return new ResponseEntity<RestMessage>(new RestErrorMessage(
				"Request malformat",
				"Eroare la parsarea string-ului JSON din request.", status),
				status);
	}

	/**
	 * 400 BAD REQUEST : Handle form binding exceptions
	 */
	@ExceptionHandler(value = BindException.class)
	public ResponseEntity<RestMessage> http400BindExceptionHandler(
			Exception exception) {

		logger.debug(EXCEPTION_LOGGING_MESSAGE, exception);

		HttpStatus status = HttpStatus.BAD_REQUEST;

		return new ResponseEntity<RestMessage>(
				new RestErrorMessage(
						"Request malformat",
						"In campul "
								+ ((BindException) exception).getFieldError()
										.getField()
								+ " din obiectul request s-a specificat un obiect necorespunzator.",
						status), status);
	}

	/**
	 * 400 BAD REQUEST : Handle request parameter binding exceptions
	 */
	@ExceptionHandler(value = TypeMismatchException.class)
	public ResponseEntity<RestMessage> http400ExceptionHandler(
			Exception exception) {

		logger.debug(EXCEPTION_LOGGING_MESSAGE, exception);

		HttpStatus status = HttpStatus.BAD_REQUEST;

		return new ResponseEntity<RestMessage>(
				new RestErrorMessage(
						"In campul "
								+ ((TypeMismatchException) exception)
										.getPropertyName() + ".",
						HttpStatus.BAD_REQUEST.value()
								+ " parametru din request s-a introdus un tip de date necorespunzator.",
						status), status);
	}

	/**
	 * 500 INTERNAL SERVER ERROR
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<RestMessage> http500Handler(Exception exception) {

		logger.error(EXCEPTION_LOGGING_MESSAGE, exception);

		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

		return new ResponseEntity<RestMessage>(
				new RestErrorMessage(
						"Eroare interna de server",
						"Ne pare rau dar serverul a intampinat o eroare irecuperabila la procesarea requestului.",
						status), status);
	}

}