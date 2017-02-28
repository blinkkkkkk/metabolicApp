import datetime
import jws as jws
import rest_framework
from django.contrib.auth.base_user import BaseUserManager
from django.contrib.auth.decorators import login_required
from django.http import Http404
from django.http import HttpResponse
from django.shortcuts import render
from django.urls import reverse, exceptions
from django.views.decorators.csrf import csrf_exempt
from rest_framework import generics
from rest_framework import mixins
from rest_framework import permissions
from rest_framework import status
from rest_framework import viewsets
from rest_framework.authtoken.models import Token
from rest_framework.decorators import api_view, permission_classes
from rest_framework.renderers import JSONRenderer
from rest_framework.response import Response
from rest_framework.views import APIView
from MetabolicApp.auth import PatientBackend, JWTAuthentication

from rest_framework import exceptions
import MetabolicApp
from MetabolicApp.models import TestResult, Patient
from MetabolicApp.rest_api.serializers import TestResultSerializer, PatientSerializer


@login_required(login_url="/login/")
def homeView(request):
    options = [{'name':'View Patient Options', 'url': reverse('Metabolic_App:view_patients')},
               {'name':'View personal Inbox', 'url' : reverse('Metabolic_App:view_inbox')},
               ]
    return render(request, 'MetabolicApp/home.html', {'options': options})


class PatientList(mixins.ListModelMixin, mixins.CreateModelMixin,generics.GenericAPIView):
    queryset = Patient.objects.all()
    serializer_class = PatientSerializer

    def get(self, request, *args, **kwargs):
        return self.list(request, *args, **kwargs)

    def post(self, request, *args, **kwargs):
        return self.create(request, *args, **kwargs)

class PatientLogin(APIView):
    permission_classes = (permissions.IsAuthenticated,)

    def get(self, request, format=None):
        patients = Patient.objects.all()

def obtain_jwt_token(request):
    try:
        username = request.POST['username']
        password = request.POST['password']
    except KeyError:
        raise rest_framework.exceptions.NotAcceptable
    try:
        user = PatientBackend().authenticate(username=username, password=password)
    except:
        raise rest_framework.exceptions.AuthenticationFailed
    expiry = datetime.date.today() + datetime.timedelta(days=30)
    token = jws.sign({'username':user.username, 'expiry':expiry}, 'seKre8', algorithm='HS256')
    return HttpResponse(token)

@login_required(login_url='/api-login/')
def getTestResults(request):
    for i in request.POST.items():
        print(i)