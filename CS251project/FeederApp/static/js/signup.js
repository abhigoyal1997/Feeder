function changerole() {
	if ($("#student").is(':checked')) {
		$(".student").removeClass("hidden");
	}
	else if ($("#instructor").is(':checked')) {
		$(".student").addClass("hidden");
	}
}