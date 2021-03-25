from django.contrib import admin

# Register your models here.
from goals.models import Goal


@admin.register(Goal)
class GoalAdmin(admin.ModelAdmin):
    list_display = ('id', 'client', 'user_creator', 'title', 'status')
