package ro.efidelity.service.business;

import java.math.BigDecimal;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public final class Constants {

	public static final String ADRESA_TT2 = "http://10.11.0.165:8080/tt2/";

	/* Constante adevarat / fals */
	public static final String[] trueValues = new String[] { "TRUE", "DA", "D",
			"T", "1", "X" };
	public static final String[] falseValues = new String[] { "FALSE", "NU",
			"N", "0" };

	/* Constante format data */
	public static final DateTimeFormatter dateFormatterRO = DateTimeFormat
			.forPattern("dd.MM.yyyy");
	public static final DateTimeFormatter dateTimeFormatterRO = DateTimeFormat
			.forPattern("dd.MM.yyyy h:mm:ss aa");
	public static final DateTimeFormatter dateTimeFormatter24HRO = DateTimeFormat
			.forPattern("dd.MM.yyyy HH:mm");

	public static final DateTimeFormatter dateTimeFormatterROWithSlash = DateTimeFormat
			.forPattern("MM/dd/yyyy h:mm:ss aa");

	/* Prioripost Hardcodings */
	public static final char ID_REGIUNE_DEFAULT = '0';
	public static final String TERMINATIE_DEFAULT = ""; // Trimiteri interne

	/* Bakker Hardcodings */
	public static final String UATTR_BAKKER_COD_COLECTARE = "bakker_cod_colectare";

	/* Realocare codururi */

	public static final String UATTR_REALOCARE_CODURI = "realocare_coduri";

	public static final String USERNAME_EMAG = "danteinternational";

	/* Other attributes */
	// public static final String UATTR_POSTA_ID_UP = "posta_id_up";
	// public static final String UATTR_POSTA_COD_OFICIU = "posta_cod_oficiu";
	public static final String UATTR_POSTA_SERVER = "posta_server";

	/* DFMT hardcodings */
	public static final String CONT_PREZENTARE_DFMT = "posta_timisoara1";
	public static final String CONT_PREZENTARE_DFMA = "posta_arad1";

	/* Setari tarife */

	public static final BigDecimal tarifMandatSimplu = new BigDecimal("2.50");
	public static final BigDecimal tarifEMandat = new BigDecimal("5.00");

	public static final int contractBasicThreshold = 10;
	public static final BigDecimal minValoareDeclarata = new BigDecimal("20.00");

	/*
	 * Setari coduri (CodTrimitere) pentru fiecare tip de trimitere.
	 */

	/* Constante header CSV */
	public static final String[] csvColumns = new String[] { "NumeExpeditor",
			"AdresaExpeditor", "JudetExpeditor", "LocalitateExpeditor",
			"CodPostalExpeditor", "TelefonExpeditor", "EmailExpeditor",
			"PersoanaDeContact", "NumeDestinatar", "AdresaDestinatar",
			"JudetDestinatar", "LocalitateDestinatar", "CodPostalDestinatar",
			"TelefonDestinatar", "EmailDestinatar", "IntervalOrarPredare",
			"CodTrimitere", "CuContract", "GreutateTrimitere", "Ramburs",
			"Valoare", "Continut", "Factaj", "AvizareTelefonica", "Fragil",
			"Voluminos", "Lungime", "Latime", "Inaltime", "ConfirmarePrimire",
			"ManaProprie", "PostRestant", "ConfirmarePrimirePostRestant",
			"Prioritar", "Recomandat", "Ec", "Pcp", "Retur",
			"ModalitateDePlata", "PlataTaxelor" };

	public static final String[] campuriObligatorii = new String[] {
			"NumeDestinatar", "AdresaDestinatar", "JudetDestinatar",
			"LocalitateDestinatar", "CodPostalDestinatar", "CodTrimitere",
			"GreutateTrimitere" };

	private Constants() {
	}

}
