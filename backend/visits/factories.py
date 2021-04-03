import factory
from django.contrib.auth.models import User
from factory.fuzzy import FuzzyChoice
from clients.models import Client
from visits.models import Visit
from faker import Faker

fake = Faker()


class VisitFactory(factory.django.DjangoModelFactory):
    class Meta:
        model = Visit

    user_creator = factory.Iterator(User.objects.all())
    client = factory.fuzzy.FuzzyChoice(Client.objects.all())

    cbr_worker_name = factory.LazyAttribute(lambda o: o.user_creator.first_name + " " + o.user_creator.last_name)

    location_drop_down = factory.Faker('text', max_nb_chars=50)

    village_no_visit = factory.Faker('pyint', min_value=1, max_value=10)
    location_visit_gps = factory.LazyAttribute(lambda _: str(fake.coordinate()))

    is_social_provision = factory.Faker('pybool')
    is_cbr_purpose = factory.Faker('pybool')

    wheelchair_health_provision = factory.Faker('pybool')
    wheelchair_health_provision_text = factory.LazyAttribute(
        lambda o: fake.text(max_nb_chars=50) if o.wheelchair_health_provision else '')

    prosthetic_health_provision = factory.Faker('pybool')
    prosthetic_health_provision_text = factory.LazyAttribute(
        lambda o: fake.text(max_nb_chars=50) if o.prosthetic_health_provision else '')

    orthotic_health_provision = factory.Faker('pybool')
    orthotic_health_provision_text = factory.LazyAttribute(
        lambda o: fake.text(max_nb_chars=50) if o.orthotic_health_provision else '')

    repairs_health_provision = factory.Faker('pybool')
    repairs_health_provision_text = factory.LazyAttribute(
        lambda o: fake.text(max_nb_chars=50) if o.repairs_health_provision else '')

    advice_social_provision = factory.Faker('pybool')
    advice_social_provision_text = factory.LazyAttribute(
        lambda o: fake.text(max_nb_chars=50) if o.advice_social_provision else '')

    advocacy_social_provision = factory.Faker('pybool')
    advocacy_social_provision_text = factory.LazyAttribute(
        lambda o: fake.text(max_nb_chars=50) if o.advocacy_social_provision else '')

    referral_social_provision = factory.Faker('pybool')
    referral_social_provision_text = factory.LazyAttribute(
        lambda o: fake.text(max_nb_chars=50) if o.referral_social_provision else '')
