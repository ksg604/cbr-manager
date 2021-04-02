import os
import sys
from pathlib import Path

sys.path.insert(0, os.path.dirname(os.getcwd()))  # need root director in path
import requests
from django.core.files.images import ImageFile

os.environ.setdefault('DJANGO_SETTINGS_MODULE',
                      'cbrsite.settings')
import django

django.setup()
from django.contrib.auth.models import User
from clients.factories import ClientFactory


def create_default_super_user(username, email, password, firstName, lastName):
    try:
        user = User.objects.create_superuser(username, email, password)
        user.first_name = firstName
        user.last_name = lastName
        user.save()
    except:
        pass


def create_random_clients(amount):
    Path("temp").mkdir(parents=True, exist_ok=True)
    # see more info on fields at https://randomuser.me/
    for _ in range(amount):
        create_random_client()


def create_random_client():
    profile = _get_random_profile()
    with open("temp/mock.jpg", mode="wb+") as jpg:
        jpg.write(_download_image(profile["picture"]["large"]))
        jpg.flush()
        jpg.seek(0)
        ClientFactory(photo=ImageFile(jpg))


def _get_random_profile():
    response = requests.get("https://randomuser.me/api/").json()
    return response["results"][0]


def _download_image(image_url):
    response = requests.get(image_url).content
    return response


if __name__ == '__main__':
    create_default_super_user("user1", "user1@email.com", "password123", "John", "Doe")
    create_default_super_user("user2", "user2@email.com", "password123", "Jane", "Doe")
    create_random_clients(2)
