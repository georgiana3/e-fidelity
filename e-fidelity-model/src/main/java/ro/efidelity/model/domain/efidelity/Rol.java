package ro.efidelity.model.domain.efidelity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the rol database table.
 * 
 */
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "ecommerce:rol")
public class Rol implements Serializable {

	public static String ROLE_POSTA = "POSTA";
	public static String ROLE_POSTA_OFICIU = "POSTA_OFICIU";

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_rol")
	private Long idRol;

	private String denumire;

	// bi-directional many-to-one association to Utilizator
	@JsonIgnore
	@OneToMany(mappedBy = "rol")
	private List<Utilizator> utilizatori;

	public Rol() {
	}

	public Long getIdRol() {
		return this.idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	public String getDenumire() {
		return this.denumire;
	}

	public void setDenumire(String denumire) {
		this.denumire = denumire;
	}

	public List<Utilizator> getUtilizatori() {
		return this.utilizatori;
	}

	public void setUtilizatori(List<Utilizator> utilizatori) {
		this.utilizatori = utilizatori;
	}

	public Utilizator addUtilizatori(Utilizator utilizatori) {
		getUtilizatori().add(utilizatori);
		utilizatori.setRol(this);

		return utilizatori;
	}

	public Utilizator removeUtilizatori(Utilizator utilizatori) {
		getUtilizatori().remove(utilizatori);
		utilizatori.setRol(null);

		return utilizatori;
	}

}