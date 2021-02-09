# Create your views here.
from rest_framework import viewsets

from visits.models import Visit
from visits.serializer import VisitSerializer


class VisitViewSet(viewsets.ModelViewSet):
    serializer_class = VisitSerializer
    queryset = Visit.objects.all()
