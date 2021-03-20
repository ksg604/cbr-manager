from rest_framework import serializers
from django.shortcuts import get_object_or_404
from rest_framework.exceptions import ValidationError
from baseline_survey.models import BaselineSurvey
from clients.models import Client
from clients.serializer import ClientSerializer


class BaselineSurveySerializer(serializers.ModelSerializer):
    class Meta:
        model = BaselineSurvey
        fields = "__all__"
