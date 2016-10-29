from __future__ import unicode_literals
from django.contrib.auth.models import User
from django.db import models

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
	roll_number = models.CharField(max_length=10)
	student_branch = models.CharField(max_length = 2,choices = BRANCHES)
	student_dob = models.DateField()
	student_program = models.CharField(max_length = 2,choices=PROGRAM)
	student_year  = models.IntegerField()

class Instructor(models.Model):
	user = models.OneToOneField(User, on_delete=models.CASCADE)	
	BRANCHES = (
        ('CS','Computer Science and Engineering'),
        ('EE', 'Electrical Engineering'),
        ('ME', 'Mechanical Engineering'),
        ('CL','Chemical Engineering'),
    )
	instructor_branch = models.CharField(max_length = 2,choices = BRANCHES)
	instructor_dob = models.DateField()

class Question(models.Model):
	TYPES = (
		('RB','Radio Buttons'),
		('TF','Text Field'),	
		)
	question = models.CharField(max_length=100)
	question_type = models.CharField(max_length=2,choices = TYPES)
	response = models.TextField(null = True)

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


class Feedback(models.Model):
	deadline = models.OneToOneField(Deadlines,on_delete=models.CASCADE)
	questions = models.ForeignKey(Question,on_delete=models.CASCADE)
	name = models.CharField(max_length = 100)
	id = models.AutoField(primary_key=True)

class Course(models.Model):
	deadline = models.ForeignKey(Deadlines,on_delete=models.CASCADE)
	instructors = models.ManyToManyField(Instructor)
	students = models.ManyToManyField(Student)
	feedbacks = models.ForeignKey(Feedback,on_delete=models.CASCADE)
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








