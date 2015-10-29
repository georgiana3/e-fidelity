package ro.efidelity.util.business;

import ro.efidelity.model.domain.efidelity.PersoanaFizica;
import ro.efidelity.model.domain.efidelity.PersoanaJuridica;
import ro.efidelity.model.domain.efidelity.Utilizator;

public class UsernameUtil {

	public static String getFullName(Utilizator utilizator) {
		if (utilizator instanceof PersoanaJuridica) {
			PersoanaJuridica pj = (PersoanaJuridica) utilizator;
			return pj.getDenumire();
		} else if (utilizator instanceof PersoanaFizica) {
			PersoanaFizica pf = (PersoanaFizica) utilizator;
			return new StringBuilder(pf.getNume()).append(" ")
					.append(pf.getPrenume()).toString();
		} else
			throw new RuntimeException("Utilizator invalid.");

	}

}
