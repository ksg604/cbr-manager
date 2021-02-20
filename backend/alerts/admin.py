from django.contrib import admin

# Register your models here.
from alerts.models import Alert


@admin.register(Alert)
class AlertAdmin(admin.ModelAdmin):
    list_display = ("id", "title", "body", "date")
