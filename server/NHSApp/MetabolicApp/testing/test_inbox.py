from django.test import TestCase
from django.urls import reverse

from MetabolicApp.models import Inbox, Patient
from MetabolicApp.testing.utility.mock_constructors import create_Clinician, create_Inbox_Entry, create_Patient

class ClinicianInboxTests(TestCase):

    def test_inbox_inaccessible_to_unauthenticated_user(self):
        response = self.client.get(reverse('Metabolic_App:view_inbox'))
        self.assertRedirects(response, reverse('Metabolic_App:login') + "?next=" + reverse('Metabolic_App:view_inbox'))

    def test_inbox_accessible_to_authenticated_user(self):
        create_Clinician('test', 'test')
        self.client.post(reverse('Metabolic_App:login'), {'username': 'test', 'password': 'test'})
        response = self.client.get(reverse('Metabolic_App:view_inbox'))
        self.assertEqual(response.status_code, 200)

    def test_inbox_displays_all_submissions_for_clinician(self):
        clinician = create_Clinician('test_clinician', 'test_clinician')
        patient = create_Patient('test_patient','test_patient')
        entry = create_Inbox_Entry(clinician, patient, 'SUBMISSION')
        self.client.post(reverse('Metabolic_App:login'), {'username': 'test_clinician', 'password': 'test_clinician'})
        response = self.client.get(reverse('Metabolic_App:view_inbox'))
        self.assertContains(response, 'SUBMISSION')
        self.assertContains(response, 'test_patient')

    def test_inbox_displays_only_submissions_for_clinician(self):
        clinician = create_Clinician('test_clinician', 'test_clinician')
        clinician2 = create_Clinician('test_clinician2', 'test_clinician2')
        patient = create_Patient('test_patient','test_patient')
        entry = create_Inbox_Entry(clinician2, patient, 'SUBMISSION')
        self.client.post(reverse('Metabolic_App:login'), {'username': 'test_clinician', 'password': 'test_clinician'})
        response = self.client.get(reverse('Metabolic_App:view_inbox'))
        self.assertNotContains(response, 'SUBMISSION')
        self.assertNotContains(response, 'test_patient')

    def test_inbox_displays_all_requests_for_clinician(self):
        clinician = create_Clinician('test_clinician', 'test_clinician')
        patient = create_Patient('test_patient','test_patient')
        entry = create_Inbox_Entry(clinician, patient, 'REQUEST')
        self.client.post(reverse('Metabolic_App:login'), {'username': 'test_clinician', 'password': 'test_clinician'})
        response = self.client.get(reverse('Metabolic_App:view_inbox'))
        self.assertContains(response, 'REQUEST')
        self.assertContains(response, 'test_patient')

    def test_inbox_displays_only_request_for_clinician(self):
        clinician = create_Clinician('test_clinician', 'test_clinician')
        clinician2 = create_Clinician('test_clinician2', 'test_clinician2')
        patient = create_Patient('test_patient', 'test_patient')
        entry = create_Inbox_Entry(clinician2, patient, 'REQUEST')
        self.client.post(reverse('Metabolic_App:login'), {'username': 'test_clinician', 'password': 'test_clinician'})
        response = self.client.get(reverse('Metabolic_App:view_inbox'))
        self.assertNotContains(response, 'REQUEST')
        self.assertNotContains(response, 'test_patient')

    def test_inbox_links_to_correct_page_for_request(self):
        clinician = create_Clinician('test_clinician', 'test_clinician')
        patient = create_Patient('test_patient','test_patient')
        entry = create_Inbox_Entry(clinician, patient, 'REQUEST')
        self.client.post(reverse('Metabolic_App:login'), {'username': 'test_clinician', 'password': 'test_clinician'})
        response = self.client.get(reverse('Metabolic_App:view_inbox'))
        self.assertContains(response, '<a href="%s">' % (reverse("Metabolic_App:view_inbox_request", args=[entry.id])))

    def test_inbox_links_to_correct_page_for_submission(self):
        clinician = create_Clinician('test_clinician', 'test_clinician')
        patient = create_Patient('test_patient','test_patient')
        entry = create_Inbox_Entry(clinician, patient, 'SUBMISSION')
        self.client.post(reverse('Metabolic_App:login'), {'username': 'test_clinician', 'password': 'test_clinician'})
        response = self.client.get(reverse('Metabolic_App:view_inbox'))
        self.assertContains(response, '<a href="%s">' % (reverse("Metabolic_App:view_inbox_submission", args=[entry.id])))

    def test_submission_page_inaccessible_to_unauthenticated_user(self):
        clinician = create_Clinician('test_clinician', 'test_clinician')
        patient = create_Patient('test_patient','test_patient')
        entry = create_Inbox_Entry(clinician, patient, 'SUBMISSION')
        response = self.client.get(reverse("Metabolic_App:view_inbox_submission", args=[entry.id]))
        self.assertRedirects(response, reverse('Metabolic_App:login') + "?next=" + reverse("Metabolic_App:view_inbox_submission", args=[entry.id]))

    def test_request_page_inaccessible_to_unauthenticated_user(self):
        clinician = create_Clinician('test_clinician', 'test_clinician')
        patient = create_Patient('test_patient', 'test_patient')
        entry = create_Inbox_Entry(clinician, patient, 'REQUEST')
        response = self.client.get(reverse("Metabolic_App:view_inbox_request", args=[entry.id]))
        self.assertRedirects(response, reverse('Metabolic_App:login') + "?next=" + reverse("Metabolic_App:view_inbox_request", args=[entry.id]))

    def test_submission_page_accessible_to_authenticated_recipient(self):
        clinician = create_Clinician('test_clinician', 'test_clinician')
        patient = create_Patient('test_patient', 'test_patient')
        entry = create_Inbox_Entry(clinician, patient, 'SUBMISSION')
        self.client.post(reverse('Metabolic_App:login'), {'username': 'test_clinician', 'password': 'test_clinician'})
        response = self.client.get(reverse("Metabolic_App:view_inbox_submission", args=[entry.id]))
        self.assertEqual(response.status_code, 200)

    def test_request_page_accessible_to_authenticated_recipient(self):
        clinician = create_Clinician('test_clinician', 'test_clinician')
        patient = create_Patient('test_patient', 'test_patient')
        entry = create_Inbox_Entry(clinician, patient, 'REQUEST')
        self.client.post(reverse('Metabolic_App:login'), {'username': 'test_clinician', 'password': 'test_clinician'})
        response = self.client.get(reverse("Metabolic_App:view_inbox_request", args=[entry.id]))
        self.assertEqual(response.status_code, 200)

    def test_submission_page_inaccessible_to_authenticated_non_recipient(self):
        clinician = create_Clinician('test_clinician', 'test_clinician')
        clinician2 = create_Clinician('test_clinician2','test_clinician2')
        patient = create_Patient('test_patient', 'test_patient')
        entry = create_Inbox_Entry(clinician2, patient, 'SUBMISSION')
        self.client.post(reverse('Metabolic_App:login'), {'username': 'test_clinician', 'password': 'test_clinician'})
        response = self.client.get(reverse("Metabolic_App:view_inbox_submission", args=[entry.id]))
        self.assertTemplateUsed('MetabolicApp/unauthorized_request.html')

    def test_request_page_inaccessible_to_authenticated_non_recipient(self):
        clinician = create_Clinician('test_clinician', 'test_clinician')
        clinician2 = create_Clinician('test_clinician2','test_clinician2')
        patient = create_Patient('test_patient', 'test_patient')
        entry = create_Inbox_Entry(clinician2, patient, 'REQUEST')
        self.client.post(reverse('Metabolic_App:login'), {'username': 'test_clinician', 'password': 'test_clinician'})
        response = self.client.get(reverse("Metabolic_App:view_inbox_request", args=[entry.id]))
        self.assertTemplateUsed('MetabolicApp/unauthorized_request.html')

    def test_request_page_displays_correct_data_for_request(self):
        clinician = create_Clinician('test_clinician', 'test_clinician')
        patient = create_Patient('test_patient', 'test_patient')
        entry = create_Inbox_Entry(clinician, patient, 'REQUEST')
        self.client.post(reverse('Metabolic_App:login'), {'username': 'test_clinician', 'password': 'test_clinician'})
        response = self.client.get(reverse("Metabolic_App:view_inbox_request", args=[entry.id]))
        self.assertContains(response, patient.first_name)
        self.assertContains(response, patient.last_name)
        self.assertContains(response, patient.NHS_no)
        self.assertContains(response, patient.DoB.strftime("%b. %d, %Y"))

    def test_submission_page_displays_correct_data_for_submission(self):
        clinician = create_Clinician('test_clinician', 'test_clinician')
        patient = create_Patient('test_patient', 'test_patient')
        entry = create_Inbox_Entry(clinician, patient, 'REQUEST')
        self.client.post(reverse('Metabolic_App:login'), {'username': 'test_clinician', 'password': 'test_clinician'})
        response = self.client.get(reverse("Metabolic_App:view_inbox_request", args=[entry.id]))
        self.assertContains(response, patient.first_name)
        self.assertContains(response, patient.last_name)

    def test_accept_request_removes_request(self):
        clinician = create_Clinician('test_clinician', 'test_clinician')
        patient = create_Patient('test_patient', 'test_patient')
        entry = create_Inbox_Entry(clinician, patient, 'REQUEST')
        self.client.post(reverse('Metabolic_App:login'), {'username': 'test_clinician', 'password': 'test_clinician'})
        self.client.post(reverse("Metabolic_App:view_inbox_request", args=[entry.id]), {'option': 'Accept'})
        self.assertQuerysetEqual(Inbox.objects.filter(clinician_id=clinician), [])

    def test_reject_request_removes_request(self):
        clinician = create_Clinician('test_clinician', 'test_clinician')
        patient = create_Patient('test_patient', 'test_patient')
        entry = create_Inbox_Entry(clinician, patient, 'REQUEST')
        self.client.post(reverse('Metabolic_App:login'), {'username': 'test_clinician', 'password': 'test_clinician'})
        self.client.post(reverse("Metabolic_App:view_inbox_request", args=[entry.id]), {'option': 'Reject'})
        self.assertQuerysetEqual(Inbox.objects.filter(clinician_id=clinician), [])

    def test_accept_request_adds_connection_to_clinician(self):
        clinician = create_Clinician('test_clinician', 'test_clinician')
        patient = create_Patient('test_patient', 'test_patient')
        entry = create_Inbox_Entry(clinician, patient, 'REQUEST')
        self.client.post(reverse('Metabolic_App:login'), {'username': 'test_clinician', 'password': 'test_clinician'})
        self.client.post(reverse("Metabolic_App:view_inbox_request", args=[entry.id]), {'option': 'Accept'})
        self.assertQuerysetEqual(Patient.objects.filter(clinician_id=clinician), [repr(patient)])

    def test_dismiss_submission_removes_request(self):
        clinician = create_Clinician('test_clinician', 'test_clinician')
        patient = create_Patient('test_patient', 'test_patient')
        entry = create_Inbox_Entry(clinician, patient, 'SUBMISSION')
        self.client.post(reverse('Metabolic_App:login'), {'username': 'test_clinician', 'password': 'test_clinician'})
        self.client.post(reverse("Metabolic_App:view_inbox_submission", args=[entry.id]), {'option': 'Dismiss'})
        self.assertQuerysetEqual(Inbox.objects.filter(clinician_id=clinician), [])
