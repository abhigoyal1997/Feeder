from django.shortcuts import render
from django.http import HttpResponse
from django.contrib.auth.models import User
from django.contrib import messages
from FeederApp.models import Student,Instructor,Course

def login(request):
	return render(request, 'login.html',{})    
def signup(request):
	return render(request, 'signup.html',{})
def register(request):
	firstname = request.POST['firstname']
	lastname = request.POST['lastname']
	ldap = request.POST['ldap']
	dob = request.POST['birthdate']
	password = request.POST['password']
	branch = request.POST.get('branch')
	role = request.POST['role']
	if {'username' : ldap} in User.objects.all().values('username'):
		messages.add_message(request, messages.ERROR, 'Username Already Exists.')
		#todo handle error
	else:
		newusr = User.objects.create_user(ldap,ldap,password)
		newusr.first_name = firstname
		newusr.last_name = lastname
		newusr.save()
		if role == 'Student':
			program = request.POST.get('program')
			progy = request.POST.get('programyear')
			newstud = Student.objects.create(user = newusr,student_ldap=ldap,student_branch=branch,student_year=progy,student_program=program,student_dob=dob)
			newstud.save()
		else:
			newins = Instructor.objects.create(user = newusr,instructor_ldap=ldap,instructor_branch=branch,instructor_dob=dob)
			newins.save()
	return render(request,'login.html',{})
