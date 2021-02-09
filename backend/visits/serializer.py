from rest_framework import serializers

from clients.models import Client
from clients.serializer import ClientSerializer
from visits.models import Visit


class VisitSerializer(serializers.ModelSerializer):
    client = ClientSerializer()
    client_id = serializers.PrimaryKeyRelatedField(queryset=Client.objects.all())

    class Meta:
        model = Visit
        fields = ['datetime_created', 'user_creator', 'client_id', 'additional_notes', 'client']

    def create(self, validated_data):
        client = validated_data['client_id']
        client_info = validated_data['client']

        pre_update_client_json = ClientSerializer(client).data
        update_object(client, **client_info)
        post_update_client_json = ClientSerializer(client).data

        visit_obj = Visit(user_creator=validated_data['user_creator'], client=client,
                          additional_notes=validated_data['additional_notes'],
                          client_state_previous=pre_update_client_json,
                          client_state_updated=post_update_client_json)
        visit_obj.save()
        return visit_obj


def update_object(obj, **kwargs):
    for k, v in kwargs.items():
        setattr(obj, k, v)
    obj.save()
