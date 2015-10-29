package ro.efidelity.model.domain.efidelity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The persistent class for the persoana_juridica database table.
 * 
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "ecommerce:persoana_juridica")
@DiscriminatorValue("JURIDICA")
@PrimaryKeyJoinColumn(name = "id_utilizator")
public class PersoanaJuridica extends Utilizator implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "cont_bancar")
	private String contBancar;

	private String cui;

	private String denumire;

	private String sucursala;

	public String getSucursala() {
		return sucursala;
	}

	public void setSucursala(String sucursala) {
		this.sucursala = sucursala;
	}

	public PersoanaJuridica() {

	}

	public String getContBancar() {
		return this.contBancar;
	}

	public void setContBancar(String contBancar) {
		this.contBancar = contBancar;
	}

	public String getCui() {
		return this.cui;
	}

	public void setCui(String cui) {
		this.cui = cui;
	}

	public String getDenumire() {
		return this.denumire;
	}

	public void setDenumire(String denumire) {
		this.denumire = denumire;
	}

}