from django.db import models


class Client(models.Model):
    """
    The clients that get visited by CBR members
    """
    # Client Basic information
    cbr_client_id = models.CharField(unique=True, editable=False, max_length=10)  # auto generated

    first_name = models.CharField(max_length=30)
    last_name = models.CharField(max_length=30)
    location = models.CharField(max_length=100)
    gps_location = models.CharField(max_length=100, blank=True)
    consent = models.CharField(max_length=5)
    village_no = models.IntegerField(default=0)
    gender = models.CharField(max_length=20)
    age = models.IntegerField(default=0)
    contact_client = models.CharField(max_length=20)
    care_present = models.CharField(max_length=5)
    contact_care = models.CharField(max_length=20)
    photo = models.ImageField(upload_to='images/', default='images/default.png')
    disability = models.CharField(max_length=250)
    date = models.DateTimeField(auto_now_add=True)
    # Extra text field for Client information(Health/Education... etc)
    health_risk = models.IntegerField(default=0)
    health_require = models.TextField(blank=True)
    health_goal = models.TextField(blank=True)

    education_risk = models.IntegerField(default=0)
    education_require = models.TextField(blank=True)
    education_goal = models.TextField(blank=True)

    social_risk = models.IntegerField(default=0)
    social_require = models.TextField(blank=True)
    social_goal = models.TextField(blank=True)

    risk_score = models.IntegerField(editable=False, default=0)

    # Extra fields checking Sync states
    last_modified = models.DateTimeField(auto_now=True)
    is_new_client = models.BooleanField(blank=True, default=False)

    # Fields updated by visits
    goal_met_health_provision = models.TextField(blank=True, max_length=30)
    goal_met_education_provision = models.TextField(blank=True, max_length=30)
    goal_met_social_provision = models.TextField(blank=True, max_length=30)

    class Meta:
        ordering = ['id']

    def __str__(self):
        return "{} {}".format(self.first_name, self.last_name)

    def save(self, *args, **kwargs):
        self.risk_score = int(self.health_risk) + int(self.social_risk) + int(self.education_risk)

        if not self.cbr_client_id:
            self.cbr_client_id = self._generate_cbr_client_id()

        super(Client, self).save(*args, **kwargs)

    def _generate_cbr_client_id(self):
        def get_first_letter(text):
            if text and len(text) > 0:
                return text[0]
            return ""

        def append_number_if_not_one(text, num):
            if num != 1:
                return text + str(num)
            return text

        candidate_number = 1
        candidate_id = get_first_letter(self.first_name).lower() + get_first_letter(self.last_name).lower()
        while Client.objects.filter(cbr_client_id=append_number_if_not_one(candidate_id, candidate_number)).exists():
            candidate_number += 1

        return append_number_if_not_one(candidate_id, candidate_number)


class ClientHistoryRecord(models.Model):
    date_created = models.DateTimeField(auto_now_add=True, editable=False)
    field = models.CharField(max_length=100, editable=False)
    old_value = models.TextField(editable=False)
    new_value = models.TextField(editable=False)
    client = models.ForeignKey(Client, on_delete=models.CASCADE, editable=False)
