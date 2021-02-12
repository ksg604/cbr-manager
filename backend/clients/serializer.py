from rest_framework import serializers
from rest_framework.authtoken.admin import User
from rest_framework.exceptions import ValidationError

from clients.models import Client
from visits.models import Visit
from visits.utils import differentiate_key_value


class ClientSerializer(serializers.ModelSerializer):
    create_visit = serializers.BooleanField(required=False, write_only=True)
    user = serializers.PrimaryKeyRelatedField(queryset=User.objects.all(), required=False, write_only=True)
    additional_visit_info = serializers.CharField(required=False, write_only=True)

    class Meta:
        model = Client
        fields = "__all__"

    def update(self, instance, validated_data):
        pre_update_client_json = ClientSerializer(instance).data

        updated_user = super().update(instance, validated_data)

        post_update_client_json = ClientSerializer(updated_user).data

        if validated_data.get("create_visit", False):
            self.create_visit_entry(updated_user, validated_data, pre_update_client_json, post_update_client_json)

        return updated_user

    def create_visit_entry(self, instance, validate_data, pre_update_client_json, post_update_client_json):
        user = validate_data.get("user", None)

        if user is None:
            raise ValidationError({
                "user": "is required when creating a visit"
            })
        additional_visit_info = validate_data.get("additional_visit_info", "")

        Visit.objects.create(user_creator=user, client_state_previous=pre_update_client_json, client=instance,
                             client_state_updated=post_update_client_json, additional_notes=additional_visit_info,
                             client_info_changed=differentiate_key_value(post_update_client_json,
                                                                         pre_update_client_json))
