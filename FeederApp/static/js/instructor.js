function addquestion(x,y,z){
	qcount = document.getElementById("qcount");
	qcount.value = parseInt(qcount.value)+1;
	var container = document.createElement("div");
	container.innerHTML = "<input type='hidden' name='t"+qcount.value+"' value='"+z+"'><label for='q"+qcount.value+"'>Question "+qcount.value +"</label><input name='q"+qcount.value+"' type='text' class='textinput' required placeholder='"+y+"'>"; 
	document.getElementById("questions").appendChild(container);
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
	element.innerHTML = "<input type='text' class='textinput' name = 'deadlinedetails' required id='deadlinedetails' placeholder='Assignment Details'>"
}
function examclicked(){
	element = document.getElementById("textfieldforassignment");
	element.innerHTML = "<input type='text' class='textinput' name = 'deadlinedetails' required id='deadlinedetails' placeholder='Exam Details'>"
}