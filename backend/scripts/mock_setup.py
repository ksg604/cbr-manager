import os
import random
from pathlib import Path

import requests
from django.core.files.images import ImageFile

os.environ.setdefault('DJANGO_SETTINGS_MODULE',
                      'cbrsite.settings')
import django
import time

django.setup()
from django.contrib.auth.models import User
from clients.models import Client


def generate_random_clients(amount):
    Path("temp").mkdir(parents=True, exist_ok=True)
    # see more info on fields at https://randomuser.me/
    for _ in range(amount):
        response = requests.get("https://randomuser.me/api/")
        json_response = response.json()

        info = json_response["results"][0]

        with open("temp/mock.jpg", mode="wb+") as jpg:
            jpg.write(download_image(info["picture"]["large"]))

            jpg.flush()

            jpg.seek(0)

            Client.objects.update_or_create(
                first_name=info["name"]["first"],
                last_name=info["name"]["last"],
                location=info["location"]["city"],
                consent="yes" if random.randint(0, 10) % 2 else "no",
                gender=info["gender"],
                care_present="yes" if random.randint(0, 10) % 2 else "no",
                disability="disability " + str(random.randint(1, 10)),
                health_risk=str(random.randint(1, 10)),
                education_risk=str(random.randint(1, 10)),
                social_risk=str(random.randint(1, 10)),
                photo=ImageFile(jpg)
            )
            time.sleep(1)


def create_default_super_user(username, email, password):
    try:
        User.objects.create_superuser(username, email, password)
    except:
        pass


def download_image(image_url):
    response = requests.get(image_url).content
    return response


if __name__ == '__main__':
    create_default_super_user("user1", "email@email.com", "password123")
    generate_random_clients(2)
