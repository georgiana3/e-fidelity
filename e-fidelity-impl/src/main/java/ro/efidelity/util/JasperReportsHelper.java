package ro.efidelity.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import ro.efidelity.service.exception.EFidelityException;

/**
 * @author Andrei Pietrusel
 */
public class JasperReportsHelper {

	protected JasperReportsHelper() {
	}

	/**
	 * Formatul fisierului exportat.
	 */
	public enum ExportFormat {
		PDF, XLS
	}

	private static final DateTimeFormatter fmt = DateTimeFormat
			.forPattern("ddMMyyyy");

	/**
	 * Exporta un raport in cadrul unui request web.
	 * 
	 * @param reportPath
	 *            calea catre raport pornind dupa classpath:/jasperreports/
	 * @param datasource
	 *            sursa de date pentru raport
	 * @param parameters
	 *            parametrii raportului
	 * @param format
	 *            formatul raportului (vezi {@see ExportFormat}
	 * @param fileName
	 *            numele fisierului exportat
	 * @param date
	 *            data din numele raportului
	 * @param request
	 *            request-ul web
	 * @param response
	 *            response-ul web
	 */
	public static final void exportReport(String reportPath, Object datasource,
			Map<String, Object> parameters, ExportFormat format,
			String fileName, DateTime date, HttpServletResponse response) {

		try {
			/* Citeste raportul */
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			JasperReport report = (JasperReport) JRLoader.loadObject(cl
					.getResourceAsStream(reportPath));

			JasperPrint jasperPrint = null;
			/* Creeaza Print */
			if (datasource instanceof JRDataSource) {
				jasperPrint = JasperFillManager.fillReport(report, parameters,
						(JRDataSource) datasource);
			} else if (datasource instanceof Connection) {
				jasperPrint = JasperFillManager.fillReport(report, parameters,
						(Connection) datasource);
			} else
				throw new RuntimeException("Probleme mari!");

			/* Seteaza header exporter */
			StringBuilder sb = new StringBuilder("attachment;filename=");
			sb.append(fileName);
			if (date != null)
				sb.append("_").append(fmt.print(date));

			/* Pregateste raport de export */
			JRExporter exporter = null;

			switch (format) {
			case PDF:
				sb.append(".pdf");
				response.setHeader("Content-Disposition", sb.toString());
				response.setContentType("application/pdf");
				exporter = new JRPdfExporter();
				break;
			case XLS:
				sb.append(".xls");
				response.setHeader("Content-Disposition", sb.toString());
				response.setContentType("application/xls");
				exporter = new JRXlsExporter();
				break;
			}

			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					response.getOutputStream());
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

			/* Exporta raport in fluxul de iesire */
			exporter.exportReport();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Exporta un raport in cadrul unui request web.
	 * 
	 * @param reportPath
	 *            calea catre raport pornind dupa classpath:/jasperreports/
	 * @param parameters
	 *            parametrii raportului
	 * @param format
	 *            formatul raportului (vezi {@see ExportFormat}
	 * @param fileName
	 *            numele fisierului exportat
	 * @param date
	 *            data din numele raportului
	 * @param request
	 *            request-ul web
	 * @param response
	 *            response-ul web
	 */
	public static final void exportReport(String reportPath,
			Map<String, Object> parameters, ExportFormat format,
			String fileName, DateTime date, HttpServletResponse response) {

		try (Connection jdbcConnection = HibernateJDBCHelper
				.getJDBCConnection()) {
			exportReport(reportPath, jdbcConnection, parameters, format,
					fileName, date, response);
		} catch (SQLException e) {
			throw new EFidelityException(
					"Baza de date a intampinat o problema la furnizarea datelor raportului.");
		}

	}

}
