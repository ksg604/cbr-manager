from django.contrib.auth.models import User
from django.db import models

from clients.models import Client


class Visit(models.Model):
    datetime_created = models.DateTimeField(auto_now_add=True)
    user_creator = models.ForeignKey(User, on_delete=models.CASCADE)
    client = models.ForeignKey(Client, on_delete=models.CASCADE)

    client_state_previous = models.JSONField(blank=True, null=True, editable=False)
    client_state_updated = models.JSONField(blank=True, null=True, editable=False)

    # contains the specific info that was changed
    client_info_changed = models.JSONField(blank=True, null=True, editable=False)

    additional_notes = models.TextField(verbose_name="additional notes", blank=True, null=True, default="")

    def __str__(self):
        return "Visit {} - {} {}".format(self.id, self.client.first_name, self.client.last_name)