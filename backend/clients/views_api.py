# Create your api views here.
from rest_framework import viewsets

from clients.models import Client
from clients.serializer import ClientSerializer


class ClientViewSet(viewsets.ModelViewSet):
    # query all client objects
    queryset = Client.objects.all()
    # define the serializer class to use
    serializer_class = ClientSerializer
