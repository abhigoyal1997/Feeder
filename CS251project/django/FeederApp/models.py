from __future__ import unicode_literals
from django.contrib.auth.models import User
from django.db import models
from datetime import date

class Student(models.Model):
	user = models.OneToOneField(User, on_delete=models.CASCADE)	
	BRANCHES = (
        ('CS','Computer Science and Engineering'),
        ('EE', 'Electrical Engineering'),
        ('ME', 'Mechanical Engineering'),
        ('CL','Chemical Engineering'),
    )
	PROGRAM = (
		('BT','B.Tech.'),
		('MT','M.Tech.'),
		('DD','Dual Degree'),
	)
	roll_number = models.CharField(null = True,max_length=10)
	student_branch = models.CharField(null = True,max_length = 2,choices = BRANCHES)
	student_dob = models.DateField(null = True)
	student_program = models.CharField(null = True,max_length = 2,choices=PROGRAM)
	student_year  = models.IntegerField(null = True)

class Instructor(models.Model):
	user = models.OneToOneField(User, on_delete=models.CASCADE)	
	BRANCHES = (
        ('CS','Computer Science and Engineering'),
        ('EE', 'Electrical Engineering'),
        ('ME', 'Mechanical Engineering'),
        ('CL','Chemical Engineering'),
    )
	instructor_branch = models.CharField(null = True,max_length = 2,choices = BRANCHES)

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
	course_semester = models.IntegerField()

class Deadlines(models.Model):
	NAME = (
		('ASS','Assignment'),
		('EX','Examination'),
		('FD','FeedBack'),
		)
	name = models.CharField(max_length=3,choices=NAME)
	desc = models.CharField(max_length = 100)
	date = models.DateField()
	code = models.CharField(max_length=10)
	course = models.ForeignKey(Course,on_delete=models.CASCADE,null =True)
	@property
	def is_past_due(self):
		if date.today() > self.date:
			return True
		return False


class Feedback(models.Model):
	deadline = models.OneToOneField(Deadlines,on_delete=models.CASCADE)
	course = models.ForeignKey(Course,on_delete=models.CASCADE)	
	name = models.CharField(max_length = 100)
	id = models.AutoField(primary_key=True)

class Question(models.Model):
	TYPES = (
		('RB','Radio Buttons'),
		('TF','Text Field'),	
		)
	qid = models.AutoField(primary_key=True)
	question = models.CharField(max_length=100)
	question_type = models.CharField(max_length=2,choices = TYPES)
	response = models.TextField(null = True)
	feedback = models.ForeignKey(Feedback,on_delete=models.CASCADE,null = True)
