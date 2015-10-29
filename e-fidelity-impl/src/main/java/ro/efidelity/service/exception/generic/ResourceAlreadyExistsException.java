package ro.efidelity.service.exception.generic;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ResourceAlreadyExistsException(String msg, Throwable e) {
		super(msg, e);
	}

	public ResourceAlreadyExistsException(Throwable e) {
		super(e);
	}

	public ResourceAlreadyExistsException(String e) {
		super(e);
	}

	public ResourceAlreadyExistsException() {
		super("Resursa exista deja");
	}

}
