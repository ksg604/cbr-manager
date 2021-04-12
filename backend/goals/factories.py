import factory
from django.contrib.auth.models import User
from django.utils import timezone
from factory.fuzzy import FuzzyChoice

from clients.models import Client
from goals.models import Goal
import random

factory.Faker._DEFAULT_LOCALE = 'en_US'


class GoalFactory(factory.django.DjangoModelFactory):
    class Meta:
        model = Goal

    class Params:
        is_completed = random.choices([True, False])

    datetime_completed = factory.lazy_attribute(lambda o: timezone.now() if o.is_completed else None)
    user_creator = factory.fuzzy.FuzzyChoice(User.objects.all())
    client_id = factory.fuzzy.FuzzyChoice(Client.objects.all(), lambda client: client.id)
    category = factory.fuzzy.FuzzyChoice(["Health", "Education", "Social"])
    title = factory.Faker("sentence", nb_words=2)
    description = factory.Faker("sentence", nb_words=10)
    is_initial_goal = factory.fuzzy.FuzzyChoice([True, False])
    status = factory.lazy_attribute(lambda o: "Completed" if o.is_completed else "Ongoing")
