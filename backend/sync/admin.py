from django.contrib import admin

# Register your models here.
from sync.models import Status


@admin.register(Status)
class Status(admin.ModelAdmin):
    readonly_fields = ['created_at', 'updated_at', 'client_last_updated']
