from django.shortcuts import render,redirect    
from django.http import HttpResponse
from django.http import HttpResponseRedirect
from django.contrib.auth.models import User
from django.contrib import messages
from FeederApp.models import *
from django.contrib.auth import authenticate
from django.contrib.auth import login as auth_login
from django.conf import settings
from django.shortcuts import redirect
from django.contrib.auth import logout
import urllib.request
from chartit import DataPool, Chart
from django.core import serializers
import json,csv,io,re

def login(request):
	if request.user.is_authenticated:
		if request.user.username[0] == 'a':
			return redirect(admin_home)
		if request.user.username[0] == 'i':
			return redirect(ins_home)
			# We can also logout the person
		return error(request,'You are osum but so are we!.')
	return render(request,'login.html',{})

def signup(request):
	return render(request,'signup.html',{
		'branches':Student.BRANCHES
		})

def register(request):
	if request.method == 'POST':
		firstname = request.POST['firstname']
		lastname = request.POST['lastname']
		email = request.POST['email']
		password = request.POST['password']
		branch = request.POST.get('branch')
		if email in list(map(lambda x:x['username'][2:],User.objects.all().values('username'))):
			messages.add_message(request, messages.ERROR, 'Username Already Exists.')
			return redirect('signup')
		else:
			newusr = User.objects.create_user("i:"+email,email,password) # Adding "i:" at the beginning of any instructer username
			newusr.first_name = firstname
			newusr.last_name = lastname
			newusr.save()
			newins = Instructor.objects.create(user = newusr,instructor_branch=branch)
			newins.save()
		messages.success(request, 'Congratulations!! Successful Signup!! Login to Continue')
		return redirect('login')
	else:
		return error(request,"Don't you wanna fill your details first?!")

def auth_admin(request):
	if request.method == 'POST':
		adminname = request.POST['email']
		password = request.POST['password']
		user = authenticate(username="a:"+adminname, password=password)
		if user is not None:
			auth_login(request,user)
			return redirect('admin_home')
		else:
			messages.error(request, 'Bad Login Credentials')
			return redirect('login')
	else:
		return error(request,'Nice try!...But not good enough!')

def auth_inst(request):
	if request.method == 'POST':
		email = request.POST['email']
		password = request.POST['password']
		user = authenticate(username="i:"+email,password=password)
		if user is not None:
			auth_login(request,user)
			return redirect('ins_home')
		else:
			messages.error(request, 'Bad Login Credentials')
			return redirect('login')
	else:
		return error(request,'Nice try!...But not good enough!')

def fb_login(request):
	if request.method == 'POST':	
		access_token = request.POST['access_token']
		url = 'https://graph.facebook.com/v2.8/me?fields=first_name,last_name,email&access_token='+access_token
		urlresponse = urllib.request.urlopen(url)
		response = json.loads(urlresponse.read().decode('utf-8'))
		firstname = response['first_name']
		lastname = response['last_name']
		email = response['email']
		username = 'i:'+email
		try:
			user = User.objects.get(username=username)
			auth_login(request,user)
			return redirect('ins_home')
		except Exception as e:
			newusr = User.objects.create_user(username,email) # Adding "i:" at the beginning of any instructer username
			newusr.first_name = firstname
			newusr.last_name = lastname
			newusr.save()
			newins = Instructor.objects.create(user = newusr)
			newins.save()
			auth_login(request,newusr)
			return redirect('ins_home')
	else:
		return error(request,'Is clicking the sign in button this hard?!!')

def admin_home(request):
	if not request.user.is_authenticated:
		return error(request,'You need to fill in the login details first!')
	try:
		user = User.objects.get(username=request.user.username)
		if not request.user.username[0] == "a":
			return error(request,"Don't even try! You don't have access to this page!")
		return render(request,'admin_home.html',{
			'admin':request.user
			})
	except Exception as e:
		return error(request,"Don't even try! You don't have access to this page!")

