# Create your api views here.
from rest_framework import viewsets

from clients.models import Client
from clients.serializer import ClientSerializer


class ClientViewSet(viewsets.ModelViewSet):
    queryset = Client.objects.all()
    serializer_class = ClientSerializer
