from django import forms
from django.contrib.auth import login, logout
from django.contrib.auth.decorators import login_required
from django.http import HttpResponseRedirect
from django.shortcuts import render
from django.urls import reverse

from MetabolicApp.auth import ClinicianBackend


class LoginForm(forms.Form):
    username = forms.CharField(max_length=100, label="Username")
    password = forms.CharField(max_length=100, label="Password")


def loginView(request):
    context = {}
    if request.method == 'POST':

        form = LoginForm(request.POST)

        if form.is_valid():
            try:
                username = request.POST['username']
                password = request.POST['password']
            except KeyError:
                context['error_message'] = 'Please enter details to login.'

            authenticator = ClinicianBackend()
            user = authenticator.authenticate(username=username, password=password)

            if user is not None:
                login(request, user, 'MetabolicApp.auth.ClinicianBackend')
                return HttpResponseRedirect(reverse('Metabolic_App:home'))
            else:
                context['error_message'] = 'Could not verify username or password on system. Please try again.'

        else:
            context['error_message'] = 'Password and Username must be <= 100 characters.'

    form = LoginForm()
    context['form'] =  form
    return render(request, 'MetabolicApp/login/login.html', context)


@login_required(login_url="/login/")
def logoutView(request):
    if request.method == 'POST':
        logout(request)
        return HttpResponseRedirect(reverse('Metabolic_App:login'))
    else:
        return render(request, 'MetabolicApp/login/confirm_logout.html')