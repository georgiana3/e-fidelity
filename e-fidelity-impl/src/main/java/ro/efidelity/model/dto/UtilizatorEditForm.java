package ro.efidelity.model.dto;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class UtilizatorEditForm {

	public static final String[] CAMPURI_FIZICA = { "nume", "prenume", "cnp" };
	public static final String[] CAMPURI_JURIDICA = { "denumire", "contBancar",
			"cui" };
	
	Long idUtilizator;

	String username;

	@Pattern(regexp = "[0-9 +,]{6,64}", message="Numarul de telefon introdus este invalid. Pentru introducerea a multiple numere de telefon folositi separatorul virgula (,).")
	String telefon;

	@NotEmpty(message = "Trebuie sa completati judetul.")
	String judet;

	@NotEmpty(message = "Trebuie sa completati localitatea.")
	String localitate;

	@NotEmpty(message = "Trebuie sa completati adresa.")
	String adresa;

	String codPostal;
	
	String codIndrumare;

	public Long getIdUtilizator() {
		return idUtilizator;
	}

	public void setIdUtilizator(Long idUtilizator) {
		this.idUtilizator = idUtilizator;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getJudet() {
		return judet;
	}

	public void setJudet(String judet) {
		this.judet = judet;
	}

	public String getLocalitate() {
		return localitate;
	}

	public void setLocalitate(String localitate) {
		this.localitate = localitate;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}

	public String getCodIndrumare() {
		return codIndrumare;
	}

	public void setCodIndrumare(String codIndrumare) {
		this.codIndrumare = codIndrumare;
	}

	
}
