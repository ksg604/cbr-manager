from django.contrib.auth.models import User
from django.contrib.contenttypes.fields import GenericForeignKey, GenericRelation
from django.contrib.contenttypes.models import ContentType
from django.db import models

from clients.models import Client
from tools.models import TimestampedModel
from referral.text_choices import InjuryLocation, UsageExperience, Condition, ServiceTypes


class Referral(TimestampedModel):
    user_creator = models.IntegerField(blank=True,null=True)
    client_id = models.IntegerField(blank=True,null=True)
    client_name = models.CharField(blank=True,max_length=150,null=True)

    status = models.CharField(blank=True,null=True,max_length=200, default="CREATED")
    outcome = models.TextField(blank=True,null=True,max_length=300, default="")
    refer_to = models.CharField(blank=True,null=True,max_length=100)

    photo = models.ImageField(blank=True, upload_to='images/', default='images/default.png', null=True)

    service_type = models.CharField(blank=True,null=True,max_length=50)
    service_object_type = models.ForeignKey(ContentType, on_delete=models.CASCADE, blank=True,null=True)
    service_object_id = models.PositiveIntegerField(blank=True,null=True)
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

    usage_experience = models.CharField(blank=True,max_length=100)
    client_hip_width = models.FloatField(blank=True,default=0)
    client_has_existing_wheelchair = models.BooleanField(blank=True,default=False)
    is_wheel_chair_repairable = models.BooleanField(blank=True,default=False)


class PhysiotherapyService(ServiceType):
    type = ServiceTypes.PHYSIOTHERAPY

    condition = models.CharField(blank=True, max_length=100)
    other_description = models.TextField(blank=True,help_text='Other condition, please specify',  default="")


class ProstheticService(ServiceType):
    type = ServiceTypes.PROSTHETIC

    knee_injury_location = models.CharField(blank=True,max_length=20)


class OrthoticService(ServiceType):
    type = ServiceTypes.ORTHOTIC

    elbow_injury_location = models.CharField(blank=True,max_length=20)


class OtherService(ServiceType):
    type = ServiceTypes.OTHER

    description = models.TextField(blank=True)
