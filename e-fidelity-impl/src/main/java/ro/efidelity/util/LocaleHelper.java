package ro.efidelity.util;

import org.apache.commons.lang3.StringUtils;

public final class LocaleHelper {

	public static String toRomanianNoDiacritics(final String text) {

		String output = StringUtils.replaceEach(text, new String[] { "ă", "Ă",
				"â", "Â", "î", "Î", "ş", "Ş", "ţ", "Ţ" }, new String[] { "a",
				"A", "a", "A", "i", "I", "s", "S", "t", "T" });

		return output;

	}

}
