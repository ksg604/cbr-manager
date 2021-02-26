from django.contrib.auth.models import User
from django.contrib.contenttypes.fields import GenericForeignKey, GenericRelation
from django.contrib.contenttypes.models import ContentType
from django.db import models

from clients.models import Client
from referral.text_choices import InjuryLocation, UsageExperience, Conditions, ReferralStatus, ReferralTypes


class Referral(models.Model):
    user_creator = models.ForeignKey(User, on_delete=models.CASCADE)
    client = models.ForeignKey(Client, on_delete=models.CASCADE)
    date_created = models.DateTimeField(auto_now_add=True)
    limit = models.Q(app_label="referral")
    status = models.CharField(max_length=20, choices=ReferralStatus.choices, default=ReferralStatus.CREATED)
    outcome = models.TextField(blank=True, default="")

    referral_type = models.CharField(max_length=50, choices=ReferralTypes.choices)
    referral_object_type = models.ForeignKey(ContentType, on_delete=models.CASCADE, limit_choices_to=limit)
    referral_object_id = models.PositiveIntegerField()
    content_object = GenericForeignKey('referral_object_type', 'referral_object_id')


class ReferralType(models.Model):
    type = None
    referral = GenericRelation(Referral)

    class Meta:
        abstract = True

    def __str__(self):
        return self.type + " ({})".format(self.id)


class WheelchairReferral(ReferralType):
    type = ReferralTypes.WHEELCHAIR

    usage_experience = models.CharField(max_length=100, choices=UsageExperience.choices)
    client_hip_width = models.FloatField(default=0)
    client_has_existing_wheelchair = models.BooleanField(default=False)
    is_wheel_chair_repairable = models.BooleanField()


class PhysiotherapyReferral(ReferralType):
    type = ReferralTypes.PHYSIOTHERAPY

    conditions = models.CharField(choices=Conditions.choices, max_length=100)
    specified_condition = models.CharField(max_length=100, help_text='Other condition, please specify', blank=True,
                                           default="")


class ProstheticReferral(ReferralType):
    type = ReferralTypes.PROSTHETIC

    knee_injury_location = models.CharField(max_length=20, choices=InjuryLocation.choices)


class OrthoticReferral(ReferralType):
    type = ReferralTypes.PHYSIOTHERAPY

    elbow_injury_location = models.CharField(max_length=20, choices=InjuryLocation.choices)
