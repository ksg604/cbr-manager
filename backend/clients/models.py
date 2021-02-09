from django.db import models


# Create your models here.
class Client(models.Model):
    """
    The clients that get visited by CBR members
    """
    #Client Basic information
    first_name = models.CharField(max_length=30)
    last_name = models.CharField(max_length=30)
    location = models.CharField(max_length=100)
    gps_location = models.CharField(max_length=100, blank=True)
    consent = models.CharField(max_length=5)
    village_no = models.IntegerField(default=0)
    date = models.DateTimeField(auto_now_add=True)
    gender = models.CharField(max_length=20)
    age = models.IntegerField(default=0)
    contact_client = models.IntegerField(blank=True)
    care_present = models.CharField(max_length=5)
    contact_care = models.IntegerField(blank=True)
    photo = models.ImageField(upload_to='images/', default='default.png')
    disability = models.CharField(max_length=50)

    #Extra text field for Client information(Health/Education... etc)
    health_risk = models.CharField(max_length=30)
    health_require = models.TextField(blank=True)
    health_goal = models.TextField(blank=True)

    education_risk = models.CharField(max_length=30)
    education_require = models.TextField(blank=True)
    education_goal = models.TextField(blank=True)

    social_risk = models.CharField(max_length=30)
    social_require = models.TextField(blank=True)
    social_goal = models.TextField(blank=True)

    class Meta:
        ordering = ['id']
