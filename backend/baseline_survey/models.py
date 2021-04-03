from django.db import models
from django.contrib.auth.models import User
from django.contrib.contenttypes.fields import GenericForeignKey, GenericRelation
from django.contrib.contenttypes.models import ContentType
from django.db import models

from clients.models import Client
from baseline_survey.text_choices import AssistiveDevice, GeneralHealth, HealthServiceSatisfaction, NoSchoolReason, ChildNourishment, YesNo, EmploymentType


# Create your models here.

class BaselineSurvey(models.Model):
    user_creator = models.ForeignKey(User, on_delete=models.SET_NULL, null=True)
    client = models.ForeignKey(Client, on_delete=models.CASCADE)
    datetime_created = models.DateTimeField(auto_now_add=True)

    # health
    general_health = models.CharField(choices=GeneralHealth.choices, max_length=50, blank=True)
    have_access_rehab = models.CharField(choices=YesNo.choices, max_length=50, blank=True)
    need_access_rehab = models.CharField(choices=YesNo.choices, max_length=50, blank=True)
    assistive_device_have = models.CharField(choices=YesNo.choices, max_length=50, blank=True)
    assistive_device_working = models.CharField(choices=YesNo.choices, max_length=50, blank=True)
    assistive_device_need = models.CharField(choices=YesNo.choices, max_length=50, blank=True)
    assistive_device = models.CharField(choices=AssistiveDevice.choices, default=AssistiveDevice.NONE, max_length=50, blank=True)
    health_satisfaction = models.CharField(choices=HealthServiceSatisfaction.choices, max_length=50, blank=True)

    # education
    attend_school = models.CharField(choices=YesNo.choices, max_length=50, blank=True)
    grade = models.IntegerField(blank=True, default=0)
    reason_no_school = models.CharField(choices=NoSchoolReason.choices, max_length=50, blank=True)
    been_to_school = models.CharField(choices=YesNo.choices, max_length=50, blank=True)
    want_to_go_school = models.CharField(choices=YesNo.choices, max_length=50, blank=True)

    # social
    feel_valued = models.CharField(choices=YesNo.choices, max_length=50, blank=True)
    feel_independent = models.CharField(choices=YesNo.choices, max_length=50, blank=True)
    able_to_participate = models.CharField(choices=YesNo.choices, max_length=50, blank=True)
    disability_affects_social = models.CharField(choices=YesNo.choices, max_length=50, blank=True)
    discriminated = models.CharField(choices=YesNo.choices, max_length=50, blank=True)

    # livelihood
    working = models.CharField(choices=YesNo.choices, max_length=50, blank=True)
    job = models.TextField(max_length=50, default="", blank=True)
    employment = models.CharField(choices=EmploymentType.choices, max_length=50, blank=True)
    meets_financial = models.CharField(choices=YesNo.choices, max_length=50, blank=True)
    disability_affects_work = models.CharField(choices=YesNo.choices, max_length=50, blank=True)
    want_work = models.CharField(choices=YesNo.choices, max_length=50, blank=True)

    # food, nutrition
    food_security = models.CharField(choices=GeneralHealth.choices, max_length=50, blank=True)
    enough_food = models.CharField(choices=YesNo.choices, max_length=50, blank=True)
    child_nourishment = models.CharField(choices=ChildNourishment.choices, max_length=50, blank=True)

    # empowerment
    member_of_organizations = models.CharField(choices=YesNo.choices, max_length=50, blank=True)
    aware_of_rights = models.CharField(choices=YesNo.choices, max_length=50, blank=True)
    able_to_influence = models.CharField(choices=YesNo.choices, max_length=50, blank=True)

    # Shelter
    adequate_shelter = models.CharField(choices=YesNo.choices, max_length=50, blank=True)
    access_essentials = models.CharField(choices=YesNo.choices, max_length=50, blank=True)