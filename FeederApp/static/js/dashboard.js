$(document).ready(function () {
	$('.nav li a').click(function(e) {

		$('.nav li').removeClass('active');

		var $parent = $(this).parent();
		if (!$parent.hasClass('active')) {
			$parent.addClass('active');
		}
		$('#navind').text($(this).text());
		// alert(this.id);
		$('#mainframe').load(this.id);
	});
});

