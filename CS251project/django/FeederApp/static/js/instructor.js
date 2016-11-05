function addquestion(){
	qcount = document.getElementById("qcount");
	qcount.value = parseInt(qcount.value)+1;
	qselect = document.getElementById("questiontoadd");
	// alert(qselect.value);
	var container = document.createElement("div");
	// container.class = 'col-sm-12';
	if(qselect.value=="RB"){
		container.innerHTML = "<input type='hidden' name='t"+qcount.value+"' value='"+"RB"+"'>Question "+qcount.value +"<input name='q"+qcount.value+"' type='text' class='input-text' required placeholder='"+"Linear Scale Question"+"'>";  
		var el = document.getElementById("feedback");
	    var height = el.offsetHeight;
	    var newHeight = height + 70;
	    el.style.height = newHeight + 'px';
	}
	else if(qselect.value=="TF"){
		container.innerHTML = "<input type='hidden' name='t"+qcount.value+"' value='"+"TF"+"'>Question "+qcount.value +"<input name='q"+qcount.value+"' type='text' class='input-text' required placeholder='"+"Text Question"+"'>";  
		var el = document.getElementById("feedback");
	    var height = el.offsetHeight;
	    var newHeight = height + 70;
	    el.style.height = newHeight + 'px';
	}
	else{
		if(qselect.value=="MCQ"){
			container.innerHTML = "<input type='hidden' name='t"+qcount.value+"' value='"+qselect.value+"'>Question "+qcount.value + "<input name='q"+qcount.value+"' type='text' class='input-text' required placeholder=" + "'Multiple Choice Question'>";
		}
		else if(qselect.value=="CB"){
			container.innerHTML = "<input type='hidden' name='t"+qcount.value+"' value='"+qselect.value+"'>Question "+qcount.value + "<input name='q"+qcount.value+"' type='text' class='input-text' required placeholder=" + "'Check Box Question'>";
		}
		else{
			container.innerHTML = "<input type='hidden' name='t"+qcount.value+"' value='"+qselect.value+"'>Question "+qcount.value + "<input name='q"+qcount.value+"' type='text' class='input-text' required placeholder=" + "'Drop down list question'>";
		}
		container.innerHTML += "<div class='col-sm-12'><input type='hidden' value='0' id='optionscount"+qcount.value+"' name='optionscount"+qcount.value+"'><div id='options"+qcount.value+"'></div><button type='button' class='btn btn-info col-sm-3' style='height:30px; margin:0px; padding: 0px;' onclick='addoption("+qcount.value+")'>Add Option</button></div>";
		//Add button and options
		var el = document.getElementById("feedback");
	    var height = el.offsetHeight;
	    var newHeight = height + 80;
	    el.style.height = newHeight + 'px';
	}
	
	document.getElementById("questions").appendChild(container);
}

function addoption(x){
	// alert("here");
	var container = document.createElement("div");
	ocount = document.getElementById("optionscount"+x);
	ocount.value = parseInt(ocount.value)+1;
	container.innerHTML = "<input style='padding-left=20px;' name='q"+x+"o"+ocount.value+"' type='text' class='input-text' required placeholder='"+"Option"+ocount.value+"'>"
	var el = document.getElementById("feedback");
    var height = el.offsetHeight;
    var newHeight = height + 50;
    el.style.height = newHeight + 'px';
	document.getElementById("options"+x).appendChild(container);
}

function validateFeedbackForm(){
	qcount = document.getElementById("qcount");
	if(parseInt(qcount.value)==0){
		alert("Add at least one question to the feedback form");
		return false;
	}
	return true;
}
function assignmentchecked(){
	element = document.getElementById("textfieldforassignment");
	element.innerHTML = "<input type='text' class='input-text' name = 'deadlinedetails' required id='deadlinedetails' placeholder='Assignment Details'>"
}
function examclicked(){
	element = document.getElementById("textfieldforassignment");
	element.innerHTML = "<input type='text' class='input-text' name = 'deadlinedetails' required id='deadlinedetails' placeholder='Exam Details'>"
}