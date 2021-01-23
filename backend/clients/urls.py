from django.contrib import admin
from django.urls import include, path

from . import views

app_name = 'clients'
urlpatterns = [
    path('', views.ClientView.as_view(), name="index")
]
