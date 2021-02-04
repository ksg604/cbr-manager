
from django.urls import path, include
from .views import UserViewSet

urlpatterns = [
    path('users/', UserViewSet.as_view()),
    path('users/<int:id>/', UserViewSet.as_view()),
]
