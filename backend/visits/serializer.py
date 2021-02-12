from rest_framework import serializers
from rest_framework.exceptions import MethodNotAllowed

from clients.serializer import ClientSerializer
from visits.models import Visit
from visits.utils import differentiate_key_value, update_object


class VisitSerializer(serializers.ModelSerializer):
    client = ClientSerializer(write_only=True)

    class Meta:
        model = Visit
        fields = ["id", "datetime_created", "user_creator", "client", "additional_notes", "client_info_changed"]

    def create(self, validated_data):
        raise MethodNotAllowed("POST", detail="user api/client/{id} to create visits")

    def update(self, instance, validated_data):
        client = instance.client

        self.update_client(client, validated_data)

        self.update_visit(instance, validated_data)

        return instance

    def update_client(self, client, validated_date):
        update_object(client, **validated_date.get("client"))

    def update_visit(self, visit_instance, validated_data):
        post_update_client_json = ClientSerializer(visit_instance.client).data

        # update what was changed from the original state
        visit_instance.client_state_updated = post_update_client_json
        visit_instance.client_info_changed = differentiate_key_value(post_update_client_json,
                                                                     visit_instance.client_state_previous)

        # update fields
        visit_instance.user_creator = validated_data.get("user_creator", visit_instance.user_creator)
        visit_instance.additional_notes = validated_data.get("additional_notes", visit_instance.additional_notes)
        visit_instance.save()
