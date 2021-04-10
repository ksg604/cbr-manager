import random

import factory

from alerts.models import Alert

factory.Faker._DEFAULT_LOCALE = 'en_US'


class AlertFactory(factory.django.DjangoModelFactory):
    class Meta:
        model = Alert

    title = factory.Faker("text", max_nb_chars=20)
    body = factory.Faker("text")
