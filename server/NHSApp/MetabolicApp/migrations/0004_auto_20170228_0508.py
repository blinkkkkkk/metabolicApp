# -*- coding: utf-8 -*-
# Generated by Django 1.10.5 on 2017-02-28 05:08
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('MetabolicApp', '0003_clinician_last_login'),
    ]

    operations = [
        migrations.AlterField(
            model_name='testresult',
            name='test_type',
            field=models.CharField(choices=[('CHOLESTEROL', 'Fasting cholesterol'), ('LIPIDS', 'Lipids'), ('PROLACTIN', 'Prolactin'), ('RENAL', 'Renal_Functions'), ('BLOOD', 'Blood_pressure'), ('ECG', 'ECGs'), ('BMI', 'BMI'), ('FBSUGAR', 'Fasting_blood_sugar'), ('HBA1C', 'HbA1c'), ('LIVER', 'Liver_function')], max_length=15),
        ),
    ]