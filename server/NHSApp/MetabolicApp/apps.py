import configparser
import json
import datetime
from django.apps import AppConfig
import django

DEFAULT_TEST_DATES = {}

class MetabolicappConfig(AppConfig):
    name = 'MetabolicApp'
    verbose_name = 'NHS-Metabolic App'
    def ready(self):
        global DEFAULT_TEST_DATES
        with open('MetabolicApp/config.json', 'r') as file:
            config = json.load(file)
        test_default_dates = {}
        for test in config:
            days = test['date']['day'] + test['date']['year']*365
            weeks = test['date']['week'] + test['date']['month']*4
            test_default_dates[test['name']] = datetime.timedelta(days=days,weeks=weeks)

        DEFAULT_TEST_DATES = test_default_dates