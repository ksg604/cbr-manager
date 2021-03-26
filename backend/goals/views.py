# Create your views here.
from rest_framework import viewsets, mixins

from goals.models import Goal
from goals.serializer import GoalSerializer


class GoalViewSet(viewsets.ModelViewSet):
    serializer_class = GoalSerializer
    queryset = Goal.objects.all()

    def get_queryset(self):
        queryset = Goal.objects.all()
        client_id = self.request.query_params.get('client_id', None)
        if client_id is not None:
            return queryset.filter(client__id=client_id)
        return queryset
