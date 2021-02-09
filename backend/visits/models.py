from django.contrib.auth.models import User
from django.db import models

from clients.models import Client


class Visit(models.Model):
    datetime_created = models.DateTimeField(auto_now_add=True)
    user_creator = models.ForeignKey(User, on_delete=models.CASCADE)
    client = models.ForeignKey(Client, on_delete=models.CASCADE)

    client_state_previous = models.JSONField()
    client_state_updated = models.JSONField()

    additional_notes = models.TextField(verbose_name="additional notes")

    def __str__(self):
        return "Visit {} - {} {}".format(self.id, self.client.first_name, self.client.last_name)