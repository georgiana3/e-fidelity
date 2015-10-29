package ro.efidelity.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import ro.efidelity.service.business.Constants;
import ro.efidelity.service.exception.EFidelityException;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

public final class ValidationHelper {

	public static <T> Collection<FieldError> filterFields(Errors br,
			String... filterFields) {

		final List<String> filters = Arrays.asList(filterFields);

		Collection<FieldError> filteredList = Collections2.filter(
				br.getFieldErrors(), new Predicate<FieldError>() {
					public boolean apply(FieldError d) {
						boolean answer = filters.contains(d.getField());
						return !answer;
					}
				});

		return filteredList;
	}

	// public static <T> Collection<FieldError> filterFields(
	// Set<ConstraintViolation<AWBApiForm>> br, String... filterFields) {
	//
	// final List<String> filters = Arrays.asList(filterFields);
	//
	// for (ConstraintViolation<AWBApiForm> cv : br) {
	// cv.getPropertyPath();
	// }
	//
	// return filteredList;
	// }

	/**
	 * Verifica valoare declarata daca e specificat ramburs.
	 */
	public static void checkValoareIfRamburs(BigDecimal valoareDeclarata,
			BigDecimal rambursEfectiv) {
		BigDecimal ramburs = rambursEfectiv != null ? rambursEfectiv
				: BigDecimal.ZERO;
		BigDecimal valoare = valoareDeclarata != null ? valoareDeclarata
				: BigDecimal.ZERO;
		if (ramburs.compareTo(BigDecimal.ZERO) > 0
				&& !(valoare.compareTo(Constants.minValoareDeclarata) >= 0)) {
			throw new EFidelityException("Valoarea trebuie sa fie minim "
					+ Constants.minValoareDeclarata
					+ " daca se trimite cu ramburs.");
		}
	}

	public static void checkSumaAsigurata(BigDecimal valoareDeclarata,
			BigDecimal limitaValoare) {
		BigDecimal valoare = valoareDeclarata != null ? valoareDeclarata
				: BigDecimal.ZERO;
		BigDecimal limita = limitaValoare != null ? limitaValoare
				: BigDecimal.ZERO;
		if (valoare.compareTo(BigDecimal.ZERO) > 0) {
			if (limita == BigDecimal.ZERO) {
				throw new EFidelityException(
						"Aceasta tara nu asigura valoarea declarata.");
			}
			if (valoare.compareTo(limita) >= 0) {
				throw new EFidelityException(
						"Valoarea asigurata trebuie sa fie maxim " + limita
								+ ".");
			}
		}
	}

	public static void checkDimensiuni(BigDecimal lungimeDeclarata,
			BigDecimal latimeDeclarata, BigDecimal lungimeMaxima,
			BigDecimal latimeMaxima) {
		BigDecimal latime = latimeDeclarata != null ? latimeDeclarata
				: BigDecimal.ZERO;
		BigDecimal lungime = lungimeDeclarata != null ? lungimeDeclarata
				: BigDecimal.ZERO;
		BigDecimal latimeAcceptata = latimeMaxima != null ? latimeMaxima
				: BigDecimal.ZERO;
		BigDecimal lungimeAcceptata = lungimeMaxima != null ? lungimeMaxima
				: BigDecimal.ZERO;
		System.out.println("#####" + latime + lungime);
		if (latime.compareTo(BigDecimal.ZERO) > 0
				|| lungime.compareTo(BigDecimal.ZERO) > 0) {
			if (lungime.compareTo(lungimeAcceptata) > 0) {
				System.out.println("lungime: " + lungime
						+ " lungimeacceptata: " + lungimeAcceptata);
				throw new EFidelityException(
						"Lungimea maxima acceptata de aceasta tara este de "
								+ lungimeAcceptata + "m.");
			}
			if (latime.compareTo(latimeAcceptata) > 0) {
				System.out.println("latime: " + latime + " latimeacceptata: "
						+ latimeAcceptata);
				throw new EFidelityException(
						"Latimea maxima acceptata de aceasta tara este de "
								+ latimeAcceptata + "m.");
			}
		}
	}

	public static void checkDimensiuniVoluminos(BigDecimal lungimeDeclarata,
			BigDecimal latimeDeclarata, BigDecimal inaltimeDeclarata,
			BigDecimal dimentsiuneA, BigDecimal dimentsiuneB) {
		BigDecimal latime = latimeDeclarata != null ? latimeDeclarata
				: BigDecimal.ZERO;
		BigDecimal lungime = lungimeDeclarata != null ? lungimeDeclarata
				: BigDecimal.ZERO;
		BigDecimal inaltime = inaltimeDeclarata != null ? inaltimeDeclarata
				: BigDecimal.ZERO;
		BigDecimal laturaAcceptata = dimentsiuneA != null ? dimentsiuneA
				: BigDecimal.ZERO;
		BigDecimal volumAcceptat = dimentsiuneB != null ? dimentsiuneB
				: BigDecimal.ZERO;
		/*
		 * BigDecimal temp = BigDecimal.ZERO; if
		 * (latime.compareTo(BigDecimal.ZERO) > 0 ||
		 * lungime.compareTo(BigDecimal.ZERO) > 0) { if
		 * (latime.compareTo(lungime) > 0) { temp = lungime; lungime = latime;
		 * latime = temp; } if (inaltime.compareTo(lungime) > 0) { temp =
		 * lungime; lungime = inaltime; inaltime = temp; }
		 */
		if (latime.compareTo(laturaAcceptata) > 0
				|| lungime.compareTo(laturaAcceptata) > 0
				|| inaltime.compareTo(laturaAcceptata) > 0) {
			throw new EFidelityException(
					"Latura maxima acceptata de aceasta tara este de "
							+ laturaAcceptata + "m.");
		}
		BigDecimal volum = inaltime.multiply(latime).multiply(lungime);
		if (volum.compareTo(volumAcceptat) > 0) {
			throw new EFidelityException(
					"Volumul maxim acceptat de aceasta tara este de "
							+ volumAcceptat + "m.");
		}
	}
}
