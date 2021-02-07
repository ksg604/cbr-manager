from rest_framework import serializers

from clients.models import Client


class ClientSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Client
        fields = ['first_name', 'last_name', 'id']  # exposes first_name and last_name of the model
