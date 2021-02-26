from django.contrib import admin

# Register your models here.
from referral.models import Referral, WheelchairReferral, PhysiotherapyReferral, OrthoticReferral, ProstheticReferral


@admin.register(Referral)
class ReferralAdmin(admin.ModelAdmin):
    readonly_fields = ('referral_type', 'referral_object_id', 'referral_object_type')
    list_display = ('status', 'referral_type', 'user_creator', 'client_name',)

    def user_creator(self, obj):
        return obj.user

    def client_name(self, obj):
        return obj.client

    def referral_type(self, obj):
        return obj.referral_type.type


class ReferralBaseAdmin(admin.ModelAdmin):
    pass


@admin.register(PhysiotherapyReferral)
class PhysioServiceAdmin(ReferralBaseAdmin):
    pass


@admin.register(WheelchairReferral)
class WheelchairServiceAdmin(ReferralBaseAdmin):
    pass


@admin.register(OrthoticReferral)
class OrthoticServiceAdmin(ReferralBaseAdmin):
    pass


@admin.register(ProstheticReferral)
class ProstheticServiceAdmin(ReferralBaseAdmin):
    pass
