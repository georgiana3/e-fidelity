function addWarning($changeTarget, $target) {
	if (round($changeTarget.val() * 1.24, 2) != $target.val()) {
		$changeTarget.addClass("problem");
	} else
		$changeTarget.removeClass("problem");
	$changeTarget
			.attr(
					"title",
					"Corespondenta imposibila intre valoarea fara TVA si cea cu TVA. Se recomanda setarea manuala a valorii convenabile.");
}

function removeWarning($target) {
	$target.removeClass("problem");
	$target.removeAttr("title");
}

var fillTva = function(e) {
	var targetId = e.target.id;
	var $target = $(e.target);
	var $changeTarget = $("#" + targetId + "CuTVA");
	if (isNumeric($target.val()) //
			&& (!$changeTarget.val() || $changeTarget.val() == 0)) {
		$changeTarget.val(round($target.val() * 1.24, 2));
		$changeTarget.highlight();
	}
	removeWarning($target);
};

var fillNoTva = function(e) {
	var targetId = e.target.id;
	var $target = $(e.target);
	var $changeTarget = $("#" + targetId.slice(0, -5));
	if (isNumeric($target.val())) {
		$changeTarget.val(round($target.val() / 1.24, 2));
		$changeTarget.highlight();

		addWarning($changeTarget, $target);
	}
};