from django.shortcuts import render,redirect	
from django.http import HttpResponse
from django.contrib.auth.models import User
from django.contrib import messages
from FeederApp.models import Student,Instructor
from django.contrib.auth import authenticate
from django.contrib.auth import login as auth_login

def login(request):
	return render(request,'login.html',{})    
def signup(request):
	return render(request,'signup.html',{})
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
		return redirect('signup')
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
	messages.success(request, 'Congratulations!! Successful Signup!! Login to Continue')
	return redirect('login')
def auth_stud(request):
	ldap = request.POST['email']
	password = request.POST['password']
	user = authenticate(username=ldap, password=password)
	if user is not None:
		auth_login(request,user)
		return HttpResponse("Hello")
	else:
		messages.error(request, 'Bad Login Credentials')
		return redirect('login')
def auth_inst(request):
	ldap = request.POST['email']
	password = request.POST['password']
	user = authenticate(username=ldap,password=password)
	if user is not None:
		auth_login(request,user)
		return HttpResponse("hello")
	else:
		messages.error(request, 'Bad Login Credentials')
		return redirect('login')

class Course(models.Model):
	instructors = models.ManyToManyField(Instructor)
	students = models.ManyToManyField(Student)
	BRANCHES = (
        ('CS','Computer Science and Engineering'),
        ('EE', 'Electrical Engineering'),
        ('ME', 'Mechanical Engineering'),
        ('CL','Chemical Engineering'),
    )
	course_code = models.CharField(max_length=8,primary_key=True)
	course_name = models.CharField(max_length=100)
	course_branch = models.CharField(max_length = 2,choices = BRANCHES)
	course_credits = models.IntegerField()
	course_duration = models.DecimalField(max_digits = 2,decimal_places = 1)
