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
	student_ldap = models.CharField(max_length=50,primary_key=True)
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
	instructor_ldap = models.CharField(max_length=50,primary_key=True)
	instructor_branch = models.CharField(max_length = 2,choices = BRANCHES)
	instructor_dob = models.DateField()