def ins_home(request):
	if not request.user.is_authenticated:
		return error(request,'You need to fill in the login details first!')
	try:
		user = User.objects.get(username=request.user.username)
		if not request.user.username[0] == "i":
			return error(request,"Don't even try! You don't have access to this page!")
		return render(request,'instructor_home.html',{
			'instructor':request.user
			})
	except Exception as e:
		return error(request,"Don't even try! You don't have access to this page!")

def logout_view(request):
	if request.user.is_authenticated:
		if request.user.username[0] == "a":
			user = "a"
		else:
			user = "i"
		logout(request)
		return redirect('login')
	else:
		return error(request,"How do you expect to logout without signing in?!")

def student_list(request):
	if request.user.is_authenticated and request.user.username[0] == "a":
		return render(request,'student_list.html',{
			'students':Student.objects.all()
			})
	else:
		return error(request,"Don't try to be smart!! We ensure quite enough security!! :)")

def instructor_list(request):
	if request.user.is_authenticated and request.user.username[0] == "a":
		return render(request,'instructor_list.html',{
			'instructors':Instructor.objects.all()
			})
	else:
		return error(request,"Don't try to be smart!! We ensure quite enough security!! :)")

def course_list(request):
	if request.user.is_authenticated and request.user.username[0] == "a":
		return render(request,'course_list.html',{
			'courses':Course.objects.all()
		})
	else:
		return error(request,"Don't try to be smart!! We ensure quite enough security!! :)")

def add_course(request):
	if request.user.is_authenticated and request.user.username[0] == "a":
		return render(request,'add_course.html',{
			'branches':Student.BRANCHES
			})
	else:
		return error(request,"Don't try to be smart!! We ensure quite enough security!! :)")
	
def admin_profile(request):
	if request.user.is_authenticated and request.user.username[0] == "a":
		return render(request,'admin_profile.html',{})
	else:
		return error(request,"Don't try to be smart!! We ensure quite enough security!! :)")

def make_course(request):
	if request.user.is_authenticated and request.user.username[0] == "a":
		if request.method == 'POST':
			cname = request.POST['cname']
			ccode = request.POST['ccode']
			if re.match('^[A-Z]{2}-[0-9]{3}$',ccode) is None:
				messages.error(request,'Invalid Course Code entered')
				return redirect('admin_home')
			semester = request.POST.get('semester')
			branch = request.POST.get('branch')
			credits = request.POST.get('credit')
			midsem = request.POST['midsem']
			endsem = request.POST['endsem']
			newcourse = Course.objects.create(course_name = cname,course_code=ccode,course_semester=semester,course_credits=credits,course_branch=branch)
			newcourse.save()
			mfdead = Deadlines.objects.create(course=newcourse,name='FD',desc='Mid-Semester Exam Feedback',date=midsem,code = ccode)
			mfdead.save()
			mfeed = Feedback.objects.create(course=newcourse,deadline=mfdead,name="Midsem Feedback")
			question1 = Question.objects.create(question="Do you have any comments or suggestions you would like to share about the instructor or the course?",question_type="TF")
			question1.save()
			mfeed.question_set.add(question1)
			question2 = Question.objects.create(question="Overall, the course was well organized.",question_type="RB")
			question2.save()
			mfeed.question_set.add(question2)
			question3 = Question.objects.create(question="The instructor was well prepared for each class.",question_type="RB")
			question3.save()
			mfeed.question_set.add(question3)
			question4 = Question.objects.create(question="Feedback on curriculum/course content",question_type="TF")
			question4.save()
			mfeed.question_set.add(question4)
			mfeed.save()

			efdead = Deadlines.objects.create(course=newcourse,name='FD',desc='End-Semester Exams Feedback',date=endsem,code = ccode)
			efdead.save()
			efeed = Feedback.objects.create(course=newcourse,deadline=efdead,name="Endsem Feedback")
			question1 = Question.objects.create(question="Do you have any comments or suggestions you would like to share about the instructor or the course?",question_type="TF")
			question1.save()
			efeed.question_set.add(question1)
			question2 = Question.objects.create(question="Overall, the course was well organized.",question_type="RB")
			question2.save()
			efeed.question_set.add(question2)
			question3 = Question.objects.create(question="The instructor was well prepared for each class.",question_type="RB")
			question3.save()
			efeed.question_set.add(question3)
			question4 = Question.objects.create(question="Feedback on curriculum/course content",question_type="TF")
			question4.save()
			efeed.question_set.add(question4)
			efeed.save()
			mdead = Deadlines.objects.create(course=newcourse,name='EX',desc='Mid-Semester Exams',date=midsem,code = ccode)
			mdead.save()
			edead = Deadlines.objects.create(course=newcourse,name='EX',desc='End-Semester Exams',date=endsem,code = ccode)
			edead.save()
			messages.add_message(request, messages.SUCCESS, 'Congratulations.New Course has been added!')
			messages.add_message(request,messages.INFO,'You can add new students to the course by clicking Add/Remove Students in Running Courses Menu')
			return redirect('admin_home')
		else:
			return error(request,"Don't try random urls!")
	else:
		return error(request,"Don't try to be smart!! We ensure quite enough security!! :)")

