$(document).ready(function () {
    $('.nav li a').click(function(e) {

        $('.nav li').removeClass('active');

        var $parent = $(this).parent();
        if (!$parent.hasClass('active')) {
            $parent.addClass('active');
        }
        $('#navind').text($(this).text());
    });
});

function load_home() {
	$('#mainframe').load('students');
}

window.onload = load_home;