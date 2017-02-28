from pathlib import _Accessor

from rest_framework import serializers
from MetabolicApp.models import TestResult, Patient


class TestResultSerializer(serializers.ModelSerializer):
    class Meta:
        model = TestResult
        #fields = ['test_type', 'test_data', 'next_test', 'message', 'date_created']

class PatientSerializer(serializers.ModelSerializer):
    tasks = serializers.PrimaryKeyRelatedField(
        many=True, queryset=TestResult.objects.all()
    )

    class Meta:
        model = Patient
        #fields = ['first_name', 'last_name', 'DoB', 'NHS_no']