def update_admin(request):
	if request.user.is_authenticated and request.user.username[0] == "a":
		if request.method == 'POST':
			firstname = request.POST['firstname']
			lastname = request.POST['lastname']
			email = request.POST['email']
			password = request.POST['password']
			user = authenticate(username=request.user.username, password=password)
			if user is not None:
				user.first_name = firstname
				user.last_name = lastname
				user.email = email
				user.username = 'a:'+email
				user.save()
				messages.success(request, 'Profile Successfully Updated')
				return redirect('admin_home')
			else:
				messages.error(request, 'Wrong Password')
				return redirect('admin_home')
		else:
			return error(request,"Don't try random urls!")
	else:
		return error(request,"Don't try to be smart!! We ensure quite enough security!! :)")

def update_ins(request):
	if request.user.is_authenticated and request.user.username[0] == "i":
		if request.method == 'POST':
			firstname = request.POST['firstname']
			lastname = request.POST['lastname']
			email = request.POST['email']
			branch = request.POST.get('branch')
			username = 'i:'+email
			try:
				user = User.objects.get(username=username)
				if not (user == request.user):
					messages.error(request, 'User with this email already exits!')
					return redirect('ins_home')
				user = request.user
				user.first_name = firstname
				user.last_name = lastname
				user.save()
				ins = Instructor.objects.get(user=user)
				ins.instructor_branch = branch
				ins.save()
				messages.success(request, 'Profile Successfully Updated')
				return redirect('ins_home')
			except Exception as e:
				user = request.user
				user.first_name = firstname
				user.last_name = lastname
				user.email = email
				user.username = username
				user.save()
				ins = Instructor.objects.get(user=user)
				ins.instructor_branch = branch
				ins.save()
				messages.success(request, 'Profile Successfully Updated')
				return redirect('ins_home')
		else:
			return error(request,"Don't try random urls!")
	else:
		return error(request,"Don't try to be smart!! We ensure quite enough security!! :)")

def add_feedback(request):
	if request.user.is_authenticated and request.user.username[0] == "i":
		return render(request,'add_feedback.html',{
			'courses':Course.objects.all()
		})
	else:
		return error(request,"Don't try to be smart!! We ensure quite enough security!! :)")

def view_feedback(request):
	if request.user.is_authenticated and request.user.username[0] == "i":
		return render(request,'view_feedback.html',{
			'courses':Course.objects.all()
		})
	else:
		return error(request,"Don't try to be smart!! We ensure quite enough security!! :)")

def add_deadline(request):
	if request.user.is_authenticated and request.user.username[0] == "i":
		return render(request,'add_deadline.html',{
			'courses':Course.objects.all()
	})
	else:
		return error(request,"Don't try to be smart!! We ensure quite enough security!! :)")

def view_deadline(request):
	if request.user.is_authenticated and request.user.username[0] == "i":
		return render(request,'view_deadline.html',{
			'courses':Course.objects.all()
			})
	else:
		return error(request,"Don't try to be smart!! We ensure quite enough security!! :)")

def instprofile(request):
	if request.user.is_authenticated and request.user.username[0] == "i":
		return render(request,'ins_profile.html',{
			'branches':Student.BRANCHES,
			'instructor':Instructor.objects.get(user=request.user)
			})
	else:
		return error(request,"Don't try to be smart!! We ensure quite enough security!! :)")

