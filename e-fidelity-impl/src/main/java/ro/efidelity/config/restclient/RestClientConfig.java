package ro.efidelity.config.restclient;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpHeaders;

public class RestClientConfig {

	public static HttpHeaders createHeaders(String username, String password) {
		HttpHeaders headers = new HttpHeaders();

		String auth = username + ":" + password;
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset
				.forName("US-ASCII")));
		String authHeader = "Basic " + new String(encodedAuth);
		headers.set("Authorization", authHeader);

		return headers;
	}
}
