from rest_framework import serializers

from clients.models import Client


class ClientSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Client
        fields = ['id', 'first_name', 'last_name', 'location', 'age', 'date']
        # exposes fields for testing POST/GET
