# Create your api views here.
from rest_framework import viewsets, renderers
from rest_framework.decorators import action
from rest_framework.generics import get_object_or_404
from rest_framework.response import Response

from clients.models import Client
from clients.serializer import ClientSerializer


class ClientViewSet(viewsets.ModelViewSet):
    serializer_class = ClientSerializer

    def get_queryset(self):
        queryset = Client.objects.all()

        order_by = self.request.query_params.get("ordering", None)

        result_limit = self.request.query_params.get("limit", None)

        if order_by is not None:
            queryset = queryset.order_by(order_by)

        if result_limit is not None:
            queryset = queryset[:int(result_limit)]

        return queryset

    @action(detail=True, methods=['post'])
    def upload(self, request, pk):
        client = get_object_or_404(Client, pk=pk)
        image_data = request.FILES['photo']

        client.photo.save(image_data.name, image_data)
        return Response({
            "url": client.photo.url
        })
