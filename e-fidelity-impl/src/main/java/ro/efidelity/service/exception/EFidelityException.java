package ro.efidelity.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceptie business de baza pentru E-Fidelity.
 * Imperativ necesita un mesaj de eroare printabil (in limba romana).
 * 
 * @author andrei.pietrusel
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class EFidelityException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public EFidelityException() {
	}

	public EFidelityException(String message) {
		super(message);
	}

	public EFidelityException(Throwable e) {
		super(e);
	}

	public EFidelityException(String msg, Throwable e) {
		super(msg, e);
	}
	
}
