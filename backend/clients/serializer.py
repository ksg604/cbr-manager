from rest_framework import serializers
from rest_framework.authtoken.admin import User
from rest_framework.exceptions import ValidationError

from clients.models import Client, ClientHistoryRecord
from visits.models import Visit
from visits.utils import differentiate_key_value


class ClientSerializer(serializers.ModelSerializer):
    risk_score = serializers.IntegerField(read_only=True)

    class Meta:
        model = Client
        fields = "__all__"

    def update(self, instance, validated_data):
        def create_client_history_record(diff_dict, original_dict):
            if diff_dict:
                for key, val in diff_dict.items():
                    new_value = val
                    old_value = original_dict[key]
                    field = key
                    ClientHistoryRecord.objects.create(
                        field=field,
                        old_value=old_value,
                        new_value=new_value,
                        client=instance
                    )

        pre_update_client_json = ClientSerializer(instance).data
        super(ClientSerializer, self).update(instance, validated_data)
        post_update_client_json = ClientSerializer(instance).data

        client_fields_diff_dict = differentiate_key_value(post_update_client_json, pre_update_client_json)

        create_client_history_record(client_fields_diff_dict, pre_update_client_json)

        return instance
