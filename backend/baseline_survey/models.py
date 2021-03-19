from django.db import models
from django.contrib.auth.models import User
from django.contrib.contenttypes.fields import GenericForeignKey, GenericRelation
from django.contrib.contenttypes.models import ContentType
from django.db import models

from clients.models import Client
from baseline_survey.text_choices import AssistiveDevice, GeneralHealth, HealthServiceSatisfaction, NoSchoolReason, ChildNourishment, YesNoBlank, EmploymentType


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

    # social
    feel_valued = models.CharField(choices=YesNoBlank.choices, max_length=50, default=YesNoBlank.BLANK)
    feel_independent = models.CharField(choices=YesNoBlank.choices, max_length=50, default=YesNoBlank.BLANK)
    able_to_participate = models.CharField(choices=YesNoBlank.choices, max_length=50, default=YesNoBlank.BLANK)
    disability_affects_social = models.CharField(choices=YesNoBlank.choices, max_length=50, default=YesNoBlank.BLANK)
    discriminated = models.CharField(choices=YesNoBlank.choices, max_length=50, default=YesNoBlank.BLANK)

    # livelihood
    working = models.CharField(choices=YesNoBlank.choices, max_length=50, default=YesNoBlank.BLANK)
    job = models.TextField(max_length=50, default="")
    employment = models.CharField(choices=EmploymentType)
    meets_finanicial = models.CharField(choices=YesNoBlank.choices, max_length=50, default=YesNoBlank.BLANK)
    disability_affects_work = models.CharField(choices=YesNoBlank.choices, max_length=50, default=YesNoBlank.BLANK)
    want_work = models.CharField(choices=YesNoBlank.choices, max_length=50, default=YesNoBlank.BLANK)

    # food, nutrition
    food_security = models.CharField(choices=GeneralHealth.choices)
    enough_food = models.CharField(choices=YesNoBlank.choices, max_length=50, default=YesNoBlank.BLANK)
    child_nourishment = models.CharField(choices=ChildNourishment)

    # empowerment
    member_of_organizations = models.CharField(choices=YesNoBlank, default=YesNoBlank.BLANK)
    aware_of_rights = models.CharField(choices=YesNoBlank, default=YesNoBlank.BLANK)
    able_to_influence = models.CharField(choices=YesNoBlank, default=YesNoBlank.BLANK)

    # Shelter
    adequate_shelter = models.CharField(choices=YesNoBlank, default=YesNoBlank.BLANK)
    access_essentials = models.CharField(choices=YesNoBlank, default=YesNoBlank.BLANK)