from django.db import models


class GeneralHealth(models.TextChoices):
    VERY_POOR = "Very Poor"
    POOR = "Poor"
    FINE = "Fine"
    GOOD = "GOOD"


class AssistiveDevice(models.TextChoices):
    WHEELCHAIR = "Wheelchair"
    PROSTHETIC = "Prosthetic"
    ORTHOTIC = "Orthotic"
    Crutch = "Crutch"
    WALKING_STICK = "Walking Stick"
    HEARING_AID = "Hearing Aid"
    GLASSES = "Glasses"
    STANDING_FRAME = "Standing Frame"
    CORNER_SEAT = "Corner Seat"


class HealthServiceSatisfaction(models.TextChoices):
    VERY_UNSATISFIED = "Very Unsatisfied"
    UNSATISFIED = "Unsatisfied"
    SATISFIED = "Satisfied"
    VERY_SATISFIED = "Very Satisfied"


class NoSchoolReason(models.TextChoices):
    LACK_OF_FUNDING = "Lack of Funding"
    DISABILITY_STOPS = "My Disability Stops Me"
    OTHER = "Other"


class ChildNourishment(models.TextChoices):
    MALNOURISHED = "Malnourished"
    UNDERNOURISHED = "Undernourished"
    WELL_NOURISHED = "Well Nourished"
