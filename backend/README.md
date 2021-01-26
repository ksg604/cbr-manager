# Project Description

Django backend as a rest api

# Project Setup

## Pycharm

It is recommended to use Pycharm by Jetbrains. We can get student licenses from https://www.jetbrains.com/community/education/#students

### Pycharm Setup

You will need to enable Django support in Pycharm to resolve import errors in the text editor

Follow: https://docs.google.com/document/d/15i48eRUt0l_3OOw7ONuFUBepGm8yF1OqGomtbdjO9rs

## Python

Django backend was setup with Python 3.9

## Dependencies

Install the dependencies from the requirements file so we all run the same version of Django

`pip install -r requirements.txt`

# Development

## Migrations

Make sure you have all the proper migrations ran before starting the development server

1. `python manage.py makemigrations`
2. `python manage.py migrate`

## Run development server

Run the following command

`python manage.py runserver`

The default site url is currently http://127.0.0.1:8000/ by default (at the least for Windows)

## Create a super user

If you want to access the site admin to see all the data in the models you'll need a super user

Run the following command and answer the prompts

`python manage.py createsuperuser`

Visit http://127.0.0.1:8000/admin and login

# Temporary Rest Example

I have created a Clients apps under `clients`, and a `Client` model with two fields `first_name` and `last_name`

With the server running we can visit http://127.0.0.1:8000/api/clients/ to list all clients

The /api/clients route is defined in cbrsite/urls.py

The implementation exists in clients/views_api.py

I used a ViewSet that automatically defines all CRUD operations and endpoints to the Client model

The clients/serializer.py defines how the `Client` model gets converted or serialized to JSON

Visit http://127.0.0.1:8000/api/clients/ to create a client or retrieve all clients

or http://127.0.0.1:8000/api/clients/1 to get only get the client of id=1

Alternatively you can also manually define the behaviour of the api through [Views](https://www.django-rest-framework.org/api-guide/views/) or further specify the ViewSet behavior.

You can use Postman or any http request method at this endpoint to do CRUD operations too
