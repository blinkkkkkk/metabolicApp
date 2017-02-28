from django.contrib.auth.decorators import login_required
from django.http import HttpResponseRedirect
from django.shortcuts import render
from django.urls import reverse

from MetabolicApp.models import Clinician, Inbox


@login_required(login_url="/login/")
def clinicianInboxView(request):
    clincian_id = request.session['_auth_user_id']
    clincian = Clinician.objects.get(id=clincian_id)
    clincian_name = clincian.full_name
    entries = Inbox.getClinicianInboxEntries(clinician_id=clincian_id)
    return render(request, 'MetabolicApp/inbox/view_inbox.html', {'entries':entries, 'name': clincian_name})


@login_required(login_url="/login/")
def requestEntryView(request, inbox_entry_id):
    clinician_id = request.session['_auth_user_id']
    clinician = Clinician.objects.get(id=clinician_id)
    if Inbox.authorizedViewRequest(clinician_id, inbox_entry_id=inbox_entry_id):
        inbox_entry = Inbox.objects.get(id=inbox_entry_id, clinician_id=clinician)
        patient = inbox_entry.patient_id

        if request.method == 'GET':
            if inbox_entry.entry_type == 'SUBMISSION':
                return HttpResponseRedirect(reverse('Metabolic_App:view_inbox'))
            return render(request, 'MetabolicApp/inbox/view_request.html', {'entry': inbox_entry, 'patient': patient})

        else:
            try:
                option = request.POST['option']
            except KeyError:
                return render(request, 'MetabolicApp/error.html', {})

            if option == 'Accept':
                try:
                    patient.clinician_id = clinician
                    patient.save()
                    inbox_entry.delete()
                except Exception:
                    print("Error")
                    return render(request, 'MetabolicApp/error.html', {})

            elif option == 'Reject':
                inbox_entry.delete()

            else:
                return render(request, 'MetabolicApp/error.html', {})

            return HttpResponseRedirect(reverse('Metabolic_App:view_inbox'))
    else:
        return render(request, 'MetabolicApp/unauthorized_request.html', {})


@login_required(login_url="/login/")
def submissionEntryView(request, inbox_entry_id):
    clinician_id = request.session['_auth_user_id']
    if Inbox.authorizedViewRequest(clinician_id, inbox_entry_id=inbox_entry_id):
        inbox_entry = Inbox.objects.get(id=inbox_entry_id, clinician_id=clinician_id)
        if request.method == 'GET':
            if(inbox_entry.entry_type == 'REQUEST'):
                return HttpResponseRedirect(reverse('Metabolic_App:view_inbox'))
            patient = inbox_entry.patient_id
            return render(request, 'MetabolicApp/inbox/view_submission.html', {'entry': inbox_entry, 'patient': patient})
        else:
            try:
                option = request.POST['option']
            except KeyError:
                return render(request, 'MetabolicApp/error.html', {})
            if option == 'Dismiss':
                Inbox.delete(inbox_entry)
            return HttpResponseRedirect(reverse('Metabolic_App:view_inbox'))
    else:
        return render(request, 'MetabolicApp/unauthorized_request.html', {})