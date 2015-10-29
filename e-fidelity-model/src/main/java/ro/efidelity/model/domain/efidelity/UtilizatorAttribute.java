package ro.efidelity.model.domain.efidelity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The persistent class for the rol database table.
 * 
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "ecommerce:utilizator_attributes")
public class UtilizatorAttribute implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_attribute")
	private Long idAttribute;

	private String attribute;

	private String value;

	@ManyToOne
	@JoinColumn(name = "id_utilizator")
	private Utilizator utilizator;

	public UtilizatorAttribute() {
	}

	public Long getIdAttribute() {
		return idAttribute;
	}

	public void setIdAttribute(Long idAttribute) {
		this.idAttribute = idAttribute;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Utilizator getUtilizator() {
		return utilizator;
	}

	public void setUtilizator(Utilizator utilizator) {
		this.utilizator = utilizator;
	}

	@Override
	public String toString() {
		return "UtilizatorAttribute [idAttribute=" + idAttribute
				+ ", attribute=" + attribute + ", value=" + value
				+ ", idUtilizator="
				+ ((utilizator == null) ? null : utilizator.getIdUtilizator())
				+ "]";
	}
}