from rest_framework import serializers
from rest_framework.authtoken.admin import User
from rest_framework.exceptions import ValidationError

from clients.models import Client
from visits.models import Visit
from visits.utils import differentiate_key_value


class ClientSerializer(serializers.ModelSerializer):
    risk_score = serializers.IntegerField(read_only=True)

    class Meta:
        model = Client
        fields = "__all__"
