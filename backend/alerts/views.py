# Create your views here.
from rest_framework import viewsets, mixins

from alerts.models import Alert
from alerts.serializer import AlertSerializer


class AlertViewSet(viewsets.ModelViewSet):
    serializer_class = AlertSerializer
    queryset = Alert.objects.all()

    def get_queryset(self):
        queryset = Alert.objects.all()
        return queryset
