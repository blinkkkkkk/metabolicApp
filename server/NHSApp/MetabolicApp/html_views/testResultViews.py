import datetime

from django.contrib.auth.decorators import login_required
from django.http import HttpResponseRedirect
from django.shortcuts import render
from django.urls import reverse

from MetabolicApp import apps
from MetabolicApp.forms import TestResultSelectForm, TestResultInsertForm
from MetabolicApp.models import Clinician, Patient, TestResult


@login_required(login_url="/login/")
def patientSelectView(request):
    clincian_id = request.session['_auth_user_id']
    clincian = Clinician.objects.get(id=clincian_id)
    patients = Patient.getAuthorizedPatients(clinician_id=clincian)
    return render(request, 'MetabolicApp/view_patient_list.html', {'patients':patients})


@login_required(login_url="/login/")
def patientView(request, patient_id):
    clincian_id = request.session['_auth_user_id']
    clincian = Clinician.objects.get(id=clincian_id)
    try:
        patient = Patient.objects.get(id=patient_id)
    except Patient.DoesNotExist:
        return render(request, 'MetabolicApp/unauthorized_request.html', {})

    if Patient.checkDataAccessRequest(clinician_id=clincian, patient_id=patient):
        return render(request, 'MetabolicApp/view_patient.html', {'patient':patient})
    else:
        return render(request, 'MetabolicApp/unauthorized_request.html', {})


@login_required(login_url="/login/")
def selectTrendTypeView(request, patient_id, option):

    if option == 'view':
        url = 'Metabolic_App:view_patient_data'
    elif option == 'input':
        url = 'Metabolic_App:input_patient_data'
    else:
        return render(request, 'MetabolicApp/unauthorized_request.html', {})

    clincian_id = request.session['_auth_user_id']
    clincian = Clinician.objects.get(id=clincian_id)
    try:
        patient = Patient.objects.get(id=patient_id)
    except Patient.DoesNotExist:
        return render(request, 'MetabolicApp/unauthorized_request.html', {})

    if Patient.checkDataAccessRequest(clinician_id=clincian, patient_id=patient):
        if request.method == 'GET':
            form = TestResultSelectForm()
            return render(request, 'MetabolicApp/select_trend.html', {'patient': patient, 'form': form})
        else:
            form = TestResultSelectForm(request.POST)
            if form.is_valid():
                trend_type = form.cleaned_data['select']
                return HttpResponseRedirect(reverse(url, args=[patient_id, trend_type]))
            else:
                return render(request, 'MetabolicApp/unauthorized_request.html', {})

    else:
        return render(request, 'MetabolicApp/unauthorized_request.html', {})


@login_required(login_url="/login/")
def viewPatientDataView(request, patient_id, test_type):
    clincian_id = request.session['_auth_user_id']
    clincian = Clinician.objects.get(id=clincian_id)
    try:
        patient = Patient.objects.get(id=patient_id)
    except Patient.DoesNotExist:
        return render(request, 'MetabolicApp/unauthorized_request.html', {})

    if Patient.checkDataAccessRequest(patient_id=patient, clinician_id=clincian) and TestResult.isValidType(test_type=test_type):
        results = TestResult.getTestResultsForPatient(patient_id=patient, test_type=test_type)
        return render(request, 'MetabolicApp/view_patient_results.html', {'type': TestResult.getReadableTypeName(test_type=test_type), 'results': results, 'patient': patient})
    else:
        return render(request, 'MetabolicApp/unauthorized_request.html', {})


@login_required(login_url="/login/")
def inputPatientDataView(request, patient_id, test_type):
    clincian_id = request.session['_auth_user_id']
    clincian = Clinician.objects.get(id=clincian_id)
    try:
        patient = Patient.objects.get(id=patient_id)
    except Patient.DoesNotExist:
        return render(request, 'MetabolicApp/unauthorized_request.html', {})

    if Patient.checkDataAccessRequest(patient_id=patient, clinician_id=clincian) and TestResult.isValidType(test_type=test_type):
        if request.method == 'GET':
            form = TestResultInsertForm()
            return render(request, 'MetabolicApp/input_patient_results.html', {'type': TestResult.getReadableTypeName(test_type=test_type), 'form': form, 'patient': patient})
        else:
            form = TestResultInsertForm(request.POST, request.FILES)
            form.is_valid()
            test_data = form.cleaned_data.get('test_data', None)
            message = form.cleaned_data.get('message', None)
            if(test_data is None or message is None):
                return render(request, 'MetabolicApp/input_patient_results.html', {'type': TestResult.getReadableTypeName(test_type=test_type), 'form': form, 'patient': patient, 'error_message': 'You must enter test results and a message to submit.'})
            next_date = form.cleaned_data.get('next_test', None)
            print(apps.DEFAULT_TEST_DATES.items())
            if next_date is None:
                next_date = datetime.datetime.now() + apps.DEFAULT_TEST_DATES[test_type]
            print(next_date)
            TestResult.objects.create(test_type=test_type, test_data=test_data, message=message, patient_id=patient, clinician_id=clincian, next_test=next_date).save()
            return HttpResponseRedirect(reverse('Metabolic_App:view_patient', args=[patient_id]))

    else:
        return render(request, 'MetabolicApp/unauthorized_request.html', {})