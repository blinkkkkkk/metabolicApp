from rest_framework import serializers
from MetabolicApp.models import TestResult

class TestResultSerializer(serializers.ModelSerializer):
    class Meta:
        model = TestResult
        fields = ['test_type', 'test_data', 'next_test', 'message', 'date_created']

