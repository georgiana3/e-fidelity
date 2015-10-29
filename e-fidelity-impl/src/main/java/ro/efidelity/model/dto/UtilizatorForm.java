package ro.efidelity.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import ro.efidelity.model.domain.efidelity.Utilizator.UtilizatorType;

public class UtilizatorForm {

	public static final String[] CAMPURI_FIZICA = { "nume", "prenume", "cnp" };
	public static final String[] CAMPURI_JURIDICA = { "denumire", "contBancar",
			"cui","banca","sucursala" };
	public static final String[] CAMPURI_NEEDITABILE = { "parola",
			"confParola", "tipPersoana", "email" };

	Long idUtilizator;

	@NotEmpty(message = "Trebuie sa completati contul (username).")
	String username;

    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%_-]).{8,20})", message = "Parola trebuie sa contina: "
            + "cel putin o cifra, o litera mica, "
            + "o litera mare, un caracter special @ # $ % _ - "
            + "si o dimensiune cuprinsa intre 8 si 20 de caractere.")
    @NotEmpty(message = "Trebuie sa completati parola.")
    String parola;

	@NotEmpty(message = "Trebuie sa reintroduceti parola pentru confirmare.")
	String confParola;

	@NotEmpty(message = "Trebuie sa introduceti o adresa de mail valida.")
	@Email(message = "Adresa de mail nu e valida.")
	String email;

	Long idRol;

	String telefon;

	@NotNull(message = "Trebuie sa completati tipul persoanei.")
	UtilizatorType tipPersoana;

	MultipartFile sigla;

	@NotEmpty(message = "Trebuie sa completati numele.")
	String nume;

	@NotEmpty(message = "Trebuie sa completati prenumele.")
	String prenume;

	// @NotEmpty(message = "Trebuie sa completati CUI.")
	String cui;

	// @NotEmpty(message = "Trebuie sa completati contul bancar.")
	// @Pattern(regexp = "[a-zA-Z]{2}[0-9]{2}[a-zA-Z0-9]{4}[0-9]{7}([a-zA-Z0-9]?){0,16}", message = "Contul bancar este invalid.")
	String contBancar;
	
	String banca;
	
	String sucursala;

	@NotEmpty(message = "Trebuie sa completati denumirea.")
	String denumire;

	@NotEmpty(message = "Trebuie sa completati judetul.")
	String judet;

	@NotEmpty(message = "Trebuie sa completati localitatea.")
	String localitate;

	@NotEmpty(message = "Trebuie sa completati adresa.")
	String adresa;

//	@NotEmpty(message = "Trebuie sa completati codul postal.")
	String codPostal;

	// @NotEmpty(message = "Trebuie sa completati CNP-ul.")
	String cnp;

	public String getCui() {
		return cui;
	}

	public void setCui(String cui) {
		this.cui = cui;
	}

	public String getContBancar() {
		return contBancar;
	}

	public void setContBancar(String contBancar) {
		this.contBancar = contBancar;
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

	public String getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}

	public String getCnp() {
		return cnp;
	}

	public void setCnp(String cnp) {
		this.cnp = cnp;
	}

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

	public String getParola() {
		return parola;
	}

	public void setParola(String parola) {
		this.parola = parola;
	}

	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public MultipartFile getSigla() {
		return sigla;
	}

	public void setSigla(MultipartFile sigla) {
		this.sigla = sigla;
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public String getPrenume() {
		return prenume;
	}

	public void setPrenume(String prenume) {
		this.prenume = prenume;
	}

	public String getConfParola() {
		return confParola;
	}

	public void setConfParola(String confParola) {
		this.confParola = confParola;
	}

	public String getDenumire() {
		return denumire;
	}

	public void setDenumire(String denumire) {
		this.denumire = denumire;
	}

	public UtilizatorType getTipPersoana() {
		return tipPersoana;
	}

	public void setTipPersoana(UtilizatorType tipPersoana) {
		this.tipPersoana = tipPersoana;
	}

	public String getBanca() {
		return banca;
	}

	public void setBanca(String banca) {
		this.banca = banca;
	}

	public String getSucursala() {
		return sucursala;
	}

	public void setSucursala(String sucursala) {
		this.sucursala = sucursala;
	}

	@Override
	public String toString() {
		return "UtilizatorForm [idUtilizator=" + idUtilizator + ", username="
				+ username + ", parola=" + parola + ", confParola="
				+ confParola + ", email=" + email + ", idRol=" + idRol
				+ ", telefon=" + telefon + ", tipPersoana=" + tipPersoana
				+ ", sigla=" + sigla + ", nume=" + nume + ", prenume="
				+ prenume + ", cui=" + cui + ", contBancar=" + contBancar
				+ ", denumire=" + denumire + ", judet=" + judet
				+ ", localitate=" + localitate + ", adresa=" + adresa
				+ ", codPostal=" + codPostal + ", cnp=" + cnp + "]";
	}

	public boolean isPasswordMatching() {
		if (parola.equals(confParola))
			return true;
		else
			return false;

	}

}
