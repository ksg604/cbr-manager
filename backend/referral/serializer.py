from rest_framework import serializers
from rest_framework.exceptions import ValidationError

from referral.models import PhysiotherapyService, Referral, WheelchairService, ProstheticService, OrthoticService, \
    OtherService
from referral.text_choices import ServiceTypes
from utils.utils import update_object


class ExposeTypeMixin(metaclass=serializers.SerializerMetaclass):
    type = serializers.CharField(required=False, read_only=True)


class PhysioServiceSerializer(ExposeTypeMixin, serializers.ModelSerializer):
    class Meta:
        model = PhysiotherapyService
        fields = '__all__'


class WheelChairServiceSerializer(ExposeTypeMixin, serializers.ModelSerializer):
    class Meta:
        model = WheelchairService
        fields = '__all__'


class ProstheticServiceSerializer(ExposeTypeMixin, serializers.ModelSerializer):
    class Meta:
        model = ProstheticService
        fields = '__all__'


class OrthoticServiceSerializer(ExposeTypeMixin, serializers.ModelSerializer):
    class Meta:
        model = OrthoticService
        fields = '__all__'


class OtherServiceSerializer(ExposeTypeMixin, serializers.ModelSerializer):
    class Meta:
        model = OtherService
        fields = '__all__'


class ServiceJSONField(serializers.JSONField):
    def to_representation(self, obj):
        referral = obj
        referral_obj = referral.content_object
        referral_type = referral.service_type
        serializer_class = _get_serializer_class(referral_type)
        return serializer_class(referral_obj).data

    def to_internal_value(self, data):
        internal_value = super(ServiceJSONField, self).to_internal_value(data)
        return {
            self.field_name: internal_value
        }


class ReferralSerializer(serializers.ModelSerializer):
    service_detail = ServiceJSONField(source='*')
    # client_name = serializers.ReadOnlyField(source='client.full_name')
    # client_id = serializers.ReadOnlyField(source='client.id')

    class Meta:
        model = Referral
        fields = "__all__"

    def create(self, validated_data):
        def extract_service_details():
            data = validated_data['service_detail']
            del validated_data['service_detail']
            if data is None:
                raise ValidationError({'service_detail': ['this field cannot be empty']})
            return data

        referral_type = validated_data['service_type']
        service_details = extract_service_details()

        serializer_class = _get_serializer_class(referral_type)
        serializer = serializer_class(data=service_details)

        if serializer.is_valid():
            content_object = serializer.save()
            validated_data['content_object'] = content_object
        else:
            raise ValidationError({'service_detail': serializer.errors})

        return super(ReferralSerializer, self).create(validated_data)

    def update(self, instance, validated_data):
        referral_obj = super(ReferralSerializer, self).update(instance, validated_data)

        self._update_referral_type_obj(referral_obj, validated_data)

        return referral_obj

    def _update_referral_type_obj(self, referral, validated_data):
        service_details = validated_data['service_detail']
        serializers_class = _get_serializer_class(referral.service_type)
        serializer = serializers_class(data=service_details)

        if serializer.is_valid():
            service_type_obj = referral.content_object
            update_object(service_type_obj, **service_details)
            service_type_obj.save()
        else:
            raise ValidationError({'service_detail': serializer.errors})


def _get_serializer_class(service_type):
    serializer_map = {
        ServiceTypes.WHEELCHAIR: WheelChairServiceSerializer,
        ServiceTypes.PHYSIOTHERAPY: PhysioServiceSerializer,
        ServiceTypes.ORTHOTIC: OrthoticServiceSerializer,
        ServiceTypes.PROSTHETIC: ProstheticServiceSerializer,
        ServiceTypes.OTHER: OtherServiceSerializer
    }

    if service_type not in serializer_map:
        raise ValidationError({'service_type': ['unrecognized service type']})
    else:
        return serializer_map.get(service_type)
