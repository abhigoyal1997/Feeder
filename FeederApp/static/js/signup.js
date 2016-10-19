function changerole() {
	if ($("#student").is(':checked')) {
		$("#program").removeClass("hidden");
		$("#programyear").removeClass("hidden");
	}
	else if ($("#instructor").is(':checked')) {
		$("#program").addClass("hidden");
		$("#programyear").addClass("hidden");
	}
}