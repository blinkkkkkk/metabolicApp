# -*- coding: utf-8 -*-
# Generated by Django 1.10.5 on 2017-02-28 06:18
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('MetabolicApp', '0005_auto_20170228_0607'),
    ]

    operations = [
        migrations.AddField(
            model_name='patient',
            name='is_active',
            field=models.BooleanField(default=True, verbose_name='active'),
        ),
    ]
