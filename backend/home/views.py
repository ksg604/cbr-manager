import glob
import os

from django.http import HttpResponse
from django.shortcuts import render

from cbrsite.settings import MEDIA_ROOT


def home_view(request):
    apk_dir = os.path.join(MEDIA_ROOT, 'apk/*.apk')

    file_list = sorted(glob.glob(apk_dir), reverse=True)

    return render(request, 'home.html', {'files': file_list})


def download(request, file_path):
    if os.path.exists(file_path):
        with open(file_path, 'rb') as fh:
            # https://docs.djangoproject.com/en/3.1/ref/request-response/#telling-the-browser-to-treat-the-response-as-a-file-attachment
            response = HttpResponse(fh.read(), content_type="application/vnd.ms-excel")
            response['Content-Disposition'] = 'inline; filename=' + os.path.basename(file_path)
            return response
    return HttpResponse(status=404)
