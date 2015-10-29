package ro.efidelity.model.domain.efidelity;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The persistent class for the persoana_fizica database table.
 * 
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "ecommerce:persoana_fizica")
@DiscriminatorValue("FIZICA")
@PrimaryKeyJoinColumn(name = "id_utilizator")
public class PersoanaFizica extends Utilizator implements Serializable {

	private static final long serialVersionUID = 1L;

	private String cnp;

	private String nume;

	private String prenume;

	public PersoanaFizica() {
	}

	public String getCnp() {
		return this.cnp;
	}

	public void setCnp(String cnp) {
		this.cnp = cnp;
	}

	public String getNume() {
		return this.nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public String getPrenume() {
		return this.prenume;
	}

	public void setPrenume(String prenume) {
		this.prenume = prenume;
	}

	public String getNumeSiPrenume() {
		return new StringBuilder(getNume()).append(" ").append(getPrenume())
				.toString();
	}

}