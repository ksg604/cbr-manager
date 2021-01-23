# Create your web views here
import requests
from django.http import HttpResponse
from django.views import View

from cbrsite.settings import SITE_URL
from clients.serializer import ClientSerializer


class ClientView(View):
    def get(self, request, *args, **kwargs):
        response = requests.get(SITE_URL + "api/clients")

        client = ClientSerializer(data=response.json(), many=True)
        return HttpResponse("hello world")
