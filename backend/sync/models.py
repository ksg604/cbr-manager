from django.db import models

# Create your models here.
from django.db.models.signals import post_save, post_migrate
from django.dispatch import receiver
from django.utils import timezone

from clients.models import Client
from tools.models import TimestampedModel


class Status(TimestampedModel):
    client_last_updated = models.DateTimeField(auto_now_add=True)


# register all models for Status model to timestamp changes
@receiver(post_save, sender=Client)
def update_sync_status(sender, instance=None, created=False, **kwargs):
    if created:
        status = Status.objects.get(pk=1)
        if sender == Client:
            status.client_last_updated = timezone.now()

        status.save()
