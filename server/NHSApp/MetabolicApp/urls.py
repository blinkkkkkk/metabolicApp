from django.conf.urls import url, include
from rest_framework.routers import DefaultRouter
from rest_framework_jwt.views import obtain_jwt_token

import MetabolicApp.html_views.inboxViews
import MetabolicApp.html_views.testResultViews
import MetabolicApp.html_views.loginViews
import MetabolicApp.views
from rest_framework.authtoken import views

app_name = 'Metabolic_App'


urlpatterns = [
    url(r'^login/', MetabolicApp.html_views.loginViews.loginView, name='login'),
    url(r'^logout/$', MetabolicApp.html_views.loginViews.logoutView, name='logout'),
    url(r'^home/$', MetabolicApp.views.homeView, name='home'),


    url(r'^view-inbox/$', MetabolicApp.html_views.inboxViews.clinicianInboxView, name='view_inbox'),
    url(r'^view-inbox/request/(?P<inbox_entry_id>[0-9]+)/$', MetabolicApp.html_views.inboxViews.requestEntryView,name='view_inbox_request'),
    url(r'^view-inbox/submission/(?P<inbox_entry_id>[0-9]+)/$', MetabolicApp.html_views.inboxViews.submissionEntryView, name='view_inbox_submission'),

    url(r'^view-patients/$', MetabolicApp.html_views.testResultViews.patientSelectView, name='view_patients'),
    url(r'^view-patients/patient/(?P<patient_id>[0-9]+)/$', MetabolicApp.html_views.testResultViews.patientView, name='view_patient'),
    url(r'^view-patients/patient/(?P<patient_id>[0-9]+)/trends/(?P<option>[a-z]+)/$',
        MetabolicApp.html_views.testResultViews.selectTrendTypeView, name='select_trends'),
    url(r'^view-patients/patient/(?P<patient_id>[0-9]+)/trends/view/(?P<test_type>[A-Z]+)/$',
        MetabolicApp.html_views.testResultViews.viewPatientDataView, name='view_patient_data'),
    url(r'^view-patients/patient/(?P<patient_id>[0-9]+)/trends/input/(?P<test_type>[A-Z]+)/$',
        MetabolicApp.html_views.testResultViews.inputPatientDataView, name='input_patient_data'),
    url(r'^test-result/$', MetabolicApp.views.getTestResults),
    url(r'^api-login/$', MetabolicApp.views.obtain_jwt_token)
]
