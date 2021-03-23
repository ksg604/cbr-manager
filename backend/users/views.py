from django.contrib.auth.models import User
from rest_framework import viewsets
from rest_framework.decorators import action
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response

from .serializers import UserSerializer


class UserViewSet(viewsets.ModelViewSet):
    serializer_class = UserSerializer
    permission_classes = [IsAuthenticated]

    @action(detail=False, methods=["get"], name="Get Current User", permission_classes=[IsAuthenticated])
    def current_user(self, request):
        serialized_user = self.serializer_class(request.user)
        return Response(serialized_user.data)

    def get_queryset(self):
        return User.objects.all()
