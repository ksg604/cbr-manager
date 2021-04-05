"""cbrsite URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.1/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.conf.urls.static import static
from django.contrib import admin
from django.urls import path, include
from rest_framework import routers

from django.conf import settings

from authenticate.views import CustomObtainToken
from clients.views_api import ClientViewSet
from home.views import home_view, download
from referral.views import ReferralViewSet
from sync.views import StatusViewSet
from users.views import UserViewSet
from visits.views import VisitViewSet
from alerts.views import AlertViewSet
from baseline_survey.views import BaselineSurveyViewSet
from goals.views import GoalViewSet

router = routers.DefaultRouter()

# Register additional api url endpoints here
router.register(r'clients', ClientViewSet, basename="Client")
router.register(r'users', UserViewSet, basename="User")
router.register(r'visits', VisitViewSet, basename='Visit')
router.register(r'alerts', AlertViewSet)
router.register(r'referrals', ReferralViewSet, basename='Referral')
router.register(r'surveys', BaselineSurveyViewSet, basename='Survey')
router.register(r'goals', GoalViewSet, basename='Goal')

urlpatterns = \
    [
        path('', home_view),
        path('download/<path:file_path>/', download),
        path('admin/', admin.site.urls),
        path('api/token-auth/', CustomObtainToken.as_view(), name='token-auth'),
        path('api/status/', StatusViewSet.as_view()),
        path('api/', include(router.urls)),

] + static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT) + static(settings.STATIC_URL,
                                                                           document_root=settings.STATIC_ROOT)
