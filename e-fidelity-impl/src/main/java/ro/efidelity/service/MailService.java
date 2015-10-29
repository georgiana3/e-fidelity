package ro.efidelity.service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	@Resource(name = "mailSender")
	private JavaMailSender mailSender;

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendMail(String cheie, final String email, String url,
			final String username) {

		// Create a thread safe "copy" of the template message and customize it

		final String link = url + "/utilizatori/confirm?cod=" + cheie;
		final String home = url;

		MimeMessagePreparator msg = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage)
					throws MessagingException {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						true, "UTF-8");
				message.setFrom("AWB - Posta Romana <platforma.cnpr@posta-romana.ro>");
				message.setTo(email);
				message.setSubject("Confirmare inregistrare - AWB");
				message.setText(
						"Buna ziua,<br>"
								+ "Ati solicitat crearea unui cont pe site-ul "
								+ "<a href=\""
								+ home
								+ "\">"
								+ home
								+ "</a>. Username-ul pentru contul solicitat este: "
								+ username
								+ "<br>Va rugam sa deschideti urmatorul link pentru a activa contul dumneavoastra: <br> "
								+ "<a href=\""
								+ link
								+ "\">"
								+ link
								+ "</a><br>Daca nu dumneavoastra ati cerut inregistrarea, ignorati acest mail.",
						true);
			}
		};

		try {
			this.mailSender.send(msg);
		} catch (MailException ex) {
			throw new RuntimeException(ex);
		}

	}

	public void sendMailContPosta(String cheie, final String email, String url) {

		// Create a thread safe "copy" of the template message and customize it

		final String link = url + "/utilizatori/confirm?cod=" + cheie;

		MimeMessagePreparator msg = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage)
					throws MessagingException {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						true, "UTF-8");
				message.setFrom("AWB - Posta Romana <platforma.cnpr@posta-romana.ro>");
				message.setTo(email);
				message.setSubject("Detalii cont oficiu - AWB");
				message.setText(
						"Buna ziua,<br>"
								+ "Va rugam sa deschideti urmatorul link pentru a activa contul dumneavoastra: <br> "
								+ "<a href=\"" + link + "\">" + link, true);
			}
		};

		try {
			this.mailSender.send(msg);
		} catch (MailException ex) {
			throw new RuntimeException(ex);
		}

	}

	public void sendMailRenewPassword(String cheie, final String email,
			String url, final String username) {
		// Create a thread safe "copy" of the template message and customize it

		final String link = url + "/utilizatori/renew-password?cod=" + cheie;

		MimeMessagePreparator msg = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage)
					throws MessagingException {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						true, "UTF-8");
				message.setFrom("AWB - Posta Romana <platforma.cnpr@posta-romana.ro>");
				message.setTo(email);
				message.setSubject("Resetare parola");
				message.setText(
						"Buna ziua,<br>"
								+ "Va rugam sa accesati urmatoarea pagina pentru a va reseta parola pentru contul: "
								+ username + "<br>" + "<a href=\"" + link
								+ "\">" + link + "</a>", true);
			}
		};

		try {
			this.mailSender.send(msg);
		} catch (MailException ex) {
			// simply log it and go on...
		}

	}
}
