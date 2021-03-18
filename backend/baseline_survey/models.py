from django.db import models
from django.contrib.auth.models import User
from django.contrib.contenttypes.fields import GenericForeignKey, GenericRelation
from django.contrib.contenttypes.models import ContentType
from django.db import models

from clients.models import Client
from baseline_survey.text_choices import AssistiveDevice, GeneralHealth, HealthServiceSatisfaction, NoSchoolReason, ChildNourishment, YesNoBlank


# Create your models here.

class BaselineSurvey(models.model):
    user_creator = models.ForeignKey(User, on_delete=models.CASCADE)
    client = models.ForeignKey(Client, on_delete=models.CASCADE)
    date_created = models.DateTimeField(auto_now_add=True)

    # health
    general_health = models.CharField(choices=GeneralHealth.choices, max_length=50)
    have_access_rehab = models.CharField(choices=YesNoBlank.choices, default=YesNoBlank.BLANK)
    need_access_rehab = models.CharField(choices=YesNoBlank.choices, default=YesNoBlank.BLANK)
    assistive_device_have = models.CharField(choices=YesNoBlank.choices, default=YesNoBlank.BLANK)
    assistive_device_working = models.CharField(choices=YesNoBlank.choices, default=YesNoBlank.BLANK)
    assistive_device_need = models.CharField(choices=YesNoBlank.choices, default=YesNoBlank.BLANK)
    assistive_device = models.CharField(choices=AssistiveDevice.choices, default=AssistiveDevice.NONE)
    health_satisfaction = models.CharField(choices=HealthServiceSatisfaction.choices, max_length=50)

    # education
    attend_school = models.CharField(choices=YesNoBlank.choices, default=YesNoBlank.BLANK)
    grade = models.IntegerField(default=0)
    reason_no_school = models.CharField(choices=NoSchoolReason, max_length=50)
    been_to_school = models.CharField(choices=YesNoBlank, default=YesNoBlank.BLANK)
    want_to_go_school = models.CharField(choices=YesNoBlank, default=YesNoBlank.BLANK)