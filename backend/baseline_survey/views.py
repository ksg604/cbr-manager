from django.shortcuts import render
from rest_framework import viewsets, mixins
from baseline_survey.models import BaselineSurvey
from baseline_survey.serializer import BaselineSurveySerializer
# Create your views here.


class BaselineSurveyViewSet(viewsets.ModelViewSet):
    serializer_class = BaselineSurveySerializer
    queryset = BaselineSurvey.objects.all()

    def get_queryset(self):
        queryset = BaselineSurvey.objects.all()
        # client_id = self.request.query_params.get('client_id', None)
        # if client_id is not None:
        #     return queryset.filter(client__id=client_id)
        return queryset
