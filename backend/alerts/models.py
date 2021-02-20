from django.db import models

# Create your models here.



class Alert(models.Model):
    title = models.TextField(max_length=200, blank=True, null=True)
    body = models.TextField(blank=True, null=True)
    date = models.DateTimeField(auto_now_add=True)

    class Meta:
        ordering = ['-date']