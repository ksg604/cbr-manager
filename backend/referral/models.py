from django.contrib.auth.models import User
from django.contrib.contenttypes.fields import GenericForeignKey, GenericRelation
from django.contrib.contenttypes.models import ContentType
from django.db import models

from clients.models import Client
from referral.text_choices import InjuryLocation, UsageExperience, Condition, ReferralStatus, ServiceTypes


class Referral(models.Model):
    user_creator = models.ForeignKey(User, on_delete=models.SET_NULL, null=True)
    client = models.ForeignKey(Client, on_delete=models.CASCADE)
    date_created = models.DateTimeField(auto_now_add=True)
    limit = models.Q(app_label="referral")
    status = models.CharField(max_length=20, choices=ReferralStatus.choices, default=ReferralStatus.CREATED)
    outcome = models.TextField(blank=True, default="")
    refer_to = models.CharField(max_length=100)

    photo = models.ImageField(blank=True, upload_to='images/')

    service_type = models.CharField(max_length=50, choices=ServiceTypes.choices)
    service_object_type = models.ForeignKey(ContentType, on_delete=models.CASCADE, limit_choices_to=limit)
    service_object_id = models.PositiveIntegerField()
    content_object = GenericForeignKey('service_object_type', 'service_object_id')


class ServiceType(models.Model):
    type = None
    referral = GenericRelation(Referral)

    class Meta:
        abstract = True

    def __str__(self):
        return self.type + " ({})".format(self.id)


class WheelchairService(ServiceType):
    type = ServiceTypes.WHEELCHAIR

    usage_experience = models.CharField(max_length=100, choices=UsageExperience.choices)
    client_hip_width = models.FloatField(default=0)
    client_has_existing_wheelchair = models.BooleanField(default=False)
    is_wheel_chair_repairable = models.BooleanField()


class PhysiotherapyService(ServiceType):
    type = ServiceTypes.PHYSIOTHERAPY

    condition = models.CharField(choices=Condition.choices, max_length=100)
    other_description = models.TextField(help_text='Other condition, please specify', blank=True, default="")


class ProstheticService(ServiceType):
    type = ServiceTypes.PROSTHETIC

    knee_injury_location = models.CharField(max_length=20, choices=InjuryLocation.choices)


class OrthoticService(ServiceType):
    type = ServiceTypes.ORTHOTIC

    elbow_injury_location = models.CharField(max_length=20, choices=InjuryLocation.choices)


class OtherService(ServiceType):
    type = ServiceTypes.OTHER

    description = models.TextField()
