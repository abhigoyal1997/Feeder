# -*- coding: utf-8 -*-
# Generated by Django 1.10.2 on 2016-11-02 20:40
from __future__ import unicode_literals

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('FeederApp', '0006_auto_20161102_1353'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='instructor',
            name='instructor_dob',
        ),
    ]
