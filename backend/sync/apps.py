from django.apps import AppConfig
from django.db.models.signals import post_migrate


def create_singular_sync_status(sender, **kwargs):
    from .models import Status
    Status.objects.get_or_create(pk=1)


class SyncConfig(AppConfig):
    name = 'sync'

    def ready(self):
        post_migrate.connect(create_singular_sync_status, sender=self)
