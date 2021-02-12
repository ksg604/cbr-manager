from rest_framework import serializers

from clients.models import Client
from clients.serializer import ClientSerializer
from visits.models import Visit
from visits.utils import differentiate_key_value, update_object


class VisitSerializer(serializers.ModelSerializer):
    client = ClientSerializer()
    client_id = serializers.PrimaryKeyRelatedField(queryset=Client.objects.all())

    class Meta:
        model = Visit
        fields = ['datetime_created', 'user_creator', 'client_id', 'additional_notes',
                  'client_info_changed', 'client']

    def create(self, validated_data):
        client = validated_data['client_id']
        client_info = validated_data['client']

        pre_update_client_json = ClientSerializer(client).data
        update_object(client, **client_info)
        post_update_client_json = ClientSerializer(client).data

        client_info_changed_json = differentiate_key_value(post_update_client_json, pre_update_client_json)

        return Visit.objects.create(user_creator=validated_data['user_creator'], client=client,
                                    additional_notes=validated_data['additional_notes'],
                                    client_state_previous=pre_update_client_json,
                                    client_state_updated=post_update_client_json,
                                    client_info_changed=client_info_changed_json)



