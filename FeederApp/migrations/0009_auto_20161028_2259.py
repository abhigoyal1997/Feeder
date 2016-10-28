# -*- coding: utf-8 -*-
# Generated by Django 1.10.2 on 2016-10-28 22:59
from __future__ import unicode_literals

from django.conf import settings
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
        ('FeederApp', '0008_auto_20161025_1947'),
    ]

    operations = [
        migrations.CreateModel(
            name='Course',
            fields=[
                ('course_code', models.CharField(max_length=8, primary_key=True, serialize=False)),
                ('course_name', models.CharField(max_length=100)),
                ('course_branch', models.CharField(choices=[('CS', 'Computer Science and Engineering'), ('EE', 'Electrical Engineering'), ('ME', 'Mechanical Engineering'), ('CL', 'Chemical Engineering')], max_length=2)),
                ('course_credits', models.IntegerField()),
                ('course_duration', models.DecimalField(decimal_places=1, max_digits=2)),
            ],
        ),
        migrations.CreateModel(
            name='Instructor',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('instructor_branch', models.CharField(choices=[('CS', 'Computer Science and Engineering'), ('EE', 'Electrical Engineering'), ('ME', 'Mechanical Engineering'), ('CL', 'Chemical Engineering')], max_length=2)),
                ('instructor_dob', models.DateField()),
                ('user', models.OneToOneField(on_delete=django.db.models.deletion.CASCADE, to=settings.AUTH_USER_MODEL)),
            ],
        ),
        migrations.CreateModel(
            name='Student',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('student_branch', models.CharField(choices=[('CS', 'Computer Science and Engineering'), ('EE', 'Electrical Engineering'), ('ME', 'Mechanical Engineering'), ('CL', 'Chemical Engineering')], max_length=2)),
                ('student_dob', models.DateField()),
                ('student_program', models.CharField(choices=[('BT', 'B.Tech.'), ('MT', 'M.Tech.'), ('DD', 'Dual Degree')], max_length=2)),
                ('student_year', models.IntegerField()),
                ('user', models.OneToOneField(on_delete=django.db.models.deletion.CASCADE, to=settings.AUTH_USER_MODEL)),
            ],
        ),
        migrations.AddField(
            model_name='course',
            name='instructors',
            field=models.ManyToManyField(to='FeederApp.Instructor'),
        ),
        migrations.AddField(
            model_name='course',
            name='students',
            field=models.ManyToManyField(to='FeederApp.Student'),
        ),
    ]
