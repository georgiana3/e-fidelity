package ro.efidelity.util.restmessage;

import org.springframework.http.HttpStatus;

public class RestErrorMessage extends RestMessage {

	private String status;
	private String code;

	public RestErrorMessage(String title, HttpStatus status) {
		super(title);
		this.status = String.valueOf(status.value());
	}

	public RestErrorMessage(String title, String detail, HttpStatus status) {
		super(title, detail);
		this.status = String.valueOf(status.value());
	}

	public RestErrorMessage(String title, String detail, HttpStatus status,
			String code) {
		super(title, detail);
		this.status = String.valueOf(status.value());
		this.code = code;
	}

	public RestErrorMessage(String title, HttpStatus status, String code) {
		super(title);
		this.status = String.valueOf(status.value());
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
