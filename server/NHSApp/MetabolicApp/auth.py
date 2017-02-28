from datetime import timedelta

import datetime

import jws
from django.contrib.auth.models import User
from django.utils.crypto import get_random_string
from rest_framework import exceptions
from rest_framework.authentication import get_authorization_header

from MetabolicApp.models import Clinician, Patient
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
    def authenticate(self, username=None, password=None, token=None):
        if token:
            last_time = datetime.datetime.today() - timedelta(days=settings.TOKEN_EXPIRY_DATE)

            try:
                patient = Patient.objects.filter(access_token=token, access_token_created_on=last_time)[0]
            except IndexError:
                return None
        elif username and password:
            try:
                patient = Patient.objects.get(username=username)
                password_hash, salt = make_password(password, patient.password_salt)
                if(password_hash == patient.password_hash):
                    return patient
                else:
                    return None
            except Patient.DoesNotExist:
                return None
        else:
            return None

    def get_user(self, user_id):
        try:
            return Patient.objects.get(pk=user_id)
        except Patient.DoesNotExist:
            return None


class JWTAuthentication(object):
    def authenticate(self, request):
        auth = get_authorization_header(request).split()

        if not auth or auth[0].lower != b'token':
            return None

        try:
            token = auth[1].decode()
        except UnicodeError:
            msg = ('Could not decode token, invalid token header.')
            raise exceptions.AuthenticationFailed(msg)

        return self.authenticate_credentials(token)

    def authenticate_credentials(self, payload):

        decoded_dict = jws.verify(payload, 'seKre8', algorithms=['HS256'])
        username = decoded_dict.get('username', None)
        expiry = decoded_dict.get('expiry', None)

        try:
            user = Patient.objects.get(username=username)
        except Patient.DoesNotExist:
            raise exceptions.AuthenticationFailed(('Invalid_token.'))

        if not user.is_active:
            raise exceptions.AuthenticationFailed('User Inactive or deleted')

        if expiry < datetime.date.today():
            raise exceptions.AuthenticationFailed('Token expired')

        return (user, payload)


    def authenticate_header(self, request):
        return 'Token'