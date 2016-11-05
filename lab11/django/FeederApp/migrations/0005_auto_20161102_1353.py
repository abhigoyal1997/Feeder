# -*- coding: utf-8 -*-
# Generated by Django 1.10.2 on 2016-11-02 13:53
from __future__ import unicode_literals

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('FeederApp', '0004_auto_20161031_1918'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='course',
            name='instructors',
        ),
        migrations.RemoveField(
            model_name='course',
            name='students',
        ),
        migrations.RemoveField(
            model_name='deadlines',
            name='course',
        ),
        migrations.RemoveField(
            model_name='feedback',
            name='course',
        ),
        migrations.RemoveField(
            model_name='feedback',
            name='deadline',
        ),
        migrations.RemoveField(
            model_name='instructor',
            name='user',
        ),
        migrations.RemoveField(
            model_name='question',
            name='feedback',
        ),
        migrations.RemoveField(
            model_name='student',
            name='user',
        ),
        migrations.DeleteModel(
            name='Course',
        ),
        migrations.DeleteModel(
            name='Deadlines',
        ),
        migrations.DeleteModel(
            name='Feedback',
        ),
        migrations.DeleteModel(
            name='Instructor',
        ),
        migrations.DeleteModel(
            name='Question',
        ),
        migrations.DeleteModel(
            name='Student',
        ),
    ]
