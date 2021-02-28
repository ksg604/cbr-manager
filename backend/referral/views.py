from rest_framework.decorators import action
from rest_framework.generics import get_object_or_404
from rest_framework.response import Response
from rest_framework.viewsets import ModelViewSet

from referral.models import Referral
from referral.serializer import ReferralSerializer


class ReferralViewSet(ModelViewSet):
    serializer_class = ReferralSerializer

    def get_queryset(self):
        queryset = Referral.objects.all()
        return queryset

    @action(detail=True, methods=['post'])
    def upload(self, request, pk):
        referral = get_object_or_404(Referral, pk=pk)

        photo = request.FILES['photo']

        referral.photo.save(photo.name, photo)

        return Response({
            'url': request.build_absolute_uri(referral.photo.url)
        })