def make_feedback(request):
	if request.user.is_authenticated and request.user.username[0] == "i":
		if request.method == 'POST':
			fcourseid = request.POST['fcourse']
			fname = request.POST['fname']
			fdeadline = request.POST['deadline']
			qcount = int(request.POST['qcount'])
			course_concerned = Course.objects.get(course_code=fcourseid)
			newdeadline = Deadlines.objects.create(course=course_concerned,name='FD',desc=fname,date=fdeadline,code=fcourseid)
			newdeadline.save()
			newfeedback = Feedback.objects.create(course=course_concerned,deadline=newdeadline,name=fname)
			for i in range(qcount):
				newquestion(question=request.POST['q'+str(i+1)],question_type=request.POST['t'+str(i+1)])
				newquestion.save()
				newfeedback.question_set.add(newquestion)
			newfeedback.save()
			return redirect(login)
		else:
			return error(request,"Don't try random urls!")
	else:
		return error(request,"Don't try to be smart!! We ensure quite enough security!! :)")

def make_deadline(request):
	if request.user.is_authenticated and request.user.username[0] == "i":
		if request.method == 'POST':
			dcourseid = request.POST['dcourse']
			course_concerned = Course.objects.get(course_code=dcourseid)
			typeofdeadline = request.POST['type']
			details = request.POST['deadlinedetails']
			deadlinedate = request.POST['deadlinedate']
			newdeadline = Deadlines.objects.create(course=course_concerned,name=typeofdeadline,desc=details,date=deadlinedate,code=dcourseid)
			newdeadline.save()
			return redirect(login)
		else:
			return error(request,"Don't try random urls!")
	else:
		return error(request,"Over smart, huh??")

def viewresponses(request, feedbackid):
	# First check that the user is instructor or not only then proceed
	feedbackconcerned = Feedback.objects.get(id=feedbackid)
	questionresponses = []
	for question in feedbackconcerned.question_set.all():
		a = ['11','22','33']
		question.response = json.dumps(a)
		question.save()
	for question in feedbackconcerned.question_set.all():
		jsonDec = json.decoder.JSONDecoder()
		a = jsonDec.decode(question.response)
		for i in range(len(a)):
			a[i] = question.question + a[i]
		questionresponses.append(a)
	questionresponses = list(map(list, zip(*questionresponses)))
	if request.user.username[0] == "i":
		return render(request,'renderresponses.html',{
			'feedback': feedbackconcerned,
			'responses': questionresponses,
			})
	else:
		return error(request,"Ahh! I am too hard, I won't break!");

def viewobjective(request, feedbackid):
	feedbackconcerned = Feedback.objects.get(id=feedbackid)
	# ds = DataPool(
 #       series=
 #        [{'options': {
 #            'source': MonthlyWeatherByCity.objects.all()},
 #          'terms': [
 #            'month',
 #            'boston_temp']}
 #         ])

	# cht = Chart(
 #        datasource = ds, 
 #        series_options = 
 #          [{'options':{
 #              'type': 'pie',
 #              'stacking': False},
 #            'terms':{
 #              'month': [
 #                'boston_temp']
 #              }}],
 #        chart_options = 
 #          {'title': {
 #               'text': 'Monthly Temperature of Boston'}},
 #        x_sortf_mapf_mts = (None, monthname, False))
	# 	chart = Chart(
	# 		datasource = chart,
	# 		)

	return render(request,'rendercharts.html',{
			# 'chart' : chart
		});

def add_stud_to_course(request,code):
	if request.user.is_authenticated and request.user.username[0] == "a":
		ccode = Course.objects.get(course_code = code)
		return render(request,'add_stud_to_course.html',{
			'students': Student.objects.all(),
			'course' :  ccode,
			'code' : code,
			})
	else:
		return error(request,"Don't try to be smart!! We ensure quite enough security!! :)")

