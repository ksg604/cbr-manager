import random

import factory

from clients.models import Client

factory.Faker._DEFAULT_LOCALE = 'en_US'


class ClientFactory(factory.django.DjangoModelFactory):
    class Meta:
        model = Client

    class Params:
        profile = factory.Faker('simple_profile')

    first_name = factory.LazyAttribute(lambda o: o.profile['name'].split()[0])
    last_name = factory.LazyAttribute(lambda o: o.profile['name'].split()[1])
    village_no = factory.Faker('pyint', min_value=1, max_value=10)
    gender = factory.LazyAttribute(lambda o: o.profile['sex'])
    age = factory.Faker('pyint', min_value=20, max_value=50)
    photo = factory.django.ImageField(color='green')
    contact_care = factory.Faker('msisdn')
    contact_client = factory.Faker('msisdn')
    location = factory.Faker('city')
    health_risk = factory.Faker('pyint', min_value=1, max_value=10)
    education_risk = factory.Faker('pyint', min_value=1, max_value=10)
    social_risk = factory.Faker('pyint', min_value=1, max_value=10)
    disability = factory.LazyAttribute(lambda _: "disability" + str(random.randint(1, 10)))
    care_present = factory.LazyAttribute(lambda _: "yes" if random.randint(0, 10) % 2 else "no")
    consent = factory.LazyAttribute(lambda _: "yes" if random.randint(0, 10) % 2 else "no")
