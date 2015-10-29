/** Verifica daca o variabila este numerica */
function isNumeric(num) {
	return (num >= 0 || num < 0);
}

/**
 * Round HALF UP la un numar fix de zecimale. Credit: Jérémie Astori @ http://stackoverflow.com/questions/11832914/round-to-at-most-2-decimal-places-in-javascript
 */
function round(value, exp) {
	if (typeof exp === 'undefined' || +exp === 0)
		return Math.round(value);

	value = +value;
	exp = +exp;

	if (isNaN(value) || !(typeof exp === 'number' && exp % 1 === 0))
		return NaN;

	// Shift
	value = value.toString().split('e');
	value = Math
			.round(+(value[0] + 'e' + (value[1] ? (+value[1] + exp) : exp)));

	// Shift back
	value = value.toString().split('e');
	return +(value[0] + 'e' + (value[1] ? (+value[1] - exp) : -exp));
}