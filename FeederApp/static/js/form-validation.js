$().ready(function() {
	$("#signup_form").validate({
	rules: {
		name: {
		required: true,
		maxlength: 100
		},
		ldap: {
		required: true,
		email: true
		},
		password: {
		required: true,
		minlength: 5
		},
		cnfm_password: {
		required: true,
		equalTo: "#password"
		},
		birthDate: {
		required: true,
		date: true
		},
		branch: {
		required: true
		},
		program: {
		required: true
		},
		program_year: {
		required: true
		}
	},

	messages: {
		name: {
		required: "Please enter your Name",
		maxlength: "Name can't be more than 100 characters long"
		},
		ldap: {
		required: "Please enter your LDAP ID",
		email: "This is not a valid LDAP ID"
		},
		password: {
		required: "Please provide a password",
		minlength: "Your password must be at least 5 characters long"
		},
		cnfm_password: {
		required: "Please enter your password again",
		equalTo: "The two passwords doesn't match"
		},
		birthDate: {
		required: "Please enter your date of birth"
		},
		branch: {
		required: "Please select your department"
		},
		program: {
		required: "Please select your program"
		},
		program_year: {
		required: "Please select your program_year"
		}
	},

	submitHandler: function(form) {
		form.submit();
	}
	});
});