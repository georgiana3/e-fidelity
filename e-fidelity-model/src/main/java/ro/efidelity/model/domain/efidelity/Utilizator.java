package ro.efidelity.model.domain.efidelity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The persistent class for the utilizator database table.
 * 
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "ecommerce:utilizator")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tip_persoana", discriminatorType = DiscriminatorType.STRING, length = 16)
public abstract class Utilizator implements Serializable {

	private static final long serialVersionUID = 1L;

	public static enum UtilizatorType {
		FIZICA, JURIDICA
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_utilizator")
	private Long idUtilizator;

	private String adresa;

	@Column(name = "cod_postal")
	private String codPostal;

	private String confirmat;

	private String email;

	@Column(name = "judet")
	private String judet;

	@Column(name = "localitate")
	private String localitate;

	private String parola;

	private String telefon;

	@Column(name = "tip_persoana", insertable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private UtilizatorType tipUtilizator;

	private String username;

	private Boolean valid;

	@Transient
	private String bancaCont;

	// bi-directional many-to-one association to Rol
	@ManyToOne
	@JoinColumn(name = "id_rol")
	private Rol rol;

	// @JsonIgnore
	// @OneToMany(mappedBy = "utilizator")
	// private List<IstoricAwb> istoricAwb;

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@OneToMany(mappedBy = "utilizator", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<UtilizatorAttribute> attributes;

	@Column(name = "nume_search_temp")
	private String numeSearchTemp;

	/* Attribute checks */
	/**
	 * @return returneaza true daca utilizatorul are un anumit atribut
	 */
	public boolean hasAttribute(String attributeName) {
		if (CollectionUtils.isNotEmpty(attributes))
			for (UtilizatorAttribute at : attributes) {
				if (at.getAttribute().trim().toLowerCase()
						.equals(attributeName.trim().toLowerCase()))
					return true;
			}
		return false;
	}

	/**
	 * @return returneaza true daca utilizatorul are un anumit atribut cu cu o
	 *         anumita valoare
	 */
	public boolean hasAttributeWithValue(String attributeName,
			String attributeValue) {

		String value = getAttributeValue(attributeName);
		if (value != null
				&& value.trim().toLowerCase()
						.equals(attributeValue.trim().toLowerCase()))
			return true;

		return false;
	}

	/**
	 * @return returneaza true daca utilizatorul are un anumit atribut cu cu o
	 *         anumita valoare dintr-o insiruire
	 */
	public boolean hasAttributeWithValues(String attributeName,
			String... attributeValues) {

		for (String attributeValue : attributeValues)
			if (hasAttributeWithValue(attributeName, attributeValue) == true)
				return true;

		return false;
	}

	/**
	 * @return returneaza valoarea unui atribut al utilizatorului daca acesta
	 *         exista, sau null daca nu
	 */
	public String getAttributeValue(String attributeName) {
		if (CollectionUtils.isNotEmpty(attributes))
			for (UtilizatorAttribute at : attributes) {
				if (at.getAttribute().trim().toLowerCase()
						.equals(attributeName.trim().toLowerCase()))
					return at.getValue();
			}
		return null;
	}

	/**
	 * @return returneaza valoarea unui atribut al utilizatorului daca acesta
	 *         exista, sau null daca nu
	 */
	public UtilizatorAttribute getAttributeByName(String attributeName) {
		if (CollectionUtils.isNotEmpty(attributes))
			for (UtilizatorAttribute at : attributes) {
				if (at.getAttribute().trim().toLowerCase()
						.equals(attributeName.trim().toLowerCase()))
					return at;
			}
		return null;
	}

	public Utilizator() {
	}

	/* Getteri si Setteri speciali */

	public String getJudet() {
		return WordUtils
				.capitalizeFully(StringUtils.trim(this.judet), ' ', '-');
	}

	public String getLocalitate() {
		return WordUtils.capitalizeFully(StringUtils.trim(this.localitate),
				' ', '-');
	}

	/* Getteri si Setteri */

	public Long getIdUtilizator() {
		return this.idUtilizator;
	}

	public void setIdUtilizator(Long idUtilizator) {
		this.idUtilizator = idUtilizator;
	}

	public String getAdresa() {
		return this.adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getCodPostal() {
		return this.codPostal;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}

	public String getConfirmat() {
		return this.confirmat;
	}

	public void setConfirmat(String confirmat) {
		this.confirmat = confirmat;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setJudet(String judet) {
		this.judet = judet;
	}

	public void setLocalitate(String localitate) {
		this.localitate = localitate;
	}

	public String getParola() {
		return this.parola;
	}

	public void setParola(String parola) {
		this.parola = parola;
	}

	public String getTelefon() {
		return this.telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getValid() {
		return this.valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public UtilizatorType getTipUtilizator() {
		return tipUtilizator;
	}

	public void setTipUtilizator(UtilizatorType tipUtilizator) {
		this.tipUtilizator = tipUtilizator;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public List<UtilizatorAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<UtilizatorAttribute> attributes) {
		this.attributes = attributes;
	}

	public String getBancaCont() {
		return bancaCont;
	}

	public void setBancaCont(String bancaCont) {
		this.bancaCont = bancaCont;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idUtilizator == null) ? 0 : idUtilizator.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Utilizator other = (Utilizator) obj;
		if (idUtilizator == null) {
			if (other.idUtilizator != null)
				return false;
		} else if (!idUtilizator.equals(other.idUtilizator))
			return false;
		return true;
	}

	public String getNumeSearchTemp() {
		return numeSearchTemp;
	}

	public void setNumeSearchTemp(String numeSearchTemp) {
		this.numeSearchTemp = numeSearchTemp;
	}

}