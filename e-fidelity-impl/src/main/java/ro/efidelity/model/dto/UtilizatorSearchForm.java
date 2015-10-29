package ro.efidelity.model.dto;

import org.apache.commons.lang3.StringUtils;

public class UtilizatorSearchForm {

	private String username;

	private String tipUtilizator;

	private String nume;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTipUtilizator() {
		return tipUtilizator;
	}

	public void setTipUtilizator(String tipUtilizator) {
		this.tipUtilizator = tipUtilizator;
	}

	public boolean isFilled() {
		if (StringUtils.isNotBlank(username)
				|| StringUtils.isNotBlank(tipUtilizator)
				|| StringUtils.isNotBlank(nume))
			return true;
		return false;
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

}
