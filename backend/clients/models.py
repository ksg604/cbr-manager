from django.db import models


# Create your models here.
class Client(models.Model):
    """
    The clients that get visited by CBR members
    """
    first_name = models.CharField(max_length=30)
    last_name = models.CharField(max_length=30)
