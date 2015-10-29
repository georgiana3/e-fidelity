package ro.efidelity.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ArrayHelper {
	@SuppressWarnings("unchecked")
	public static <T> T[] arrayMerge(T[]... arrays) {
		// Determine required size of new array

		int count = 0;
		for (T[] array : arrays) {
			count += array.length;
		}

		// create new array of required class

		T[] mergedArray = (T[]) Array.newInstance(arrays[0][0].getClass(),
				count);

		// Merge each array into new array

		int start = 0;
		for (T[] array : arrays) {
			System.arraycopy(array, 0, mergedArray, start, array.length);
			start += array.length;
		}
		return (T[]) mergedArray;
	}

	public static <T> List<T> intersection(List<T> a, List<T> b) {

		List<T> rez = new ArrayList<T>(a);
		for (T element : a) {
			if (!b.contains(element)) {
				rez.remove(element);
			}
		}
		return rez;

	}
}
