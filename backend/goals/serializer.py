from django.shortcuts import get_object_or_404
from rest_framework import serializers

from clients.models import Client
from clients.serializer import ClientSerializer
from goals.utils import differentiate_key_value, update_object
from goals.models import Goal


class GoalSerializer(serializers.ModelSerializer):
    client_id = serializers.IntegerField(required=True)
    client = ClientSerializer(write_only=True, required=True)

    class Meta:
        model = Goal
        fields = "__all__"

    def create(self, validated_data):
        client = get_object_or_404(Client, id=validated_data['client_id'])

        pre_update_client_json = ClientSerializer(client).data
        self.update_client(client, validated_data)
        post_update_client_json = ClientSerializer(client).data

        client_info_changed_json = differentiate_key_value(post_update_client_json, pre_update_client_json)

        return Goal.objects.create(user_creator=validated_data['user_creator'],
                                    client=client,                                    
                                    client_state_previous=pre_update_client_json,
                                    client_state_updated=post_update_client_json,
                                    client_info_changed=client_info_changed_json,
                    
                                    category=validated_data["category"],
                                    title=validated_data["title"],
                                    description=validated_data["description"],
                                    is_initial_goal=validated_data["is_initial_goal"],
                                    status=validated_data["status"],
                                    )
                                    


    def update(self, instance, validated_data):
        client = instance.client

        self.update_client(client, validated_data)

        self.update_goal_on_put(instance, validated_data)

        return instance

    def update_client(self, client, validated_date):
        update_object(client, **validated_date.get("client"))

    def update_goal_on_put(self, goal_instance, validated_data):
        post_update_client_json = ClientSerializer(goal_instance.client).data

        goal_instance.client_state_updated = post_update_client_json
        goal_instance.client_info_changed = differentiate_key_value(post_update_client_json,
                                                                        goal_instance.client_state_previous)
        for key, value in validated_data.items():
            if key == 'client':
                client = get_object_or_404(Client, id=validated_data['client_id'])
                setattr(goal_instance, 'client', client)
            else:
                setattr(goal_instance, key, value)
        goal_instance.save()