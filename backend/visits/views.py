# Create your views here.
from rest_framework import viewsets, mixins

from visits.models import Visit
from visits.serializer import VisitSerializer


class VisitViewSet(viewsets.ModelViewSet):
    serializer_class = VisitSerializer
    queryset = Visit.objects.all()

    def get_queryset(self):
        queryset = Visit.objects.all()
        client_id = self.request.query_params.get('client_id', None)
        if client_id is not None:
            return queryset.filter(client__id=client_id)
        return queryset
