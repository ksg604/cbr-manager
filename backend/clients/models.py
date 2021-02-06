from django.db import models


# Create your models here.
class Client(models.Model):
    """
    The clients that get visited by CBR members
    """
    first_name = models.CharField(max_length=30)
    last_name = models.CharField(max_length=30)
    location = models.CharField(max_length=100)
    consent = models.CharField(max_length=5)
    village_no = models.IntegerField(default=0)
    date = models.DateTimeField(auto_now_add=True)
    gender = models.CharField(max_length=20)
    age = models.IntegerField(default=0)
    contact_client = models.IntegerField(default=0)
    care_present = models.CharField(max_length=5)
    contact_care = models.IntegerField(default=0)
    disability = models.CharField(max_length=50)

    class Meta:
        ordering = ['id']
