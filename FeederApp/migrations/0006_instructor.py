# -*- coding: utf-8 -*-
# Generated by Django 1.10.2 on 2016-10-19 16:39
from __future__ import unicode_literals

from django.conf import settings
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
        ('FeederApp', '0005_student'),
    ]

    operations = [
        migrations.CreateModel(
            name='Instructor',
            fields=[
                ('instructor_ldap', models.CharField(max_length=50, primary_key=True, serialize=False)),
                ('instructor_branch', models.CharField(choices=[('CS', 'Computer Science and Engineering'), ('EE', 'Electrical Engineering'), ('ME', 'Mechanical Engineering'), ('CH', 'Chemical Engineering')], max_length=2)),
                ('instructor_dob', models.DateField()),
                ('user', models.OneToOneField(on_delete=django.db.models.deletion.CASCADE, to=settings.AUTH_USER_MODEL)),
            ],
        ),
    ]
