from rest_framework import serializers
from rest_framework.exceptions import ValidationError

from referral.models import PhysiotherapyReferral, Referral, WheelchairReferral, ProstheticReferral, OrthoticReferral
from referral.text_choices import ReferralTypes


class PhysioReferralSerializer(serializers.ModelSerializer):
    class Meta:
        model = PhysiotherapyReferral
        fields = '__all__'


class WheelChairReferralSerializer(serializers.ModelSerializer):
    class Meta:
        model = WheelchairReferral
        fields = '__all__'


class ProstheticReferralSerializer(serializers.ModelSerializer):
    class Meta:
        model = ProstheticReferral
        fields = '__all__'


class OrthoticReferralSerializer(serializers.ModelSerializer):
    class Meta:
        model = OrthoticReferral
        fields = '__all__'


class ReferralJSONField(serializers.JSONField):
    def to_representation(self, obj):
        referral = obj
        referral_obj = referral.content_object
        referral_type = referral.referral_type
        serializer_class = _get_serializer_class(referral_type)
        return serializer_class(referral_obj).data

    def to_internal_value(self, data):
        internal_value = super(ReferralJSONField, self).to_internal_value(data)
        return {
            self.field_name: internal_value
        }


class ReferralSerializer(serializers.ModelSerializer):
    referral = ReferralJSONField(source='*')

    class Meta:
        model = Referral
        fields = ('id', 'referral', 'date_created', 'status', 'outcome', 'referral_type', 'client', 'user_creator')

    def create(self, validated_data):
        def extract_referral_data():
            data = validated_data['referral']
            del validated_data['referral']
            if data is None:
                raise ValidationError({'referral': ['this field cannot be empty']})
            return data

        referral_type = validated_data['referral_type']
        referral_data = extract_referral_data()

        serializer_class = _get_serializer_class(referral_type)
        serializer = serializer_class(data=referral_data)

        if serializer.is_valid():
            content_object = serializer.save()
            validated_data['content_object'] = content_object
        else:
            raise ValidationError({'referral': serializer.errors})

        return super(ReferralSerializer, self).create(validated_data)

    def to_representation(self, instance):
        data = super(ReferralSerializer, self).to_representation(instance)
        return data


def _get_serializer_class(referral_type):
    serializer_map = {
        ReferralTypes.WHEELCHAIR: WheelChairReferralSerializer,
        ReferralTypes.PHYSIOTHERAPY: PhysioReferralSerializer,
        ReferralTypes.ORTHOTIC: OrthoticReferralSerializer,
        ReferralTypes.PROSTHETIC: ProstheticReferralSerializer
    }

    if referral_type not in serializer_map:
        raise ValidationError({'referral_type': ['unrecognized referral type']})
    else:
        return serializer_map.get(referral_type)
