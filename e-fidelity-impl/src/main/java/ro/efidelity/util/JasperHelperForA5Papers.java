//package ro.efidelity.util;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.Map;
//
//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
//
//import org.apache.commons.lang.LocaleUtils;
//
//import ro.efidelity.model.domain.efidelity.PersoanaFizica;
//import ro.efidelity.model.domain.efidelity.PersoanaJuridica;
//import ro.efidelity.model.domain.efidelity.Utilizator;
//
//import com.ibm.icu.text.RuleBasedNumberFormat;
//
///**
// * @deprecated Use {@link JasperReportsHelper} instead.
// * @author Georgiana Voicea
// */
//public class JasperHelperForA5Papers {
//
//	private static final String IMG_LOGO_POSTA = "jasperreports/resources/logo_posta.png";
//	private static final String IMG_LOGO_PRIORI = "jasperreports/resources/logo_prioripost.png";
//	private static final String IMG_CHECKED = "jasperreports/resources/check.png";
//	private static final String IMG_UNCHECKED = "jasperreports/resources/uncheck.png";
//	private static final String IMG_SCISSORS = "jasperreports/resources/scissors.png";
//	private static final String IMG_LOGO_POSTA_MIC = "jasperreports/resources/sigla_mic.png";
//	private static final String CONFIRMARE_PRIMIRE = "jasperreports/resources/CONFIRMARE.png";
//	private static final String STAMPILA = "jasperreports/resources/stampila.jpg";
//	private static final String STAMPILA_VERSO = "jasperreports/resources/stampila_verso.jpg";
//	private static final String ELIPSE = "jasperreports/resources/elipse.jpg";
//	private static final String CIRCLE = "jasperreports/resources/circle.png";
//	private static final String SCISSORS_ROTATE = "jasperreports/resources/scissorsRotate.png";
//
//	public static JasperPrint generateAwbFata(Awb awb, JasperReport awb_fata,
//			Utilizator user) throws JRException {
//
//		Map<String, Object> m = new HashMap<String, Object>();
//
//		m.put("format", "pdf");
//		m.put("IMG_LOGO_POSTA", IMG_LOGO_POSTA);
//		m.put("IMG_LOGO_PRIORI", IMG_LOGO_PRIORI);
//		m.put("IMG_CHECKED", IMG_CHECKED);
//		m.put("IMG_UNCHECKED", IMG_UNCHECKED);
//		m.put("IMG_SCISSORS", IMG_SCISSORS);
//		m.put("COD_AWB", awb.getCodAwb());
//
//		/*
//		 * Detaliile despre EXPEDITOR
//		 */
//		m.put("NUME_EXP", awb.getNumeExpeditor());
//		m.put("ADRESA_EXP", awb.getAdresaExpeditor());
//		m.put("LOC_EXP", awb.getLocalitateExpeditor());
//		m.put("JUDET_EXP", awb.getJudetExpeditor());
//		m.put("TEL_EXP", awb.getTelefonExpeditor());
//		m.put("MAIL_EXP", awb.getEmailExpeditor());
//		m.put("NUME_PERS_CONTACT", awb.getPersoanaDeContact());
//		/*
//		 * Detaliile despre DESTINATAR
//		 */
//		m.put("NUME_DESTINATAR", awb.getNumeDestinatar());
//		m.put("ADRESA_DESTINATAR", awb.getAdresaDestinatar());
//		m.put("LOC_DESTINATAR", awb.getLocalitateDestinatar());
//		m.put("JUDET_DESTINATAR", awb.getJudetDestinatar());
//		m.put("TEL_DESTINATAR", awb.getTelefonDestinatar());
//		m.put("MAIL_DESTINATAR", awb.getEmailDestinatar());
//		m.put("OFICIU_DISTRIBUIRE", awb.getOficiuDistribuire());
//
//		/* Detalii trimitere */
//
//		BigDecimal ramburs = awb.getRamburs();
//		Boolean checkRamburs = (ramburs == null
//				|| (ramburs.compareTo(BigDecimal.ZERO) == 0) ? false : true);
//
//		m.put("RAMBURS", ramburs);
//		m.put("CHECK_RAMBURS", checkRamburs);
//
//		BigDecimal valoare = awb.getValoare();
//		Boolean checkValoare = (valoare == null
//				|| (valoare.compareTo(BigDecimal.ZERO) == 0) ? false : true);
//
//		m.put("VALOARE_ASIG", valoare);
//
//		m.put("CHECK_VALOARE", checkValoare);
//
//		m.put("GREUTATE", awb.getGreutateTrimitere());
//
//		/* TODO: Harcoding VOLKSBANK */
//		if (awb.getUtilizator().hasAttributeWithValues(
//				VolksbankConstants.UATTR_VOLKSBANK,
//				VolksbankConstants.UVALUE_MASTER,
//				VolksbankConstants.UVALUE_SUCURSALA)||awb.getUtilizator().getRol().getDenumire()=="CEC") {
//			m.put("SUMA", null);
//		} else {
//			m.put("SUMA", awb.getSumaPlataCuTva());
//		}
//		m.put("CHECK_FRAGIL", awb.getFragil());
//		m.put("CHECK_VOLUMINOS", awb.getVoluminos());
//
//		String codClient = null;
//		if (awb.getRamburs() != null
//				&& awb.getRamburs().compareTo(BigDecimal.ZERO) > 0
//				&& awb.getObiectContract() != null
//				&& (codClient = awb.getObiectContract().getCodClient()) != null) {
//			String codColectare = awb.getCodAwb() + codClient
//					+ ((ramburs != null) ? ramburs.toString() : "0.00");
//			m.put("COD_COLECTARE", codColectare);
//		}
//
//		/* checkbox-uri pentru tipul expeditiei, momentan avem doar colet */
//
//		m.put("CHECK_MIXTA", false);
//
//		m.put("CONTINUT", awb.getContinut());
//		m.put("CHECK_ARPR", awb.getConfirmarePrimirePostRestant());
//
//		m.put("CHECK_RECOMANDAT", awb.getRecomandat());
//		m.put("CHECK_AR", awb.getConfirmarePrimire());
//		m.put("CHECK_EC", awb.getEc());
//		m.put("CHECK_PCP", awb.getPcp());
//		m.put("CHECK_MP", awb.getManaProprie());
//		m.put("CHECK_RETUR", awb.getRetur());
//		m.put("CHECK_AVIZ_TEL", awb.getAvizareTelefonica());
//		m.put("CHECK_PRIORITAR", awb.getPrioritar());
//		m.put("CHECK_POST_RESTANT", awb.getPostRestant());
//
////		if (user instanceof PersoanaJuridica) {
////			byte[] siglaBytes = ((PersoanaJuridica) user).getSigla();
////			if (siglaBytes != null) {
////				@SuppressWarnings("unused")
////				BufferedImage sigla = null;
////				try {
////					sigla = ImageIO.read(new ByteArrayInputStream(siglaBytes));
////				} catch (IOException | IllegalArgumentException e) {
////					// Silently ignore
////				}
////			}
////		}
//
//		Boolean checkPlic = false;
//		Boolean checkColet = false;
//		TipTrimitereType t = getTipTrimitereAwb(awb, user);
//		if (t.equals(TipTrimitereType.COLET)) {
//			checkColet = true;
//			checkPlic = false;
//		} else if (t.equals(TipTrimitereType.PLIC)) {
//			checkColet = false;
//			checkPlic = true;
//		}
//		m.put("CHECK_PLIC", checkPlic);
//		m.put("CHECK_COLET", checkColet);
//
//		// TODO: Volksbank Harcoding
//		if (VolksbankHelper.isContVolksbank(user)) {
//			m.put("CHECK_RETUR", true);
//		}
//		if (VolksbankHelper.isPaynetVolksbank(user)) {
//			m.put("CHECK_DEST", true);
//		} else {
//			m.put("CHECK_EXP", true);
//		}
//
//		// PlataTaxelorType plataTaxelor = awb.getPlataTaxelor();
//		// if (plataTaxelor != null) {
//		// m.put("PLATA_TAXELOR", plataTaxelor.getDescriere());
//		// } else {
//		// m.put("PLATA_TAXELOR", plataTaxelor);
//		// }
//		//
//		// ModalitatePlataType modPlata = awb.getModalitateDePlata();
//		// if (modPlata != null) {
//		// m.put("MODALITATE_PLATA", modPlata.getDescriere());
//		// } else {
//		// m.put("MODALITATE_PLATA", modPlata);
//		// }
//
//		return JasperFillManager.fillReport(awb_fata, m);
//
//	}
//
//	public static JasperPrint generateAwbVerso(Awb awb, JasperReport awb_verso)
//			throws JRException {
//
//		Map<String, Object> m = new HashMap<String, Object>();
//		m.put("format", "pdf");
//		return JasperFillManager.fillReport(awb_verso, m);
//
//	}
//
//	public static JasperPrint generateMandatPersoanaJuridica(Awb awb,
//			JasperReport report, Utilizator user) throws JRException {
//
//		Map<String, Object> m = new HashMap<String, Object>();
//
//		m.put("format", "pdf");
//		m.put("IMG_LOGO_POSTA", IMG_LOGO_POSTA);
//		m.put("IMG_SCISSORS", IMG_SCISSORS);
//		m.put("SCISSORS_ROTATE", SCISSORS_ROTATE);
//		m.put("ELIPSE", ELIPSE);
//		m.put("CIRCLE", CIRCLE);
//
//		m.put("COD_AWB", awb.getCodAwb());
//
//		/* Detaliile despre DESTINATAR */
//		m.put("NUME_DEST", awb.getNumeExpeditor());
//		m.put("ADRESA_DEST", awb.getAdresaExpeditor());
//		m.put("LOC_DEST", awb.getLocalitateExpeditor());
//		m.put("JUDET_DEST", awb.getJudetExpeditor());
//		m.put("COD_DEST", awb.getCodPostalExpeditor());
//		m.put("TEL_DEST", awb.getTelefonExpeditor());
//
//		/* Detaliile despre EXPEDITOR */
//		m.put("NUME_EXP", awb.getNumeDestinatar());
//		m.put("ADRESA_EXP", awb.getAdresaDestinatar());
//		m.put("LOC_EXP", awb.getLocalitateDestinatar());
//		m.put("COD_EXP", awb.getCodPostalDestinatar());
//		m.put("TEL_EXP", awb.getTelefonDestinatar());
//
//		/* Detalii trimitere */
//		BigDecimal ramburs = awb.getRamburs();
//		m.put("RAMBURS", ramburs);
//
//		RuleBasedNumberFormat format = new RuleBasedNumberFormat(
//				LocaleUtils.toLocale("ro_RO"), RuleBasedNumberFormat.SPELLOUT);
//		String ramburs_litere = null;
//		if (ramburs != null) {
//			String r = ramburs.toString();
//			String bani = r.substring(r.indexOf('.') + 1);
//			Integer lei = Integer.parseInt(r.substring(0, r.indexOf('.')));
//			String ramburs_total = LocaleHelper.toRomanianNoDiacritics(format
//					.format(lei));
//			ramburs_litere = ramburs_total + " de lei si " + bani + " bani";
//		}
//
//		m.put("RAMBURS_LITERE", ramburs_litere);
//		m.put("CONTINUT", awb.getContinut());
//		m.put("CUI_DEST", ((PersoanaJuridica) awb.getUtilizator()).getCui());
//
//		/* gasirea unui cod bic din contul bancar */
//		m.put("CONT_BANCAR_DEST",
//				((PersoanaJuridica) awb.getUtilizator()).getContBancar());
//		m.put("BANCA_CONT_DEST", awb.getUtilizator().getBancaCont());
//
//		return JasperFillManager.fillReport(report, m);
//
//	}
//
//	public static JasperPrint generateMandatPersoanaJuridicaVerso(Awb awb,
//			JasperReport report, Utilizator user) throws JRException {
//
//		Map<String, Object> m = new HashMap<String, Object>();
//
//		m.put("format", "pdf");
//		m.put("IMG_LOGO_POSTA", IMG_LOGO_POSTA);
//		m.put("IMG_SCISSORS", IMG_SCISSORS);
//		m.put("SCISSORS_ROTATE", SCISSORS_ROTATE);
//		m.put("ELIPSE", ELIPSE);
//		m.put("CIRCLE", CIRCLE);
//
//		m.put("COD_AWB", awb.getCodAwb());
//
//		/* Detaliile despre DESTINATAR */
//		m.put("NUME_DEST", awb.getNumeExpeditor());
//		m.put("ADRESA_DEST", awb.getAdresaExpeditor());
//		m.put("LOC_DEST", awb.getLocalitateExpeditor());
//		m.put("JUDET_DEST", awb.getJudetExpeditor());
//		m.put("COD_DEST", awb.getCodPostalExpeditor());
//		m.put("TEL_DEST", awb.getTelefonExpeditor());
//
//		/* Detaliile despre EXPEDITOR */
//		m.put("NUME_EXP", awb.getNumeDestinatar());
//		m.put("ADRESA_EXP", awb.getAdresaDestinatar());
//		m.put("LOC_EXP", awb.getLocalitateDestinatar());
//		m.put("COD_EXP", awb.getCodPostalDestinatar());
//		m.put("TEL_EXP", awb.getTelefonDestinatar());
//
//		/* Detalii trimitere */
//		BigDecimal ramburs = awb.getRamburs();
//		m.put("RAMBURS", ramburs);
//
//		RuleBasedNumberFormat format = new RuleBasedNumberFormat(
//				LocaleUtils.toLocale("ro_RO"), RuleBasedNumberFormat.SPELLOUT);
//		String ramburs_litere = null;
//		if (ramburs != null) {
//			String r = ramburs.toString();
//			String bani = r.substring(r.indexOf('.') + 1);
//			Integer lei = Integer.parseInt(r.substring(0, r.indexOf('.')));
//			String ramburs_total = LocaleHelper.toRomanianNoDiacritics(format
//					.format(lei));
//			ramburs_litere = ramburs_total + " de lei si " + bani + " bani";
//		}
//
//		m.put("RAMBURS_LITERE", ramburs_litere);
//		m.put("CONTINUT", awb.getContinut());
//		m.put("CUI_DEST", ((PersoanaJuridica) awb.getUtilizator()).getCui());
//
//		/* gasirea unui cod bic din contul bancar */
//		m.put("CONT_BANCAR_DEST",
//				((PersoanaJuridica) awb.getUtilizator()).getContBancar());
//		m.put("BANCA_CONT_DEST", awb.getUtilizator().getBancaCont());
//
//		return JasperFillManager.fillReport(report, m);
//
//	}
//
//	public static JasperPrint generateMandatPersoanaFizica(Awb awb,
//			JasperReport mandat_persoana_fizica, Utilizator user)
//			throws JRException {
//
//		Map<String, Object> m = new HashMap<String, Object>();
//
//		m.put("format", "pdf");
//		m.put("IMG_LOGO_POSTA", IMG_LOGO_POSTA);
//		m.put("IMG_SCISSORS", IMG_SCISSORS);
//		m.put("SCISSORS_ROTATE", SCISSORS_ROTATE);
//		m.put("ELIPSE", ELIPSE);
//		m.put("CIRCLE", CIRCLE);
//		m.put("COD_AWB", awb.getCodAwb());
//
//		/* Detaliile despre DESTINATAR */
//		m.put("NUME_DEST", awb.getNumeExpeditor());
//		m.put("ADRESA_DEST", awb.getAdresaExpeditor());
//		m.put("LOC_DEST", awb.getLocalitateExpeditor());
//		m.put("JUDET_DEST", awb.getJudetExpeditor());
//		m.put("COD_DEST", awb.getCodPostalExpeditor());
//		m.put("TEL_DEST", awb.getTelefonExpeditor());
//		m.put("CNP_DEST", ((PersoanaFizica) awb.getUtilizator()).getCnp());
//
//		/* Detaliile despre EXPEDITOR */
//		m.put("NUME_EXP", awb.getNumeDestinatar());
//		m.put("ADRESA_EXP", awb.getAdresaDestinatar());
//		m.put("LOC_EXP", awb.getLocalitateDestinatar());
//		m.put("COD_EXP", awb.getCodPostalDestinatar());
//		m.put("TEL_EXP", awb.getTelefonDestinatar());
//		m.put("JUDET_EXP", awb.getJudetDestinatar());
//
//		/* Detalii trimitere */
//		BigDecimal ramburs = awb.getRamburs();
//		m.put("RAMBURS", ramburs);
//
//		RuleBasedNumberFormat format = new RuleBasedNumberFormat(
//				LocaleUtils.toLocale("ro_RO"), RuleBasedNumberFormat.SPELLOUT);
//		String ramburs_litere = null;
//		if (ramburs != null) {
//			String r = ramburs.toString();
//			String bani = r.substring(r.indexOf('.') + 1);
//			Integer lei = Integer.parseInt(r.substring(0, r.indexOf('.')));
//			String ramburs_total = LocaleHelper.toRomanianNoDiacritics(format
//					.format(lei));
//			ramburs_litere = ramburs_total + " de lei si " + bani + " bani";
//		}
//
//		m.put("RAMBURS_LITERE", ramburs_litere);
//
//		m.put("TARIF_POSTAL", awb.getSumaPlataCuTva());
//
//		return JasperFillManager.fillReport(mandat_persoana_fizica, m);
//
//	}
//
//	public static JasperPrint generateMandatPersoanaFizicaVerso(Awb awb,
//			JasperReport mandat_persoana_fizica_verso, Utilizator user)
//			throws JRException {
//
//		Map<String, Object> m = new HashMap<String, Object>();
//
//		m.put("format", "pdf");
//		m.put("IMG_LOGO_POSTA", IMG_LOGO_POSTA);
//		m.put("IMG_SCISSORS", IMG_SCISSORS);
//		m.put("SCISSORS_ROTATE", SCISSORS_ROTATE);
//		m.put("ELIPSE", ELIPSE);
//		m.put("CIRCLE", CIRCLE);
//		m.put("COD_AWB", awb.getCodAwb());
//
//		/* Detaliile despre DESTINATAR */
//		m.put("NUME_DEST", awb.getNumeExpeditor());
//		m.put("ADRESA_DEST", awb.getAdresaExpeditor());
//		m.put("LOC_DEST", awb.getLocalitateExpeditor());
//		m.put("JUDET_DEST", awb.getJudetExpeditor());
//		m.put("COD_DEST", awb.getCodPostalExpeditor());
//		m.put("TEL_DEST", awb.getTelefonExpeditor());
//		m.put("CNP_DEST", ((PersoanaFizica) awb.getUtilizator()).getCnp());
//
//		/* Detaliile despre EXPEDITOR */
//		m.put("NUME_EXP", awb.getNumeDestinatar());
//		m.put("ADRESA_EXP", awb.getAdresaDestinatar());
//		m.put("LOC_EXP", awb.getLocalitateDestinatar());
//		m.put("COD_EXP", awb.getCodPostalDestinatar());
//		m.put("TEL_EXP", awb.getTelefonDestinatar());
//		m.put("JUDET_EXP", awb.getJudetDestinatar());
//
//		/* Detalii trimitere */
//		BigDecimal ramburs = awb.getRamburs();
//		m.put("RAMBURS", ramburs);
//
//		RuleBasedNumberFormat format = new RuleBasedNumberFormat(
//				LocaleUtils.toLocale("ro_RO"), RuleBasedNumberFormat.SPELLOUT);
//		String ramburs_litere = null;
//		if (ramburs != null) {
//			String r = ramburs.toString();
//			String bani = r.substring(r.indexOf('.') + 1);
//			Integer lei = Integer.parseInt(r.substring(0, r.indexOf('.')));
//			String ramburs_total = LocaleHelper.toRomanianNoDiacritics(format
//					.format(lei));
//			ramburs_litere = ramburs_total + " de lei si " + bani + " bani";
//		}
//
//		m.put("RAMBURS_LITERE", ramburs_litere);
//
//		m.put("TARIF_POSTAL", awb.getSumaPlataCuTva());
//
//		return JasperFillManager.fillReport(mandat_persoana_fizica_verso, m);
//
//	}
//
//	public static JasperPrint generateConfirmarePrimirePlic(Awb awb,
//			JasperReport confirmare_primire_plic, Utilizator user)
//			throws JRException {
//
//		Map<String, Object> m = new HashMap<String, Object>();
//
//		m.put("format", "pdf");
//		m.put("IMG_LOGO_POSTA", IMG_LOGO_POSTA);
//		m.put("IMG_SCISSORS", IMG_SCISSORS);
//		m.put("IMG_CHECKED", IMG_CHECKED);
//		m.put("IMG_UNCHECKED", IMG_UNCHECKED);
//
//		m.put("COD_AWB", awb.getCodAwb());
//
//		/* Detaliile despre DESTINATAR */
//		m.put("NUME_DEST", awb.getNumeDestinatar());
//		m.put("ADRESA_DEST", awb.getAdresaDestinatar());
//		m.put("LOC_DEST", awb.getLocalitateDestinatar());
//		m.put("JUDET_DEST", awb.getJudetDestinatar());
//		m.put("COD_DEST", awb.getCodPostalDestinatar());
//
//		/* Detaliile despre EXPEDITOR */
//		m.put("NUME_EXP", awb.getNumeExpeditor());
//		m.put("ADRESA_EXP", awb.getAdresaExpeditor());
//		m.put("LOC_EXP", awb.getLocalitateExpeditor());
//		m.put("COD_EXP", awb.getCodPostalExpeditor());
//		m.put("JUDET_EXP", awb.getJudetExpeditor());
//
//		/* Detalii trimitere */
//		m.put("RAMBURS", awb.getRamburs());
//		m.put("VALOARE", awb.getValoare());
//		m.put("GREUTATE", awb.getGreutateTrimitere());
//
//		Boolean checkColet = false;
//		Boolean checkPlic = false;
//		Boolean checkMandat = false;
//
//		TipTrimitereType t = getTipTrimitereAwb(awb, user);
//		if (t.equals(TipTrimitereType.COLET)) {
//			checkColet = true;
//			checkPlic = false;
//			checkMandat = false;
//		} else if (t.equals(TipTrimitereType.PLIC)) {
//			checkColet = false;
//			checkPlic = true;
//			checkMandat = false;
//		} else if (t.equals(TipTrimitereType.MANDAT)) {
//			checkColet = false;
//			checkPlic = false;
//			checkMandat = true;
//		}
//
//		m.put("CHECK_PLIC", checkPlic);
//		m.put("CHECK_COLET", checkColet);
//		m.put("CHECK_MANDAT", checkMandat);
//
//		return JasperFillManager.fillReport(confirmare_primire_plic, m);
//
//	}
//
//	public static JasperPrint generateConfirmarePrimireColet(Awb awb,
//			JasperReport confirmare_primire_colet, Utilizator user)
//			throws JRException {
//
//		Map<String, Object> m = new HashMap<String, Object>();
//
//		m.put("format", "pdf");
//		m.put("IMG_LOGO_POSTA", IMG_LOGO_POSTA);
//		m.put("IMG_LOGO_PRIORI", IMG_LOGO_PRIORI);
//		m.put("IMG_CHECKED", IMG_CHECKED);
//		m.put("IMG_UNCHECKED", IMG_UNCHECKED);
//		m.put("IMG_SCISSORS", IMG_SCISSORS);
//
//		m.put("COD_AWB", awb.getCodAwb());
//
//		/*
//		 * Detaliile despre EXPEDITOR
//		 */
//		m.put("NUME_EXP", awb.getNumeExpeditor());
//		m.put("ADRESA_EXP", awb.getAdresaExpeditor());
//		m.put("LOC_EXP", awb.getLocalitateExpeditor());
//		m.put("JUDET_EXP", awb.getJudetExpeditor());
//		m.put("TEL_EXP", awb.getTelefonExpeditor());
//		m.put("MAIL_EXP", awb.getEmailExpeditor());
//		m.put("NUME_PERS_CONTACT", awb.getPersoanaDeContact());
//		/*
//		 * Detaliile despre DESTINATAR
//		 */
//		m.put("NUME_DESTINATAR", awb.getNumeDestinatar());
//		m.put("ADRESA_DESTINATAR", awb.getAdresaDestinatar());
//		m.put("LOC_DESTINATAR", awb.getLocalitateDestinatar());
//		m.put("JUDET_DESTINATAR", awb.getJudetDestinatar());
//		m.put("TEL_DESTINATAR", awb.getTelefonDestinatar());
//		m.put("MAIL_DESTINATAR", awb.getEmailDestinatar());
//		m.put("OFICIU_DISTRIBUIRE", awb.getOficiuDistribuire());
//
//		/* Detalii trimitere */
//
//		BigDecimal ramburs = awb.getRamburs();
//		Boolean checkRamburs = (ramburs == null
//				|| (ramburs.compareTo(BigDecimal.ZERO) == 0) ? false : true);
//
//		m.put("RAMBURS", ramburs);
//		m.put("CHECK_RAMBURS", checkRamburs);
//
//		BigDecimal valoare = awb.getValoare();
//		Boolean checkValoare = (valoare == null
//				|| (valoare.compareTo(BigDecimal.ZERO) == 0) ? false : true);
//
//		m.put("VALOARE_ASIG", valoare);
//
//		m.put("CHECK_VALOARE", checkValoare);
//
//		m.put("GREUTATE", awb.getGreutateTrimitere());
//
//		/* HARDCODARE VOLKSBANK */
//		if (awb.getUtilizator().hasAttributeWithValues(
//				VolksbankConstants.UATTR_VOLKSBANK,
//				VolksbankConstants.UVALUE_MASTER,
//				VolksbankConstants.UVALUE_SUCURSALA)||awb.getUtilizator().getRol().getDenumire()=="CEC") {
//			m.put("SUMA", null);
//		} else {
//			m.put("SUMA", awb.getSumaPlataCuTva());
//		}
//		m.put("CHECK_FRAGIL", awb.getFragil());
//		m.put("CHECK_VOLUMINOS", awb.getVoluminos());
//
//		String codClient = null;
//		if (awb.getRamburs() != null
//				&& awb.getRamburs().compareTo(BigDecimal.ZERO) > 0
//				&& awb.getObiectContract() != null
//				&& (codClient = awb.getObiectContract().getCodClient()) != null) {
//			String codColectare = awb.getCodAwb() + codClient
//					+ ((ramburs != null) ? ramburs.toString() : "0.00");
//			m.put("COD_COLECTARE", codColectare);
//		}
//
//		/* checkbox-uri pentru tipul expeditiei, momentan avem doar colet */
//
//		m.put("CHECK_MIXTA", false);
//
//		m.put("CONTINUT", awb.getContinut());
//		m.put("CHECK_ARPR", awb.getConfirmarePrimirePostRestant());
//
//		m.put("CHECK_RECOMANDAT", awb.getRecomandat());
//		m.put("CHECK_AR", awb.getConfirmarePrimire());
//		m.put("CHECK_EC", awb.getEc());
//		m.put("CHECK_PCP", awb.getPcp());
//		m.put("CHECK_MP", awb.getManaProprie());
//		m.put("CHECK_RETUR", awb.getRetur());
//		m.put("CHECK_AVIZ_TEL", awb.getAvizareTelefonica());
//		m.put("CHECK_PRIORITAR", awb.getPrioritar());
//		m.put("CHECK_POST_RESTANT", awb.getPostRestant());
//
////		if (user instanceof PersoanaJuridica) {
////			byte[] siglaBytes = ((PersoanaJuridica) user).getSigla();
////			if (siglaBytes != null) {
////				@SuppressWarnings("unused")
////				BufferedImage sigla = null;
////				try {
////					sigla = ImageIO.read(new ByteArrayInputStream(siglaBytes));
////				} catch (IOException | IllegalArgumentException e) {
////					// Silently ignore
////				}
////
////				// m.put("IMG_REF_SIGLA", sigla);
////			}
////		}
//
//		m.put("IMG_LOGO_POSTA_MIC", IMG_LOGO_POSTA_MIC);
//		m.put("CONFIRMARE_PRIMIRE", CONFIRMARE_PRIMIRE);
//		m.put("STAMPILA", STAMPILA);
//		m.put("STAMPILA_VERSO", STAMPILA_VERSO);
//
//		/* Formularul de confirmare de primire */
//
//		m.put("CODP_DEST", awb.getCodPostalDestinatar());
//		m.put("CODP_EXP", awb.getCodPostalExpeditor());
//
//		String felTrimitere = null;
//		Boolean checkPlic = false;
//		Boolean checkColet = false;
//
//		TipTrimitereType t = getTipTrimitereAwb(awb, user);
//		if (t.equals(TipTrimitereType.COLET)) {
//			felTrimitere = "Colet";
//			checkColet = true;
//			checkPlic = false;
//		} else if (t.equals(TipTrimitereType.PLIC)) {
//			felTrimitere = "Plic";
//			checkColet = false;
//			checkPlic = true;
//		}
//		if (VolksbankHelper.isContVolksbank(user)) {
//			m.put("CHECK_RETUR", true);
//		}
//
//		m.put("FEL_TRIMITERE", felTrimitere);
//		m.put("CHECK_PLIC", checkPlic);
//		m.put("CHECK_COLET", checkColet);
//		Boolean ar_colet = false;
//		if (awb.getConfirmarePrimire() && t.equals(TipTrimitereType.COLET)) {
//			ar_colet = true;
//		}
//		m.put("CHECK_AR_COLET", ar_colet);
//
//		// TODO: Volksbank Harcoding
//		if (VolksbankHelper.isPaynetVolksbank(user)) {
//			m.put("CHECK_DEST", true);
//		} else {
//			m.put("CHECK_EXP", true);
//		}
//
//		return JasperFillManager.fillReport(confirmare_primire_colet, m);
//
//	}
//
//	public static JasperPrint generateConfirmarePrimireColetVerso(Awb awb,
//			JasperReport confirmare_primire_colet_verso, Utilizator user)
//			throws JRException {
//
//		Map<String, Object> m = new HashMap<String, Object>();
//
//		m.put("format", "pdf");
//		m.put("IMG_LOGO_POSTA", IMG_LOGO_POSTA);
//		m.put("IMG_LOGO_PRIORI", IMG_LOGO_PRIORI);
//		m.put("IMG_CHECKED", IMG_CHECKED);
//		m.put("IMG_UNCHECKED", IMG_UNCHECKED);
//		m.put("IMG_SCISSORS", IMG_SCISSORS);
//
//		m.put("COD_AWB", awb.getCodAwb());
//
//		/*
//		 * Detaliile despre EXPEDITOR
//		 */
//		m.put("NUME_EXP", awb.getNumeExpeditor());
//		m.put("ADRESA_EXP", awb.getAdresaExpeditor());
//		m.put("LOC_EXP", awb.getLocalitateExpeditor());
//		m.put("JUDET_EXP", awb.getJudetExpeditor());
//		m.put("TEL_EXP", awb.getTelefonExpeditor());
//		m.put("MAIL_EXP", awb.getEmailExpeditor());
//		m.put("NUME_PERS_CONTACT", awb.getPersoanaDeContact());
//		/*
//		 * Detaliile despre DESTINATAR
//		 */
//		m.put("NUME_DESTINATAR", awb.getNumeDestinatar());
//		m.put("ADRESA_DESTINATAR", awb.getAdresaDestinatar());
//		m.put("LOC_DESTINATAR", awb.getLocalitateDestinatar());
//		m.put("JUDET_DESTINATAR", awb.getJudetDestinatar());
//		m.put("TEL_DESTINATAR", awb.getTelefonDestinatar());
//		m.put("MAIL_DESTINATAR", awb.getEmailDestinatar());
//		m.put("OFICIU_DISTRIBUIRE", awb.getOficiuDistribuire());
//
//		/* Detalii trimitere */
//
//		BigDecimal ramburs = awb.getRamburs();
//		Boolean checkRamburs = (ramburs == null
//				|| (ramburs.compareTo(BigDecimal.ZERO) == 0) ? false : true);
//
//		m.put("RAMBURS", ramburs);
//		m.put("CHECK_RAMBURS", checkRamburs);
//
//		BigDecimal valoare = awb.getValoare();
//		Boolean checkValoare = (valoare == null
//				|| (valoare.compareTo(BigDecimal.ZERO) == 0) ? false : true);
//
//		m.put("VALOARE_ASIG", valoare);
//
//		m.put("CHECK_VALOARE", checkValoare);
//
//		m.put("GREUTATE", awb.getGreutateTrimitere());
//
//		/* HARDCODARE VOLKSBANK */
//		if (awb.getUtilizator().hasAttributeWithValues(
//				VolksbankConstants.UATTR_VOLKSBANK,
//				VolksbankConstants.UVALUE_MASTER,
//				VolksbankConstants.UVALUE_SUCURSALA)||awb.getUtilizator().getRol().getDenumire()=="CEC") {
//			m.put("SUMA", null);
//		} else {
//			m.put("SUMA", awb.getSumaPlataCuTva());
//		}
//		m.put("CHECK_FRAGIL", awb.getFragil());
//		m.put("CHECK_VOLUMINOS", awb.getVoluminos());
//
//		String codClient = null;
//		if (awb.getRamburs() != null
//				&& awb.getRamburs().compareTo(BigDecimal.ZERO) > 0
//				&& awb.getObiectContract() != null
//				&& (codClient = awb.getObiectContract().getCodClient()) != null) {
//			String codColectare = awb.getCodAwb() + codClient
//					+ ((ramburs != null) ? ramburs.toString() : "0.00");
//			m.put("COD_COLECTARE", codColectare);
//		}
//
//		/* checkbox-uri pentru tipul expeditiei, momentan avem doar colet */
//
//		m.put("CHECK_MIXTA", false);
//
//		m.put("CONTINUT", awb.getContinut());
//		m.put("CHECK_ARPR", awb.getConfirmarePrimirePostRestant());
//
//		m.put("CHECK_RECOMANDAT", awb.getRecomandat());
//		m.put("CHECK_AR", awb.getConfirmarePrimire());
//		m.put("CHECK_EC", awb.getEc());
//		m.put("CHECK_PCP", awb.getPcp());
//		m.put("CHECK_MP", awb.getManaProprie());
//		m.put("CHECK_RETUR", awb.getRetur());
//		m.put("CHECK_AVIZ_TEL", awb.getAvizareTelefonica());
//		m.put("CHECK_PRIORITAR", awb.getPrioritar());
//		m.put("CHECK_POST_RESTANT", awb.getPostRestant());
//
////		if (user instanceof PersoanaJuridica) {
////			byte[] siglaBytes = ((PersoanaJuridica) user).getSigla();
////			if (siglaBytes != null) {
////				@SuppressWarnings("unused")
////				BufferedImage sigla = null;
////				try {
////					sigla = ImageIO.read(new ByteArrayInputStream(siglaBytes));
////				} catch (IOException | IllegalArgumentException e) {
////					// Silently ignore
////				}
////
////				// m.put("IMG_REF_SIGLA", sigla);
////			}
////		}
//
//		m.put("IMG_LOGO_POSTA_MIC", IMG_LOGO_POSTA_MIC);
//		m.put("CONFIRMARE_PRIMIRE", CONFIRMARE_PRIMIRE);
//		m.put("STAMPILA", STAMPILA);
//		m.put("STAMPILA_VERSO", STAMPILA_VERSO);
//
//		/* Formularul de confirmare de primire */
//
//		m.put("CODP_DEST", awb.getCodPostalDestinatar());
//		m.put("CODP_EXP", awb.getCodPostalExpeditor());
//
//		String felTrimitere = null;
//		Boolean checkPlic = false;
//		Boolean checkColet = false;
//
//		TipTrimitereType t = getTipTrimitereAwb(awb, user);
//		if (t.equals(TipTrimitereType.COLET)) {
//			felTrimitere = "Colet";
//			checkColet = true;
//			checkPlic = false;
//		} else if (t.equals(TipTrimitereType.PLIC)) {
//			felTrimitere = "Plic";
//			checkColet = false;
//			checkPlic = true;
//		}
//		if (VolksbankHelper.isContVolksbank(user)) {
//			m.put("CHECK_RETUR", true);
//		}
//
//		m.put("FEL_TRIMITERE", felTrimitere);
//		m.put("CHECK_PLIC", checkPlic);
//		m.put("CHECK_COLET", checkColet);
//		Boolean ar_colet = false;
//		if (awb.getConfirmarePrimire() && t.equals(TipTrimitereType.COLET)) {
//			ar_colet = true;
//		}
//		m.put("CHECK_AR_COLET", ar_colet);
//
//		// TODO: Volksbank Harcoding
//		if (VolksbankHelper.isPaynetVolksbank(user)) {
//			m.put("CHECK_DEST", true);
//		} else {
//			m.put("CHECK_EXP", true);
//		}
//
//		return JasperFillManager.fillReport(confirmare_primire_colet_verso, m);
//
//	}
//
//	public static JasperPrint generateCodColectareContClient(Awb awb,
//			JasperReport cod_colectare_cont_client, Utilizator user)
//			throws JRException {
//
//		Map<String, Object> m = new HashMap<String, Object>();
//
//		m.put("format", "pdf");
//
//		m.put("COD_AWB", awb.getCodAwb());
//		m.put("NUME_DESTINATAR", awb.getNumeDestinatar());
//
//		BigDecimal ramburs = awb.getRamburs();
//		m.put("RAMBURS", ramburs);
//
//		String codClient = null;
//		if (ramburs != null && ramburs.compareTo(BigDecimal.ZERO) > 0
//				&& awb.getObiectContract() != null
//				&& (codClient = awb.getObiectContract().getCodClient()) != null) {
//			String codColectare = awb.getCodAwb() + codClient
//					+ ((ramburs != null) ? ramburs.toString() : "0.00");
//			m.put("COD_COLECTARE", codColectare);
//		}
//
//		return JasperFillManager.fillReport(cod_colectare_cont_client, m);
//
//	}
//
//	public static JasperPrint generateBlankPage(JasperReport blank_page)
//			throws JRException {
//
//		Map<String, Object> m = new HashMap<String, Object>();
//		m.put("format", "pdf");
//		return JasperFillManager.fillReport(blank_page, m);
//
//	}
//
//	public static TipTrimitereType getTipTrimitereAwb(Awb awb, Utilizator user) {
//		if (awb.getCodTrimiterePk().getPrest() == 3
//				& awb.getCodTrimiterePk().getTippr() == 1
//				& (awb.getCodTrimiterePk().getCodi() == 2 || awb
//						.getCodTrimiterePk().getCodi() == 10)) {
//			awb.setTipTrimitereAwb(TipTrimitereType.COLET);
//		} else if (awb.getCodTrimiterePk().getPrest() == 3
//				& awb.getCodTrimiterePk().getTippr() == 1
//				& (awb.getCodTrimiterePk().getCodi() == 1 || awb
//						.getCodTrimiterePk().getCodi() == 9)) {
//			awb.setTipTrimitereAwb(TipTrimitereType.PLIC);
//		} else if (VolksbankHelper.isContVolksbank(user)
//				&& awb.getGreutateTrimitere().compareTo(BigDecimal.ONE) <= 0) {
//			// TODO: Volksbank Harcoding
//			awb.setTipTrimitereAwb(TipTrimitereType.PLIC);
//		} else if (VolksbankHelper.isContVolksbank(user)
//				&& awb.getGreutateTrimitere().compareTo(BigDecimal.ONE) > 0) {
//			// TODO: Volksbank Harcoding
//			awb.setTipTrimitereAwb(TipTrimitereType.COLET);
//		}
//
//		return awb.getTipTrimitereAwb();
//	}
//
//}
