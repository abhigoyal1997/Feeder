# -*- coding: utf-8 -*-
# Generated by Django 1.10.2 on 2016-10-25 19:47
from __future__ import unicode_literals

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('FeederApp', '0007_auto_20161019_1659'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='instructor',
            name='user',
        ),
        migrations.RemoveField(
            model_name='student',
            name='user',
        ),
        migrations.DeleteModel(
            name='Instructor',
        ),
        migrations.DeleteModel(
            name='Student',
        ),
    ]
