# Create your web views here
import requests
from django.shortcuts import render
from django.views import View

from cbrsite.settings import SITE_URL


class ClientView(View):
    def get(self, request, *args, **kwargs):
        response = requests.get(SITE_URL + "api/clients")
        clients = response.json()
        return render(request, 'index.html', {
            "object_list": clients
        })
