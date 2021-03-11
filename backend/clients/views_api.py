# Create your api views here.
import django_filters
from rest_framework import viewsets
from rest_framework.decorators import action
from rest_framework.generics import get_object_or_404
from rest_framework.response import Response

from clients.models import Client, ClientHistoryRecord
from clients.serializer import ClientSerializer, ClientHistoryRecordSerializer


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

    @action(detail=True, methods=['get'], name='Get Client History')
    def history(self, request, pk):
        """
        Endpoint supports filtering changes by field_name
        Ex. api/client/1/history/?field=first_name
        """

        def filter_history(query_params):
            filter_field_by = query_params.get('field')
            return ClientHistoryRecord.objects.filter(client=pk, field=filter_field_by)

        if request.query_params:
            client_history_records = filter_history(request.query_params)
        else:
            client_history_records = ClientHistoryRecord.objects.filter(client=pk)

        client_history_json = ClientHistoryRecordSerializer(client_history_records, many=True).data
        return Response(client_history_json)
