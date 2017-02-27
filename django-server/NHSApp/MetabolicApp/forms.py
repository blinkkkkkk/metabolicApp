from django.forms import forms, ChoiceField, Select, ModelForm

from MetabolicApp.models import TestResult


class TestResultSelectForm(forms.Form):
    choices = TestResult.ENTRY_TYPES
    select = ChoiceField(widget=Select, choices=choices)

class TestResultInsertForm(ModelForm):
    class Meta:
        model = TestResult
        fields = ['test_data', 'message', 'next_test']