def modify(request):
	if request.user.is_authenticated and request.user.username[0] == "i":
		if request.method == 'POST':
			idstd = request.POST.get('id', None)
			code = request.POST.get('code',None)
			usr = User.objects.get(id = idstd)
			if {'course_code' : code} in usr.student.course_set.values('course_code'):
				usr.student.course_set.remove(Course.objects.get(course_code = code))
			else:
				usr.student.course_set.add(Course.objects.get(course_code=code))
			return error(request,"Modifications were successful! However you were not redirected! :p")
		else:
			return error(request,"Don't try random urls!")
	else:
		return error(request,"Don't try to be smart!! We ensure quite enough security!! :)")

def updatedatabase(request):
	if request.user.is_authenticated and request.user.username[0] == "a":
		if request.method == 'POST':
			csvf = request.FILES["studentscsv"]
			for a in csvf:
				row = a.decode("utf-8").split(',')
				if {'username' : "s:" + row[0]} in User.objects.all().values('username'):
					continue
				else:
					usr = User.objects.create_user("s:"+row[0],row[0],row[1])
					usr.first_name = row[3]
					usr.last_name = row[4]
					usr.save()
					newstudent = Student.objects.create(user = usr,roll_number = row[2])
					newstudent.save()
			return redirect('login')
		else:
			return error(request,"Don't try random urls!")
	else:
		return error(request,"Don't try to be smart!! We ensure quite enough security!! :)")

def stud_login(request):
	# print(request.body.decode('utf-8'))
	body_unicode = request.body.decode('utf-8')
	body = json.loads(body_unicode)
	username = body['username']
	password = body['password']
	user = authenticate(username="s:"+username,password=password)
	response_data = {}
	if user is not None:
		auth_login(request,user)
		response_data['name'] = user.first_name;
		response_data['uname'] = user.username;
		return HttpResponse(json.dumps(response_data),content_type="application/json")
	else:
		response_data['name'] = 'Does Not Exist';
		response_data['uname'] = 'invalid access';
		return HttpResponse(json.dumps(response_data),content_type="application/json")

def questionjson(question):
	return "{\"question_type\":\""+question.question_type+"\",\"question\":\"" + question.question + "\",}"

def deadlinejson(deadline):
	jsonstr = "{\"date\":\"" + str(deadline.date) + "\",\"name\":\"" + deadline.name + "\",\"description\":\"" + deadline.desc + "\"," 
	if deadline.name == 'FD':
		jsonstr = jsonstr + "\"feedbackname\":\"" + deadline.feedback.name + "\",\"questionset\":[" 
		for question in deadline.feedback.question_set.all():
			jsonstr = jsonstr + questionjson(question) + ","
		jsonstr = jsonstr + "],"
	jsonstr = jsonstr + "}"
	return jsonstr

def coursejson(course):
	jsonstr = "{\"course_name\":\""+course.course_name+"\",\"course_code\":\""+course.course_code+"\",\"deadlines\":["
	for deadline in course.deadlines_set.all():
		jsonstr = jsonstr + deadlinejson(deadline) + ","
	jsonstr = jsonstr + "]}"
	return jsonstr

def stud_home(request):
	body_unicode = request.body.decode('utf-8')
	body = json.loads(body_unicode)
	username1 = body["username"]
	# username1 is the required username
	# print(username1)
	user = User.objects.get(username = username1)
	jsonfinal = "{\"courses\":["
	#do something
	for course in user.student.course_set.all():
		jsonfinal = jsonfinal + coursejson(course) + ","
	jsonfinal = jsonfinal + "]}"
	# data = serializers.serialize('json',user.student.course_set.all(),fields=('course_name'))
	# print(jsonfinal)
	return HttpResponse(jsonfinal,content_type="application/json") 
	# # print(data)
	# struct = json.loads(data)
	# # temp_arr = [val[0] for val in data.itervalues()]
	# # print(temp_arr)
	# # print(struct)
	# print(struct[0])
	# # data = json.dumps(struct[0])
	# data = [json.dumps(item) for item in struct]
	# data = json.dumps(data)
	# print(data)
	# return HttpResponse(data,content_type="application/json")
	# # return HttpResponse(json.dumps(data['fields']),content_type="application/json")

def error(request, error):
	return render(request,'error.html',{
		'error':error
		})
