from django.contrib.auth.decorators import login_required
from django.http import HttpResponse
from django.shortcuts import render
from django.urls import reverse
from django.views.decorators.csrf import csrf_exempt
from rest_framework.renderers import JSONRenderer

from MetabolicApp.models import TestResult
from MetabolicApp.rest_api.serializers import TestResultSerializer


@login_required(login_url="/login/")
def homeView(request):
    options = [{'name':'View Patient Options', 'url': reverse('Metabolic_App:view_patients')},
               {'name':'View personal Inbox', 'url' : reverse('Metabolic_App:view_inbox')},
               ]
    return render(request, 'MetabolicApp/home.html', {'options': options})


class JSONResponse(HttpResponse):

    def __init__(self, data, **kwargs):
        content = JSONRenderer().render(data)
        kwargs['content_type']='application/json'
        super(JSONResponse, self).__init__(content, **kwargs)


@csrf_exempt
def getTestResults(request):
    if request.method == 'GET':
        testResults = TestResult.objects.all()
        serializer = TestResultSerializer(testResults, many=True)
        return JSONResponse(serializer.data)

