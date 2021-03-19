from rest_framework import serializers
from django.shortcuts import get_object_or_404
from rest_framework.exceptions import ValidationError
from baseline_survey.models import BaselineSurvey
from clients.models import Client
from clients.serializer import ClientSerializer


class BaselineSurveySerializer(serializers.ModelSerializer):
    client_id = serializers.IntegerField(required=True)
    client = ClientSerializer(write_only=True, required=True)

    class Meta:
        model = BaselineSurvey
        fields = "__all__"

    def create(self, validated_data):
        client = get_object_or_404(Client, id=validated_data['client_id'])

        return BaselineSurvey.objects.create(user_creator=validated_data['user_creator'],
                                             client=client,
                                             general_health=validated_data['general_health'],
                                             have_access_rehab=validated_data['have_access_rehab'],
                                             assistive_device_have=validated_data['assistive_device_have'],
                                             assistive_device_working=validated_data['assistive_device_working'],
                                             assistive_device_need=validated_data['assistive_device_need'],
                                             assistive_device=validated_data['assistive_device'],
                                             health_satisfaction=validated_data['health_satisfaction'],
                                             attend_school=validated_data['attend_school'],
                                             grade=validated_data['grade'],
                                             reason_no_school=validated_data['reason_no_school'],
                                             been_to_school=validated_data['been_to_school'],
                                             want_to_go_school=validated_data['want_to_go_school'],
                                             feel_valued=validated_data['feel_valued'],
                                             feel_independent=validated_data['feel_independent'],
                                             able_to_participate=validated_data['able_to_participate'],
                                             disability_affects_social=validated_data['disability_affects_social'],
                                             discriminated=validated_data['discriminated'],
                                             working=validated_data['working'],
                                             job=validated_data['job'],
                                             employment=validated_data['employment'],
                                             meets_financial=validated_data['meets_financial'],
                                             disability_affects_work=validated_data['disability_affects_work'],
                                             want_work=validated_data['want_work'],
                                             food_security=validated_data['food_security'],
                                             enough_food=validated_data['enough_food'],
                                             child_nourishment=validated_data['child_nourishment'],
                                             member_of_organizations=validated_data['member_of_organizations'],
                                             aware_of_rights=validated_data['aware_of_rights'],
                                             able_to_influence=validated_data['able_to_influence'],
                                             adequate_shelter=validated_data['adequate_shelter'],
                                             access_essentials=validated_data['access_essentials']
                                             )
