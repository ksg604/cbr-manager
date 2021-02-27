from django.shortcuts import render
from rest_framework import viewsets, mixins
from rest_framework.viewsets import GenericViewSet, ModelViewSet

from referral.models import Referral
from referral.serializer import ReferralSerializer


class ReferralViewSet(ModelViewSet):
    serializer_class = ReferralSerializer

    def get_queryset(self):
        queryset = Referral.objects.all()
        return queryset
