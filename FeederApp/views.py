from django.shortcuts import render,redirect    
from django.http import HttpResponse
from django.http import HttpResponseRedirect
from django.contrib.auth.models import User
from django.contrib import messages
from FeederApp.models import Student,Instructor,Course
from django.contrib.auth import authenticate
from django.contrib.auth import login as auth_login
#added for admin login page added from django raw way page
from django.conf import settings
from django.shortcuts import redirect
from django.contrib.auth import logout

def login(request):
	if request.user.is_authenticated:
		if request.user.username[0] == 'a':
			return redirect(admin_home)
		if request.user.username[0] == 'i':
			return redirect(ins_home)
		if request.user.username[0] == 's':
			return HttpResponse("Student is logged in")
			# We can also logout the person
		return HttpResponse("How??")
	return render(request,'login.html',{})    
def signup(request):
	return render(request,'signup.html',{})
def admin_login(request):
	return render(request,'admin_login.html',{})
def register(request):
	firstname = request.POST['firstname']
	lastname = request.POST['lastname']
	email = request.POST['email']
	dob = request.POST['birthdate']
	password = request.POST['password']
	branch = request.POST.get('branch')
	role = request.POST['role']
	if {'username' : email} in User.objects.all().values('username'):
		messages.add_message(request, messages.ERROR, 'Username Already Exists.')
		return redirect('signup')
	else:
		if role == 'Student':
			newusr = User.objects.create_user("s:"+email,email,password) # Adding "s:" at the beginning of any student username
			newusr.first_name = firstname
			newusr.last_name = lastname
			newusr.save()
			program = request.POST.get('program')
			progy = request.POST.get('programyear')
			rollno = request.POST.get('rollnumber')
			newstud = Student.objects.create(user = newusr,student_branch=branch,student_year=progy,student_program=program,student_dob=dob,roll_number=rollno)
			newstud.save()
		else:
			newusr = User.objects.create_user("i:"+email,email,password) # Adding "i:" at the beginning of any instructer username
			newusr.first_name = firstname
			newusr.last_name = lastname
			newusr.save()
			newins = Instructor.objects.create(user = newusr,instructor_branch=branch,instructor_dob=dob)
			newins.save()
	messages.success(request, 'Congratulations!! Successful Signup!! Login to Continue')
	return redirect('login')

def auth_admin(request):
	password = request.POST['password']
	adminname = request.POST['username']
	user = authenticate(username="a:"+adminname, password=password)
	if user is not None:
		auth_login(request,user)
		return redirect('admin_home')
	else:
		messages.error(request, 'Bad Login Credentials')
		return redirect('login')

def auth_inst(request):
	email = request.POST['email']
	password = request.POST['password']
	user = authenticate(username="i:"+email,password=password)
	if user is not None:
		auth_login(request,user)
		return redirect('ins_home')
	else:
		messages.error(request, 'Bad Login Credentials')
		return redirect('login')

def admin_home(request):
	if not request.user.is_authenticated:
		return redirect(login)
	if not request.user.username == "a:admin@feeder.com":
		return redirect(login)
	return render(request,'admin_home.html',{})

def ins_home(request):
	if not request.user.is_authenticated:
		return redirect(login)
	if not request.user.username[0] == "i":
		return redirect(login)
	return render(request,'instructor_home.html',{})

def logout_view(request):
	if request.user.username[0] == "a":
		user = "a"
	else:
		user = "i"
	logout(request)
	if user == "a":
		return redirect('admin_login')
	else:
		return redirect('login')

def student_list(request):
	if request.user.username[0] == "a":
		return render(request,'student_list.html',{
			'students':Student.objects.all()
			})
	else:
		return HttpResponse("Don't try to be smart!! We ensure quite enough security!! :)")

def instructor_list(request):
	if request.user.username[0] == "a":
		return render(request,'instructor_list.html',{
			'instructors':Instructor.objects.all()
			})
	else:
		return HttpResponse("Don't try to be smart!! We ensure quite enough security!! :)")

def course_list(request):
	return render(request,'course_list.html',{
		'courses':Course.objects.all()
		})

def add_course(request):
	if request.user.username[0] == "a":
		return render(request,'add_course.html',{})
	else:
		return HttpResponse("Don't try to be smart!! We ensure quite enough security!! :)")
	
def admin_profile(request):
	if request.user.username[0] == "a":
		return render(request,'admin_profile.html',{})
	else:
		return HttpResponse("Don't try to be smart!! We ensure quite enough security!! :)")

def update_admin(request):
	if request.user.username[0] == "a":
		firstname = request.POST['firstname']
		lastname = request.POST['lastname']
		email = request.POST['email']
		password = request.POST['password']
		user = authenticate(username=request.user.username, password=password)
		if user is not None:
			user.first_name = firstname
			user.last_name = lastname
			user.email = email
			user.save()
			messages.success(request, 'Profile Successfully Updated')
			return redirect('admin_home')
		else:
			messages.error(request, 'Wrong Password')
			return redirect('admin_home')
	else:
		return HttpResponse("Don't try to be smart!! We ensure quite enough security!! :)")