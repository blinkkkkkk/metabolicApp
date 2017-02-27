from django.test import TestCase


class ClinicianTestResultInteractionTests(TestCase):

    def test_patients_list_inaccessible_to_unauthenticated_user(self):
        pass

    def test_patients_list_accessible_to_authenticated_clinician(self):
        pass

    def test_patients_list_displays_all_patients_for_clinician(self):
        pass

    def test_patients_list_displays_only_patients_for_clinicain(self):
        pass

    def test_patient_view_inaccessible_to_unauthenticated_user(self):
        pass

    def test_patient_view_accessible_to_authenticated_clinician(self):
        pass

    def test_patient_view_only_accessible_to_connected_clinician(self):
        pass

    def test_patient_view_displays_correct_patient_data(self):
        pass

    def test_select_trends_inaccessible_to_unauthenticated_user(self):
        pass

    def test_select_trends_accessible_to_authenticated_clinician(self):
        pass

    def test_select_trends_only_accessible_to_connected_clinician(self):
        pass

    def test_select_trends_displays_correct_data(self):
        pass

    def test_select_trends_links_correctly(self):
        pass

    def test_view_trends_inaccessible_to_unauthenticated_user(self):
        pass

    def test_view_trends_accessible_to_authenticated_clinician(self):
        pass

    def test_view_trends_only_accessible_to_connected_clinician(self):
        pass

    def test_view_trends_displays_correct_data(self):
        pass

    def test_input_trends_inaccessible_to_unauthenticated_user(self):
        pass

    def test_input_trends_accessible_to_authenticated_clinician(self):
        pass

    def test_input_trends_only_accessible_to_connected_clinician(self):
        pass

    def test_input_trends_rejects_incomplete_test_data(self):
        pass

    def test_input_trends_rejects_incomplete_message(self):
        pass

    def test_input_trends_accepts_incomplete_next_text(self):
        pass

    def test_input_trends_default_next_date_matches_config(self):
        pass

    def test_input_trends_submission_updates_model(self):
        pass


