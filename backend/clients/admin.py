from django.contrib import admin

# Register your models here.
from clients.models import Client


@admin.register(Client)
class ClientAdmin(admin.ModelAdmin):
    list_display = ('id', 'cbr_client_id', "first_name", "last_name", "risk_score")
    readonly_fields = ('risk_score', 'cbr_client_id')
