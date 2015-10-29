package ro.efidelity.model.dto;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class ModificareParolaForm {
	
	Long idUtilizator;
	
	@NotEmpty(message = "Trebuie sa completati parola veche.")
	String parolaVeche;
	
	@Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%_-]).{8,20})", message = "Parola trebuie sa contina: "
	+ "cel putin o cifra, o litera mica, "
	+ "o litera mare, un caracter special @ # $ % _ - "
	+ "si o dimensiune cuprinsa intre 8 si 20 de caractere.")
	@NotEmpty(message = "Trebuie sa completati parola.")
	String parola;
	
	@NotEmpty(message = "Trebuie sa reintroduceti parola pentru confirmare.")
	String confParola;
	
	
	
	public Long getIdUtilizator() {
		return idUtilizator;
	}



	public void setIdUtilizator(Long idUtilizator) {
		this.idUtilizator = idUtilizator;
	}



	public String getParolaVeche() {
		return parolaVeche;
	}



	public void setParolaVeche(String parolaVeche) {
		this.parolaVeche = parolaVeche;
	}



	public String getParola() {
		return parola;
	}



	public void setParola(String parola) {
		this.parola = parola;
	}



	public String getConfParola() {
		return confParola;
	}



	public void setConfParola(String confParola) {
		this.confParola = confParola;
	}



	public boolean isPasswordMatching(){
		if(parola.equals(confParola))
			return true;
		else
			return false;
	}
	

}
