package ro.efidelity.util;

import java.util.Arrays;
import java.util.List;

public final class EnumHelper {

	public static <T extends Enum<?>> List<T> getList(Class<T> clazz) {
		return Arrays.asList(clazz.getEnumConstants());
	}

}
