import random
from datetime import datetime

from MetabolicApp.auth import make_password
from MetabolicApp.models import Clinician, Inbox, Patient


def create_Clinician(username, password, GMC_no = None, Practice_no = None):
    if GMC_no is None:
        GMC_no = str(random.Random().randint(10000,10000000))
    if Practice_no is None:
        Practice_no = str(random.Random().randint(10000,10000000))
    password_hash, password_salt = make_password(password)
    return Clinician.objects.create(username=username, password_hash=password_hash, password_salt=password_salt, GMC_no=GMC_no, practice_no=Practice_no)

def create_Patient(username, password, first_name=None, last_name=None, DoB=None, NHS_no=None, clinician_id=None):
    if first_name is None:
        first_name = username
    if last_name is None:
        last_name =  username
    if DoB is None:
        DoB = datetime.utcnow()
    if NHS_no is None:
        NHS_no = str(random.Random().randint(10000, 10000000))
    password_hash, password_salt = make_password(password)
    return Patient.objects.create(username=username, password_hash=password_hash, password_salt=password_salt, first_name=first_name, last_name=last_name, DoB=DoB, NHS_no=NHS_no, clinician_id=clinician_id)

def create_Inbox_Entry(clinician,patient, entry_type, message="Hello", image=None):
    return Inbox.objects.create(clinician_id=clinician, patient_id=patient, entry_type=entry_type, message=message,image=image)