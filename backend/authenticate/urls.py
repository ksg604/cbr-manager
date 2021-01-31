from django.urls import path
from rest_framework.authtoken import views as auth_view

app_name = 'authenticate'
urlpatterns = [
    path('token-auth/', auth_view.obtain_auth_token, name="token-auth")
]
