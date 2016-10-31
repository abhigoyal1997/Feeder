function addquestion(x,y,z){
	questions = document.getElementById("questions");
	qcount = document.getElementById("qcount");
	qcount.value = parseInt(qcount.value)+1;
	questions.innerHTML = questions.innerHTML + "<input type'hidden' name='t"+qcount.value+"' value='"+z+"'><label for='q"+qcount.value+"'>Question "+qcount.value +"</label><input type='text' class='textinput' name = '"+x+"' required name='q"+qcount.value+"' placeholder='"+y+"'>"
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