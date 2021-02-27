from django.contrib import admin

# Register your models here.
from referral.models import Referral, WheelchairService, PhysiotherapyService, OrthoticService, ProstheticService, \
    OtherService


@admin.register(Referral)
class ReferralAdmin(admin.ModelAdmin):
    readonly_fields = ('service_type', 'service_object_id', 'service_object_type')
    list_display = ('status', 'service_type', 'user_creator', 'client_name',)

    def user_creator(self, obj):
        return obj.user

    def client_name(self, obj):
        return obj.client

    def referral_type(self, obj):
        return obj.service_type.type


class ReferralBaseAdmin(admin.ModelAdmin):
    pass


@admin.register(PhysiotherapyService)
class PhysioServiceAdmin(ReferralBaseAdmin):
    pass


@admin.register(WheelchairService)
class WheelchairServiceAdmin(ReferralBaseAdmin):
    pass


@admin.register(OrthoticService)
class OrthoticServiceAdmin(ReferralBaseAdmin):
    pass


@admin.register(ProstheticService)
class ProstheticServiceAdmin(ReferralBaseAdmin):
    pass


@admin.register(OtherService)
class ProstheticServiceAdmin(ReferralBaseAdmin):
    pass
