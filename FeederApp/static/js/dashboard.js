$(document).ready(function () {
	$('.nav li a').click(function(e) {

		$('.nav li').removeClass('active');

		var $parent = $(this).parent();
		if (!$parent.hasClass('active')) {
			$parent.addClass('active');
		}
		$('#navind').text($(this).text());

		$('#mainframe').load(this.id);
	});
});

function load_home() {
	$('#mainframe').load('#');
}

window.onload = load_home;