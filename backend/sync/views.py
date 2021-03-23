# Create your views here.
from rest_framework import viewsets
from rest_framework.response import Response
from rest_framework.views import APIView

from sync.models import Status
from sync.serializer import StatusSerializer


class StatusViewSet(APIView):
    serializer_class = StatusSerializer

    def get(self, request):
        status = Status.objects.get(pk=1)
        response = self.serializer_class(status)
        return Response(response.data)
