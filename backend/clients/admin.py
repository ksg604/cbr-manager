from django.contrib import admin

# Register your models here.
from clients.models import Client, ClientHistoryRecord


@admin.register(Client)
class ClientAdmin(admin.ModelAdmin):
    list_display = ("id", "first_name", "last_name", "risk_score")
    readonly_fields = ('risk_score',)


@admin.register(ClientHistoryRecord)
class ClientHistoryAdmin(admin.ModelAdmin):
    list_display = ['client', 'date_created', 'field', 'old_value', 'new_value', ]

    readonly_fields = ['client', 'date_created', 'field', 'old_value', 'new_value', ]
