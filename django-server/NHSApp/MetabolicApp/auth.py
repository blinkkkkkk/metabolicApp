from django.contrib.auth.models import User
from django.utils.crypto import get_random_string

from .models import Clinician, Patient
from django.conf import settings
from django.contrib.auth.hashers import check_password, UNUSABLE_PASSWORD_SUFFIX_LENGTH, UNUSABLE_PASSWORD_PREFIX, \
    get_hasher

def make_password(password, salt=None, hasher='default'):
    if password is None:
        return UNUSABLE_PASSWORD_PREFIX + get_random_string(UNUSABLE_PASSWORD_SUFFIX_LENGTH)
    hasher = get_hasher(hasher)
    if not salt:
        salt = hasher.salt()
    return hasher.encode(password, salt), salt


class ClinicianBackend:
    def authenticate(self, username=None, password=None):
        try:
            clinician = Clinician.objects.get(username=username)
            password_hash, salt = make_password(password, clinician.password_salt)
            if(password_hash == clinician.password_hash):
                return clinician
            else:
                return None
        except Clinician.DoesNotExist:
            return None

    def get_user(self, user_id):
        try:
            return Clinician.objects.get(pk=user_id)
        except Clinician.DoesNotExist:
            return None

class PatientBackend:
    def authenticate(self, username=None, password=None):
        password_hash = make_password(password)
        try:
            patient = Patient.objects.get(username=username)
            password_hash, salt = make_password(password, patient.password_salt)
            if(password_hash == patient.password_hash):
                return patient
            else:
                return None
        except Patient.DoesNotExist:
            return None

    def get_user(self, user_id):
        try:
            return Patient.objects.get(pk=user_id)
        except Patient.DoesNotExist:
            return None