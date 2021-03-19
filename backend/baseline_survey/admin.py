from django.contrib import admin

# Register your models here.
from baseline_survey.models import BaselineSurvey


@admin.register(BaselineSurvey)
class BaselineSurveyAdmin(admin.ModelAdmin):
    list_display = ('id', 'client', 'user_creator', 'datetime_created')