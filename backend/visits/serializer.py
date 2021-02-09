from rest_framework import serializers

from visits.models import Visit


class VisitSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Visit
        fields = ['datetime_created', 'user_creator', 'client']
