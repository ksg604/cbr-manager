from django.contrib import admin

# Register your models here.
from sync.models import Status


@admin.register(Status)
class Status(admin.ModelAdmin):
    pass
