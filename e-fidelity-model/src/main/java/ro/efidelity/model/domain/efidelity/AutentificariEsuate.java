package ro.efidelity.model.domain.efidelity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the utentificari_esuate database table.
 * 
 */

@Entity
@Table(name = "ecommerce:autentificari_esuate")
public class AutentificariEsuate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ip")
	private String ip;

	@Column(name = "nr_incercari_esuate")
	private Integer nrIncercariEsuate;

	public AutentificariEsuate() {
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getNrIncercariEsuate() {
		return nrIncercariEsuate;
	}

	public void setNrIncercariEsuate(Integer nrIncercariEsuate) {
		this.nrIncercariEsuate = nrIncercariEsuate;
	}

	@Override
	public String toString() {
		return "AutentificariEsuate [ip=" + ip + ", nrIncercariEsuate="
				+ nrIncercariEsuate + "]";
	}

}