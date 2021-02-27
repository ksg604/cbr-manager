from django.db import models


class InjuryLocation(models.TextChoices):
    BELOW = 'Below'
    ABOVE = 'Above'


class UsageExperience(models.TextChoices):
    BASIC = 'Basic',
    INTERMEDIATE = 'Intermediate'


class Conditions(models.TextChoices):
    AMPUTEE = 'Amputee',
    POLIO = 'Polio',
    SPINAL_CORD_INJURY = 'Spinal Cord Injury'
    CEREBRAL_PALSY = 'Cerebral Palsy'
    SPINA_BIFIDA = 'Spina Bifida'
    HYDROCEPHALUS = 'Hydrocephalus'
    VISUAL_IMPAIRMENT = 'Visual Impairment'
    HEARING_IMPAIRMENT = 'Hearing Impairment'
    OTHER = 'Other'


class ReferralStatus(models.TextChoices):
    CREATED = 'CREATED'
    RESOLVED = 'RESOLVED'


class ServiceTypes(models.TextChoices):
    WHEELCHAIR = "Wheelchair"
    PHYSIOTHERAPY = "Physiotherapy"
    PROSTHETIC = "Prosthetic"
    ORTHOTIC = "Orthotic"
