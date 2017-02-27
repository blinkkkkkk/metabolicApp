from django.test import TestCase
from django.urls import reverse

from MetabolicApp.testing.utility.mock_constructors import create_Clinician


# Create your testing here.
class ClinicianAuthenticationTests(TestCase):

    def test_login_page_accessible(self):
        response = self.client.get(reverse('Metabolic_App:login'))
        self.assertEqual(response.status_code, 200)

    def test_certified_clinician_can_login(self):
        create_Clinician('test', 'test')
        response = self.client.post(reverse('Metabolic_App:login'), {'username': 'test', 'password': 'test'})
        self.assertRedirects(response, reverse('Metabolic_App:home'))
        self.assertIn('_auth_user_id', self.client.session)

    def test_uncertified_clinician_can_not_login(self):
        response = self.client.post(reverse('Metabolic_App:login'), {'username': 'test', 'password': 'test'})
        self.assertEqual(response.status_code, 200)
        self.assertNotIn('_auth_user_id', self.client.session)

    def test_partially_correct_data_still_prevents_login(self):
        create_Clinician('test', 'test')
        response = self.client.post(reverse('Metabolic_App:login'), {'username': 'test'})
        self.assertNotIn('_auth_user_id', self.client.session)
        response = self.client.post(reverse('Metabolic_App:login'), {'password': 'test'})
        self.assertNotIn('_auth_user_id', self.client.session)

    def test_logout_verification_page_accessible_to_logged_in_user(self):
        create_Clinician('test', 'test')
        self.client.post(reverse('Metabolic_App:login'), {'username': 'test', 'password': 'test'})
        response = self.client.get(reverse('Metabolic_App:logout'))
        self.assertEqual(response.status_code, 200)

    def test_logout_verification_page_logs_out_user(self):
        create_Clinician('test', 'test')
        self.client.post(reverse('Metabolic_App:login'), {'username': 'test', 'password': 'test'})
        response = self.client.post(reverse('Metabolic_App:logout'))
        self.assertRedirects(response, reverse('Metabolic_App:login'))
        self.assertNotIn('_auth_user_id', self.client.session)

    def test_logout_page_inaccessible_to_logged_out_user(self):
        response = self.client.get(reverse('Metabolic_App:logout'))
        self.assertRedirects(response, reverse('Metabolic_App:login')+"?next="+reverse('Metabolic_App:logout'))
