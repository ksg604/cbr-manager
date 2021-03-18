from django.db import models
from django.contrib.auth.models import User
from django.contrib.contenttypes.fields import GenericForeignKey, GenericRelation
from django.contrib.contenttypes.models import ContentType
from django.db import models

from clients.models import Client
from baseline_survey.text_choices import AssistiveDevice, GeneralHealth, HealthServiceSatisfaction, NoSchoolReason, ChildNourishment


# Create your models here.

class BaselineSurvey(models.model):
    user_creator = models.ForeignKey(User, on_delete=models.CASCADE)
    client = models.ForeignKey(Client, on_delete=models.CASCADE)
    date_created = models.DateTimeField(auto_now_add=True)

    # health
    general_health = models.CharField(choices=GeneralHealth.choices, max_length=50)
