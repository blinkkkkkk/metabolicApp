from django.db import models

# Create your models here.

class Clinician(models.Model):

    def __str__(self):
        return self.username

    def is_authenticated(self):
        return True

    username = models.CharField(max_length=40, null=False, unique=True)
    full_name = models.CharField(max_length=40, null=False)
    password_hash = models.TextField(null=False)
    password_salt = models.CharField(max_length=255)
    GMC_no = models.CharField(max_length=40, null=False, unique=True)
    practice_no =  models.CharField(max_length=40, null=False, unique=True)
    last_login = models.DateTimeField(null=True, blank=False)
    date_created = models.DateTimeField(auto_now_add=True)

class Patient(models.Model):

    def __str__(self):
        return self.username

    def is_authenticated(self):
        return True

    @staticmethod
    def getAuthorizedPatients(clinician_id):
        return Patient.objects.filter(clinician_id=clinician_id)


    @staticmethod
    def checkDataAccessRequest(patient_id, clinician_id):
        return Patient.objects.filter(clinician_id=clinician_id, id=patient_id.id).exists()

    @staticmethod
    def hasClinician(patient_id):
        return Patient.objects.get(id=patient_id.id).clinician_id is not None

    username = models.CharField(max_length=40, null=False, unique=True)
    first_name = models.CharField(max_length=40, null=False)
    last_name = models.CharField(max_length=40, null=False)
    password_hash = models.TextField(null=False)
    password_salt = models.CharField(max_length=255)
    DoB = models.DateField(null=False)
    NHS_no = models.CharField(max_length=40, null=False, unique=True)
    clinician_id = models.ForeignKey(Clinician,models.SET_NULL, null=True, blank=True)
    date_created =models.DateTimeField(auto_now_add=True)

class Inbox(models.Model):

    def __str__(self):
        return 'InboxEntry: '+ self.patient_id.username + ' ' + self.clinician_id.username

    @staticmethod
    def hasPendingRequest(patient_id):
        pass     #TODO(Kiran): Implement methods

    @staticmethod
    def getPendingRequest(patient_id):
        pass     #TODO(Kiran): Implement methods

    @staticmethod
    def getClinicianInboxEntries(clinician_id):
        entries = Inbox.objects.filter(clinician_id=clinician_id)
        result = []
        for entry in entries:
            patient = entry.patient_id
            result_entry = {}
            result_entry['id'] = patient.id
            result_entry['name'] = patient.first_name + " " + patient.last_name
            result_entry['type'] = entry.entry_type
            result_entry['age'] = entry.date_created.ctime()
            result.append(result_entry)
        return result

    @staticmethod
    def authorizedViewRequest(clinician_id, inbox_entry_id):
        return Inbox.objects.filter(clinician_id=clinician_id, id=inbox_entry_id).exists()


    ENTRY_TYPES = (('REQUEST', 'request'),('SUBMISSION','submission'))
    # id = models.AutoField(primary_key=True, unique=True, blank=True, null=False)
    clinician_id = models.ForeignKey(Clinician,models.CASCADE,null=False)
    patient_id = models.ForeignKey(Patient,models.CASCADE,null=False)
    entry_type = models.CharField(max_length=10,choices=ENTRY_TYPES, null=False, blank=False)
    message = models.TextField(null=True, blank=True)
    image = models.ImageField(null=True, blank=True)
    date_created = models.DateTimeField(auto_now_add=True)

class TestResult(models.Model):

    def __str__(self):
        return 'TestEntry: '+' '.join(self.patient_id, self.clinician_id)

    @staticmethod
    def getTestResultsForPatient(patient_id, test_type):
        return TestResult.objects.filter(patient_id=patient_id, test_type=test_type)
        responses = []

        for result in results:
            response = {}
            response['date_created'] = result.date_created
            response['message'] = result.message
            response['data'] = result.test_data
            response['next_test'] = result.next_test



    @staticmethod
    def getAllTestResultsForPatient(patient_id):
        return TestResult.objects.filter(patient_id=patient_id)

    @staticmethod
    def isValidType(test_type):
        return any([test_type == test[0] for test in TestResult.ENTRY_TYPES])

    @staticmethod
    def getReadableTypeName(test_type):
        return [test[1] for test in TestResult.ENTRY_TYPES if test_type == test[0]][0]

    ENTRY_TYPES = (('CHOLESTEROL', 'Fasting cholesterol'),('LIPIDS','Lipids'), ('PROLACTIN','Prolactin'), ('RENAL','Renal_Functions'),
                   ('BLOOD','Blood_pressure'), ('ECG','ECGs'), ('BMI','BMI'), ('FBSUGAR','Fasting_blood_sugar'), ('HBA1C','HbA1c'),
                   ('LIVER','Liver_function'))

    test_type = models.CharField(max_length=15,choices=ENTRY_TYPES, null=False, blank=False)
    test_data = models.FileField(null=False)
    clinician_id = models.ForeignKey(Clinician,models.ProtectedError,null=False)
    patient_id = models.ForeignKey(Patient, models.ProtectedError, null=False)
    next_test = models.DateField(null=False, blank=True)
    message = models.TextField(null=False, blank=False)
    date_created = models.DateTimeField(auto_now_add=True)